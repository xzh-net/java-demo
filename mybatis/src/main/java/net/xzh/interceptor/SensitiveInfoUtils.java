package net.xzh.interceptor;

/**
 * 敏感信息屏蔽函数
 * 
 * @author admin
 *
 */
public final class SensitiveInfoUtils {

	/**
	 * [中文姓名] 只显示第一个汉字，其他隐藏为星号<例子：李**>
	 * 
	 * @param name 姓名
	 * @return
	 */
	public static String chineseName(String fullName) {
		if (isBlank(fullName)) {
			return "";
		}
		String name = left(fullName, 1);
		return rightPad(name, length(fullName), "*");
	}

	public static String chineseName(String familyName, String givenName) {
		if (isBlank(familyName) || isBlank(givenName)) {
			return "";
		}
		if (familyName.length() > 1) {
			String name = left(familyName, familyName.length());
			return rightPad(name, length(familyName + givenName), "*");
		}
		return chineseName(familyName + givenName);
	}

	/**
	 * [身份证号] 前六位，后四位，其他用星号隐藏每位1个星号<例子:451002********1647>
	 * 
	 * @param carId
	 * @return
	 */
	public static String idCard(String carId) {
		if (isBlank(carId)) {
			return "";
		}
		return left(carId, 6).concat(removeStart(leftPad(right(carId, 4), length(carId), "*"), "******"));
	}

	/**
	 * [固定电话] 后四位，其他隐藏<例子：****1234>
	 * 
	 * @param num
	 * @return
	 */
	public static String fixedPhone(String num) {
		if (isBlank(num)) {
			return "";
		}
		return leftPad(right(num, 4), length(num), "*");
	}

	/**
	 * [手机号码] 前三位，后四位，其他隐藏<例子:138******1234>
	 * 
	 * @param num
	 * @return
	 */
	public static String mobilePhone(String num) {
		if (isBlank(num)) {
			return "";
		}
		return left(num, 3).concat(removeStart(leftPad(right(num, 4), length(num), "*"), "***"));
	}

	/**
	 * [地址] 只显示到地区，不显示详细地址；我们要对个人信息增强保护<例子：北京市海淀区****>
	 * 
	 * @param address
	 * @param sensitiveSize 敏感信息长度
	 * @return
	 */
	public static String address(String address, int sensitiveSize) {
		if (isBlank(address)) {
			return "";
		}
		int length = length(address);
		return rightPad(left(address, length - sensitiveSize), length, "*");
	}

	/**
	 * [电子邮箱] 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示<例子:g**@163.com>
	 * 
	 * @param email
	 * @return
	 */
	public static String email(String email) {
		if (isBlank(email)) {
			return "";
		}
		int index = indexOf(email, "@");
		if (index <= 1)
			return email;
		else
			return rightPad(left(email, 1), index, "*").concat(mid(email, index, length(email)));
	}

	/**
	 * [银行卡号] 前六位，后四位，其他用星号隐藏每位1个星号<例子:6222600**********1234>
	 * 
	 * @param cardNum
	 * @return
	 */
	public static String bankCard(String cardNum) {
		if (isBlank(cardNum)) {
			return "";
		}
		return left(cardNum, 6).concat(removeStart(leftPad(right(cardNum, 4), length(cardNum), "*"), "******"));
	}

	public static void main(String[] args) {
		System.out.println(chineseName("今晚打老虎"));
	}

	/**
	 * [公司开户银行联号] 公司开户银行联行号,显示前两位，其他用星号隐藏，每位1个星号<例子:12********>
	 * 
	 * @param code
	 * @return
	 */
	public static String cnapsCode(String code) {
		if (isBlank(code)) {
			return "";
		}
		return rightPad(left(code, 2), length(code), "*");
	}

//    =====================================================================================================
//    copy by org.apache.commons.lang.StringUtils
//    =====================================================================================================

	public static final String EMPTY = "";
	public static final int INDEX_NOT_FOUND = -1;

