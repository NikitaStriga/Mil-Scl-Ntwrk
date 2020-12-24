package q3df.mil.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Log4j2
public class LoggingAspect {

    //a simple aspect for logging  in global exception handler
    @Before("execution(* q3df.mil.controller.exception.GlobalExceptionHandler.*(..))")
    public void beforeExceptionHandling(JoinPoint jp) {
        for (Object arg : jp.getArgs()) {
            if (arg instanceof Exception) {
                Exception ex = (Exception) arg;
                log.error(ex.getMessage());
            }
        }
    }


}
