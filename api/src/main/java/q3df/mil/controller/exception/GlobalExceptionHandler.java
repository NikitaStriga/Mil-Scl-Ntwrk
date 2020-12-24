package q3df.mil.controller.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.MappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import q3df.mil.exception.CriteriaCustomException;
import q3df.mil.exception.CustomException;
import q3df.mil.exception.CustomMailException;
import q3df.mil.exception.DialogNotFoundException;
import q3df.mil.exception.EmailExistException;
import q3df.mil.exception.CustomExceptionConstants;
import q3df.mil.exception.LoginExistException;
import q3df.mil.exception.MessageNotFoundException;
import q3df.mil.exception.NoPermissionCustomException;
import q3df.mil.exception.PhotoCommentLikeNotFoundException;
import q3df.mil.exception.PhotoCommentNotFoundException;
import q3df.mil.exception.PhotoLikeNotFoundException;
import q3df.mil.exception.PhotoNotFoundException;
import q3df.mil.exception.RoleNotFoundException;
import q3df.mil.exception.TextCommentLikeNotFoundException;
import q3df.mil.exception.TextCommentNotFoundException;
import q3df.mil.exception.TextLikeNotFoundException;
import q3df.mil.exception.TextNotFoundException;
import q3df.mil.exception.UserNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * GLOBAL exception handler
 */
@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {


    //for all my custom exceptions
    @ExceptionHandler({
            DialogNotFoundException.class,
            EmailExistException.class,
            LoginExistException.class,
            MessageNotFoundException.class,
            PhotoCommentLikeNotFoundException.class,
            PhotoCommentNotFoundException.class,
            PhotoLikeNotFoundException.class,
            PhotoNotFoundException.class,
            RoleNotFoundException.class,
            TextCommentLikeNotFoundException.class,
            TextCommentNotFoundException.class,
            TextLikeNotFoundException.class,
            TextNotFoundException.class,
            UserNotFoundException.class
    })
    public ResponseEntity<HelperClassException> handleLoginExistExceptionAndEmailExistException(Exception ex) {
        return new ResponseEntity<>(
                new HelperClassException(ex.getMessage(), CustomExceptionConstants.CANT_FIND_ENTITY),
                HttpStatus.NOT_FOUND);
    }


    //for ModelMapperException (model mapper wraps our own exception and throw MappingException )
    @ExceptionHandler(org.modelmapper.MappingException.class)
    public ResponseEntity<HelperClassException> handleModelMappingException(MappingException ex) {
        return new ResponseEntity<>(
                new HelperClassException(ex.getCause().getMessage(), CustomExceptionConstants.CANT_FIND_ENTITY),
                HttpStatus.NOT_FOUND);
    }


    //for @Valid annotation that throw MethodArgumentNotValidException if fields from request is no valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errorsFromValid = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(
                error -> {
                    String checkedField = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errorsFromValid.put(checkedField, errorMessage);
                });
        return new ResponseEntity<>(errorsFromValid, HttpStatus.BAD_REQUEST);
    }


    //for bad URL (if spring can't find a handler for url he throw NoHandlerFoundException)
    //commented out because this exception is off in a properties cuz swagger dont work with it
