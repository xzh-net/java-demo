package net.xzh.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.xzh.annotation.UserParam;
import net.xzh.domain.UserEntity;

@Controller
public class IndexController {

	@RequestMapping("/index")
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		map.put("name", "admin");
		return "index";
	}

	@RequestMapping("/index1")
	public ModelAndView main() {
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("name", "admin" + System.currentTimeMillis());
		return modelAndView;
	}

	@RequestMapping("/json1")
	@ResponseBody
	public UserEntity getUser() {
		return new UserEntity("xcg", 18);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/json2")
	@ResponseBody // user 对象 Map
	public Object test(String name, HttpServletRequest request, HttpServletResponse response, @UserParam Map map) {
		map.put("name", request.getParameter("name"));
		return map;
	}
}