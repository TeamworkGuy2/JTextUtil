package twg2.text.stringUtils;

import java.util.List;

/**
 * @author TeamworkGuy2
 * @since 2015-0-0
 */
public class StringTrim {


	private StringTrim() { throw new AssertionError("cannot instantiate static class StringTrim"); }


	public static final void trim(List<String> strs, char ch) {
		for(int i = 0, size = strs.size(); i < size; i++) {
			String str = strs.get(i);
			if(str.length() > 0) {
				boolean first = str.charAt(0) == ch;
				boolean last = str.charAt(str.length() - 1) == ch;
				
				if(first || last) {
					if(first && last && str.length() > 1) {
						strs.set(i, str.substring(1, str.length() - 1));
					}
					else if(first) {
						// trim first only
						strs.set(i, str.substring(1, str.length()));
					}
					else {
						// trim last only
						strs.set(i, str.substring(0, str.length() - 1));
					}
				}
			}
		}
	}


	public static final void trim(List<String> strs, String trimStr) {
		int trimStrLen = trimStr.length();
		for(int i = 0, size = strs.size(); i < size; i++) {
			String str = strs.get(i);
			if(str.length() >= trimStrLen) {
				boolean first = str.startsWith(trimStr);
				boolean last = str.endsWith(trimStr);
				if(first || last) {
					if(first && last && str.length() >= trimStrLen * 2) {
						strs.set(i, str.substring(trimStrLen, str.length() - trimStrLen));
					}
					else if(first) {
						// trim first only
						strs.set(i, str.substring(trimStrLen, str.length()));
					}
					else {
						// trim last only
						strs.set(i, str.substring(0, str.length() - trimStrLen));
					}
				}
			}
		}
	}


	/** Trim quotes from the start and end of a list of strings if there
	 * are quotes {@code "} at both the beginning and end of the string.
	 * @param strs the list of strings to trim quotes from, the
	 * modified strings are replaced in the list by their trimmed versions.
	 * This list must be modifiable, its size will not be changed.
	 */
	public static final void trimQuotes(List<String> strs) {
		trimIfSurrounding(strs, '"');
	}


	public static final void trimIfSurrounding(List<String> strs, char ch) {
		for(int i = 0, size = strs.size(); i < size; i++) {
			String str = strs.get(i);
			if(str.length() > 1 && str.charAt(0) == ch && str.charAt(str.length() - 1) == ch) {
				strs.set(i, str.substring(1, str.length() - 1));
			}
		}
	}


	public static final void trimIfSurrounding(List<String> strs, String trimStr) {
		int trimStrLen = trimStr.length();
		for(int i = 0, size = strs.size(); i < size; i++) {
			String str = strs.get(i);
			if(str.length() >= trimStrLen * 2 && str.startsWith(trimStr) && str.endsWith(trimStr)) {
				strs.set(i, str.substring(trimStrLen, str.length() - trimStrLen));
			}
		}
	}


	public static final String trim(String str, char ch) {
		if(str.length() > 0) {
			boolean first = str.charAt(0) == ch;
			boolean last = str.charAt(str.length() - 1) == ch;
			if(first || last) {
				if(first && last && str.length() > 1) {
					return str.substring(1, str.length() - 1);
				}
				else if(first) {
					return str.substring(1, str.length());
				}
				else {
					return str.substring(0, str.length() - 1);
				}
			}
		}
		return str;
	}


	public static final String trim(String str, String trimStr) {
		int trimStrLen = trimStr.length();
		if(str.length() >= trimStrLen) {
			boolean first = str.startsWith(trimStr);
			boolean last = str.endsWith(trimStr);
			if(first || last) {
				if(first && last && str.length() >= trimStrLen * 2) {
					return str.substring(trimStrLen, str.length() - trimStrLen);
				}
				else if(first) {
					return str.substring(trimStrLen, str.length());
				}
				else {
					return str.substring(0, str.length() - trimStrLen);
				}
			}
		}
		return str;
	}


	/** Trim quotes from the start and end of a string if there
	 * are quotes {@code "} at both the beginning and end of the string.
	 * @param str the string to trim quotes from, the modified strings are
	 * replaced in the list by their trimmed versions.  This list must be
	 * modifiable, its size will not be changed.
	 */
	public static final String trimQuotes(String str) {
		return trimIfSurrounding(str, '"');
	}


	public static final String trimIfSurrounding(String str, char ch) {
		if(str.length() > 1 && str.charAt(0) == ch && str.charAt(str.length() - 1) == ch) {
			return str.substring(1, str.length() - 1);
		}
		return str;
	}


	public static final String trimIfSurrounding(String str, String trimStr) {
		int trimStrLen = trimStr.length();
		if(str.length() >= trimStrLen * 2 && str.startsWith(trimStr) && str.endsWith(trimStr)) {
			return str.substring(trimStrLen, str.length() - trimStrLen);
		}
		return str;
	}

}
