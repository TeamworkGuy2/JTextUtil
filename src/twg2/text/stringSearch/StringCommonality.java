package twg2.text.stringSearch;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/** Find common or shared characteristics among groups of strings.
 * For example find the common suffix shared by a list of strings.
 * @author TeamworkGuy2
 * @since 2014-12-19
 */
public class StringCommonality {


	// TODO prefixes and suffixes can be iterated over in one loop by taking the common starting/ending
	// portions between the first and second string, second and third string, etc.

	public static String findPrefix(int offset, List<String> strs) {
		return findPrefix(0, Integer.MAX_VALUE, offset, strs);
	}


	public static String findPrefix(int minIndex, int maxIndex, int startOffset, List<String> strs) {
		return commonStringPortion(minIndex, maxIndex, startOffset, strs, true, false);
	}


	public static String findSuffix(int offset, List<String> strs) {
		return findSuffix(0, Integer.MAX_VALUE, offset, strs);
	}


	public static String findSuffix(int minIndex, int maxIndex, int startOffset, List<String> strs) {
		return commonStringPortion(minIndex, maxIndex, startOffset, strs, false, true);
	}


	public static String commonStringPortion(int minIndex, int maxIndex, int startOffset, List<String> strs, boolean direction, boolean startAtEndOfStr) {
		int count = commonStringRange(minIndex, maxIndex, startOffset, strs, direction, startAtEndOfStr);
		if(count == 0) {
			return "";
		}
		else {
			String str = strs.get(0);
			if(direction == true) {
				int start = startOffset;
				return str.substring(start, start + count);
			}
			else {
				int start = (startAtEndOfStr ? str.length() - startOffset : startOffset + 1) - count; // +1 because substring() doesn't include the last character which is startOffset
				return str.substring(start, start + count);
			}
		}
	}


	/**
	 * @param minIndex the minimum index in any of the strings at which to stop matching common substrings (only applies if {@code direction = false})
	 * @param maxIndex the maximum index in any of the strings at which to stop matching common substrings (only applies if {@code direction = true})
	 * @param startOffset the offset into each string at which to start matching substrings (only applies if {@code startAtEndofStr = false})
	 * @param strs the list of strings to find a common substring among
	 * @param direction the direction to search for matching substring.<br>
	 * True: search forward starting at {@code startOffset}.<br>
	 * False: search backward from {@code startOffset} or the end of the string backward if {@code startAtEndOfStr = true}
	 * @param startAtEndOfStr only used if {@code direction = false} in which case:<br>
	 * True: {@code startOffset} is ignored and matching begins at the end of each string.<br>
	 * False: substrings are found by matching backward starting at {@code startOffset}
	 * @return the length of the common substring found among all the {@code strs}, based on the above stated rules about the input parameters
	 */
	public static int commonStringRange(int minIndex, int maxIndex, int startOffset, Collection<String> strs, boolean direction, boolean startAtEndOfStr) {
		if(minIndex < 0) {
			throw new IndexOutOfBoundsException("minIndex " + minIndex + " must be greater than or equal to 0");
		}
		if(startOffset < minIndex) {
			throw new IndexOutOfBoundsException("startOffset " + startOffset + " must be greater than or equal to 0 and greater than or equal to minIndex");
		}
		if(maxIndex < startOffset) {
			throw new IndexOutOfBoundsException("maxIndex " + maxIndex + " must be greater than or equal to 0 and greater than or equal to startOffset");
		}

		int size = strs.size();
		if(size < 2) {
			if(size < 1) {
				return 0;
			}
			else {
				int strLen = strs.iterator().next().length();
				if(direction == true) {
					int startIndex = startOffset;
					return Math.min(strLen, maxIndex) - startIndex;
				}
				else {
					return (startAtEndOfStr ? strLen - minIndex : startOffset - minIndex);
				}
			}
		}

		Iterator<String> iter = strs.iterator();
		int iiOff = (startAtEndOfStr ? 1 : 0);
		int commonCount = Integer.MAX_VALUE;
		int i = 0;
		int sizeI = 0;
		String str2 = iter.next();

		// TODO could change to while(iter.hasNext()), but we already have the size from checking earlier
		for(i = 0, sizeI = size - 1; i < sizeI; i++) {
			String str1 = str2;
			str2 = iter.next();
			int str1Len = str1.length();
			int str2Len = str2.length();
			int str1Off = (direction ? startOffset : (startAtEndOfStr ? str1Len - startOffset : startOffset));
			int str2Off = (direction ? startOffset : (startAtEndOfStr ? str2Len - startOffset : startOffset));
			int ii = 0;

			// count matching characters between previous and current strings (str1 and str2)
			if(direction == true) {
				int shorterStrLen = Math.min(Math.min(str1Len, str2Len), maxIndex);
				while((startOffset + ii) < shorterStrLen && ii < commonCount && str1.charAt(str1Off + ii) == str2.charAt(str2Off + ii)) {
					ii++;
				}
			}
			else {
				int minOff = Math.min(str1Off, str2Off);
				if(str1Len <= str1Off - iiOff || str2Len <= str2Off - iiOff) {
					return 0;
				}
				while((minOff - ii) > minIndex && ii < commonCount && str1.charAt(str1Off - (ii + iiOff)) == str2.charAt(str2Off - (ii + iiOff))) {
					ii++;
				}
			}

			// update the number of common characters based on minimum common characters from previous strings comparisons and the current comparison
			commonCount = Math.min(commonCount, ii);

			// if no common characters in the last comparison, return
			if(commonCount == 0) {
				return 0;
			}
		}
		if(i != size - 1) {
			throw new IllegalStateException("collection had " + (i < size - 1 ? "fewer" : "more") + " values than expected based on .size()");
		}

		return commonCount;
	}


