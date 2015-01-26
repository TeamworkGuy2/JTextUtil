package stringUtils;

import java.util.List;
import java.util.RandomAccess;

/** Methods for finding the index of a specific character, string, or value
 * within a string
 * @author TeamworkGuy2
 * @since 2015-1-2
 */
@javax.annotation.Generated("StringTemplate")
public final class StringIndex {

	private StringIndex() { throw new AssertionError("cannot instantiate static class StringIndex"); }


	/**
	 * @see #indexOf(String, int, int, int)
	 */
	public static final int indexOf(String str, int strOff, int matchChar) {
		return indexOf(str, strOff, str.length() - strOff, matchChar);
	}


	/** Search for a character in an array of characters and return the absolute index
	 * where the character occurs, or -1 if the character does not exist in the main array
	 * @param str the character array to search
	 * @param strOff the offset into the character array at which to start searching
	 * @param strLen the number of chars to compare starting at {@strOff} in {@code str}
	 * @param matchChar the character to search for in {@code str}
	 * @return the index of the found matching character, from index 0, or -1 if the character could not be found
	 */
	public static final int indexOf(String str, int strOff, int strLen, int matchChar) {
		int strLength = strOff + strLen;

		if(matchChar < Character.MIN_SUPPLEMENTARY_CODE_POINT) {
			// search for the first matching character
			for(int i = strOff; i < strLength; i++) {
				if(str.charAt(i) == matchChar) {
					return i;
				}
			}
			return -1;
		}
		else {
			return indexOfSupplement(str, strOff, strLength - strOff, matchChar);
		}
	}


	/** Search for the index of a supplementary character in an array of characters
	 * @param str the array of characters to search
	 * @param strOff the offset into {@code str} at which to start comparing characters
	 * @param strLen the number of chars to compare starting at {@strOff} in {@code str}
	 * @param matchChar the supplementary characters to search for
	 * @return the lower index within the {@code str} array of the matching {@code matchChar}
	 */
	private static final int indexOfSupplement(String str, int strOff, int strLen, int matchChar) {
		if(Character.isValidCodePoint(matchChar)) {
			final char high = Character.highSurrogate(matchChar);
			final char low = Character.lowSurrogate(matchChar);
			final int maxI = strOff + strLen - 1;
			for(int i = strOff; i < maxI; i++) {
				if(str.charAt(i) == high && str.charAt(i + 1) == low) {
					return i;
				}
			}
		}
		return -1;
	}


	/**
	 * @see #indexOf(char[], int, int, int)
	 */
	public static final int indexOf(char[] str, int strOff, int matchChar) {
		return indexOf(str, strOff, str.length - strOff, matchChar);
	}


	/** Search for a character in an array of characters and return the absolute index
	 * where the character occurs, or -1 if the character does not exist in the main array
	 * @param str the character array to search
	 * @param strOff the offset into the character array at which to start searching
	 * @param strLen the number of chars to compare starting at {@strOff} in {@code str}
	 * @param matchChar the character to search for in {@code str}
	 * @return the index of the found matching character, from index 0, or -1 if the character could not be found
	 */
	public static final int indexOf(char[] str, int strOff, int strLen, int matchChar) {
		int strLength = strOff + strLen;

		if(matchChar < Character.MIN_SUPPLEMENTARY_CODE_POINT) {
			// search for the first matching character
			for(int i = strOff; i < strLength; i++) {
				if(str[i] == matchChar) {
					return i;
				}
			}
			return -1;
		}
		else {
			return indexOfSupplement(str, strOff, strLength - strOff, matchChar);
		}
	}


	/** Search for the index of a supplementary character in an array of characters
	 * @param str the array of characters to search
	 * @param strOff the offset into {@code str} at which to start comparing characters
	 * @param strLen the number of chars to compare starting at {@strOff} in {@code str}
	 * @param matchChar the supplementary characters to search for
	 * @return the lower index within the {@code str} array of the matching {@code matchChar}
	 */
	private static final int indexOfSupplement(char[] str, int strOff, int strLen, int matchChar) {
		if(Character.isValidCodePoint(matchChar)) {
			final char high = Character.highSurrogate(matchChar);
			final char low = Character.lowSurrogate(matchChar);
			final int maxI = strOff + strLen - 1;
			for(int i = strOff; i < maxI; i++) {
				if(str[i] == high && str[i + 1] == low) {
					return i;
				}
			}
		}
		return -1;
	}


