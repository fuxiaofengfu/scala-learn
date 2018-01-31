package com.fxf;

import org.springframework.aop.framework.ProxyFactory;

public class UserService {

	public static void main(String[] args) {

		ProxyFactory proxyFactory = new ProxyFactory(new UserService());
		proxyFactory.addAdvice(new CommonCglib());
		UserService userService = (UserService)proxyFactory.getProxy();
		userService.setUser();
	}

	public void setUser(){
		System.out.println("set user........");
	}

	private String p1 = "1111";

	public void setUser11(){
		System.out.println("set user........");
	}
}
