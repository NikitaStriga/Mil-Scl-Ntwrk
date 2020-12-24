package q3df.mil.exception;

public class CustomMailException  extends RuntimeException{

    public CustomMailException() {
    }

    public CustomMailException(String message) {
        super(message);
    }

    public CustomMailException(String message, Throwable cause) {
        super(message, cause);
    }
}
