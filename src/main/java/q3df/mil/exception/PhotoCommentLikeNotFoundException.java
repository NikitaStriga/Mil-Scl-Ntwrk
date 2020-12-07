package q3df.mil.exception;

public class PhotoCommentLikeNotFoundException extends RuntimeException{
    public PhotoCommentLikeNotFoundException() {
    }

    public PhotoCommentLikeNotFoundException(String message) {
        super(message);
    }

    public PhotoCommentLikeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
