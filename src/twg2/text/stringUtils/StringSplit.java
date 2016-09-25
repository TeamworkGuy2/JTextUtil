package twg2.text.stringUtils;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import twg2.text.stringSearch.StringIndex;

/** A utility for simple string splitting.
 * @author TeamworkGuy2
 * @since 2013-12-21
 */
public class StringSplit {
	/** The default size of a contain to hold a split string if the maximum number of splits
	 * is undefined or greater than MAX_SPLIT_SIZE */
	private static final int DEFAULT_SPLIT_SIZE = 10;
	/** The maximum size of a contain to hold split strings, if the split limit is greater than
	 * this value, than default to {@link StringUtility#DEFAULT_SPLIT_SIZE DEFAULT_SPLIT_SIZE} */
	private static final int MAX_SPLIT_SIZE = 100;


	private StringSplit() { throw new AssertionError("cannot instantiate static class StringSplit"); }


	/** A slightly faster version of {@link String#split(String)} that does not
	 * used {@link Pattern}, instead the pattern is interpreted literally
	 * and the input string is split based on the literal split string.
	 * @param input the input char sequence to split
	 * @param pattern the exact pattern to find and split around
	 * @param limit the maximum number of splits to make, zero indicates that
	 * an infinite number of splits can occur
	 * @return an array of strings containing the resulting stings after
	 * splitting the input string
	 */
	public static final String[] split(String input, String pattern, int limit) {
		List<String> strs = split(input, pattern, limit, null);
		return strs.toArray(new String[strs.size()]);
	}


	/**
	 * @see #split(String, String, int, List)
	 */
	public static final List<String> split(String input, String pattern, List<String> dst) {
		return split(input, pattern, 0, dst);
	}


	/** A slightly faster version of {@link String#split(String)} that does not
	 * used {@link Pattern}, instead the pattern is interpreted literally
	 * and the input string is split based on the literal split string.
	 * @param input the input char sequence to split
	 * @param pattern the exact pattern to find and split around
	 * @param limit the maximum number of splits to make, zero indicates that
	 * an infinite number of splits can occur
	 * @param dst the destination list to add the split strings to
	 * @return the {@code dst} list with the split strings added to it
	 */
	public static final List<String> split(String input, String pattern, int limit, List<String> dst) {
		if(input == null || pattern == null) {
			return dst;
		}
		// If the limit is zero (meaning an infinite number of splits) make the
		// limit negative so that it never matches the comparison in the loop
		limit = (limit == 0) ? -1 : limit;
		int inputSize = input.length();
		int patternSize = pattern.length();
		int index = 0;
		int indexPlusPatternSize = 0;
		int nextIndex = 0;
		int matchingCount = 0;
		if(dst == null) {
			dst = new ArrayList<String>((limit > 0 && limit < MAX_SPLIT_SIZE) ?
					limit : DEFAULT_SPLIT_SIZE);
		}
		// Since the first .indexOf() call uses index+patternSize, we want the first index to be at 0
		index = -patternSize;
		// Keep adding new strings until on cannot be found
		// (reaching the maximum number of strings is handled inside the loop)
		do {
			indexPlusPatternSize = index + patternSize; // small optimization (me being picky)
			// Find the next matching string index after the current matching string index
			nextIndex = input.indexOf(pattern, indexPlusPatternSize);
			// If the maximum number of strings have been match, set the next index to -1
			// so that the next statement acts as if the end of the string has been reached
			if(matchingCount == limit - 1) { nextIndex = -1; }
			// If no more matching strings can be found, include the remainder of
			// the string after the last matching string
			nextIndex = (nextIndex == -1) ? inputSize : nextIndex;
			// If the matching string index is greater than or equal to the end
			// of the string, then break, there is no more string left to parse
			if(indexPlusPatternSize > inputSize) { break; }
			// Add the new sub string between the end of the previous matching
			// pattern and the next matching pattern to the list of splits
			dst.add(input.substring(indexPlusPatternSize, nextIndex));
			// If the next found index is the end of the string, set the index
			// to -1 so that the outer while loop ends, else keep the current index
			index = (nextIndex == inputSize) ? -1 : nextIndex;
			// Increment the number of split sub strings
			matchingCount++;
		} while(index != -1); // While at end because (index = -patternSize) could equal -1 if the pattern size is -1
		// so initialize everything first and run the first loop before evaluating the while statement
		// Return the string list
		return dst;
	}


