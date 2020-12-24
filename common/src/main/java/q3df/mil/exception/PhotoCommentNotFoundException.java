package q3df.mil.exception;

public class PhotoCommentNotFoundException extends RuntimeException{

    public PhotoCommentNotFoundException() { }

    public PhotoCommentNotFoundException(String message) {
        super(message);
    }

    public PhotoCommentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
