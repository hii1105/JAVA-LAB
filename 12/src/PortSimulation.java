//глава 12
//Вариант А
// 1 вариант
//Разработать многопоточное приложение. Использовать возможности, предоставляемые пакетом java.util.concurrent. Не использовать слово synchronized.
//Все сущности, желающие получить доступ к ресурсу, должны быть потоками.
//Порт. Корабли заходят в порт для разгрузки/загрузки контейнеров. Число контейнеров, находящихся в текущий момент в порту и на корабле, должно
//быть неотрицательным и превышающим заданную грузоподъемность судна и вместимость порта. В порту работает несколько причалов.
// У одного причала может стоять один корабль. Корабль может загружаться у причала, разгружаться или выполнять оба действия.

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class PortSimulation {

    public static void main(String[] args) {
        int portCapacity = 100;//вместимость порта
        int berthCount = 3;//количество причалов

        Port port = new Port(portCapacity, berthCount);

        Ship[] ships = {
            new Ship(port, "Танкер", 30, 10, Action.LOAD), //загрузить 10 контейнеров
            new Ship(port, "Контейнеровоз", 50, 20, Action.UNLOAD), //разгрузить 20
            new Ship(port, "Балкер", 40, 15, Action.BOTH), //и загрузить, и разгрузить
            new Ship(port, "Рефрижератор", 25, 5, Action.LOAD),
            new Ship(port, "Ролкер", 60, 30, Action.UNLOAD)
        };

        //запускаем корабли в отдельных потоках
        ExecutorService executor = Executors.newFixedThreadPool(ships.length);
        for (Ship ship : ships) {
            executor.execute(ship);
        }

        //даём время на выполнение, а затем завершаем
        executor.shutdown();
        try {
            if (!executor.awaitTermination(2, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        System.out.println("\nРабота завершена!");
        System.out.println("Итоговое состояние порта: " + port);
    }
}

enum Action {
    LOAD, //только загрузка (порт -> корабль)
    UNLOAD, //только разгрузка (корабль -> порт)
    BOTH //сначала разгрузка, потом загрузка
}

class Port {
    private final int capacity; //макс количество контейнеров в порту
    private int containers; //тек количество контейнеров
    private final Semaphore berths; //причалы
    private final Lock lock = new ReentrantLock(); //блокировка для изменения containers
    private final Condition spaceAvailable = lock.newCondition(); //для ожидания места
    private final Condition goodsAvailable = lock.newCondition(); // для ожидания груза

    public Port(int capacity, int berthCount) {
        this.capacity = capacity;
        this.containers = capacity / 2; //нач количество контейнеров
        this.berths = new Semaphore(berthCount, true); //честный семафор
    }

    public void loadShip(int count, String shipName) throws InterruptedException {
        lock.lock();
        try {
            while (containers < count) {
                System.out.printf("%s: ждёт %d контейнеров (в порту только %d)%n",
                        shipName, count, containers);
                goodsAvailable.await(); //ждём появления груза
            }
            containers -= count;
            System.out.printf("%s: загружено %d конт. Осталось в порту: %d%n",
                    shipName, count, containers);
            spaceAvailable.signalAll(); //уведомляем тех, кто ждёт место
        } finally {
            lock.unlock();
        }
    }

    public void unloadShip(int count, String shipName) throws InterruptedException {
        lock.lock();
        try {
            while (containers + count > capacity) {
                System.out.printf("%s: не хватает места для %d конт. (свободно %d)%n",
                        shipName, count, capacity - containers);
                spaceAvailable.await(); //ждём появления места
            }
            containers += count;
            System.out.printf("%s: разгружено %d конт. В порту стало: %d%n",
                    shipName, count, containers);
            goodsAvailable.signalAll(); //уведомляем тех, кто ждёт груз
        } finally {
            lock.unlock();
        }
    }

    //занять причал
    public void occupyBerth(String shipName) throws InterruptedException {
        System.out.printf("%s: ожидает свободный причал...%n", shipName);
        berths.acquire();
        System.out.printf("%s: занял причал (свободно %d из %d)%n",
                shipName, berths.availablePermits(), berths.availablePermits() + 1);
    }

    //освободить причал
    public void releaseBerth(String shipName) {
        berths.release();
        System.out.printf("%s: освободил причал (свободно %d)%n",
                shipName, berths.availablePermits());
    }

    public int getContainers() {
        return containers;
    }

    @Override
    public String toString() {
        return String.format("Порт: %d контейнеров из %d", containers, capacity);
    }
}

class Ship implements Runnable {
    private final Port port;
    private final String name;
    private final int capacity; //грузоподъёмность корабля
    private int cargo; //тек количество контейнеров на борту
    private final Action action; //тип операции

    public Ship(Port port, String name, int capacity, int initialCargo, Action action) {
        this.port = port;
        this.name = name;
        this.capacity = capacity;
        this.cargo = Math.min(initialCargo, capacity);
        this.action = action;
    }

    @Override
    public void run() {
        try {
            System.out.printf("%s: вошёл в порт (груз %d/%d)%n", name, cargo, capacity);
            port.occupyBerth(name);

            //выполнить операции
            if (action == Action.UNLOAD) {
                //разгрузить все контейнеры (не более грузоподъёмности)
                int toUnload = cargo;
                if (toUnload > 0) {
                    port.unloadShip(toUnload, name);
                    cargo -= toUnload;
                }
            } else if (action == Action.LOAD) {
                //загрузить до полной грузоподъёмности
                int toLoad = capacity - cargo;
                if (toLoad > 0) {
                    port.loadShip(toLoad, name);
                    cargo += toLoad;
                }
            } else { //сначала разгружаем, потом загружаем
                int toUnload = cargo;
                if (toUnload > 0) {
                    port.unloadShip(toUnload, name);
                    cargo -= toUnload;
                }
                int toLoad = capacity - cargo;
                if (toLoad > 0) {
                    port.loadShip(toLoad, name);
                    cargo += toLoad;
                }
            }

            //освободить причал
            port.releaseBerth(name);

            System.out.printf("%s: покидает порт (груз %d/%d)%n", name, cargo, capacity);
        } catch (InterruptedException e) {
            System.out.printf("%s: прерван – %s%n", name, e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}