	/**
	 * @see #indexOf(String, int, int, String, int, int)
	 */
	public static final int indexOf(String str, int strOff, String subStr) {
		return indexOf(str, strOff, str.length() - strOff, subStr, 0, subStr.length());
	}


	/**
	 * @see #indexOf(String, int, int, String, int, int)
	 */
	public static final int indexOf(String str, int strOff, String subStr, int subStrOff) {
		return indexOf(str, strOff, str.length() - strOff, subStr, subStrOff, subStr.length() - subStrOff);
	}


	/**
	 * @see #indexOf(String, int, int, String, int, int)
	 */
	public static final int indexOf(String str, int strOff, int strLen, String subStr) {
		return indexOf(str, strOff, strLen, subStr, 0, subStr.length());
	}


	/**
	 * @see #indexOf(String, int, int, String, int, int)
	 */
	public static final int indexOf(String str, int strOff, int strLen, String subStr, int subStrOff) {
		return indexOf(str, strOff, strLen, subStr, subStrOff, subStr.length() - subStrOff);
	}


	/** Search for a sub String in a String and return the absolute index
	 * where the sub String begins, or -1 if the sub String does not exist in the main String
	 * @param str the String to search
	 * @param strOff the offset into the character array at which to start searching
	 * @param strLen the number of chars to compare starting at {@strOff} in {@code str}
	 * @param subStr the sub String to search for in {@code str}
	 * @param subStrOff the offset into the sub string array at which to start searching
	 * @param subStrLen the number of chars to search for starting at {@code subStrOff} in {@code subStr}
	 * @return the index of the found sub string, from index 0, or -1 if the sub string could not be found
	 */
	public static final int indexOf(String str, int strOff, int strLen, String subStr, int subStrOff, int subStrLen) {
		// if the sub string has a length of zero, return the offset into the search string, no comparison needed
		if(subStrLen == 0) { return strOff; }

		char firstChar = subStr.charAt(subStrOff);
		int maxI = strOff + strLen;
		int maxK = subStrOff + subStrLen;

		for(int i = strOff; i < maxI; i++) {
			// search for the first matching character
			while(i < maxI && str.charAt(i) != firstChar) { i++; }

			if(i < maxI) {
				// j and k have + 1 because the first character was already matched by the previous loop
				int j = i + 1;
				for(int k = subStrOff + 1; k < maxK && j < maxI && str.charAt(j) == subStr.charAt(k); j++, k++) {
				}
				// if the entire substring was found
				if(j == i + subStrLen) {
					return j-subStrLen;
				}
			}
		}
		return -1;
	}


	/**
	 * @see #indexOf(String, int, int, char[], int, int)
	 */
	public static final int indexOf(String str, int strOff, char[] subStr) {
		return indexOf(str, strOff, str.length() - strOff, subStr, 0, subStr.length);
	}


	/**
	 * @see #indexOf(String, int, int, char[], int, int)
	 */
	public static final int indexOf(String str, int strOff, char[] subStr, int subStrOff) {
		return indexOf(str, strOff, str.length() - strOff, subStr, subStrOff, subStr.length - subStrOff);
	}


	/**
	 * @see #indexOf(String, int, int, char[], int, int)
	 */
	public static final int indexOf(String str, int strOff, int strLen, char[] subStr) {
		return indexOf(str, strOff, strLen, subStr, 0, subStr.length);
	}


	/**
	 * @see #indexOf(String, int, int, char[], int, int)
	 */
	public static final int indexOf(String str, int strOff, int strLen, char[] subStr, int subStrOff) {
		return indexOf(str, strOff, strLen, subStr, subStrOff, subStr.length - subStrOff);
	}


