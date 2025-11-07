package net.xzh;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.xzh.service.A;

public class ApplicationXML {

	public static void main(String[] args) {
		
//		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml", "dao.xml" });
//		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:/*.xml");
//		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:appcontext.xml");
//		ApplicationContext ac = new ClassPathXmlApplicationContext("appcontext.xml"); // src目录下的
//		ApplicationContext ac = new ClassPathXmlApplicationContext("conf/appcontext.xml"); // src/conf 目录下的
//		ApplicationContext ac = new ClassPathXmlApplicationContext("file:G:/Test/src/appcontext.xml");
		
		try (ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext2.xml")) {
			String[] names = ac.getBeanDefinitionNames();
			for (String name : names) {
				System.out.println(name);
			}

			A a = (A) ac.getBean("a");
			a.test();
		}
	}
}

//BeanDefinitionReaderUtils.generateBeanName    beanname命名生成规则
//ReflectionUtils 自动注入的时候根据bean去解析需要装配的属性和方法
//ProxyGenerator 字节码拆装