	/** A slightly faster version of {@link String#split(String)} that does not
	 * used {@link Pattern}, instead the pattern is interpreted literally
	 * and the input string is split based on the literal split string.<br/>
	 * This method is more space efficient than the {@link StringSplit#split(String, String, int)} version
	 * since no internal structure is created to store the split strings, instead the array provided
	 * is filled with the split strings.
	 * @param input the input char sequence to split
	 * @param pattern the exact pattern to find and split around
	 * @param dst an array of strings equal in length to the number of strings to split the {@code input} string into
	 * @return the {@code results} array of strings passed into the method
	 */
	public static final String[] split(String input, String pattern, String[] dst) {
		if(input == null || pattern == null) {
			return new String[] {input};
		}
		// If the limit is zero (meaning an infinite number of splits) make the
		// limit negative so that it never matches the comparison in the loop
		int limit = dst.length;
		int inputSize = input.length();
		int patternSize = pattern.length();
		int index = 0;
		int indexPlusPatternSize = 0;
		int nextIndex = 0;
		int matchingCount = 0;
		// Since the first .indexOf() call uses index+patternSize, we want the first index to be at 0
		index = -patternSize;
		// Keep adding new strings until on cannot be found
		// (reaching the maximum number of strings is handled inside the loop)
		do {
			indexPlusPatternSize = index + patternSize; // small optimization (me being picky)
			// Find the next matching string index after the current matching string index
			nextIndex = input.indexOf(pattern, indexPlusPatternSize);
			// If the maximum number of strings have been match, set the next index to -1
			// so that the next statement acts as if the end of the string has been reached
			if(matchingCount == limit - 1) { nextIndex = -1; }
			// If no more matching strings can be found, include the remainder of
			// the string after the last matching string
			nextIndex = (nextIndex == -1) ? inputSize : nextIndex;
			// If the matching string index is greater than or equal to the end
			// of the string, then break, there is no more string left to parse
			if(indexPlusPatternSize > inputSize) { break; }
			// Add the new sub string between the end of the previous matching
			// pattern and the next matching pattern to the list of splits
			dst[matchingCount] = input.substring(indexPlusPatternSize, nextIndex);
			// If the next found index is the end of the string, set the index
			// to -1 so that the outer while loop ends, else keep the current index
			index = (nextIndex == inputSize) ? -1 : nextIndex;
			// Increment the number of split sub strings
			matchingCount++;
		} while(index != -1); // While at end because (index = -patternSize) could equal -1 if the pattern size is -1,
		// so initialize everything first and run the first loop before evaluating the while statement
		// Return the array of split sub strings
		return dst;
	}


	/**
	 * @see #split(String, char, int, List)
	 */
	public static final List<String> split(String input, char splitAt) {
		List<String> dst = new ArrayList<>();
		return split(input, splitAt, 0, dst);
	}


	/**
	 * @see #split(String, char, int, List)
	 */
	public static final List<String> split(String input, char splitAt, List<String> dst) {
		return split(input, splitAt, 0, dst);
	}


