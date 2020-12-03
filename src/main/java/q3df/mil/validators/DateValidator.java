package q3df.mil.validators;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator implements ConstraintValidator<ValidDate, String> {

   @Override
   public void initialize(ValidDate validDate) {

   }

   @Override
   public boolean isValid(String localDate, ConstraintValidatorContext constraintValidatorContext) {
      return !StringUtils.isBlank(localDate) && isValidFormat("yyyy-MM-dd", localDate);
   }

   public boolean isValidFormat(String format, String localDate) {
      try {
         LocalDate.parse(localDate, DateTimeFormatter.ofPattern(format));
         if(!LocalDate.now().isAfter(LocalDate.parse(localDate))){

         }
      } catch (DateTimeParseException e) {
         return false;
      }
      return true;
   }
}