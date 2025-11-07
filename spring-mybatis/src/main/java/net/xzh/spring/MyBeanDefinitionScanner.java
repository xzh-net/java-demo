package net.xzh.spring;

import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

public class MyBeanDefinitionScanner extends ClassPathBeanDefinitionScanner{

	public MyBeanDefinitionScanner(BeanDefinitionRegistry registry) {
		super(registry);
	}
	
	@Override
	protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
		return beanDefinition.getMetadata().isInterface();
	}


	@Override
	protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
		Set<BeanDefinitionHolder> beanDefinitionHolders=super.doScan(basePackages);
		for(BeanDefinitionHolder beanDefinitionHolder:beanDefinitionHolders) {
			GenericBeanDefinition beanDefinition=(GenericBeanDefinition)beanDefinitionHolder.getBeanDefinition();
			beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(beanDefinition.getBeanClassName());
			beanDefinition.setBeanClassName(MyBatisFactoryBean.class.getName());
			beanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
		}
		return beanDefinitionHolders;
	}

}
