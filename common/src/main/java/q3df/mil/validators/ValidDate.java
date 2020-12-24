package q3df.mil.validators;

import  com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Past;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;


/**
 * this annotation check for null,past and present date
 * u need to use format yyyy-MM-dd
 */
@Target({FIELD,ANNOTATION_TYPE,PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateValidator.class)
public @interface ValidDate {

    /**
     * u can use ur own message but this by default covers all that u need
     * @return message for exception
     */
    String message() default "Please enter the correct date of birth! It must be between {afterYear}-{beforeYear} and not null!";

    /**
     *
     * @return after date
     */
    int afterYear()  default 1950;

    /**
     *
     * @return before date
     */
    int beforeYear() default 2017;


    Class<?>[] groups() default {};


    Class<? extends Payload>[] payload() default {};

}