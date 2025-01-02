package chat.server.exceptions;

public class UnknownRecipientException extends RuntimeException {
    public UnknownRecipientException(String recipient) {
        super("Unknown recipient: " + recipient);
    }
}