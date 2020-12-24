package q3df.mil.exception;

public class CriteriaCustomException extends RuntimeException{

    public CriteriaCustomException() { }

    public CriteriaCustomException(String message) {
        super(message);
    }

    public CriteriaCustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