	/** A slightly faster version of {@link String#split(String)} that does not
	 * used {@link Pattern}, instead the string is split based on a specific char.<br>
	 * @param input the input char sequence to split
	 * @param splitAt the exact string to find and split around
	 * @param limit the maximum number of splits to make, zero indicates that
	 * an infinite number of splits can occur
	 * @param dst the destination list to add the split strings to
	 * @return the {@code dst} list with the split strings added to it
	 */
	public static final List<String> split(String input, char splitAt, int limit, List<String> dst) {
		if(input == null) {
			return dst;
		}
		// If the limit is zero (meaning an infinite number of splits) make the
		// limit negative so that it never matches the comparison in the loop
		limit = (limit == 0) ? -1 : limit;
		int inputSize = input.length();
		int patternSize = 1;
		int index = 0;
		int indexPlusPatternSize = 0;
		int nextIndex = 0;
		int matchingCount = 0;
		if(dst == null) {
			dst = new ArrayList<String>((limit > 0 && limit < MAX_SPLIT_SIZE) ?
					limit : DEFAULT_SPLIT_SIZE);
		}
		// Since the first .indexOf() call uses index+patternSize, we want the first index to be at 0
		index = -patternSize;
		// Keep adding new strings until on cannot be found
		// (reaching the maximum number of strings is handled inside the loop)
		do {
			indexPlusPatternSize = index + patternSize; // small optimization (me being picky)
			// Find the next matching string index after the current matching string index
			nextIndex = input.indexOf(splitAt, indexPlusPatternSize);
			// If the maximum number of strings have been match, set the next index to -1
			// so that the next statement acts as if the end of the string has been reached
			if(matchingCount == limit - 1) { nextIndex = -1; }
			// If no more matching strings can be found, include the remainder of
			// the string after the last matching string
			nextIndex = (nextIndex == -1) ? inputSize : nextIndex;
			// If the matching string index is greater than or equal to the end
			// of the string, then break, there is no more string left to parse
			if(indexPlusPatternSize > inputSize) { break; }
			// Add the new sub string between the end of the previous matching
			// pattern and the next matching pattern to the list of splits
			dst.add(input.substring(indexPlusPatternSize, nextIndex));
			// If the next found index is the end of the string, set the index
			// to -1 so that the outer while loop ends, else keep the current index
			index = (nextIndex == inputSize) ? -1 : nextIndex;
			// Increment the number of split sub strings
			matchingCount++;
		} while(index != -1); // While at end because (index = -patternSize) could equal -1 if the pattern size is -1
		// so initialize everything first and run the first loop before evaluating the while statement
		// Return the string list
		return dst;
	}


	/** A slightly faster version of {@link String#split(String)} that does not
	 * used {@link Pattern}, instead the string is split based on a specific char.<br/>
	 * This method is more space efficient than the {@link StringSplit#split(String, String, int)} version
	 * since no internal structure is created to store the split strings, instead the array provided
	 * is filled with the split strings.
	 * @param input the input char sequence to split
	 * @param splitAt the exact string to find and split around
	 * @param dst an array of strings equal in length to the number of strings to split the {@code input} string into
	 * @return the {@code results} array of strings passed into the method
	 */
	public static final String[] split(String input, char splitAt, String[] dst) {
		if(input == null) {
			return new String[] {input};
		}
		// If the limit is zero (meaning an infinite number of splits) make the
		// limit negative so that it never matches the comparison in the loop
		int limit = dst.length;
		int inputSize = input.length();
		int patternSize = 1;
		int index = 0;
		int indexPlusPatternSize = 0;
		int nextIndex = 0;
		int matchingCount = 0;
		// Since the first .indexOf() call uses index+patternSize, we want the first index to be at 0
		index = -patternSize;
		// Keep adding new strings until on cannot be found
		// (reaching the maximum number of strings is handled inside the loop)
		do {
			indexPlusPatternSize = index + patternSize; // small optimization (me being picky)
			// Find the next matching string index after the current matching string index
			nextIndex = input.indexOf(splitAt, indexPlusPatternSize);
			// If the maximum number of strings have been match, set the next index to -1
			// so that the next statement acts as if the end of the string has been reached
			if(matchingCount == limit - 1) { nextIndex = -1; }
			// If no more matching strings can be found, include the remainder of
			// the string after the last matching string
			nextIndex = (nextIndex == -1) ? inputSize : nextIndex;
			// If the matching string index is greater than or equal to the end
			// of the string, then break, there is no more string left to parse
			if(indexPlusPatternSize > inputSize) { break; }
			// Add the new sub string between the end of the previous matching
			// pattern and the next matching pattern to the list of splits
			dst[matchingCount] = input.substring(indexPlusPatternSize, nextIndex);
			// If the next found index is the end of the string, set the index
			// to -1 so that the outer while loop ends, else keep the current index
			index = (nextIndex == inputSize) ? -1 : nextIndex;
			// Increment the number of split sub strings
			matchingCount++;
		} while(index != -1); // While at end because (index = -patternSize) could equal -1 if the pattern size is -1,
		// so initialize everything first and run the first loop before evaluating the while statement
		// Return the array of split sub strings
		return dst;
	}



