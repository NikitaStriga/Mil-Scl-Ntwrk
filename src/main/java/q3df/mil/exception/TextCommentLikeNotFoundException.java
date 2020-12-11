package q3df.mil.exception;

public class TextCommentLikeNotFoundException extends RuntimeException{

    public TextCommentLikeNotFoundException() {
    }

    public TextCommentLikeNotFoundException(String message) {
        super(message);
    }

    public TextCommentLikeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