//    @ExceptionHandler(NoHandlerFoundException.class)
//    public ResponseEntity<Map<String,String>> handleNoHandlerFoundException(HttpServletRequest request,NoHandlerFoundException e){
//        Map<String, String> errors = new HashMap<>();
//        errors.put("message","This URL doesn't exist!");
//        errors.put("url",request.getRequestURL().toString());
//        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
//    }


    //if request method is not supported for current url
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request) {
        Map<String, Object> responseMap = new LinkedHashMap<>();
        responseMap.put("message", "Request method " + ex.getMethod() + " not supported for this URL " + request.getRequestURL().toString());
        responseMap.put("supported methods", ex.getSupportedHttpMethods());
        return new ResponseEntity<>(responseMap, HttpStatus.METHOD_NOT_ALLOWED);
    }


    //for custom exception with bad passed parameters
    @ExceptionHandler({
            CustomException.class,
            CriteriaCustomException.class,
            CustomMailException.class
    })
    public ResponseEntity<HelperClassException> customEx(CustomException ex) {
        return new ResponseEntity<>(
                new HelperClassException(ex.getMessage(), CustomExceptionConstants.BAD_PARAMS),
                HttpStatus.BAD_REQUEST);
    }


    //for JacksonParsing serialization  or deserialization exceptions
    @ExceptionHandler(com.fasterxml.jackson.databind.exc.InvalidFormatException.class)
    public ResponseEntity<HelperClassException> jacksonSerializeException(InvalidFormatException ex, HttpServletRequest request) {
        final StringBuilder stringBuilder = new StringBuilder();
        final String simpleNameType = ex.getTargetType().getSimpleName();
        stringBuilder
                .append("Cant parse value |")
                .append(ex.getValue())
                .append("| for type ")
                .append(simpleNameType.equals("LocalDate") ? simpleNameType + " , use valid type 'yyyy-MM-dd' !" : simpleNameType);
        return new ResponseEntity<>(
                new HelperClassException(stringBuilder.toString(), CustomExceptionConstants.BAD_PARAMS),
                HttpStatus.BAD_REQUEST);
    }


    //for bad syntax of Json
    @ExceptionHandler(com.fasterxml.jackson.databind.JsonMappingException.class)
    public ResponseEntity<HelperClassException> badSyntaxOfJson(JsonMappingException exception) {
        return new ResponseEntity<>(
                new HelperClassException(exception.getMessage(), CustomExceptionConstants.BAD_JSON),
                HttpStatus.BAD_REQUEST);
    }


    //for BadCredentialsException in authentication controller
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HelperClassException> notAuthorized(BadCredentialsException ex) {
        return new ResponseEntity<>(
                new HelperClassException(ex.getMessage(), CustomExceptionConstants.NO_PERMISSION_TO_EXECUTE),
                HttpStatus.UNAUTHORIZED);
    }


    //for NoPermissionException
    @ExceptionHandler(NoPermissionCustomException.class)
    public ResponseEntity<HelperClassException> noPermission(NoPermissionCustomException ex) {
        return new ResponseEntity<>(
                new HelperClassException(ex.getMessage(), CustomExceptionConstants.NO_PERMISSION_TO_EXECUTE),
                HttpStatus.FORBIDDEN);
    }


    //javax validation from entity (exception from Hibernate entities)
    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> badDataInEntities(javax.validation.ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(e -> {

            String field = e.getPropertyPath().toString();
            String message = e.getMessage();
            errors.put(field, message);

        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


    //This exception is thrown when method argument is not the expected type
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = StringUtils.join(ex.getName() + " should be next type - " + ex.getRequiredType().getName());
        return new ResponseEntity<>(
                new HelperClassException(message, CustomExceptionConstants.BAD_PARAMS),
                HttpStatus.BAD_REQUEST);
    }


    //for jsonParseError
    @ExceptionHandler({com.fasterxml.jackson.core.JsonParseException.class})
    public ResponseEntity<Map<String, String>> handleJParseExc(com.fasterxml.jackson.core.JsonParseException jsEx) {
        Map<String, String> map = new HashMap<>();
        map.put("message", "invalid format of json");
        map.put("cause", jsEx.getOriginalMessage());
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

//    /** For unexpected exceptions*  :) */
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<HelperClassException> universalHandler(Exception ex, HttpServletRequest request){
//        return new ResponseEntity<>(new HelperClassException("Something going wrong :( "),HttpStatus.INTERNAL_SERVER_ERROR);
//    }


    @Data
    @AllArgsConstructor
    class HelperClassException {
        private String message;
        private CustomExceptionConstants customExceptionConstants;
    }


}