	/** Search for a sub char[] in a String and return the absolute index
	 * where the sub char[] begins, or -1 if the sub char[] does not exist in the main String
	 * @param str the String to search
	 * @param strOff the offset into the character array at which to start searching
	 * @param strLen the number of chars to compare starting at {@strOff} in {@code str}
	 * @param subStr the sub char[] to search for in {@code str}
	 * @param subStrOff the offset into the sub string array at which to start searching
	 * @param subStrLen the number of chars to search for starting at {@code subStrOff} in {@code subStr}
	 * @return the index of the found sub string, from index 0, or -1 if the sub string could not be found
	 */
	public static final int indexOf(String str, int strOff, int strLen, char[] subStr, int subStrOff, int subStrLen) {
		// if the sub string has a length of zero, return the offset into the search string, no comparison needed
		if(subStrLen == 0) { return strOff; }

		char firstChar = subStr[subStrOff];
		int maxI = strOff + strLen;
		int maxK = subStrOff + subStrLen;

		for(int i = strOff; i < maxI; i++) {
			// search for the first matching character
			while(i < maxI && str.charAt(i) != firstChar) { i++; }

			if(i < maxI) {
				// j and k have + 1 because the first character was already matched by the previous loop
				int j = i + 1;
				for(int k = subStrOff + 1; k < maxK && j < maxI && str.charAt(j) == subStr[k]; j++, k++) {
				}
				// if the entire substring was found
				if(j == i + subStrLen) {
					return j-subStrLen;
				}
			}
		}
		return -1;
	}


	/**
	 * @see #indexOf(char[], int, int, String, int, int)
	 */
	public static final int indexOf(char[] str, int strOff, String subStr) {
		return indexOf(str, strOff, str.length - strOff, subStr, 0, subStr.length());
	}


	/**
	 * @see #indexOf(char[], int, int, String, int, int)
	 */
	public static final int indexOf(char[] str, int strOff, String subStr, int subStrOff) {
		return indexOf(str, strOff, str.length - strOff, subStr, subStrOff, subStr.length() - subStrOff);
	}


	/**
	 * @see #indexOf(char[], int, int, String, int, int)
	 */
	public static final int indexOf(char[] str, int strOff, int strLen, String subStr) {
		return indexOf(str, strOff, strLen, subStr, 0, subStr.length());
	}


	/**
	 * @see #indexOf(char[], int, int, String, int, int)
	 */
	public static final int indexOf(char[] str, int strOff, int strLen, String subStr, int subStrOff) {
		return indexOf(str, strOff, strLen, subStr, subStrOff, subStr.length() - subStrOff);
	}


	/** Search for a sub String in a char[] and return the absolute index
	 * where the sub String begins, or -1 if the sub String does not exist in the main char[]
	 * @param str the char[] to search
	 * @param strOff the offset into the character array at which to start searching
	 * @param strLen the number of chars to compare starting at {@strOff} in {@code str}
	 * @param subStr the sub String to search for in {@code str}
	 * @param subStrOff the offset into the sub string array at which to start searching
	 * @param subStrLen the number of chars to search for starting at {@code subStrOff} in {@code subStr}
	 * @return the index of the found sub string, from index 0, or -1 if the sub string could not be found
	 */
	public static final int indexOf(char[] str, int strOff, int strLen, String subStr, int subStrOff, int subStrLen) {
		// if the sub string has a length of zero, return the offset into the search string, no comparison needed
		if(subStrLen == 0) { return strOff; }

		char firstChar = subStr.charAt(subStrOff);
		int maxI = strOff + strLen;
		int maxK = subStrOff + subStrLen;

		for(int i = strOff; i < maxI; i++) {
			// search for the first matching character
			while(i < maxI && str[i] != firstChar) { i++; }

			if(i < maxI) {
				// j and k have + 1 because the first character was already matched by the previous loop
				int j = i + 1;
				for(int k = subStrOff + 1; k < maxK && j < maxI && str[j] == subStr.charAt(k); j++, k++) {
				}
				// if the entire substring was found
				if(j == i + subStrLen) {
					return j-subStrLen;
				}
			}
		}
		return -1;
	}