	/** Check for a common prefix among the keys an array of {@link java.util.Map.Entry Map.Entry} values
	 * @param offset the offset into each key string to start comparing values
	 * @param strs an array of map entries to compare the keys of
	 * @return the common string prefix of all of the map entry keys
	 */
	public static <T> String findPrefix(int offset, Map.Entry<String, T>[] strs) {
		StringBuilder strB = new StringBuilder();
		String firstString = strs[0].getKey();
		int size = strs.length;
		for(int i = offset; ; i++) {
			boolean match = true;
			if(i >= firstString.length()) {
				return strB.toString();
			}
			char c = firstString.charAt(i);
			for(int ii = 0; ii < size; ii++) {
				String str = strs[ii].getKey();
				if(i >= str.length()) {
					return strB.toString();
				}
				if(str.charAt(i) != c) {
					match = false;
					break;
				}
			}
			if(match) {
				strB.append(c);
			}
			else {
				return strB.toString();
			}
		}
	}


	/** Find a common prefix of an array of strings, for example, if:
	 * {@code strs = ["v_alpha", "v_beta", "v_be"]}, the returned prefix would be {@code "v_"}
	 * @param stringOffset the offset into each string at which to start searching for a common prefix
	 * @param strs the array of strings to search
	 * @return the common prefix shared by all the strings searched or an empty
	 * string if there is no common prefix
	 */
/*	public static String findPrefix(int stringOffset, String[] strs) {
		StringBuilder strB = new StringBuilder();
		String firstString = strs[0];
		for(int i = stringOffset; ; i++) {
			boolean match = true;
			if(i >= firstString.length()) {
				return strB.toString();
			}
			char c = firstString.charAt(i);
			for(String str : strs) {
				if(i >= str.length()) {
					return strB.toString();
				}
				if(str.charAt(i) != c) {
					match = false;
					break;
				}
			}
			if(match) {
				strB.append(c);
			}
			else {
				return strB.toString();
			}
		}
	}
*/

