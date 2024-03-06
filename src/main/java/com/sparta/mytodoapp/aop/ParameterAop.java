package com.sparta.mytodoapp.aop;

import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ParameterAop {

	@Pointcut("execution(* com.sparta.mytodoapp.controller.*.*(..))")
	private void cut(){};

	@Before("cut()")
	public void beforePrint(JoinPoint joinPoint) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		System.out.println(method.getName() + "메서드 실행");
		Object[] args = joinPoint.getArgs();
		for (Object obj : args) {
			System.out.println("type : " + obj.getClass().getSimpleName());
			System.out.println("value : " + obj);
		}
	}

	@Around("cut()")
	public Object timeCheck(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		try {
			return joinPoint.proceed();
		} finally {
			long endTime = System.currentTimeMillis();
			long runTime = endTime - startTime;
			System.out.println("메서드 실행 시간: "+runTime);
		}
	}
}