	/**
	 * @see #indexOf(char[], int, int, char[], int, int)
	 */
	public static final int indexOf(char[] str, int strOff, char[] subStr) {
		return indexOf(str, strOff, str.length - strOff, subStr, 0, subStr.length);
	}


	/**
	 * @see #indexOf(char[], int, int, char[], int, int)
	 */
	public static final int indexOf(char[] str, int strOff, char[] subStr, int subStrOff) {
		return indexOf(str, strOff, str.length - strOff, subStr, subStrOff, subStr.length - subStrOff);
	}


	/**
	 * @see #indexOf(char[], int, int, char[], int, int)
	 */
	public static final int indexOf(char[] str, int strOff, int strLen, char[] subStr) {
		return indexOf(str, strOff, strLen, subStr, 0, subStr.length);
	}


	/**
	 * @see #indexOf(char[], int, int, char[], int, int)
	 */
	public static final int indexOf(char[] str, int strOff, int strLen, char[] subStr, int subStrOff) {
		return indexOf(str, strOff, strLen, subStr, subStrOff, subStr.length - subStrOff);
	}


	/** Search for a sub char[] in a char[] and return the absolute index
	 * where the sub char[] begins, or -1 if the sub char[] does not exist in the main char[]
	 * @param str the char[] to search
	 * @param strOff the offset into the character array at which to start searching
	 * @param strLen the number of chars to compare starting at {@strOff} in {@code str}
	 * @param subStr the sub char[] to search for in {@code str}
	 * @param subStrOff the offset into the sub string array at which to start searching
	 * @param subStrLen the number of chars to search for starting at {@code subStrOff} in {@code subStr}
	 * @return the index of the found sub string, from index 0, or -1 if the sub string could not be found
	 */
	public static final int indexOf(char[] str, int strOff, int strLen, char[] subStr, int subStrOff, int subStrLen) {
		// if the sub string has a length of zero, return the offset into the search string, no comparison needed
		if(subStrLen == 0) { return strOff; }

		char firstChar = subStr[subStrOff];
		int maxI = strOff + strLen;
		int maxK = subStrOff + subStrLen;

		for(int i = strOff; i < maxI; i++) {
			// search for the first matching character
			while(i < maxI && str[i] != firstChar) { i++; }

			if(i < maxI) {
				// j and k have + 1 because the first character was already matched by the previous loop
				int j = i + 1;
				for(int k = subStrOff + 1; k < maxK && j < maxI && str[j] == subStr[k]; j++, k++) {
				}
				// if the entire substring was found
				if(j == i + subStrLen) {
					return j-subStrLen;
				}
			}
		}
		return -1;
	}


	/** Find the index of a matching sub-string that is not proceeded by a specified prefix<br>
	 * For example, if<br>
	 * {@code str="//C style string containing a C style comment"}<br>
	 * {@code subStr="C style"}<br>
	 * {@code prefix="//"}<br>
	 * the return index would be {@code 30}, not {@code 2}, because the first
	 * instance of {@code "C style"} at index 2 is proceeded by the prefix {@code "//"}.
	 * @param str the string to search
	 * @param strOff the offset into the string at which to start searching
	 * @param subStr the sub-string to search for
	 * @param subStrOff the offset into the sub-string at which the sub-string starts,
	 * the sub-string is assumed occupy the remaining portion of the array
	 * @param prefix the prefix string
	 * @param prefixOff the offset into the prefix string at which the prefix starts,
	 * the sub-string is assumed occupy the remaining portion of the array
	 * @return the index of the start of a matching {@code subStr} within {@code str} that
	 * is not proceeded by {@code prefix}.
	 */
	public static final int indexOfNotPrefixedBy(char[] str, int strOff, String subStr, int subStrOff,
			char[] prefix, int prefixOff) {
		int prefixLen = prefix.length - prefixOff;
		int index = indexOf(str, strOff, subStr, subStrOff);
		while(index > -1) {
			if(index-strOff >= prefixLen) {
				boolean equal = StringCompare.equal(str, index - prefixLen, prefix, prefixOff, prefixLen);
				if(equal == false) {
					return index;
				}
			}
			else {
				return index;
			}
			index = indexOf(str, index+prefixLen, subStr, subStrOff);
		}
		return -1;
	}


