package q3df.mil.exception;

public class TextCommentNotFoundException extends RuntimeException{
    public TextCommentNotFoundException() {
    }

    public TextCommentNotFoundException(String message) {
        super(message);
    }

    public TextCommentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
