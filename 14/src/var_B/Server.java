//2 задание
//1 вар
// Игра по сети в «Морской бой»
package var_B;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 12345;
    private static final int SIZE = 5;
    private static final int[] SHIPS = {3, 2, 2, 1};

    private int[][] myField;
    private int[][] enemyField;
    private int myShipsAlive;
    private int enemyShipsAlive;
    private Random rand = new Random();

    public static void main(String[] args) {
        new Server().start();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен. Ожидаем клиента...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Клиент подключился: " + clientSocket.getRemoteSocketAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            initFields();
            out.println(SIZE);

            boolean isServerTurn = true; //сервер ходит первым

            while (myShipsAlive > 0 && enemyShipsAlive > 0) {
                if (isServerTurn) {
                    System.out.println("\nХод сервера:");
                    printMyField();
                    int[] coords = getRandomMove();
                    int row = coords[0];
                    int col = coords[1];
                    System.out.printf("Сервер стреляет в %d,%d\n", row, col);
                    out.println(row + "," + col); //отправляем выстрел клиенту
                    String result = in.readLine(); //ждём hit или miss
                    if ("hit".equals(result)) {
                        System.out.println("Попадание!");
                        enemyField[row][col] = 2;
                        enemyShipsAlive--;
                        if (enemyShipsAlive == 0) break;
                        //продолжаем ход сервера
                    } else {
                        System.out.println("Промах!");
                        enemyField[row][col] = 1;
                        isServerTurn = false;  //ход переходит клиенту
                        out.println("YOUR_TURN");  //сообщаем клиенту
                    }
                } else {
                    System.out.println("\nОжидание хода клиента");
                    String shot = in.readLine();  //получаем координаты от клиента
                    if (shot == null) break;
                    String[] parts = shot.split(",");
                    int row = Integer.parseInt(parts[0]);
                    int col = Integer.parseInt(parts[1]);
                    System.out.printf("Клиент стреляет в %d,%d\n", row, col);
                    if (myField[row][col] == 1) {
                        myField[row][col] = 2;
                        myShipsAlive--;
                        out.println("hit");
                        if (myShipsAlive == 0) break;
                        //клиент продолжает ход
                        out.println("YOUR_TURN");
                    } else {
                        out.println("miss");
                        isServerTurn = true;
                    }
                }
            }

            //объявление победителя
            if (myShipsAlive == 0) {
                System.out.println("Вы проиграли!");
                out.println("GAME_OVER:lose");
            } else {
                System.out.println("Вы победили!");
                out.println("GAME_OVER:win");
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initFields() {
        myField = new int[SIZE][SIZE];
        enemyField = new int[SIZE][SIZE];
        placeShips(myField);
        placeShips(enemyField);
        myShipsAlive = countShipCells(myField);
        enemyShipsAlive = countShipCells(enemyField);
    }

    private void placeShips(int[][] field) {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                field[i][j] = 0;
        for (int shipSize : SHIPS) {
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

    private int[] getRandomMove() {
        while (true) {
            int row = rand.nextInt(SIZE);
            int col = rand.nextInt(SIZE);
            if (enemyField[row][col] == 0) {
                return new int[]{row, col};
            }
        }
    }

    private void printMyField() {
        System.out.println("Ваше поле (сервер):");
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
}