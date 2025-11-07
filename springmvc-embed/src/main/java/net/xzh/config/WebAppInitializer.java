package net.xzh.config;

import java.nio.charset.StandardCharsets;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * 编程式的启动web
 * 
 * @author Administrator
 *
 */
//servlet 3.0 SPI规范
public class WebAppInitializer implements WebApplicationInitializer {

	// tomcat 启动的时候会调用 onStartup方法 为什么？
	// 传入一个ServletContext ： web上下文对象 web.xml能做的 ServletContext都能做

	// 因为servlet 3.0的一个新规范,跟tomcat没关系，tomcat是规范的实现者之一。
	// 为什么不是tomcat规范而是servlet规范？因为市面上有很多web容器，例如jetty。如果你是web容器的规范，如果换了容器，代码将不再适用。
	// SPI "你"=>这里指的是spring
	public void onStartup(ServletContext servletContext) {
//		log.info("META-INF/services/org.springframework.web.SpringServletContainerInitializer");
		// 字符集过滤器
		FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encodingFilter",
				CharacterEncodingFilter.class);
		encodingFilter.setInitParameter("encoding", String.valueOf(StandardCharsets.UTF_8));
		encodingFilter.setInitParameter("forceEncoding", "true");
		encodingFilter.addMappingForUrlPatterns(null, false, "/*");

		// 系统过滤器
//		FilterRegistration.Dynamic sysFilter = servletContext.addFilter("sysFilter", SysFilter.class);
//		sysFilter.addMappingForUrlPatterns(null, false, "/*");

		// applicationContext.xml注册
		AnnotationConfigWebApplicationContext applicationConfig = new AnnotationConfigWebApplicationContext();
		applicationConfig.register(ApplicationConfig.class);
		servletContext.addListener(new ContextLoaderListener(applicationConfig));
		
		// springmvc.xml注册
		AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
		webContext.register(WebConfig.class);
		
		// 声明SpringMVC核心控制器
		DispatcherServlet dispatcher = new DispatcherServlet(webContext);
		dispatcher.setThrowExceptionIfNoHandlerFound(true);
		ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", dispatcher);// DispatcherServlet添加到servletContext（上下文）
		registration.setLoadOnStartup(1);
		// 映射路径
		registration.addMapping("/");
	}
}