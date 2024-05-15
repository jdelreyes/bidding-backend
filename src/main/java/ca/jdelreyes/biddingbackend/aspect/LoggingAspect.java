package ca.jdelreyes.biddingbackend.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Before("execution(* ca.jdelreyes.biddingbackend.service.impl.*.*(..))")
    public void logBeforeMethods(@NonNull JoinPoint joinPoint) {
        log.info(joinPoint.toString());
    }
}