	/** A slightly faster version of {@link String#split(String)} that does not
	 * used {@link Pattern}, instead the pattern is interpreted literally
	 * and the input string is split based on the literal split string.
	 * @param input the input char sequence to split
	 * @param pattern the exact pattern to find and split around
	 * @param childI the index of the matching substring to retrieve
	 * @param expectedCount the expected number of string when the string is split using the pattern, 0 if the expected count is not know
	 * @return the n-th matching split string
	 */
	public static final String findNthMatch(String input, String pattern, int childI, int expectedCount) {
		if(input == null || pattern == null) {
			return null;
		}
		if(expectedCount < 0) {
			throw new IllegalArgumentException("expectedCount (" + expectedCount + ") must be greater than 0");
		}
		if(childI >= expectedCount) {
			throw new IllegalStateException("childI (" + childI + ") must be less than expected count (" + expectedCount + ")");
		}

		// If the limit is zero (meaning an infinite number of splits) make the
		// limit negative so that it never matches the comparison in the loop
		expectedCount = (expectedCount == 0) ? -1 : expectedCount;
		int inputSize = input.length();
		int patternSize = pattern.length();
		int index = 0;
		int indexPlusPatternSize = 0;
		int nextIndex = 0;
		int matchingCount = 0;
		String matchStr = null;

		// Since the first .indexOf() call uses index+patternSize, we want the first index to be at 0
		index = -patternSize;
		// Keep adding new strings until on cannot be found
		// (reaching the maximum number of strings is handled inside the loop)
		do {
			indexPlusPatternSize = index + patternSize; // small optimization (me being picky)
			// Find the next matching string index after the current matching string index
			nextIndex = input.indexOf(pattern, indexPlusPatternSize);
			// If the maximum number of strings have been match, set the next index to -1
			// so that the next statement acts as if the end of the string has been reached
			if(matchingCount == expectedCount - 1 && nextIndex > -1) { throw new IllegalStateException("found more than " + expectedCount + " split strings"); }
			// If no more matching strings can be found, include the remainder of
			// the string after the last matching string
			nextIndex = (nextIndex == -1) ? inputSize : nextIndex;
			// If the matching string index is greater than or equal to the end
			// of the string, then break, there is no more string left to parse
			if(indexPlusPatternSize > inputSize) { break; }
			// Add the new sub string between the end of the previous matching
			// pattern and the next matching pattern to the list of splits
			//dst.add(input.substring(indexPlusPatternSize, nextIndex));
			if(matchingCount == childI) {
				matchStr = input.substring(indexPlusPatternSize, nextIndex);
				// only return the split string if the number of expected split strings is not specified
				if(expectedCount == 0) {
					return matchStr;
				}
			}
			// If the next found index is the end of the string, set the index
			// to -1 so that the outer while loop ends, else keep the current index
			index = (nextIndex == inputSize) ? -1 : nextIndex;
			// Increment the number of split sub strings
			matchingCount++;
		} while(index != -1); // While at end because (index = -patternSize) could equal -1 if the pattern size is -1

		if(expectedCount != 0 && matchingCount != expectedCount) { throw new IllegalStateException("found " + matchingCount + ", expected " + expectedCount + " split strings"); }
		// so initialize everything first and run the first loop before evaluating the while statement
		// Return the string list
		return matchStr;
	}


	public static final int countMatches(String src, String pattern) {
		if(src == null || pattern == null) {
			return 0;
		}

		int srcSize = src.length();
		int patternSize = pattern.length();
		int index = 0;
		int matchingCount = 0;

		// Since the first .indexOf() call uses index+patternSize, we want the first index to be at 0
		index = -patternSize;
		// Keep adding new strings until on cannot be found
		// (reaching the maximum number of strings is handled inside the loop)
		do {
			int indexPlusPatternSize = index + patternSize;
			// Find the next matching string index after the current matching string index
			int nextIndex = src.indexOf(pattern, indexPlusPatternSize);
			// If no more matching strings can be found, include the remainder of
			// the string after the last matching string
			nextIndex = (nextIndex == -1) ? srcSize : nextIndex;
			// If the matching string index is greater than or equal to the end
			// of the string, then break, there is no more string left to parse
			if(indexPlusPatternSize > srcSize) { break; }
			// If the next found index is the end of the string, set the index
			// to -1 so that the outer while loop ends, else keep the current index
			index = (nextIndex == srcSize) ? -1 : nextIndex;
			// Increment the number of split sub strings
			matchingCount++;
		} while(index != -1); // While at end because (index = -patternSize) could equal -1 if the pattern size is -1

		return matchingCount - 1; // -1 because loop counts the number of strings between matching patterns, the number of matching patterns is 1 less
	}


