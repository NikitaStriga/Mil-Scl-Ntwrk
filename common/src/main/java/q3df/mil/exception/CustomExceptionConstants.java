package q3df.mil.exception;

/**
 * sup enum class for custom response status in exception handling
 */
public enum CustomExceptionConstants {

    CANT_FIND_ENTITY("Some entities participating in the request were not found in the database!"),
    BAD_PARAMS("Invalid input parameters! Some of params are not correct (cant parse,etc.)!"),
    NO_PERMISSION_TO_EXECUTE("The owner of the request have no permission to execute operation!"),
    BAD_JSON("Invalid syntax of  input JSON!");

    private final String customResponse;

    CustomExceptionConstants(String customResponse) {
        this.customResponse = customResponse;
    }

    public String getExplainStatus(CustomExceptionConstants customExceptionConstants){
        return customExceptionConstants.customResponse;
    }
}
