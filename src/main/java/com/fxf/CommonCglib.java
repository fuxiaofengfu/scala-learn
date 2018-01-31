package com.fxf;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class CommonCglib implements MethodBeforeAdvice,AfterReturningAdvice {
	@Override
	public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
		//这里异步
		System.out.println("afterRetuning ......");
	}

	@Override
	public void before(Method method, Object[] objects, Object o) throws Throwable {
		//这里异步
		System.out.println("before advice .......");
	}

}
