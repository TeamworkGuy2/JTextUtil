package twg2.text.stringUtils;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

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


	// ==== trim char ====
	public static final String trimLeading(String str, char leadingChar) {
		return trimLeading(str, leadingChar, false);
	}


	public static final String trimLeading(String str, char leadingChar, boolean trimRepeats) {
		int strLen = str.length();
		if(trimRepeats) {
			while(strLen > 0 && str.charAt(0) == leadingChar) {
				str = (strLen > 0 && str.charAt(0) == leadingChar) ? str.substring(1, strLen) : str;
				strLen--;
			}
			return str;
		}
		// remove a single leading char if matching
		return (strLen > 0 && str.charAt(0) == leadingChar) ? str.substring(1, strLen) : str;
	}


	public static final String trimTrailing(String str, char trailingChar) {
		return trimTrailing(str, trailingChar, false);
	}


	public static final String trimTrailing(String str, char trailingChar, boolean trimRepeats) {
		int strLen = str.length();
		if(trimRepeats) {
			while(strLen > 0 && str.charAt(str.length() - 1) == trailingChar) {
				str = (strLen > 0 && str.charAt(strLen - 1) == trailingChar) ? str.substring(0, strLen - 1) : str;
				strLen--;
			}
			return str;
		}
		// remove a single trailing char if matching
		return (strLen > 0 && str.charAt(strLen - 1) == trailingChar) ? str.substring(0, strLen - 1) : str;
	}


	// ==== trim String ====
	public static final String trimLeading(String str, String leadingStr) {
		return trimLeading(str, leadingStr, false);
	}


	public static final String trimLeading(String str, String leadingStr, boolean trimRepeats) {
		int strLen = str.length();
		int leadingStrLen = leadingStr.length();
		if(trimRepeats) {
			while(strLen >= leadingStrLen && str.startsWith(leadingStr)) {
				str = str.substring(leadingStrLen, strLen);
				strLen -= leadingStrLen;
			}
			return str;
		}
		// remove a single leading char if matching
		return (strLen > leadingStrLen && str.startsWith(leadingStr)) ? str.substring(leadingStrLen, strLen) : str;
	}


	public static final String trimTrailing(String str, String trailingStr) {
		return trimTrailing(str, trailingStr, false);
	}


	public static final String trimTrailing(String str, String trailingStr, boolean trimRepeats) {
		int strLen = str.length();
		int trailingStrLen = trailingStr.length();
		if(trimRepeats) {
			while(strLen >= trailingStrLen && str.endsWith(trailingStr)) {
				str = str.substring(0, strLen - trailingStrLen);
				strLen -= trailingStrLen;
			}
			return str;
		}
		// remove a single trailing char if matching
		return (strLen > 0 && str.endsWith(trailingStr)) ? str.substring(0, strLen - trailingStrLen) : str;
	}


	// ==== count and trim char ====
	public static final Map.Entry<Integer, String> countAndTrimLeading(String str, char ch) {
		return countAndTrimLeading(str, ch, false);
	}


	public static final Map.Entry<Integer, String> countAndTrimLeading(String str, char ch, boolean trimRepeats) {
		int strLen = str.length();
		int replacementCount = 0;
		if(trimRepeats) {
			while(strLen > 0 && str.charAt(0) == ch) {
				str = (strLen > 0 && str.charAt(0) == ch) ? str.substring(1, strLen) : str;
				strLen--;
				replacementCount++;
			}
			return new AbstractMap.SimpleImmutableEntry<>(replacementCount, str);
		}
		// remove a single leading char if matching
		if(strLen > 0 && str.charAt(0) == ch) {
			str = str.substring(1, strLen);
			strLen--;
			replacementCount++;
		}
		return new AbstractMap.SimpleImmutableEntry<>(replacementCount, str);
	}


	public static final Map.Entry<Integer, String> countAndTrimTrailing(String str, char ch) {
		return countAndTrimTrailing(str, ch, false);
	}


	public static final Map.Entry<Integer, String> countAndTrimTrailing(String str, char ch, boolean trimRepeats) {
		int strLen = str.length();
		int replacementCount = 0;
		if(trimRepeats) {
			while(strLen > 0 && str.charAt(str.length() - 1) == ch) {
				str = (strLen > 0 && str.charAt(strLen - 1) == ch) ? str.substring(0, strLen - 1) : str;
				strLen--;
				replacementCount++;
			}
			return new AbstractMap.SimpleImmutableEntry<>(replacementCount, str);
		}
		// remove a single trailing char if matching
		if(strLen > 0 && str.charAt(strLen - 1) == ch) {
			str = str.substring(0, strLen - 1);
			strLen--;
			replacementCount++;
		}
		return new AbstractMap.SimpleImmutableEntry<>(replacementCount, str);
	}


	// ==== count and trim String ====
	public static final Map.Entry<Integer, String> countAndTrimLeading(String str, String leadingStr) {
		return countAndTrimLeading(str, leadingStr, false);
	}


	public static final Map.Entry<Integer, String> countAndTrimLeading(String str, String leadingStr, boolean trimRepeats) {
		int strLen = str.length();
		int leadingStrLen = leadingStr.length();
		int replacementCount = 0;
		if(trimRepeats) {
			while(strLen > 0 && str.startsWith(leadingStr)) {
				str = str.substring(leadingStrLen, strLen);
				strLen -= leadingStrLen;
				replacementCount++;
				
			}
			return new AbstractMap.SimpleImmutableEntry<>(replacementCount, str);
		}
		// remove a single leading char if matching
		if(strLen > 0 && str.startsWith(leadingStr)) {
			str = str.substring(leadingStrLen, strLen);
			strLen -= leadingStrLen;
			replacementCount++;
		}
		return new AbstractMap.SimpleImmutableEntry<>(replacementCount, str);
	}


	public static final Map.Entry<Integer, String> countAndTrimTrailing(String str, String trailingStr) {
		return countAndTrimTrailing(str, trailingStr, false);
	}


	public static final Map.Entry<Integer, String> countAndTrimTrailing(String str, String trailingStr, boolean trimRepeats) {
		int strLen = str.length();
		int trailingStrLen = trailingStr.length();
		int replacementCount = 0;
		if(trimRepeats) {
			while(strLen > 0 && str.endsWith(trailingStr)) {
				str = str.substring(0, strLen - trailingStrLen);
				strLen -= trailingStrLen;
				replacementCount++;
			}
			return new AbstractMap.SimpleImmutableEntry<>(replacementCount, str);
		}
		// remove a single trailing char if matching
		if(strLen > 0 && str.endsWith(trailingStr)) {
			str = str.substring(0, strLen - trailingStrLen);
			strLen -= trailingStrLen;
			replacementCount++;
		}
		return new AbstractMap.SimpleImmutableEntry<>(replacementCount, str);
	}

}
