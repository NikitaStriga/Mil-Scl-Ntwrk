package q3df.mil.exception;

public class PhotoLikeNotFoundException extends RuntimeException{

    public PhotoLikeNotFoundException() { }

    public PhotoLikeNotFoundException(String message) {
        super(message);
    }

    public PhotoLikeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
