package stringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;

/** Find common or shared characteristics among groups of strings.
 * For example find the common suffix shared by a list of strings.
 * @author TeamworkGuy2
 * @since 2014-12-19
 */
public class StringCommonality {


	// TODO prefixes and suffixes can be iterated over in one loop by taking the common starting/ending
	// portions between the first and second string, second and third string, etc.

	/** Check for a common prefix among the keys an array of {@link java.util.Map.Entry Map.Entry} values
	 * @param offset the offset into each key string to start comparing values
	 * @param strs an array of map entries to compare the keys of
	 * @return the common string prefix of all of the map entry keys
	 */
	public static String findPrefix(int offset, Map.Entry<String, String>[] strs) {
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
	public static String findPrefix(int stringOffset, String[] strs) {
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


	/** Find a common prefix of an array of strings, for example, if:
	 * {@code strs = ["v_alpha", "v_beta", "v_be"]}, the returned prefix would be {@code "v_"}
	 * @param stringOffset the offset into each string at which to start searching for a common prefix
	 * @param strs the array of strings to search
	 * @return the common prefix shared by all the strings searched or an empty
	 * string if there is no common prefix
	 */
	public static String findPrefix(int stringOffset, Collection<String> strs) {
		return findPrefix(stringOffset, strs.toArray(new String[strs.size()]));
	}


	/** Find a common prefix of an array of strings, for example, if:
	 * {@code strs = ["v_alpha", "v_beta", "v_be"]}, the returned prefix would be {@code "v_"}
	 * @param stringOffset the offset into each string at which to start searching for a common prefix
	 * @param strs the array of strings to search
	 * @return the common prefix shared by all the strings searched or an empty
	 * string if there is no common prefix
	 */
	public static String findPrefix(int stringOffset, List<String> strs) {
		StringBuilder strB = new StringBuilder();
		String firstString = strs.get(0);

		if(strs instanceof RandomAccess) {
			int size = strs.size();
			for(int i = stringOffset; ; i++) {
				boolean match = true;
				if(i >= firstString.length()) {
					return strB.toString();
				}
				char c = firstString.charAt(i);
				for(int ii = 0; ii < size; ii++) {
					String str = strs.get(ii);
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


	/** Find a common suffix of an array of strings, for example, if:
	 * {@code strs = ["jumping", "swimming", "laughing"]}, the returned prefix would be {@code "ing"}
	 * @param strOffset the offset from the end of each string at which to start searching for a common suffix
	 * @param strs the array of strings to search
	 * @return the common suffix shared by all the strings searched or an empty
	 * string if there is no common suffix
	 */
	public static String findSuffix(int strOffset, String[] strs) {
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


	/** Find a common suffix of an array of strings, for example, if:
	 * {@code strs = ["jumping", "swimming", "laughing"]}, the returned prefix would be {@code "ing"}
	 * @param strOffset the offset from the end of each string at which to start searching for a common suffix
	 * @param strs the array of strings to search
	 * @return the common suffix shared by all the strings searched or an empty
	 * string if there is no common suffix
	 */
	public static String findSuffix(int strOffset, Collection<String> strs) {
		return findSuffix(strOffset, strs.toArray(new String[strs.size()]));
	}


	/** Find a common suffix of an array of strings, for example, if:
	 * {@code strs = ["jumping", "swimming", "laughing"]}, the returned prefix would be {@code "ing"}
	 * @param offset the offset from the end of each string at which to start searching for a common suffix
	 * @param strs the array of strings to search
	 * @return the common suffix shared by all the strings searched or an empty
	 * string if there is no common suffix
	 */
	public static String findSuffix(int strOffset, List<String> strs) {
		StringBuilder strB = new StringBuilder();
		String firstString = strs.get(0);

		// i = 1 because suffixes are searched in reverse order starting at {@code string.length()-i}
		if(strs instanceof RandomAccess) {
			int size = strs.size();
			for(int i = 1+strOffset; ; i++) {
				boolean match = true;
				if(i > firstString.length()) {
					strB.reverse();
					return strB.reverse().toString();
				}
				char c = firstString.charAt(firstString.length()-i);
				for(int ii = 0; ii < size; ii++) {
					String str = strs.get(ii);
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

}
