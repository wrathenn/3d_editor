package exceptions;

public class NotExistedNameException extends Exception {
    public NotExistedNameException(String message) {
        super(message);
    }

    public NotExistedNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
