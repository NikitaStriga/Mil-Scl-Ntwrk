package q3df.mil.controller.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import q3df.mil.exception.DialogNotFoundException;
import q3df.mil.exception.EmailExistException;
import q3df.mil.exception.LoginExistException;
import q3df.mil.exception.UserNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
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
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach( error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }



    //for NoHandlerFoundException (if spring can't find a handler)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String,String>> handleNoHandlerFoundException(HttpServletRequest request,NoHandlerFoundException e){
        Map<String, String> errors = new HashMap<>();
        errors.put("message","This URL doesnt exists");
        errors.put("url",request.getRequestURL().toString());
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }



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