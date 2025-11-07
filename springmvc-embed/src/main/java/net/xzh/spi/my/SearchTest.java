package net.xzh.spi.my;

import java.util.Iterator;
import java.util.ServiceLoader;

public class SearchTest {

	public static void main(String[] args) {

		SpiService spi = null;
		ServiceLoader<SpiService> serviceLoader = ServiceLoader.load(SpiService.class);
		Iterator<SpiService> searchs = serviceLoader.iterator();

		while (searchs.hasNext()) {
			spi = searchs.next();
			spi.exec(System.currentTimeMillis() + "");
		}
		if (spi == null) {// 如果没有spi实现,就是用默认的类对象实现
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			try {
				spi = (SpiService) classLoader.loadClass("net.xzh.spi.my.ServiceImplDefault").newInstance();
				spi.exec("end");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

	}
}
