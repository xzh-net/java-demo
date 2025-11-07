package net.xzh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderSerice {
	@Autowired
	private UserSerice userSerice;

	public void test() {
		userSerice.current();
	}
	
	public void current() {
		System.out.println("OrderSerice->"+System.currentTimeMillis());
	}

}