	/** Find the index of a matching sub-string that is not proceeded by a specified prefix<br>
	 * For example, if<br>
	 * {@code str="//C style string containing a C style comment"}<br>
	 * {@code subStr="C style"}<br>
	 * {@code prefix="//"}<br>
	 * the return index would be {@code 30}, not {@code 2}, because the first
	 * instance of {@code "C style"} at index 2 is proceeded by the prefix {@code "//"}.
	 * @param str the string to search
	 * @param strOff the offset into the string at which to start searching
	 * @param subStr the sub-string to search for
	 * @param subStrOff the offset into the sub-string at which the sub-string starts,
	 * the sub-string is assumed occupy the remaining portion of the array
	 * @param prefix the prefix string
	 * @param prefixOff the offset into the prefix string at which the prefix starts,
	 * the sub-string is assumed occupy the remaining portion of the array
	 * @return the index of the start of a matching {@code subStr} within {@code str} that
	 * is not proceeded by {@code prefix}.
	 */
	public static final int indexOfNotPrefixedBy(char[] str, int strOff, char[] subStr, int subStrOff,
			char[] prefix, int prefixOff) {
		int prefixLen = prefix.length - prefixOff;
		int index = indexOf(str, strOff, subStr, subStrOff);
		while(index > -1) {
			if(index-strOff >= prefixLen) {
				boolean equal = StringCompare.equal(str, index - prefixLen, prefix, prefixOff, prefixLen);
				if(equal == false) {
					return index;
				}
			}
			else {
				return index;
			}
			index = indexOf(str, index + prefixLen, subStr, subStrOff);
		}
		return -1;
	}


	/** Search for a matching sub-string in an array characters from a list of possible sub-strings
	 * @param str the character array to search
	 * @param strOff the offset into the character array at which to start searching
	 * @param matchStrs the list of string to search for in {@code str}
	 * @return the index of the found sub-string, in the range {@code [0, matchStrs.size()-1]},
	 * or -1 if the sub-string could not be found
	 */
	public static final int indexOfMatch(char[] str, int strOff, List<String> matchStrs) {
		int matchStrIndex = -1;
		if(matchStrs instanceof RandomAccess) {
			for(int i = 0, size = matchStrs.size(); i < size; i++) {
				if((matchStrIndex = StringIndex.indexOf(str, strOff, matchStrs.get(i), 0)) != -1) {
					matchStrIndex = i;
					break;
				}
			}
		}
		else {
			int i = 0;
			for(String matchStr : matchStrs) {
				if((matchStrIndex = StringIndex.indexOf(str, strOff, matchStr, 0)) != -1) {
					matchStrIndex = i;
					break;
				}
				i++;
			}
		}
		return matchStrIndex;
	}


	/** Search for a sub-string in an array of characters from an array of possible sub-strings
	 * @param str the character array to search
	 * @param strOff the offset into the character array at which to start searching
	 * @param matchStrs the array of string to search for in {@code str}
	 * @return the index of the found sub-string, in the range {@code [0, matchStrs.size()-1]},
	 * or -1 if the sub-string could not be found
	 */
	public static final int indexOfMatch(char[] str, int strOff, String[] matchStrs) {
		int matchStrIndex = -1;
		for(int i = 0, size = matchStrs.length; i < size; i++) {
			if((matchStrIndex = StringIndex.indexOf(str, strOff, matchStrs[i], 0)) != -1) {
				matchStrIndex = i;
				break;
			}
		}
		return matchStrIndex;
	}


