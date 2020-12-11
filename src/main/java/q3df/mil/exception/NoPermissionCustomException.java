package q3df.mil.exception;

public class NoPermissionCustomException extends RuntimeException{

    public NoPermissionCustomException() {
    }

    public NoPermissionCustomException(String message) {
        super(message);
    }

    public NoPermissionCustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
