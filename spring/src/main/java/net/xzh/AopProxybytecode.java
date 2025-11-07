package net.xzh;

import java.io.FileOutputStream;

import net.xzh.service.UserSerice;
import sun.misc.ProxyGenerator;

/**
 * aop生成字节码
 */
@SuppressWarnings("restriction")
public class AopProxybytecode {

	public static void main(String[] args) {
		byte[] proxyClassFile = ProxyGenerator.generateProxyClass("$Proxy", new Class[] { UserSerice.class });
		try {
			FileOutputStream fileOutputStream = new FileOutputStream("Proxy.class");
			fileOutputStream.write(proxyClassFile);
			fileOutputStream.flush();
			fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
