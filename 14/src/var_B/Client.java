package var_B;
import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    private static final int PORT = 12345;
    private static final int SIZE = 5;

    private int[][] myField;
    private int[][] enemyField;
    private int myShipsAlive;
    private Random rand = new Random();

    public static void main(String[] args) {
        new Client().start();
    }

    public void start() {
        try (Socket socket = new Socket("localhost", PORT)) {
            System.out.println("Подключён к серверу.");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            int fieldSize = Integer.parseInt(in.readLine());
            if (fieldSize != SIZE) {
                System.out.println("Несовпадение размера поля.");
                return;
            }

            initFields();

            boolean gameOver = false;
            while (!gameOver) {
                String line = in.readLine();
                if (line == null) break;

                if (line.startsWith("GAME_OVER")) {
                    System.out.println(line.equals("GAME_OVER:win") ? "Вы победили!" : "Вы проиграли.");
                    gameOver = true;
                    break;
                }

                if (line.contains(",")) {
                    //выстрел сервера
                    String[] parts = line.split(",");
                    int row = Integer.parseInt(parts[0]);
                    int col = Integer.parseInt(parts[1]);
                    System.out.printf("Сервер выстрелил в %d,%d\n", row, col);
                    if (myField[row][col] == 1) {
                        System.out.println("Попадание по вашему кораблю!");
                        myField[row][col] = 2;
                        myShipsAlive--;
                        out.println("hit");
                        if (myShipsAlive == 0) {
                            //игра закончится после отправки hit, сервер пришлёт GAME_OVER
                        }
                    } else {
                        System.out.println("Промах!");
                        out.println("miss");
                    }
                } else if (line.equals("YOUR_TURN")) {
                    // Ваш ход
                    System.out.println("Ваш ход!");
                    printMyField();
                    printEnemyField();
                    int[] coords = getClientMove();
                    out.println(coords[0] + "," + coords[1]);
                    String result = in.readLine(); //hit или miss от сервера
                    if ("hit".equals(result)) {
                        System.out.println("Попадание!");
                        enemyField[coords[0]][coords[1]] = 2;
                        //сервер после попадания сразу отправит снова YOUR_TURN
                    } else {
                        System.out.println("Промах!");
                        enemyField[coords[0]][coords[1]] = 1;
                        //ход переходит серверу, YOUR_TURN не будет
                    }
                }
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initFields() {
        myField = new int[SIZE][SIZE];
        enemyField = new int[SIZE][SIZE];
        placeShips(myField);
        myShipsAlive = countShipCells(myField);
    }

    private void placeShips(int[][] field) {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                field[i][j] = 0;
        int[] ships = {3, 2, 2, 1};
        for (int shipSize : ships) {
            boolean placed = false;
            while (!placed) {
                boolean horizontal = rand.nextBoolean();
                int row = rand.nextInt(SIZE);
                int col = rand.nextInt(SIZE);
                if (canPlace(field, row, col, shipSize, horizontal)) {
                    for (int i = 0; i < shipSize; i++) {
                        if (horizontal) field[row][col + i] = 1;
                        else field[row + i][col] = 1;
                    }
                    placed = true;
                }
            }
        }
    }

    private boolean canPlace(int[][] field, int row, int col, int size, boolean horizontal) {
        if (horizontal) {
            if (col + size > SIZE) return false;
            for (int i = 0; i < size; i++)
                if (field[row][col + i] != 0) return false;
        } else {
            if (row + size > SIZE) return false;
            for (int i = 0; i < size; i++)
                if (field[row + i][col] != 0) return false;
        }
        return true;
    }

    private int countShipCells(int[][] field) {
        int count = 0;
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (field[i][j] == 1) count++;
        return count;
    }

    private void printMyField() {
        System.out.println("Ваши корабли:");
        System.out.print("  ");
        for (int j = 0; j < SIZE; j++) System.out.print(j + " ");
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < SIZE; j++) {
                char c = (myField[i][j] == 1) ? '■' : (myField[i][j] == 2) ? 'X' : '·';
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

    private void printEnemyField() {
        System.out.println("Поле противника (результаты выстрелов):");
        System.out.print("  ");
        for (int j = 0; j < SIZE; j++) System.out.print(j + " ");
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < SIZE; j++) {
                char c = (enemyField[i][j] == 0) ? '?' : (enemyField[i][j] == 1) ? '○' : 'X';
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

    private int[] getClientMove() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Введите координаты (строка столбец, через пробел): ");
            String input = scanner.nextLine();
            String[] parts = input.split(" ");
            if (parts.length == 2) {
                try {
                    int row = Integer.parseInt(parts[0]);
                    int col = Integer.parseInt(parts[1]);
                    if (row >= 0 && row < SIZE && col >= 0 && col < SIZE && enemyField[row][col] == 0) {
                        return new int[]{row, col};
                    } else {
                        System.out.println("Клетка уже прострелена или неверные координаты.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Неверный ввод. Введите два числа.");
                }
            } else {
                System.out.println("Введите два числа через пробел.");
            }
        }
    }
}