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
 * 以编程方式配置 Servlet、Filter、Listener
 * @author xzh
 *
 */
public class WebAppInitializer implements WebApplicationInitializer {

	public void onStartup(ServletContext servletContext) {
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
		applicationConfig.register(ApplicationContextConfig.class);
		servletContext.addListener(new ContextLoaderListener(applicationConfig));
		
		// springmvc.xml注册
		AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
		webContext.register(SpringMvcConfig.class);
		
		// 声明SpringMVC核心控制器
		DispatcherServlet dispatcher = new DispatcherServlet(webContext);
		dispatcher.setThrowExceptionIfNoHandlerFound(true);
		ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", dispatcher);// DispatcherServlet添加到servletContext（上下文）
		registration.setLoadOnStartup(1);
		// 映射路径
		registration.addMapping("/");
	}
}