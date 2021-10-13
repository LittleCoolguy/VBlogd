package com.gao.vblogd.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    @Before("execution(* com.gao.vblogd.controller.*Controller.*(..))")
    public void BeforeMethod(JoinPoint joinPoint){
        StringBuilder stringBuilder = new StringBuilder();
        for (Object arg:joinPoint.getArgs()){
            if (arg != null) {
                stringBuilder.append("arg:" + arg.toString() + "|");
            }
        }
        logger.info("before method: "+stringBuilder.toString());
    }
}
