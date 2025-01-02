package chat.server;

import java.util.concurrent.CopyOnWriteArrayList;

public class ClientRegistry {
    private static final CopyOnWriteArrayList<ClientHandler> registeredClients = new CopyOnWriteArrayList<>();

    public static void register(ClientHandler clientHandler) {
        registeredClients.add(clientHandler);
        broadcast(clientHandler, "Connected to the chat server");
    }

    public static void unregister(ClientHandler clientHandler) {
        registeredClients.remove(clientHandler);
        broadcast(clientHandler, "Disconnected from the chat server");
    }

    public static void broadcast(ClientHandler sender, String message) {
        for (ClientHandler client : registeredClients) {
            if (client != sender) client.sendMessage(sender.getClientName() + " >> " + message + "\n");
        }
    }

    public static void sendPrivate(ClientHandler sender, ClientHandler recipient, String message) {
        recipient.sendMessage(sender.getClientName() + " (private) >> " + message + "\n");
    }

    public static String getRegisteredClientsAsString() {
        StringBuilder clients = new StringBuilder("[");

        int index = 0;
        for (ClientHandler client : registeredClients) {
            if (index == registeredClients.size() - 1) {
                clients.append(client.getClientName());
                break;
            }
            clients.append(client.getClientName()).append(", ");
            index++;
        }

        clients.append("]");

        return clients.isEmpty() ? "No clients connected" : clients.toString();
    }

    public static CopyOnWriteArrayList<ClientHandler> getRegisteredClients() {
        return registeredClients;
    }
}