	public static final int countMatches(char[] src, int srcOff, int srcLen, char[] pattern, int patternOff, int patternLen) {
		if(src == null || pattern == null) {
			return 0;
		}

		int srcSize = srcOff + srcLen;
		int patternSize = patternLen;
		int index = srcOff;
		int matchingCount = 0;

		// Since the first .indexOf() call uses index+patternSize, we want the first index to be at 0
		index = -patternSize;
		// Keep adding new strings until on cannot be found
		// (reaching the maximum number of strings is handled inside the loop)
		do {
			int indexPlusPatternSize = index + patternSize;
			// Find the next matching string index after the current matching string index
			int nextIndex = StringIndex.indexOf(src, indexPlusPatternSize, srcSize - indexPlusPatternSize, pattern, patternOff, patternLen);
			// If no more matching strings can be found, include the remainder of
			// the string after the last matching string
			nextIndex = (nextIndex == -1) ? srcSize : nextIndex;
			// If the matching string index is greater than or equal to the end
			// of the string, then break, there is no more string left to parse
			if(indexPlusPatternSize > srcSize) { break; }
			// If the next found index is the end of the string, set the index
			// to -1 so that the outer while loop ends, else keep the current index
			index = (nextIndex == srcSize) ? -1 : nextIndex;
			// Increment the number of split sub strings
			matchingCount++;
		} while(index != -1); // While at end because (index = -patternSize) could equal -1 if the pattern size is -1

		return matchingCount - 1; // -1 because loop counts the number of strings between matching patterns, the number of matching patterns is 1 less
	}


	/** Get the sub string preceding the first index of 'patternChar'
	 */
	public static final String firstMatch(String src, char patternChar) {
		return _firstMatch(src, src.indexOf(patternChar));
	}

	/** Get the sub string preceding the first index of 'pattern'
	 */
	public static final String firstMatch(String src, String pattern) {
		return _firstMatch(src, src.indexOf(pattern));
	}

	private static final String _firstMatch(String src, int idx) {
		return src.substring(0, idx >= src.length() ? src.length() : (idx < 0 ? 0 : idx));
	}


	/** Get the sub string following the first index of 'patternChar'
	 */
	public static final String postFirstMatch(String src, char patternChar) {
		return _postFirstMatch(src, src.indexOf(patternChar), 1);
	}

	/** Get the sub string following the first index of 'pattern'
	 */
	public static final String postFirstMatch(String src, String pattern) {
		return _postFirstMatch(src, src.indexOf(pattern), pattern.length());
	}

	public static final String _postFirstMatch(String src, int idx, int patternLen) {
		return src.substring(idx > -1 && idx + patternLen >= src.length() ? src.length() : (idx < 0 ? 0 : idx + patternLen));
	}


	/** Returns the portions of a string before and after the first index of a sub-string.
	 * For example {@code firstMatchParts("abc-mid-123", "-")}, returns {@code Entry("abc", "mid-123")}
	 * @param src the string to search
	 * @param pattern the sub-string to search for
	 * @return an entry where the key is the portion of {@code src} before the first matching sub-string
	 * and the value is the portion of {@code src} after the first matching sub-string
	 */
	public static final Map.Entry<String, String> firstMatchParts(String src, String pattern) {
		int idxPre = src.indexOf(pattern);
		String pre = src.substring(0, (idxPre < 0 ? src.length() : idxPre));

		int idxPost = idxPre;
		String post = src.substring(idxPost > -1 && idxPost + pattern.length() >= src.length() ? src.length() : (idxPost < 0 ? src.length() : idxPost + pattern.length()));

		return new AbstractMap.SimpleImmutableEntry<>(pre, post);
	}


	/** Get the sub string following the last index of 'patternChar'
	 */
	public static final String lastMatch(String src, char patternChar) {
		return _lastMatch(src, src.lastIndexOf(patternChar), 1);
	}

	/** Get the sub string following the last index of 'pattern'
	 */
	public static final String lastMatch(String src, String pattern) {
		return _lastMatch(src, src.lastIndexOf(pattern), pattern.length());
	}

	public static final String _lastMatch(String src, int idx, int patternLen) {
		return src.substring(idx > -1 && idx + patternLen >= src.length() ? src.length() : (idx < 0 ? 0 : idx + patternLen));
	}


	/** Get the sub string preceding the last index of 'patternChar'
	 */
	public static final String preLastMatch(String src, char patternChar) {
		return _preLastMatch(src, src.lastIndexOf(patternChar));
	}

