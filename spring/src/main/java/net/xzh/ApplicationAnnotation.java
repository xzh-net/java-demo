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
//1、三级缓存解决循环依赖问题的关键是什么？为什么通过提前暴漏对象能解决
//实例化和初始化分开操作，在中间过程中给其他对象赋值的时候，并不是一个完整的对象，而是把半成品对象赋值给了其他对象
//2、如果使用一级缓存能否不解决问题？
//不能，在整个过程中，缓存中放的是半成品(二级early)和成品对象(一级)，如果只有一级缓存，半成品和成品都放在一级中，由于半成品
//无法使用 需要做额外的判断，因此把成品和半成品存放空间分割开来
//3、只有使用二级缓存行不行？为什么需要三级缓存？如果我们能保证所有的bean对象都不去调用getEarlyBeanReference，使用二级可以么？
//是的，如果我们能保证不调用getEarlyBeanReference就可以使用二级缓存来解决
//使用三级缓存的本质在于aop代理问题
//4、如果某个bean对象是代理对象，那么会不会创建普通的bean对象
//会 见 getEarlyBeanReference 需要改变对象的时候进行原对象的覆盖
//5、为什么使用三级缓存能解决aop代理问题？
//当一个对象需要被代理的时候，整个创建过程包含两个对象，原对象和代理生成对象
//bean默认的都是单例，在整个生命周期的处理环节中一个beanName不能对应多个对象 ,所有对代理对象的时候覆盖原对象
//6、如何知道什么时候使用代理对象？
//因为不知道什么时候调用，所以通过一个匿名内部类的方式在使用的方式覆盖原对象，保证全局唯一，这就是三级缓存的本质
//7.cglib核心代理处理
//ObjenesisCglibAopProxy.java
