package net.xzh.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import net.xzh.annotation.Login;
import net.xzh.domain.LoginUser;

/**
 * 这是使用注解的Controller
 * @author xzh
 *
 */
@Controller
public class IndexController {

	/**
	 * 首页
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@GetMapping("/index")
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		modelMap.put("name", "admin");
		return "index";
	}
	
	/**
	 * 我的桌面（仅仅是演示如何调用实现controller接口的类）
	 */
	@GetMapping("/dashboard2")
	public ModelAndView dashboard2(HttpSession session, ModelAndView modelAndView,@Login LoginUser loginUser) {
		modelAndView.addObject("welcome", "欢迎回来");
		modelAndView.addObject("userName", loginUser.getUsername());
		modelAndView.setViewName("dashboard");
		return modelAndView;
	}
	
}