package chat.server;

public class Main {
    public static void main(String[] args) {
        ChatServer server = new ChatServer(9876);
        server.start();
    }
}