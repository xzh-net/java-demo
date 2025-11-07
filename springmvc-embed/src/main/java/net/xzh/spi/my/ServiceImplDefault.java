package net.xzh.spi.my;

public class ServiceImplDefault implements SpiService {

	public void exec(String dd) {
		System.out.println("Default---"+dd);
	}

}
