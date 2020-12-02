package q3df.mil.exception;

public class DialogNotFoundException  extends RuntimeException{

    public DialogNotFoundException() { }

    public DialogNotFoundException(String message) {
        super(message);
    }

    public DialogNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
