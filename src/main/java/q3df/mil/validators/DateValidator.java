package q3df.mil.validators;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

/**
 * this class used by @ValidDate to validate date for null, past and present
 */
public class DateValidator implements ConstraintValidator<ValidDate, LocalDate> {

    private int afterDate;
    private int beforeDate;

    @Override
    public void initialize(ValidDate validDate) {
        afterDate = validDate.afterYear();
        beforeDate = validDate.beforeYear();
    }


    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        if (localDate == null) return false;
        return isValidFormat(beforeDate + "-01-01", afterDate + "-01-01", localDate);
    }

    public boolean isValidFormat(String bDate, String aDate, LocalDate localDate) {
        LocalDate dateBefore = LocalDate.parse(bDate);
        LocalDate dateAfter = LocalDate.parse(aDate);
        return localDate.isAfter(dateAfter) && localDate.isBefore(dateBefore);
    }

}