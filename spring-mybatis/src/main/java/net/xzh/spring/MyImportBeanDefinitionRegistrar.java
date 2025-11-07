package net.xzh.spring;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry,
			BeanNameGenerator importBeanNameGenerator) {
		Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(MyScanner.class.getName());
		String basePackages = (String) attributes.get("value");
		MyBeanDefinitionScanner scanner = new MyBeanDefinitionScanner(registry);
		scanner.addIncludeFilter(new TypeFilter() {
			@Override
			public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
					throws IOException {
				return true;
			}
		});
		scanner.scan(basePackages);
	}
}
