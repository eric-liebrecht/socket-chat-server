package chat.server.exceptions;

public class UnknownCommandException extends RuntimeException {
    public UnknownCommandException(String command) {
        super("Unknown command: " + command);
    }
}