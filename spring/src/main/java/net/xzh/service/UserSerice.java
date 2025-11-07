package net.xzh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserSerice {

	@Autowired
	private OrderSerice orderSerice;

	public void test() {
		orderSerice.current();
	}

	public void current() {
		System.out.println("UserSerice->" + System.currentTimeMillis());
	}
}
