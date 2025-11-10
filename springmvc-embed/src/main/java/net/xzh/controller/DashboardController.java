package net.xzh.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * 实现Controller接口,重写handleRequest方法（映射地址在mvc中配置）
 * @author xzh
 *
 */
public class DashboardController implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView("dashboard");
		modelAndView.addObject("welcome", "欢迎回来");
		return modelAndView;
	}
	
}
