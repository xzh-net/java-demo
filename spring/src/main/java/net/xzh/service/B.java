package net.xzh.service;

public class B {
	private A a;

	public void current() {
		System.out.println("B-" + System.currentTimeMillis());
	}

	public void test() {
		a.current();
	}
	
	public A getA() {
		return a;
	}

	public void setA(A a) {
		this.a = a;
	}
}
