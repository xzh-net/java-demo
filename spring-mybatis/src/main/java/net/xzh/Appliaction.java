package net.xzh;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import net.xzh.frame.scanner.MyScanner;
import net.xzh.service.UserService;

/**
 * spring整合mybatis
 * 
 * @author Administrator
 *
 */
@ComponentScan("net.xzh")
@MyScanner("net.xzh.mapper")
public class Appliaction {

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sqlSessionFactory;
	}

	public static void main(String[] args) throws IOException {
		try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext()) {
			applicationContext.register(Appliaction.class);// 配置类
			applicationContext.refresh();
			UserService userService = applicationContext.getBean("userService", UserService.class);
			userService.save();
		}
	}

}
