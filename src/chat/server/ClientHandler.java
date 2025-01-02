package chat.server;

import chat.server.exceptions.ClientConnectionInterruptedException;
import chat.server.exceptions.UnknownCommandException;
import chat.server.exceptions.UnknownRecipientException;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    private String clientName = "Anonymous";

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
    }

    @Override
    public void run() {
        try {
            this.showWelcomeMessage();

            this.out.println("\nPlease enter your name: ");
            this.clientName = in.readLine();
            this.out.println("\nWelcome, " + this.clientName + "!\n");

            ClientRegistry.register(this);

            while (true) {
                String clientMessage = in.readLine();
                if (clientMessage != null) {
                    System.out.println("Received message from client: " + clientMessage);

                    String[] messageParts = clientMessage.trim().split(" ", 2);
                    String command = messageParts[0];
                    String argument = messageParts.length > 1 ? messageParts[1] : "";

                    try {
                        switch (command) {
                            case "/exit" -> throw new ClientConnectionInterruptedException();
                            case "/send" -> ClientRegistry.broadcast(this, argument);
                            case "/list" -> this.out.println("Connected clients: " + ClientRegistry.getRegisteredClientsAsString());
                            case "/private" -> {
                                String[] privateMessageParts = argument.trim().split(" ", 2);
                                String recipientName = privateMessageParts[0];
                                String privateMessage = privateMessageParts.length > 1 ? privateMessageParts[1] : "";

                                ClientHandler recipient = ClientRegistry.getRegisteredClients().stream()
                                        .filter(client -> client.getClientName().equals(recipientName))
                                        .findFirst().orElseThrow(() -> new UnknownRecipientException(recipientName));

                                ClientRegistry.sendPrivate(this, recipient, privateMessage);
                            }
                            default -> throw new UnknownCommandException(command);
                        }
                    } catch (UnknownCommandException e) {
                        this.out.println(e.getMessage());
                        this.showAvailableCommands();
                    } catch (UnknownRecipientException e) {
                        this.out.println(e.getMessage());
                    }

                    this.out.println("\n");
                }
            }
        } catch (ClientConnectionInterruptedException e) {
            this.out.println("Goodbye, " + this.clientName + "!");
        }
        catch (IOException e) {
            System.err.println("Connection with client interrupted: " + e.getMessage());
        } finally {
            ClientRegistry.unregister(this);
            this.closeConnection();
        }
    }

    public String getClientName() {
        return this.clientName;
    }

    public void sendMessage(String message) {
        this.out.println(message);
    }

    private void showWelcomeMessage() {
        this.out.println("Welcome to the Chat Server!");
        this.out.println("----------------------------------");
        this.showAvailableCommands();
        this.out.println("----------------------------------");
    }

    private void showAvailableCommands() {
        this.out.println("Please use one of the following commands:");
        this.out.println("  /exit            - Leave the chat");
        this.out.println("  /send <message>  - Send a message to all connected clients");
        this.out.println("  /list            - List all connected clients");
        this.out.println("  /private <name> <message> - Send a private message to a client");
    }

    private void closeConnection() {
        try {
            this.in.close();
            this.out.close();
            this.socket.close();
        } catch (IOException e) {
            System.err.println("Error closing client socket: " + e.getMessage());
        }
    }
}