package com.healthcare.management.logger;
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

	@Pointcut(value = "execution(* com.dataBase.automate.*.*.*(..))")

	 /**

     * Pointcut that matches all methods in the specified package structure.

     */

	public void pointCut() {

	}

	/**

     * Logs the entry and exit of methods matched by the pointcut.

     *

     * @param joinPoint The join point representing the method being intercepted.

     * @return The result of the method execution.

     * @throws Throwable If the intercepted method throws an exception.

     */

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

