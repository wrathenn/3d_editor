package exceptions;

import java.io.IOException;

public class NotExistedNameException extends IOException {
    public NotExistedNameException(String message) {
        super(message);
    }

    public NotExistedNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
