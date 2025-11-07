package net.xzh;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import net.xzh.config.WebConfig;

public class Jetty9 {

	private static final int DEFAULT_PORT = 80;
	private static final String CONTEXT_PATH = "/";
	private static final String MAPPING_URL = "/*";

	private WebApplicationContext webApplicationContext() {
		AnnotationConfigWebApplicationContext configWebApplicationContext = new AnnotationConfigWebApplicationContext();
		configWebApplicationContext.register(WebConfig.class);
		return configWebApplicationContext;
	}

	private ServletContextHandler servletContextHandler(WebApplicationContext context) {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		
		ServletContextHandler handler = new ServletContextHandler();
		handler.setContextPath(CONTEXT_PATH);
		handler.addServlet(new ServletHolder(new DispatcherServlet(context)), MAPPING_URL);
		handler.addEventListener(new ContextLoaderListener(context));
		handler.addFilter(new FilterHolder(filter), MAPPING_URL, EnumSet.allOf(DispatcherType.class));
//		handler.setInitParameter("contextConfigLocation", "classpath:spring-server.xml");
		return handler;
	}

	public void start() throws Exception {
		Server server = new Server();
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(DEFAULT_PORT);
		server.setConnectors(new Connector[] { connector });
		server.setHandler(servletContextHandler(webApplicationContext()));
		server.start();
		server.join();
	}

	public static void main(String[] args) throws Exception {
		Jetty9 jetty9 = new Jetty9();
		jetty9.start();
	}
}
