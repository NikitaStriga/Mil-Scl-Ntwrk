package q3df.mil.exception;

public class EntityAddingException extends RuntimeException{

    private static final String NULL_ENTITY = "Can't add null ";
    private static final String ALREADY_ASSIGNED = " has already been added ";

    public EntityAddingException(String message) {
        super(NULL_ENTITY + message);
    }

    public EntityAddingException(String added,String to){
        super(added + ALREADY_ASSIGNED + to);
    }

}
