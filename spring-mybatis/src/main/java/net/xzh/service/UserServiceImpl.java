package net.xzh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.xzh.mapper.OrderMapper;
import net.xzh.mapper.UserMapper;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private OrderMapper orderMapper;

	@Override
	public void save() {
		System.out.println(userMapper.selectByPrimaryKey(1L));
		System.out.println(orderMapper.selectByPrimaryKey(8L));
	}

}
