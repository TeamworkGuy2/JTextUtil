package stringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;

/** Utility methods for comparing portions of strings.<br>
 * Includes {@link #startsWith(String, String...)} and {@link #endsWith(String, String...)}
 * to find common prefixes and suffixes amongst a group of strings.<br>
 * And the complementary {@link #searchStartsWith(List, StringBuilder)} that searching for
 * any string in a list that starts with a specified sub-string.<br>
 * {@link #compareEqualCount(CharSequence, CharSequence)} to compare how many consecutive
 * characters are equal between two strings.
 * And more comparison and string search methods...
 * @author TeamworkGuy2
 * @since 2014-11-3
 */
public final class StringCompare {

	private StringCompare() { throw new AssertionError("cannot instantiate StringCompare"); }


	/** Check if the specified string starts with any of a set of prefixes
	 * @param str the main string to compare prefixes to
	 * @param prefixes the set of prefixes to compare to the beginning of {@code str}
	 * @return true if {@code str} starts with any one of the {@code prefixes}
	 */
	public static final boolean startsWith(String str, String... prefixes) {
		if(prefixes != null) {
			for(String prefix : prefixes) {
				if(str.startsWith(prefix)) {
					return true;
				}
			}
		}
		return false;
	}


	/** Check if the specified string ends with any of a set of suffixes
	 * @param str the main string to compare suffixes to
	 * @param suffixes the set of suffixes to compare to the end of {@code str}
	 * @return true if {@code str} ends with any one of the {@code suffixes}
	 */
	public static final boolean endsWith(String str, String... suffixes) {
		if(suffixes != null) {
			for(String suffix : suffixes) {
				if(str.endsWith(suffix)) {
					return true;
				}
			}
		}
		return false;
	}


	/** Check if the first char array starts with the second char array
	 * @param str the full char array to check, must be equal to or longer than {@code subStr}
	 * @param strOff the offset into {@code str} at which to start comparing characters
	 * @param subStr the sub array to compare to the start of {@code str}
	 * @param subStrOff the offset into {@code subStr} at which to start comparing characters
	 * @return true if {@code str} starts with {@code subStr}, false otherwise
	 */
	public static final boolean startsWith(char[] str, int strOff, char[] subStr, int subStrOff) {
		int i = strOff + (subStr.length - subStrOff) - 1;
		int k = subStr.length - 1;
		if(i >= str.length) {
			return false;
		}
		while(k >= subStrOff) {
			if(str[i] != subStr[k]) {
				return false;
			}
			k--;
			i--;
		}
		return true;
	}


	/** Returns true if any of the list of strings starts with {@code strBldr}
	 * @param strs the list of strings, the start of each is compared to {@code strBldr}
	 * @param strBuilder the string builder contents to compare to the start of each string
	 * in {@code strs}
	 * @return true if any of the strings in {@code strs} starts with the contents of {@code strBldr},
	 * false if none of the strings start with {@code strBldr}
	 */
	public static final boolean searchStartsWith(List<String> strs, StringBuilder strBuilder) {
		for(int strI = 0, len = strs.size(); strI < len; strI++) {
			if(compareStartsWith(strs.get(strI), strBuilder, 0) == 0) {
				return true;
			}
		}
		return false;
	}


	/** Returns true if any of the list of strings starts with {@code strBldr}
	 * @param strs the list of strings, the start of each is compared to {@code strBldr}
	 * @param strBldr the string builder contents to compare to the start of each string
	 * in {@code strs}
	 * @param strBldrOffset the offset into the string builder's contents at which to start
	 * comparing characters to strings from {@code strs}
	 * @return true if any of the strings in {@code strs} starts with the contents of {@code strBldr},
	 * false if none of the strings start with {@code strBldr}
	 */
	public static final boolean searchStartsWith(List<String> strs, StringBuilder strBldr, int strBldrOffset) {
		for(int strI = 0, len = strs.size(); strI < len; strI++) {
			if(compareStartsWith(strs.get(strI), strBldr, strBldrOffset) == 0) {
				return true;
			}
		}
		return false;
	}


	/** Compare a string to the string builder's contents. Returns 0 if {@code str} starts
	 * with the contents of {@code strBldr}.
	 * <pre>
	 * str="b" > strB="ab"
	 * str="ab" < strB="b"
	 * str="ab" < strB="abc"
	 * str="abc" == strB="ab"
	 * str="abc" == strB="abc"
	 * </pre>
	 * @param str the string to compare to
	 * @param strBldr the string builder contents to compare to the beginning of {@code str}
	 * @param strBldrOffset the offset into the string builder's contents at which to start
	 * comparing characters to {@code str}
	 * @return 0 if {@code str} starts with {@code strBldr}, greater than 0 if {@code str}
	 * is greater than {@code strBldr}, less than 0 if {@code str} is less than {@code strBldr}
	 */
	public static final int compareStartsWith(String str, StringBuilder strBldr, int strBldrOffset) {
		int len = str.length() > (strBldr.length()-strBldrOffset) ? (strBldr.length()-strBldrOffset) : str.length();
		int k = 0;
		for( ; k < len; k++) {
			int c1 = str.charAt(k);
			int c2 = strBldr.charAt(strBldrOffset+k);
			if(c1 != c2) {
				return c1 - c2;
			}
		}
		if((strBldr.length()-strBldrOffset == 0 || k == 0) && str.length() != 0) { return 1; }
		return k == strBldr.length()-strBldrOffset ? 0 : str.length() - (strBldr.length()-strBldrOffset);
	}