	/**
	 * <p>
	 * The maximum size to which the padding constant(s) can expand.
	 * </p>
	 */
	private static final int PAD_LIMIT = 8192;

	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	public static String left(String str, int len) {
		if (str == null) {
			return null;
		}
		if (len < 0) {
			return EMPTY;
		}
		if (str.length() <= len) {
			return str;
		}
		return str.substring(0, len);
	}

	public static int length(String str) {
		return str == null ? 0 : str.length();
	}

	public static String rightPad(String str, int size, String padStr) {
		if (str == null) {
			return null;
		}
		if (isEmpty(padStr)) {
			padStr = " ";
		}
		int padLen = padStr.length();
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (padLen == 1 && pads <= PAD_LIMIT) {
			return rightPad(str, size, padStr.charAt(0));
		}

		if (pads == padLen) {
			return str.concat(padStr);
		} else if (pads < padLen) {
			return str.concat(padStr.substring(0, pads));
		} else {
			char[] padding = new char[pads];
			char[] padChars = padStr.toCharArray();
			for (int i = 0; i < pads; i++) {
				padding[i] = padChars[i % padLen];
			}
			return str.concat(new String(padding));
		}
	}

	public static String rightPad(String str, int size, char padChar) {
		if (str == null) {
			return null;
		}
		int pads = size - str.length();
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (pads > PAD_LIMIT) {
			return rightPad(str, size, String.valueOf(padChar));
		}
		return str.concat(padding(pads, padChar));
	}

	public static String rightPad(String str, int size) {
		return rightPad(str, size, ' ');
	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	private static String padding(int repeat, char padChar) throws IndexOutOfBoundsException {
		if (repeat < 0) {
			throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
		}
		final char[] buf = new char[repeat];
		for (int i = 0; i < buf.length; i++) {
			buf[i] = padChar;
		}
		return new String(buf);
	}

	public static String leftPad(String str, int size, char padChar) {
		if (str == null) {
			return null;
		}
		int pads = size - str.length();
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (pads > PAD_LIMIT) {
			return leftPad(str, size, String.valueOf(padChar));
		}
		return padding(pads, padChar).concat(str);
	}

	public static String leftPad(String str, int size, String padStr) {
		if (str == null) {
			return null;
		}
		if (isEmpty(padStr)) {
			padStr = " ";
		}
		int padLen = padStr.length();
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (padLen == 1 && pads <= PAD_LIMIT) {
			return leftPad(str, size, padStr.charAt(0));
		}

		if (pads == padLen) {
			return padStr.concat(str);
		} else if (pads < padLen) {
			return padStr.substring(0, pads).concat(str);
		} else {
			char[] padding = new char[pads];
			char[] padChars = padStr.toCharArray();
			for (int i = 0; i < pads; i++) {
				padding[i] = padChars[i % padLen];
			}
			return new String(padding).concat(str);
		}
	}

	public static String leftPad(String str, int size) {
		return leftPad(str, size, ' ');
	}

	public static String right(String str, int len) {
		if (str == null) {
			return null;
		}
		if (len < 0) {
			return EMPTY;
		}
		if (str.length() <= len) {
			return str;
		}
		return str.substring(str.length() - len);
	}

	public static String removeStart(String str, String remove) {
		if (isEmpty(str) || isEmpty(remove)) {
			return str;
		}
		if (str.startsWith(remove)) {
			return str.substring(remove.length());
		}
		return str;
	}

	public static String mid(String str, int pos, int len) {
		if (str == null) {
			return null;
		}
		if (len < 0 || pos > str.length()) {
			return EMPTY;
		}
		if (pos < 0) {
			pos = 0;
		}
		if (str.length() <= (pos + len)) {
			return str.substring(pos);
		}
		return str.substring(pos, pos + len);
	}

	public static int indexOf(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return INDEX_NOT_FOUND;
		}
		return str.indexOf(searchStr);
	}

}