package net.xzh.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import net.xzh.service.UserService;

/**
 * 用于生产service代理对象的工厂 此处我们只做入门，实现对AccountService的代理创建，同时加入事务
 * 
 * @author admin
 */
@Component
public class ProxyServiceFactory {

	@Autowired
	private UserService userService;

	@Bean("proxyUserService")
	public UserService getProxyUserService() {
		// 1.创建代理对象
		UserService proxyUserService = (UserService) Proxy.newProxyInstance(userService.getClass().getClassLoader(),
				userService.getClass().getInterfaces(), new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						// 1.定义返回值
						Object rtValue = null;
						// 执行被代理对象的方法
						System.out.println("前置增强");
						rtValue = method.invoke(userService, args);
						System.out.println("后置增强");
						// 返回
						return rtValue;
					}
				});
		// 2.返回
		return proxyUserService;
	}
}
