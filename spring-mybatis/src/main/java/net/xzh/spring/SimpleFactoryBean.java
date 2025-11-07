package net.xzh.spring;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.beans.factory.FactoryBean;

public class SimpleFactoryBean implements FactoryBean<Object> {
	private Class<?> mapperClass;

	public SimpleFactoryBean(Class<?> mapperClass) {
		this.mapperClass = mapperClass;
	}

	@Override
	public Object getObject() throws Exception {
		Object proxyInstance = Proxy.newProxyInstance(SimpleFactoryBean.class.getClassLoader(),
				new Class[] { mapperClass }, new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						return null;
					}
				});
		return proxyInstance;
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return mapperClass;
	}

}
