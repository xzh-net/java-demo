package net.xzh.domain;

import java.util.Set;

/**
 * 用户登录信息
 * @author xzh
 *
 */

public class LoginUser {
	private String username;
	private String password;
	private Set<String> permissions;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<String> getPermissions() {
		return permissions;
	}
	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}

}
