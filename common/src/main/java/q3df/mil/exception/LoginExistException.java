package q3df.mil.exception;

public class LoginExistException extends RuntimeException{

    public LoginExistException() { }

    public LoginExistException(String message) {
        super(message);
    }

    public LoginExistException(String message, Throwable cause) {
        super(message, cause);
    }

}
