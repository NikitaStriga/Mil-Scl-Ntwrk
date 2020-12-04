package q3df.mil.validators;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;


/**
 * check for null and past,present date
 * */
@Target({FIELD,ANNOTATION_TYPE,PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateValidator.class)
public @interface ValidDate {

    /**
     * this default message contains all information that u need, but if u want u can override it
     */
    String message() default "Please enter the correct date of birth! It must be between {afterYear}-{beforeYear} and not null!";

    /**
     * u need to use format yyyy-MM-dd
     */
    int afterYear()  default 1950;

    /**
     * u need to use format yyyy-MM-dd
     */
    int beforeYear() default 2017;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}