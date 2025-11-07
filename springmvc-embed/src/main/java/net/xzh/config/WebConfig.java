package net.xzh.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

import net.xzh.annotation.UserParam;
/**
 * 相当于springmvc.xml文件
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan("net.xzh.controller")
public class WebConfig implements WebMvcConfigurer {

	/**
	 * thymeleaf模板引擎参数
	 */
	public final static String CHARACTER_ENCODING = "UTF-8";
	public final static String TEMPLATE_PREFIX = "classpath:/templates/";
	public final static String TEMPLATE_SUFFIX = ".html";
	public final static Boolean TEMPLATE_CACHEABLE = false;
	public final static TemplateMode TEMPLATE_MODE = TemplateMode.HTML;
	public final static Integer TEMPLATE_ORDER = 1;

	/**
	 * 模板解析器
	 *
	 * @return
	 */
	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setPrefix(TEMPLATE_PREFIX);
		templateResolver.setSuffix(TEMPLATE_SUFFIX);
		templateResolver.setCacheable(TEMPLATE_CACHEABLE);
		templateResolver.setCharacterEncoding(CHARACTER_ENCODING);
		templateResolver.setTemplateMode(TEMPLATE_MODE);
		templateResolver.setOrder(TEMPLATE_ORDER);
		return templateResolver;
	}

	/**
	 * 模板引擎
	 *
	 * @return
	 */
	@Bean
	public SpringTemplateEngine springTemplateEngine(SpringResourceTemplateResolver templateResolver) {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		return templateEngine;
	}

	/**
	 * 视图解析器
	 *
	 * @return
	 */
	@Bean
	public ThymeleafViewResolver viewResolver(SpringTemplateEngine springTemplateEngine) {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(springTemplateEngine);
		viewResolver.setCharacterEncoding(CHARACTER_ENCODING);
		return viewResolver;
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
		converters.add(fastJsonHttpMessageConverter);
	}

	/**
	 * 启用spring mvc 的注解（不启用只能通过显示的配置）
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//		configurer.enable();
	}

	/**
	 * 配置静态资源的映射
	 * 
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/")
				.setCacheControl(CacheControl.maxAge(31536000, TimeUnit.MILLISECONDS).cachePublic());
	}

	/**
	 * 配置项目启动加载的首页
	 * 
	 * @param registry
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController( "/" ).setViewName( "forward:/index" );
//		registry.addViewController("/").setViewName("index");//视图名称直接跳转
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		// TODO Auto-generated method stub
		resolvers.add(new HandlerMethodArgumentResolver() {

			@Override
			public boolean supportsParameter(MethodParameter parameter) {
				return parameter.hasParameterAnnotation(UserParam.class);
			}

			@Override
			public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
					NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
				// 通过Reques.getParameter();
				// request.getSession
				// 从redis 里面获取....
				Map<String, Object> hashMap = new HashMap<String, Object>();
				hashMap.put("key", "自定义解析");
				return hashMap;
			}
		});
	}

}