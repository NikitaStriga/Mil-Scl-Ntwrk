package q3df.mil.exception;

public class TextNotFoundException extends RuntimeException{

    public TextNotFoundException() {
    }

    public TextNotFoundException(String message) {
        super(message);
    }

    public TextNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
