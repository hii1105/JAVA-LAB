package var_A;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Scanner console = new Scanner(System.in);

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    public void start() {
        try {
            socket = new Socket("localhost", 12345);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            //поток для чтения сообщений от сервера
            Thread receiver = new Thread(() -> {
                try {
                    String line;
                    while ((line = in.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    System.out.println("Соединение с сервером потеряно.");
                }
            });
            receiver.start();

            //ввод имени
            System.out.print("Введите ваше имя: ");
            String name = console.nextLine();
            out.println(name);

            //основной цикл отправки сообщений
            while (true) {
                System.out.print("\nВведите получателя и сообщение (формат: имя: текст). Для выхода введите exit \n");
                String input = console.nextLine();
                if (input.equalsIgnoreCase("exit")) {
                    break;
                }
                out.println(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) socket.close();
            } catch (IOException ignored) {}
        }
    }
}