	/** Search for a matching sub-string in an array characters from a list of possible sub-strings
	 * @param str the character array to search
	 * @param strOff the offset into the character array at which to start searching
	 * @param matchStrs the list of string to search for in {@code str}
	 * @return the index of the found sub-string, in the range {@code [0, matchStrs.size()-1]},
	 * or -1 if the sub-string could not be found
	 */
	public static final int indexOfMatchNotPrefixedBy(char[] str, int strOff, List<String> matchStrs,
			char[] prefix, int prefixOff) {
		int matchStrIndex = -1;
		if(matchStrs instanceof RandomAccess) {
			for(int i = 0, size = matchStrs.size(); i < size; i++) {
				if((matchStrIndex = StringIndex.indexOfNotPrefixedBy(str, strOff,
						matchStrs.get(i), 0, prefix, prefixOff)) != -1) {
					matchStrIndex = i;
					break;
				}
			}
		}
		else {
			int i = 0;
			for(String matchStr : matchStrs) {
				if((matchStrIndex = StringIndex.indexOf(str, strOff, matchStr, 0)) != -1) {
					matchStrIndex = i;
					break;
				}
				i++;
			}
		}
		return matchStrIndex;
	}


	/** Search for a sub string of characters in an array of characters and return the absolute index
	 * where the sub string begins, or -1 if the sub string does not exist in the main array
	 * @param str the char sequence to search
	 * @param strOff the offset into the character array at which to start searching
	 * @param subStr the sub string to search for in {@code str}
	 * @param subStrOff the offset into the sub string array at which to start searching
	 * @return the index of the found sub string, from index 0, or -1 if the sub string could not be found
	 */
	public static final int indexOf(CharSequence str, int strOff, CharSequence subStr, int subStrOff, int len) {
		int subStrCount = len; // don't check for subStr bounds, let charAt() throw IOOBE
		// if the sub string has a length of zero, return the offset into the search string, no comparison needed
		if(subStrCount == 0) { return strOff; }

		char firstChar = subStr.charAt(subStrOff);
		int maxI = str.length();
		int maxK = subStrOff + subStrCount;

		for(int i = strOff; i < maxI; i++) {
			// search for the first matching character
			while(i < maxI && str.charAt(i) != firstChar) { i++; }

			if(i < maxI) {
				// j and k have + 1 because the first character was already matched by the previous loop
				int j = i + 1;
				for(int k = subStrOff + 1; k < maxK && j < maxI && str.charAt(j) == subStr.charAt(k); j++, k++) {
				}
				// if the entire substring was found
				if(j == i + subStrCount) {
					return j - subStrCount;
				}
			}
		}
		return -1;
	}


	/** Search for a sub string of characters in an array of characters and return the absolute index
	 * where the sub string begins, or -1 if the sub string does not exist in the main array
	 * @param str the char sequence to search
	 * @param strOff the offset into the character array at which to start searching
	 * @param subStr the sub string to search for in {@code str}
	 * @param subStrOff the offset into the sub string array at which to start searching
	 * @return the index of the found sub string, from index 0, or -1 if the sub string could not be found
	 */
	public static final int indexOf(CharSequence str, int strOff, int searchChar) {
		int maxI = str.length();

		for(int i = strOff; i < maxI; i++) {
			if(str.charAt(i) == searchChar) {
				return i;
			}
		}
		return -1;
	}


	/** Returns the list index of the string that starts with {@code strBuilder}
	 * @param strs the list of strings, the start of each is compared to {@code strBuilder}
	 * @param strBuilder the string builder contents to compare to the start of each string
	 * in {@code strs}
	 * @param offsetStrB the offset into the string builder's contents at which to start
	 * comparing characters to strings from {@code strs}
	 * @return the {@code str} index of the string that starts with the contents of {@code strBldr},
	 * -1 if none of the strings start with {@code strBuilder}
	 */
	public static final int startsWithIndex(List<String> strs, StringBuilder strBuilder, int offsetStrB) {
		for(int strI = 0, len = strs.size(); strI < len; strI++) {
			if(StringCompare.compareStartsWith(strs.get(strI), strBuilder, offsetStrB) == 0) {
				return strI;
			}
		}
		return -1;
	}

}
