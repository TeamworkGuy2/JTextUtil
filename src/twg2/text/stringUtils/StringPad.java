package twg2.text.stringUtils;

import java.util.Arrays;

/**
 * @author TeamworkGuy2
 * @since 2016-08-15
 */
public class StringPad {

	public static final String padLeft(String str, int maxLen, char padding) {
		int len = str.length();
		if(len >= maxLen) { return str; }

		int padLen = maxLen - len;
		char[] chs = new char[maxLen];
		Arrays.fill(chs, 0, padLen, padding);

		str.getChars(0, len, chs, padLen);
		return new String(chs);
	}


	public static final String padRight(String str, int maxLen, char padding) {
		int len = str.length();
		if(len >= maxLen) { return str; }

		char[] chs = new char[maxLen];
		str.getChars(0, len, chs, 0);

		Arrays.fill(chs, len, maxLen, padding);
		return new String(chs);
	}

}
