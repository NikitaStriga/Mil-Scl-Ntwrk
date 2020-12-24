package q3df.mil.exception;

public class EmailExistException extends RuntimeException{

    public EmailExistException() { }

    public EmailExistException(String message) {
        super(message);
    }

    public EmailExistException(String message, Throwable cause) {
        super(message, cause);
    }

}