	/** Compares a string to a string builder's contents
	 * @param str the string to compare
	 * @param strB the string builder to compare
	 * @return true if the string and string builder's contents are equal, false otherwise
	 */
	public static final boolean equal(String str, StringBuilder strB) {
		if(str.length() != strB.length()) { return false; }
		for(int i = str.length()-1; i > -1; i--) {
			if(str.charAt(i) != strB.charAt(i)) { return false; }
		}
		return true;
	}


	/** Check if portions of two strings are equal
	 * @param str1 the first char array to compare characters from 
	 * @param str1Off the offset into {@code str1} at which to start comparing characters
	 * @param str2 the second char array to compare characters from
	 * @param str2Off the offset into {@code str2} at which to start comparing characters
	 * @param len the number of characters to compare
	 * @return  true if the sub-portions of {@code str1} and {@code str2} match
	 */
	public static final boolean equal(char[] str1, int str1Off, char[] str2, int str2Off, int len) {
		if(str1Off+len > str1.length) {
			return false;
		}
		for(int i = len-1; i > -1; i--) {
			if(str1[str1Off+i] != str2[str2Off+i]) {
				return false;
			}
		}
		return true;
	}


	/** Check if a character sequence is entirely composed of whitespace
	 * @param str the string to check
	 * @return true if the string is entirely composed of whitespace, false if not
	 */
	public static final boolean isWhitespace(CharSequence str) {
		for(int i = 0, size = str.length(); i < size; i++) {
			if(!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}


	public static final boolean containsEqualIgnoreCase(String[] strs, String str) {
		for(int i = 0, size = strs.length; i < size; i++) {
			if(strs[i].equalsIgnoreCase(str)) {
				return true;
			}
		}
		return false;
	}


	public static final boolean containsIgnoreCase(String[] strs, String str) {
		String strUpper = str.toUpperCase();
		for(int i = 0, size = strs.length; i < size; i++) {
			if(strs[i].toUpperCase().contains(strUpper)) {
				return true;
			}
		}
		return false;
	}


	/** Check if a collection of strings contains one or more strings equal to {@code str}
	 * @param strs
	 * @param str
	 * @return
	 */
	public static final boolean containsEqualIgnoreCase(Collection<String> strs, String str) {
		if(strs instanceof RandomAccess && strs instanceof List) {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			List<String> strList = (List)strs;
			for(int i = 0, size = strList.size(); i < size; i++) {
				if(strList.get(i).equalsIgnoreCase(str)) {
					return true;
				}
			}
		}
		else {
			for(String s : strs) {
				if(s.equalsIgnoreCase(str)) {
					return true;
				}
			}
		}
		return false;
	}


	/** Check if a collection of strings contains one or more strings which contain {@code str}
	 * @param strs
	 * @param str
	 * @return
	 */
	public static final boolean containsIgnoreCase(Collection<String> strs, String str) {
		String strUpper = str.toUpperCase();
		if(strs instanceof RandomAccess && strs instanceof List) {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			List<String> strList = (List)strs;
			for(int i = 0, size = strList.size(); i < size; i++) {
				if(strList.get(i).toUpperCase().contains(strUpper)) {
					return true;
				}
			}
		}
		else {
			for(String s : strs) {
				if(s.toUpperCase().contains(strUpper)) {
					return true;
				}
			}
		}
		return false;
	}


	/** Find the longest matching to {@code chseq} in the array of search entry key strings
	 * @param chseq the char sequence to find a string that matches
	 * @param chseqOffset the offset into {@code chseq} at which to beginning searching
	 * for a matching string
	 * @param searchStrs an array of {@link java.util.Map.Entry Map.Entries}, search the keys for
	 * a match to {@code chseq}
	 * @param isSorted true if {@code searchStrs} is sorted (use binary search to find match),
	 * false if {@code searchStrs} is not sorted and each entry's key should be compared to {@code chseq}
	 * @return the entry from {@code searchStrs} where the entry's key matches the longest substring
	 * portion of {@code chseq} starting at character index {@code chseqOffset} of {@code chseq}
	 */
	public static Map.Entry<String, String> closestMatch(CharSequence chseq, int chseqOffset,
			Map.Entry<String, String>[] searchStrs, boolean isSorted) {
		assert chseq != null && chseqOffset > -1 && searchStrs != null;

		if(isSorted) {
			int searchSeqLen = 1;
			// Possible matching range in search strings
			int index = -1;
			int lastIndex = -1;
			// Search for the index in the sorted array of search strings where strings beginning
			// with N matching letters of the search string begin
			// e.g. search for "carmichael" by searching for the portion of the sorted search strings
			// array where strings starting with "car" or "c" exist
			do {
				lastIndex = index;
				// TODO remove print
				System.out.println("sorted compare to: " + chseq.subSequence(chseqOffset, chseqOffset+searchSeqLen));

				index = EntriesCopy.binarySearch(searchStrs, chseq.subSequence(chseqOffset, chseqOffset+searchSeqLen).toString());
				if(index < 0) { index = (-index) - 1; }
				if(index >= searchStrs.length) {
					index = lastIndex;
					break;
				}
				int countEqual = compareEqualCount(chseq, chseqOffset, searchStrs[index].getKey(), 0);
				// TODO remove print
				System.out.println("sorted matching count: " + countEqual + " at " + index + " (search len: " + searchSeqLen + ")");
				if(countEqual == searchStrs[index].getKey().length()) {
					// TODO remove print
					System.out.println("sorted returning closest: " + index + ": " + searchStrs[index]);
					return searchStrs[index];
				}
				if(countEqual >= searchSeqLen) {
					// TODO remove pring
					System.out.println("sorted found closer: " + searchStrs[index].getKey());
					searchSeqLen++;
				}
				else {
					index = lastIndex;
					break;
				}
			} while(index > -1);

			return null;
		}
		// If the search string entry array is not sorted
		else {
			// TODO remove print
			System.out.println("unsorted compare to: " + chseq.subSequence(chseqOffset, chseq.length()));

			// Find the string in the list of search strings that matches the best
			int closestMatchIndex = -1;
			int countEqual = -1;
			int bestCount = 1;
			for(int index = 0, size = searchStrs.length; index < size; index++) {
				countEqual = compareEqualCount(chseq, chseqOffset, searchStrs[index].getKey(), 0);
				// TODO remove print
				System.out.println("comparing: " + countEqual + ": " + searchStrs[index].getKey());
				if(countEqual > bestCount) {
					bestCount = countEqual;
					closestMatchIndex = index;
					// TODO remove print
					System.out.println("new best: " + searchStrs[index].getKey());

					if(bestCount == searchStrs[index].getKey().length()) {
						return searchStrs[closestMatchIndex];
					}
				}
			}
			return closestMatchIndex > -1 ? searchStrs[closestMatchIndex] : null;
		}
	}


	/** Compare how many characters in the two strings are equal before a pair of
	 * unequal characters are encountered.
	 * @param str1 the first char sequence
	 * @param str2 the second char sequence
	 * @return the number of characters equal between the two char sequences
	 */
	public static final int compareEqualCount(CharSequence str1, CharSequence str2) {
		assert str1 != null && str2 != null;
		final int size = str1.length() > str2.length() ? str2.length() : str1.length();
		for(int i = 0; i < size; i++) {
			if(str1.charAt(i) != str2.charAt(i)) { return i; }
		}
		return size;
	}


	/** Compare how many characters in the two char sequences are equal before a
	 * pair of unequal characters are encountered.
	 * @param str1 the first string
	 * @param offset1 the offset into the first char sequence at which to start
	 * comparing characters
	 * @param str2 the second string
	 * @param offset2 the offset into the second char sequence at which to start
	 * comparing characters
	 * @return the number of characters equal between the two char sequences
	 */
	public static final int compareEqualCount(CharSequence str1, int offset1, CharSequence str2, int offset2) {
		return compareEqualCount(str1, offset1, str2, offset2,
				Math.min(str1.length()-offset1, str2.length()-offset2));
	}


	/** Compare how many characters in the two char sequences are equal before a
	 * pair of unequal characters are encountered.
	 * @param str1 the first string
	 * @param offset1 the offset into the first char sequence at which to start
	 * comparing characters
	 * @param str2 the second string
	 * @param offset2 the offset into the second char sequence at which to start
	 * comparing characters
	 * @param length the number of characters to compare between the two char sequences
	 * @return the number of characters equal between the two char sequences
	 */
	public static final int compareEqualCount(CharSequence str1, int offset1, CharSequence str2, int offset2,
			int length) {
		assert str1 != null && str2 != null && offset1 > -1 && offset2 > -1 && length > -1;
		int size = length > str1.length()-offset1 ? str1.length()-offset1 :
			(length > str2.length()-offset2 ? str2.length()-offset2 : length);
		size = offset1+length;
		// Compare characters up to the length of the shorter of the two strings
		for(int i1 = offset1, i2 = offset2; i1 < size; i1++, i2++) {
			if(str1.charAt(i1) != str2.charAt(i2)) {
				return i1-offset1;
			}
		}
		return size-offset1;
	}

}
