package net.xzh;

import java.io.IOException;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import net.xzh.mapper.OrderMapper;
import net.xzh.mapper.UserMapper;
import net.xzh.service.UserService;
import net.xzh.spring.SimpleFactoryBean;

@ComponentScan("com.xuzhihao")
public class SimpleAppliaction {
	public static void main(String[] args) throws IOException {
		try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext()) {
			applicationContext.register(SimpleAppliaction.class);// 配置类

			AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
			beanDefinition.setBeanClass(SimpleFactoryBean.class);// 实例化
			beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(UserMapper.class);
			applicationContext.registerBeanDefinition("xxx", beanDefinition);

			AbstractBeanDefinition beanDefinition2 = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
			beanDefinition2.setBeanClass(SimpleFactoryBean.class);// 实例化
			beanDefinition2.getConstructorArgumentValues().addGenericArgumentValue(OrderMapper.class);
			applicationContext.registerBeanDefinition("xxx2", beanDefinition2);

			applicationContext.refresh();

			UserService userService = applicationContext.getBean("userService", UserService.class);
			userService.save();
			UserService proxyUserService = (UserService)applicationContext.getBean("proxyUserService");
			proxyUserService.save();
		}
	}

}
