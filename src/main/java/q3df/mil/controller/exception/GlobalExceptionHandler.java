package q3df.mil.controller.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import q3df.mil.exception.DialogNotFoundException;
import q3df.mil.exception.EmailExistException;
import q3df.mil.exception.LoginExistException;
import q3df.mil.exception.UserNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {


    //for LoginExistException and EmailExistException
    @ExceptionHandler({LoginExistException.class, EmailExistException.class})
    public ResponseEntity<HelperClassException> handleLoginExistExceptionAndEmailExistException(Exception e) {
            return new ResponseEntity<>(new HelperClassException(e.getMessage()), HttpStatus.OK);
        }



    //for UserNotFoundException
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<HelperClassException> handleUserNotFoundException(UserNotFoundException e){
            return new ResponseEntity<>(new HelperClassException(e.getMessage()),HttpStatus.NOT_FOUND);
    }



    //for DialogNotFoundException
    @ExceptionHandler(DialogNotFoundException.class)
    public ResponseEntity<HelperClassException> handleDialogNotFoundException(DialogNotFoundException e){
        return new ResponseEntity<>(new HelperClassException(e.getMessage()),HttpStatus.NOT_FOUND);
    }



    //for @Valid annotation that throw MethodArgumentNotValidException if fields from request is no valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e){
        Map<String, String> errorsFromValid = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(
                error -> {
                    String checkedField = ((FieldError) error).getField();
                    String errorMessage=error.getDefaultMessage();
                    errorsFromValid.put(checkedField,errorMessage);
                });
        return new ResponseEntity<>(errorsFromValid,HttpStatus.BAD_REQUEST);
    }



    //for bad URL (if spring can't find a handler for url he throw NoHandlerFoundException)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String,String>> handleNoHandlerFoundException(HttpServletRequest request,NoHandlerFoundException e){
        Map<String, String> errors = new HashMap<>();
        errors.put("message","This URL doesn't exist!");
        errors.put("url",request.getRequestURL().toString());
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }


    //if request method is not supported for current url
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<Map<String,Object>> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request) {
        Map<String,Object> responseMap=new LinkedHashMap<>();
        responseMap.put("message","Request method " + ex.getMethod() + " not supported for this URL " + request.getRequestURL().toString());
        responseMap.put("supported methods",ex.getSupportedHttpMethods());
        return new ResponseEntity<>(responseMap,HttpStatus.METHOD_NOT_ALLOWED);
    }



    //for JacksonParsing serialization  or deserialization exceptions
    @ExceptionHandler(com.fasterxml.jackson.databind.exc.InvalidFormatException.class)
    public ResponseEntity<HelperClassException> jacksonSerializeException(InvalidFormatException ex,HttpServletRequest request){
        final StringBuilder stringBuilder=new StringBuilder();
        final String simpleNameType = ex.getTargetType().getSimpleName();
        stringBuilder
                .append("Cant parse value |")
                .append(ex.getValue())
                .append("| for type ")
                .append(simpleNameType.equals("LocalDate")?simpleNameType + " , use valid type 'yyyy-MM-dd' !" : simpleNameType);
        return new ResponseEntity<>(new HelperClassException(stringBuilder.toString()),HttpStatus.BAD_REQUEST);
    }




//    /** For unexpected exceptions*  :) */
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<HelperClassException> universalHandler(Exception ex, HttpServletRequest request){
//        return new ResponseEntity<>(new HelperClassException("Something going wrong :( "),HttpStatus.INTERNAL_SERVER_ERROR);
//    }




    @Data
    @AllArgsConstructor
    class HelperClassException{
            private String message;
    }


}



//can use directly in controllers
//        try{
//            return ResponseEntity.ok(userService.saveUser(userRegistrationDto));
//        }catch (Exception ex){
//            throw new ResponseStatusException(HttpStatus.OK,ex.getMessage(),ex);
//        }



//This exception is thrown when method argument is not the expected type:
//    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
//    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
//            MethodArgumentTypeMismatchException ex, WebRequest request) {
//        String error =
//                ex.getName() + " should be of type " + ex.getRequiredType().getName();
//
//        ApiError apiError =
//                new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
//        return new ResponseEntity<Object>(
//                apiError, new HttpHeaders(), apiError.getStatus());
//    }


//com.fasterxml.jackson.databind.JsonMappingException if we use bad mapping (for example we forget to write " in some place)