package q3df.mil.exception;

public class AmazonCustomException extends RuntimeException {

    public AmazonCustomException() {
    }

    public AmazonCustomException(String message) {
        super(message);
    }

    public AmazonCustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
