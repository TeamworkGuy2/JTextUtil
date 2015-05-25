package stringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
	 * This method is more space efficient than the {@link StringModify#split(String, String, int)} version
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
	 * This method is more space efficient than the {@link StringModify#split(String, String, int)} version
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

}