	/** Find a common prefix of an array of strings, for example, if:
	 * {@code strs = ["v_alpha", "v_beta", "v_be"]}, the returned prefix would be {@code "v_"}
	 * @param stringOffset the offset into each string at which to start searching for a common prefix
	 * @param strs the array of strings to search
	 * @return the common prefix shared by all the strings searched or an empty
	 * string if there is no common prefix
	 */
/*	public static String findPrefix(int stringOffset, Collection<String> strs) {
		StringBuilder strB = new StringBuilder();

		if(strs instanceof List && strs instanceof RandomAccess) {
			List<String> strsList = (List<String>)strs;
			String firstString = strsList.get(0);
			int size = strsList.size();

			for(int i = stringOffset; ; i++) {
				boolean match = true;
				if(i >= firstString.length()) {
					return strB.toString();
				}
				char c = firstString.charAt(i);

				for(int ii = 0; ii < size; ii++) {
					String str = strsList.get(ii);
					if(i >= str.length()) {
						return strB.toString();
					}
					if(str.charAt(i) != c) {
						match = false;
						break;
					}
				}
				if(match) {
					strB.append(c);
				}
				else {
					return strB.toString();
				}
			}
		}

		else {
			// get the first string
			String firstString = null;
			{
				Iterator<String> iter = strs.iterator();
				if(!iter.hasNext()) {
					return strB.toString();
				}
				firstString = iter.next();
			}

			for(int i = stringOffset; ; i++) {
				boolean match = true;
				if(i >= firstString.length()) {
					return strB.toString();
				}
				char c = firstString.charAt(i);

				for(String str : strs) {
					if(i >= str.length()) {
						return strB.toString();
					}
					if(str.charAt(i) != c) {
						match = false;
						break;
					}
				}
				if(match) {
					strB.append(c);
				}
				else {
					return strB.toString();
				}
			}
		}
	}
*/

	/** Find a common suffix of an array of strings, for example, if:
	 * {@code strs = ["jumping", "swimming", "laughing"]}, the returned prefix would be {@code "ing"}
	 * @param strOffset the offset from the end of each string at which to start searching for a common suffix
	 * @param strs the array of strings to search
	 * @return the common suffix shared by all the strings searched or an empty
	 * string if there is no common suffix
	 */
/*	public static String findSuffix(int strOffset, String[] strs) {
		StringBuilder strB = new StringBuilder();
		String firstString = strs[0];
		// i = 1 because suffixes are searched in reverse order starting at {@code string.length()-i}
		for(int i = 1+strOffset; ; i++) {
			boolean match = true;
			if(i > firstString.length()) {
				strB.reverse();
				return strB.reverse().toString();
			}
			char c = firstString.charAt(firstString.length()-i);
			for(String str : strs) {
				if(i > str.length()) {
					return strB.reverse().toString();
				}
				if(str.charAt(str.length()-i) != c) {
					match = false;
					break;
				}
			}
			if(match) {
				strB.append(c);
			}
			else {
				return strB.reverse().toString();
			}
		}
	}
*/

	/** Find a common suffix of an array of strings, for example, if:
	 * {@code strs = ["jumping", "swimming", "laughing"]}, the returned prefix would be {@code "ing"}
	 * @param strOffset the offset from the end of each string at which to start searching for a common suffix
	 * @param strs the array of strings to search
	 * @return the common suffix shared by all the strings searched or an empty
	 * string if there is no common suffix
	 */
/*	public static String findSuffix(int strOffset, Collection<String> strs) {
		StringBuilder strB = new StringBuilder();

		// i = 1 because suffixes are searched in reverse order starting at {@code string.length()-i}
		if(strs instanceof List && strs instanceof RandomAccess) {
			List<String> strsList = (List<String>)strs;
			String firstString = strsList.get(0);
			int size = strsList.size();

			for(int i = 1+strOffset; ; i++) {
				boolean match = true;
				if(i > firstString.length()) {
					strB.reverse();
					return strB.reverse().toString();
				}
				char c = firstString.charAt(firstString.length()-i);
				for(int ii = 0; ii < size; ii++) {
					String str = strsList.get(ii);
					if(i > str.length()) {
						return strB.reverse().toString();
					}
					if(str.charAt(str.length()-i) != c) {
						match = false;
						break;
					}
				}
				if(match) {
					strB.append(c);
				}
				else {
					return strB.reverse().toString();
				}
			}
		}

		else {
			// get the first string
			String firstString = null;
			{
				Iterator<String> iter = strs.iterator();
				if(!iter.hasNext()) {
					return strB.reverse().toString();
				}
				firstString = iter.next();
			}

			for(int i = 1+strOffset; ; i++) {
				boolean match = true;
				if(i > firstString.length()) {
					strB.reverse();
					return strB.reverse().toString();
				}
				char c = firstString.charAt(firstString.length()-i);
				for(String str : strs) {
					if(i > str.length()) {
						return strB.reverse().toString();
					}
					if(str.charAt(str.length()-i) != c) {
						match = false;
						break;
					}
				}
				if(match) {
					strB.append(c);
				}
				else {
					return strB.reverse().toString();
				}
			}
		}
	}
*/
}
