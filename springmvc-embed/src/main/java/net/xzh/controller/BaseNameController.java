package net.xzh.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * controller定义方式
	1. 实现Controller接口,重写handleRequest方法
	2. 实现HttpRequestHandler接口，重写 handleRequest方法。这个实现方式与servlet 基本一致
	3. 使用@Controller注解配合扫描器
 *
 */
@Component("/index2")
public class BaseNameController implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView("index2");
		modelAndView.addObject("ofid", "admin_" + System.currentTimeMillis());
		return modelAndView;
	}

}
