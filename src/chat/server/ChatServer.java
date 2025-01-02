package chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private final int port;
    private final ExecutorService virtualThreadExecutor;

    public ChatServer(int port) {
        this.port = port;
        this.virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            System.out.printf("Chat Server %s is running\n", serverSocket);

            while (true) {
                ClientHandler client = new ClientHandler(serverSocket.accept());
                this.virtualThreadExecutor.submit(client);
            }
        } catch (IOException e) {
            System.err.println("Error starting the server: " + e.getMessage());
        } finally {
            System.out.println("Shutting down the server...");
            this.virtualThreadExecutor.shutdown();
        }
    }
}