	/** Get the sub string preceding the last index of 'pattern'
	 */
	public static final String preLastMatch(String src, String pattern) {
		return _preLastMatch(src, src.lastIndexOf(pattern));
	}

	public static final String _preLastMatch(String src, int idx) {
		return src.substring(0, idx >= src.length() ? src.length() : (idx < 0 ? 0 : idx));
	}


	/** Returns the portions of a string before and after the last index of a sub-string.
	 * For example {@code firstMatchParts("abc-mid-123", "-")}, returns {@code Entry("abc-mid", "123")}
	 * @param src the string to search
	 * @param pattern the sub-string to search for
	 * @return an entry where the key is the portion of {@code src} before the last matching sub-string
	 * and the value is the portion of {@code src} after the last matching sub-string
	 */
	public static final Map.Entry<String, String> lastMatchParts(String src, String pattern) {
		int idxPre = src.lastIndexOf(pattern);
		String pre = src.substring(0, (idxPre < 0 ? src.length() : idxPre));

		int idxPost = idxPre;
		String post = src.substring(idxPost > -1 && idxPost + pattern.length() >= src.length() ? src.length() : (idxPost < 0 ? src.length() : idxPost + pattern.length()));

		return new AbstractMap.SimpleImmutableEntry<>(pre, post);
	}


	// ==== substring(char) methods ====

	/** Find the first sub-string between a start and end char, default to the start and end of the string if either search char cannot be found
	 * @see #_substring(String, char, boolean, char, boolean, boolean, boolean)
	 */
	public static String substring(String src, char startChar, char endChar) {
		return _substring(src, startChar, true, endChar, true, false, false);
	}


	/** Find the first sub-string between a start and end char, throw an error if either search char cannot be found
	 * @see #_substring(String, char, boolean, char, boolean, boolean, boolean)
	 */
	public static String substringThrows(String src, char startChar, char endChar) {
		return _substring(src, startChar, true, endChar, true, true, false);
	}


	/** Find the first sub-string between a start and end char, return null if either search char cannot be found
	 * @see #_substring(String, char, boolean, char, boolean, boolean, boolean)
	 */
	public static String substringNull(String src, char startChar, char endChar) {
		return _substring(src, startChar, true, endChar, true, false, true);
	}


	/** Find the last sub-string between a start and end char, default to the start and end of the string if either search char cannot be found
	 * @see #_substring(String, char, boolean, char, boolean, boolean, boolean)
	 */
	public static String lastSubstring(String src, char startChar, char endChar) {
		return _substring(src, startChar, false, endChar, true, false, false);
	}


	/** Find the last sub-string between a start and end char, throw an error if either search char cannot be found
	 * @see #_substring(String, char, boolean, char, boolean, boolean, boolean)
	 */
	public static String lastSubstringThrows(String src, char startChar, char endChar) {
		return _substring(src, startChar, false, endChar, true, true, false);
	}


	/** Find the last sub-string between a start and end char, return null if either search char cannot be found
	 * @see #_substring(String, char, boolean, char, boolean, boolean, boolean)
	 */
	public static String lastSubstringNull(String src, char startChar, char endChar) {
		return _substring(src, startChar, false, endChar, true, false, true);
	}


