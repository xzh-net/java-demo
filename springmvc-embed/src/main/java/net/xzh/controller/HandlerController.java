package net.xzh.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

/**
 * controller定义方式
	1. 实现Controller接口,重写handleRequest方法
	2. 实现HttpRequestHandler接口，重写 handleRequest方法。这个实现方式与servlet 基本一致
	3. 使用@Controller注解配合扫描器
 *
 */
@Component("/index3")
public class HandlerController implements HttpRequestHandler {
//	支持servlet参数
//	HttpServletRequest
//	HttpServletResponse
//	HttpSession
//	java.security.Principal
//	Locale
//	InputStream
//	OutputStream
//	Reader
//	Writer
	
	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("index3");
        //设置返回页面
		response.getWriter().println("implements HttpRequestHandler");
//		response.sendRedirect("index1");//客户端重定向
//		request.getRequestDispatcher("index2").forward(request,response);//服务器跳转
		
		
	}
}
