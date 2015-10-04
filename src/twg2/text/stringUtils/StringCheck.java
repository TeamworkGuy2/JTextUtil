package twg2.text.stringUtils;

/**
 * @author TeamworkGuy2
 * @since 2015-5-19
 */
public class StringCheck {
	public static final char[] SIMPLE_WHITESPACE_NOT_NEWLINE = new char[] {' '/* space: 32 */, '	'/* tab: 9 */,
		12/* vertical tab: 12 */ };
	public static final char[] SIMPLE_WHITESPACE = new char[] {' '/* space: 32 */, '	'/* tab: 9 */,
		12/* vertical tab: 12 */, '\n'/* line terminators */ };

	private StringCheck() { throw new AssertionError("cannot instantiate static class StringCheck"); }


	public static final boolean isNullOrEmpty(String str) {
		return str == null || str.length() == 0;
	}


	public static final boolean isNullOrWhitespace(String str) {
		return str == null || isWhitespace(str, 0, str.length());
	}


	public static final boolean isWhitespace(String str) {
		return isWhitespace(str, 0, str.length());
	}


	/** Check if a character sequence is entirely composed of whitespace
	 * @param str the string to check
	 * @return true if the string is entirely composed of whitespace, false if not
	 */
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
