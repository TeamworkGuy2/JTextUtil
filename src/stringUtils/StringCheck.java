package stringUtils;

/**
 * @author TeamworkGuy2
 * @since 2015-5-19
 */
public class StringCheck {


	public static final boolean isNullOrEmpty(String str) {
		return str == null || str.length() == 0;
	}


	public static final boolean isNullOrWhitespace(String str) {
		return str == null || isWhitespace(str, 0, str.length());
	}


	public static final boolean isWhitespace(String str) {
		return isWhitespace(str, 0, str.length());
	}


	public static final boolean isWhitespace(String str, int off, int len) {
		for(int i = 0; i < len; i++) {
			if(!Character.isWhitespace(str.charAt(off + i))) {
				return false;
			}
		}
		return true;
	}


	public static final boolean isWhitespace(StringBuilder strBldr, int off, int len) {
		for(int i = 0; i < len; i++) {
			if(!Character.isWhitespace(strBldr.charAt(off + i))) {
				return false;
			}
		}
		return true;
	}


	public static final boolean isWhitespace(char[] chs, int off, int len) {
		for(int i = 0; i < len; i++) {
			if(!Character.isWhitespace(chs[off + i])) {
				return false;
			}
		}
		return true;
	}

}
