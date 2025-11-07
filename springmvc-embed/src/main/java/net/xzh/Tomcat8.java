package net.xzh;

import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.Tomcat;

/**
 * TOMCAT8
 */
//https://blog.csdn.net/ly91526188/article/details/80110149
// https://www.cnblogs.com/lusaisai/p/13121668.html 原理
// https://www.jianshu.com/p/3a3edbcd8f24 spi
// https://blog.csdn.net/guanzhengyinqin/article/details/85255840 自定义参数处理器

//1.DispatcherServlet->doService->doDispatch->getHandler->(从不同handlerMappings类型中获取URI)
//2.getHandler->getHandlerInternal(AbstractUrlHandlerMapping/AbstractHandlerMethodMapping)

public class Tomcat8 {

	private static int PORT = 80;
	private static String CONTEXT_PATH = "/"; // 二级路径
	private static String WEB_APP_PATH = Tomcat8.class.getResource("/").getPath();
	private Tomcat tomcat = new Tomcat();

	public void start() throws Exception {
		tomcat.setPort(PORT);
		tomcat.setBaseDir(WEB_APP_PATH);
		tomcat.getHost().setAppBase(WEB_APP_PATH);

		StandardServer server = (StandardServer) tomcat.getServer();
		AprLifecycleListener listener = new AprLifecycleListener();
		server.addLifecycleListener(listener);

		tomcat.addWebapp(CONTEXT_PATH, WEB_APP_PATH);
		tomcat.enableNaming();
		tomcat.start();
		tomcat.getServer().await();

	}

	public void stop() throws Exception {
		tomcat.stop();
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Tomcat8 tomcat8 = new Tomcat8();
		tomcat8.start();
	}
}