	/** Find a sub string between the last index of a start and end string.<br>
	 * For example:<br>
	 * {@code lastSubstring("In 'A Thesis on Subatomic Wormholes' there are...", '"', '"', true)}<br>
	 * returns:<br>
	 * {@code "A Thesis on Subatomic Wormholes"}
	 * @param src the source string
	 * @param startChar the starting string, if this cannot be found in {@code src}, the start index defaults to 0
	 * @param startFirst true to use {@link String#indexOf(String)}, false to use {@link String#lastIndexOf(String)} when searching {@code src} for {@code startChar}
	 * @param endChar the ending string, if this cannot be found in {@code src}, the end index defaults to {@code src.length() - 1}
	 * @param endFirst true to use {@link String#indexOf(String, int)}, false to use {@link String#lastIndexOf(String, int)} when searching {@code src} for {@code endChar}
	 * @param throwIfEitherNotFound if either {@code startChar} or {@code endChar} cannot be found in {@code src}, throw an error instead of defaulting to the start/end of the {@code src} string.  Overrides {@code nullIfEitherNotFound}
	 * @param nullIfEitherNotFound if either {@code startChar} or {@code endChar} cannot be found in {@code src}, return null instead of defaulting to the start/end of the {@code src} string
	 * @return the sub string between {@code startChar} and {@code endChar} excluding both
	 */
	public static String _substring(String src, char startChar, boolean startFirst, char endChar, boolean endFirst, boolean throwIfEitherNotFound, boolean nullIfEitherNotFound) {
		int startIdx = startFirst ? src.indexOf(startChar) : src.lastIndexOf(startChar);
		if(startIdx < 0) {
			if(throwIfEitherNotFound) {
				throw new StringIndexOutOfBoundsException("could not find startStr in src");
			}
		}

		int startOff = startIdx + 1; // +1 even if startIdx could not be found (-1), this works
		int endIdx = endFirst ? src.indexOf(endChar, startOff) : src.lastIndexOf(endChar, startOff);
		if(endIdx < 0) {
			if(throwIfEitherNotFound) {
				throw new StringIndexOutOfBoundsException("could not find endStr in src");
			}
			endIdx = !nullIfEitherNotFound ? src.length() : -1;
		}

		return (nullIfEitherNotFound && (startIdx < 0 || endIdx < 0)) ? null : src.substring(startOff, endIdx);
	}


	// ==== substring(String) methods ====

	/** Find the first sub-string between a start and end string, default to the start and end of the string if either search string cannot be found
	 * @see #_substring(String, String, boolean, String, boolean, boolean, boolean)
	 */
	public static String substring(String src, String startStr, String endStr) {
		return _substring(src, startStr, true, endStr, true, false, false);
	}


	/** Find the first sub-string between a start and end char, throw an error if either the search char cannot be found
	 * @see #_substring(String, String, boolean, String, boolean, boolean, boolean)
	 */
	public static String substringThrows(String src, String startStr, String endStr) {
		return _substring(src, startStr, true, endStr, true, true, false);
	}


	/** Find the first sub-string between a start and end string, return null if either search string cannot be found
	 * @see #_substring(String, String, boolean, String, boolean, boolean, boolean)
	 */
	public static String substringNull(String src, String startStr, String endStr) {
		return _substring(src, startStr, true, endStr, true, false, true);
	}


	/** Find the last sub-string between a start and end string, default to the start and end of the string if either search string cannot be found
	 * @see #_substring(String, String, boolean, String, boolean, boolean, boolean)
	 */
	public static String lastSubstring(String src, String startStr, String endStr) {
		return _substring(src, startStr, false, endStr, true, false, false);
	}


	/** Find the last sub-string between a start and end char, throw an error if either the search char cannot be found
	 * @see #_substring(String, String, boolean, String, boolean, boolean, boolean)
	 */
	public static String lastSubstringThrows(String src, String startStr, String endStr) {
		return _substring(src, startStr, false, endStr, true, true, false);
	}


	/** Find the last sub-string between a start and end string, return null if either search string cannot be found
	 * @see #_substring(String, String, boolean, String, boolean, boolean, boolean)
	 */
	public static String lastSubstringNull(String src, String startStr, String endStr) {
		return _substring(src, startStr, false, endStr, true, false, true);
	}


	/** Find a sub string between the last index of a start and end string.<br>
	 * For example:<br>
	 * {@code lastSubstring("In 'A Thesis on Subatomic Wormholes' there are...", "\"", "\"", true)}<br>
	 * returns:<br>
	 * {@code "A Thesis on Subatomic Wormholes"}
	 * @param src the source string
	 * @param startStr the starting string, if this cannot be found in {@code src}, the start index defaults to 0
	 * @param startFirst true to use {@link String#indexOf(String)}, false to use {@link String#lastIndexOf(String)} when searching {@code src} for {@code startStr}
	 * @param endStr the ending string, if this cannot be found in {@code src}, the end index defaults to {@code src.length() - 1}
	 * @param endFirst true to use {@link String#indexOf(String, int)}, false to use {@link String#lastIndexOf(String, int)} when searching {@code src} for {@code endStr}
	 * @param throwIfEitherNotFound if either {@code startStr} or {@code endStr} cannot be found in {@code src}, throw an error instead of defaulting to the start/end of the {@code src} string.  Overrides {@code nullIfEitherNotFound}
	 * @param nullIfEitherNotFound if either {@code startStr} or {@code endStr} cannot be found in {@code src}, return null instead of defaulting to the start/end of the {@code src} string
	 * @return the sub string between {@code startStr} and {@code endStr} excluding both
	 */
	public static String _substring(String src, String startStr, boolean startFirst, String endStr, boolean endFirst, boolean throwIfEitherNotFound, boolean nullIfEitherNotFound) {
		int startIdx = startFirst ? src.indexOf(startStr) : src.lastIndexOf(startStr);
		if(startIdx < 0) {
			if(throwIfEitherNotFound) {
				throw new StringIndexOutOfBoundsException("could not find startStr in src");
			}
			startIdx = -startStr.length(); // works because startIdx is only used by startOff which add startStr.length()
		}

		int startOff = startIdx + startStr.length();
		int endIdx = endFirst ? src.indexOf(endStr, startOff) : src.lastIndexOf(endStr, startOff);
		if(endIdx < 0) {
			if(throwIfEitherNotFound) {
				throw new StringIndexOutOfBoundsException("could not find endStr in src");
			}
			endIdx = !nullIfEitherNotFound ? src.length() : -1;
		}

		return (nullIfEitherNotFound && (startIdx < 0 || endIdx < 0)) ? null : src.substring(startOff, endIdx);
	}


