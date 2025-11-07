package net.xzh.service;

public class A {
	private B b;

	public void current() {
		System.out.println("A-" + System.currentTimeMillis());
	}

	public void test() {
		b.current();
	}

	public B getB() {
		return b;
	}

	public void setB(B b) {
		this.b = b;
	}

}
