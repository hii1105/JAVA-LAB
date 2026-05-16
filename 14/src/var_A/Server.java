//1 задание
//1 вар
// Создать на основе сокетов клиент/серверное приложение:
//1. Клиент посылает через сервер сообщение другому клиенту, выбранному из списка.

package var_A;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Server {
    //список всех клиентов (потокобезопасный)
    private static List<ClientHandler> clients = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        int port = 12345;
        System.out.println("Сервер запущен на порту " + port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Новый клиент подключился: " + clientSocket.getRemoteSocketAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket, clients);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String clientName;
    private List<ClientHandler> allClients;

    public ClientHandler(Socket socket, List<ClientHandler> allClients) {
        this.socket = socket;
        this.allClients = allClients;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            //получить имя клиента
            clientName = in.readLine();
            if (clientName == null || clientName.trim().isEmpty()) {
                return; // отключился
            }
            System.out.println("Клиент " + clientName + " зарегистрирован.");

            //отправить новому клиенту список всех имён (для выбора получателя)
            sendClientList();

            //оповестить остальных о новом пользователе
            broadcast("Система: " + clientName + " присоединился к чату.", this);

            //основной цикл обработки сообщений от клиента
            String message;
            while ((message = in.readLine()) != null) {
                String[] parts = message.split(":", 2);
                if (parts.length == 2) {
                    String recipient = parts[0];
                    String text = parts[1];
                    sendPrivateMessage(recipient, text);
                } else {
                    out.println("Неверный формат. Используйте: имя_получателя: сообщение");
                }
            }
        } catch (IOException e) {
            System.out.println("Клиент " + clientName + " отключился.");
        } finally {
            allClients.remove(this);
            broadcast("Система: " + clientName + " покинул чат.", null);
            try { socket.close(); } catch (IOException ignored) {}
        }
    }

    private void sendPrivateMessage(String recipientName, String text) {
        ClientHandler recipient = findClientByName(recipientName);
        if (recipient != null) {
            recipient.out.println(clientName + " (личное): " + text);
            out.println(">>> Сообщение отправлено пользователю " + recipientName);
        } else {
            out.println("Ошибка: пользователь " + recipientName + " не найден.");
        }
    }

    private ClientHandler findClientByName(String name) {
        for (ClientHandler client : allClients) {
            if (client.clientName.equalsIgnoreCase(name)) {
                return client;
            }
        }
        return null;
    }

    private void broadcast(String message, ClientHandler exclude) {
        for (ClientHandler client : allClients) {
            if (client != exclude) {
                client.out.println(message);
            }
        }
    }

    private void sendClientList() {
        StringBuilder sb = new StringBuilder("Список подключённых клиентов:\n");
        for (ClientHandler client : allClients) {
            sb.append("  • ").append(client.clientName).append("\n");
        }
        out.println(sb.toString());
    }
}