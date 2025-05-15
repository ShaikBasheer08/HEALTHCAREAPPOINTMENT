package com.cts.healthcareappointment.notificationmodule.logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
 
import lombok.extern.slf4j.Slf4j;
 
@Slf4j
@Aspect
@Component
public class LoggerAdvice {
	@Pointcut(value = "execution(* com.cts.healthcareappointment.notificationmodule.controller.*.*(..))")
	public void pointCut() {
	}
	@Around("pointCut()")
	public Object applicationLogger(ProceedingJoinPoint joinPoint) throws Throwable {
		String className = joinPoint.getTarget().getClass().toString();
		String methodName = joinPoint.getSignature().getName();
		log.info("{}::{}: Entry", className, methodName);
		Object obj = joinPoint.proceed();
		log.info("{}::{}: Exit", className, methodName);
		return obj;
	}
}
 