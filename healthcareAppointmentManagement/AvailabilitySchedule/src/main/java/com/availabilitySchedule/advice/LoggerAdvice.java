package com.availabilitySchedule.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Aspect for logging execution of service and repository Spring components.
 * 
 * @author Swapnil Rajesh
 * @since 18/02/2025
 */
@Slf4j
@Aspect
@Component
public class LoggerAdvice {

	/**
	 * Pointcut that matches all repositories, services and controllers.
	 */
	@Pointcut(value = "execution(* com.availabilitySchedule.controller.*.*(..))")
	public void pointCut() {
	}

	/**
	 * Advice that logs method entry and exit.
	 *
	 * @param joinPoint the join point
	 * @return the result of the method execution
	 * @throws Throwable if any error occurs
	 */
	@Around("pointCut()")
	public Object applicationLogger(ProceedingJoinPoint joinPoint) throws Throwable {
		Class<?> targetClass = AopProxyUtils.ultimateTargetClass(joinPoint.getTarget());
		String className = targetClass.getSimpleName();
		String methodName = joinPoint.getSignature().getName();
		log.info("{}::{}: Entry", className, methodName);
		Object obj = joinPoint.proceed();
		log.info("{}::{}: Exit", className, methodName);
		return obj;
	}
}