	// ==== Split at spaces ====
	/** Split a string into multiple sub-string at boundary characters while keeping each string's length less than or equal to the maxLength
	 * Example: splitAtBoundary("the quick sly fox", ' ', 5)
	 * returns: ["the", "quick", "sly", "fox"]
	 *
	 * @param longStr the string to split up
	 * @param maxLen the max length of each sub-string
	 * @return a list of sub-strings split at the first boundary character before {@code maxLen} or the {@code maxLen}
	 */
	public static List<String> splitAtBoundary(String longStr, char boundryMarker, int maxLen) {
		int len = longStr.length();
		int offset = 0;
		List<String> strs = new ArrayList<>();

		while(true) {
			int nextSplitLen = getNextBoundarySplitIndex(longStr, offset, len, boundryMarker, maxLen);
			strs.add(longStr.substring(offset, offset + nextSplitLen));
			nextSplitLen += (offset + nextSplitLen < len && longStr.charAt(offset + nextSplitLen) == boundryMarker ? 1 : 0);
			if(offset + nextSplitLen >= len) {
				break;
			}
			offset += nextSplitLen;
		}

		return strs;
	}


	/** Determine the next index at which to split a string (up to a max sub-string size), preferring to split at boundary characters (e.g. spaces between words)
	 * Example: getNextWordSplitIndex("the quick sly fox", 4, ' ', 5)
	 * returns: 5 // meaning 4 (the offset) + 5 is the next absolute split index which produces "quick"
	 *
	 * Example: getNextWordSplitIndex("the quick sly fox", 10, ' ', 5)
	 * returns: 3 // meaning 10 (the offset) + 3 is the next absolute split index which produces "sly"
	 *
	 * @param str the string
	 * @param the character to split on
	 * @param offset an initial (0-based) offset at which to start searching for a split point
	 * @param maxLen the max length of the sub-string
	 * @return the number of characters to take from the string, 'offset' is not included.
	 */
	private static int getNextBoundarySplitIndex(String str, int offset, int strLen, char boundaryMarker, int maxLen) {
		int idx = str.indexOf(boundaryMarker, offset) - offset;
		// look for the last boundary less than maxLen + 1 (+1 because we drop the boundary if it comes at the end of a max length sub-string)
		while(idx > -1 && idx < maxLen + 1) {
			int nextIdx = str.indexOf(boundaryMarker, offset + idx + 1) - offset;
			// if no more boundaries exist or the next boundary is at an index greater than maxLen, then break
			if(nextIdx < 0 || nextIdx >= maxLen + 1) {
				// if remainder of the string is less than the maxLen, just take all of it
				if(nextIdx < 0 && strLen - offset <= maxLen) {
					idx = strLen - offset;
				}
				break;
			}
			// the next boundary index is still less than maxLen
			idx = nextIdx;
		}
		return (idx < 0
				? Math.min(strLen - offset, maxLen) // if no boundary exist in the remaining string, take the remainder or maxLen, whichever is smaller
				: (idx >= maxLen + 1 ? maxLen : idx)); // if the index is somehow too large (shouldn't be possible) cap it at maxLen, otherwise return the next valid index
	}

}
