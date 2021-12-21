package exceptions;

import java.io.IOException;

public class ExistedNameException extends IOException {
    public ExistedNameException(String message) {
        super(message);
    }

    public ExistedNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
