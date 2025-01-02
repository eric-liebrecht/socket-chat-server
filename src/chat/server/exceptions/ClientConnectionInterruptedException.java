package chat.server.exceptions;

public class ClientConnectionInterruptedException extends RuntimeException {
    public ClientConnectionInterruptedException() {
        super("Client exited connection");
    }
}