package net.xzh;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.xzh.config.SpringConfiguration;
import net.xzh.service.OrderSerice;

// 注册配置文件方式：扫描范围在配置文件重定义 配置文件无需标记@Configuration
// 扫描路径方式：配置文件必须标记@Configuration 无须定义@ComponentScan
// 两种方式都可以使用@import
public class ApplicationAnnotation {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		String[] names = ac.getBeanDefinitionNames();
		for (String name : names) {
			System.out.println(name);
		}
		OrderSerice orderSerice = (OrderSerice) ac.getBean("orderSerice");
		orderSerice.test();
		ac.close();
	}
}
