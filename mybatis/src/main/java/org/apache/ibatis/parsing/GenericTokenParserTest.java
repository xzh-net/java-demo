package org.apache.ibatis.parsing;

import java.util.HashMap;
import java.util.Map;

/**
 * sql解析测试
 * 
 * @author admin
 *
 */
public class GenericTokenParserTest {
	public static void main(String[] args) {
		Map<String, Object> mapper = new HashMap<String, Object>();
		mapper.put("name", "张三");
		mapper.put("pwd", "123456");
		// 先初始化一个handler
		GenericTokenParser genericTokenParser = new GenericTokenParser("${", "}", new TokenHandler() {
			@Override
			public String handleToken(String content) {
				return (String) mapper.get(content);
			}
		});
		System.out.println("****" + genericTokenParser.parse("用户：${name}，你的密码是:${pwd}"));

	}
}
