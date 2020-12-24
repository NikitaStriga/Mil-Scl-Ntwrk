package q3df.mil.exception;

public class TextLikeNotFoundException extends RuntimeException{

    public TextLikeNotFoundException() { }

    public TextLikeNotFoundException(String message) {
        super(message);
    }

    public TextLikeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
