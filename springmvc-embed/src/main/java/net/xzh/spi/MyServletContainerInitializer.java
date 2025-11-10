package net.xzh.spi;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 模仿SpringServletContainerInitializer通过SPI机制扫描实现类
 */
@HandlesTypes(WebInit.class)
public class MyServletContainerInitializer implements ServletContainerInitializer {
	@Override
	public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
		System.out.println("MyServletContainerInitializer");
		List<WebInit> list = new ArrayList<>();
		for (Class<?> aClass : set) {
			try {
				list.add((WebInit) aClass.newInstance());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		for (WebInit webInit : list) {
			webInit.start(servletContext);
		}
	}
}
