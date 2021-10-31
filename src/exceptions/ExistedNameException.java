package exceptions;

public class ExistedNameException extends Exception {
    public ExistedNameException(String message) {
        super(message);
    }

    public ExistedNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
