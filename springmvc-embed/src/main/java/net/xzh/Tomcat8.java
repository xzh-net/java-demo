package net.xzh;

import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.Tomcat;

/**
 * 便携式tomcat
 */

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
		//手动添加生命周期监听器
		server.addLifecycleListener(listener);

		// 有两种方式启动项目：1.war  2.文件夹
		tomcat.addWebapp(CONTEXT_PATH, WEB_APP_PATH);
		
		tomcat.enableNaming();
		tomcat.start();
		//挂起
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