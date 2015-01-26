package stringUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;

/** Methods for replacing portions of a string
 * @author TeamworkGuy2
 * @since 2014-10-18
 */
public final class StringReplace {
	public static final String escapeStartStr = "\\";
	/** the valid escape characters that can come after a '\', other than unicode escape sequences */
	public static char[] escapeIdentifiers = new char[] {'t', 'b', 'n', 'r', 'f', '\'', '\"', '\\'};
	/** the escape characters created by the escape sequences in {@link StringReplace#escapeIdentifiers} */
	public static char[] escapeChars = new char[] {'\t', '\b', '\n', '\r', '\f', '\'', '\"', '\\'};


	private StringReplace() { throw new AssertionError("cannot instantiate static class StringReplace"); }


	/** Replace escape literals in a string with their character equivalents.
	 * Escape literals are characters from the {@link #escapeIdentifiers} list,
	 * like "\n", "\t", etc.
	 * @param str the string to replace escape literals in
	 * @return the result of replacing escape literals in {@code str} with the escape literals values,
	 * for example "\n" is replaced with a single newline character
	 */
	public static final String replaceEscapeLiterals(String str) {
		StringBuilder converted = new StringBuilder(str);
		int index = 0;
		index = converted.indexOf(escapeStartStr, 0);
		int escapeIdIndex = -1;
		while(index > -1) {
			if(index + 1 >= converted.length() ||
					(escapeIdIndex = ArrayUtilCopy.indexOf(escapeIdentifiers, converted.charAt(index + 1))) == -1) {
				break;
			}
			converted.replace(index, index+2, "" + escapeChars[escapeIdIndex]);
			index = converted.indexOf(escapeStartStr, index+1);
		}
		return converted.toString();
	}


	/** Replace all instances of a matching string with a replace strings in a {@code content} string.<br>
	 * For example, {@code replaceStrings("*** or ** * with ***", "***", "*")}<br>
	 * would return "* or ** * with *"
	 * @param content the string to search and replace the matching string in
	 * @param contentOffset the offset into {@code content} at which to start replacing
	 * @param search the search string to search for in {@code content}
	 * @param replace the replacement string to replace {@code search} for in {@code content}
	 * @return the {@code content} string with matching {@code search} string replaced with {@code replace}
	 */
	public static String replace(String content, String search, String replace) {
		return replace(content, 0, search, replace);
	}


	/** Replace all instances of a matching string with a replace strings in a {@code content} string.<br>
	 * For example, {@code replaceStrings("*** or ** * with ***", "***", "*")}<br>
	 * would return "* or ** * with *"
	 * @param content the string to search and replace the matching string in
	 * @param contentOffset the offset into {@code content} at which to start replacing
	 * @param search the search string to search for in {@code content}
	 * @param replace the replacement string to replace {@code search} for in {@code content}
	 * @return the entire {@code content} string with matching {@code search} string replaced with {@code replace}
	 */
	public static String replace(String content, int contentOffset, String search, String replace) {
		int index = content.indexOf(search, contentOffset);
		if(index == -1) {
			return content;
		}
		StringBuilder validated = new StringBuilder(content);
		while(index > -1) {
			validated.replace(index, index+search.length(), replace);
			index = validated.indexOf(search, index+replace.length());
		}
		return validated.toString();
	}


	/** Replace all instances of a matching string with a replace strings in a {@code content} string.<br>
	 * For example, {@code replaceStrings("*** or ** * with ***", "***", "*")}<br>
	 * would return "* or ** * with *"
	 * @param content the string to search and replace the matching string in
	 * @param contentOffset the offset into {@code content} at which to start replacing
	 * @param search the search string to search for in {@code content}
	 * @param replace the replacement string to replace {@code search} for in {@code content}
	 * @return the entire {@code content} string with matching {@code search} string replaced with {@code replace}
	 */
	public static StringBuilder replace(StringBuilder content, int contentOffset, String search, String replace) {
		int index = content.indexOf(search, contentOffset);
		if(index == -1) {
			return content;
		}
		while(index > -1) {
			content.replace(index, index+search.length(), replace);
			index = content.indexOf(search, index+replace.length());
		}
		return content;
	}


	/** Replace all instances of a list of matching strings with a list of corresponding
	 * replace strings in a {@code content} string.<br>
	 * For example, {@code replaceStrings("&lt; or &amp;&gt;", Arrays.asList("&amp;", "&lt;", "&gt;"), Arrays.asList("&", "<", ">"))}<br>
	 * would return "< or &>"
	 * @param content the string to search and replace matching strings in
	 * @param contentOffset the offset into {@code content} at which to start replacing
	 * @param searchStrs the list of search strings to search for in {@code content},
	 * matching strings are replaced in the order they appear in the list
	 * @param replaceStrs the list of replacement strings, each index in this list
	 * corresponds to an index in {@code searchStrs}
	 * @return the {@code content} string with matching {@code searchStrs} replaced with corresponding {@code replaceStrs}
	 */
	public static String replaceStrings(String content, int contentOffset, List<String> searchStrs, List<String> replaceStrs) {
		if(searchStrs.size() != replaceStrs.size()) {
			throw new IllegalArgumentException("list of search strings must be equal in length to list of replace strings");
		}
		StringBuilder validated = new StringBuilder(content);
		if(searchStrs instanceof RandomAccess && replaceStrs instanceof RandomAccess) {
			for(int i = 0, size = searchStrs.size(); i < size; i++) {
				String search = searchStrs.get(i);
				String replace = replaceStrs.get(i);
				int index = validated.indexOf(search, contentOffset);
				while(index > -1) {
					validated.replace(index, index+search.length(), replace);
					index = validated.indexOf(search, index+replace.length());
				}
			}
		}
		else {
			Iterator<String> searchIter = searchStrs.iterator();
			Iterator<String> replaceIter = replaceStrs.iterator();
			while(searchIter.hasNext()) {
				String search = searchIter.next();
				String replace = replaceIter.next();
				int index = validated.indexOf(search, contentOffset);
				while(index > -1) {
					validated.replace(index, index+search.length(), replace);
					index = validated.indexOf(search, index+replace.length());
				}
			}
		}
		return validated.toString();
	}


	/** Replace all instances of a matching string with a replace strings in a {@code content} string.<br>
	 * For example, {@code replaceStrings("*** or ** * with ***", "***", "*")}<br>
	 * would return "* or ** * with *"
	 * @param content the string to search and replace the matching string in
	 * @param contentOffset the offset into {@code content} at which to start replacing
	 * @param search the search string to search for in {@code content}
	 * @param replace the replacement string to replace {@code search} for in {@code content}
	 * @param dst an empty string builder to store the subset of the {@code content} string specified
	 * by {@code contentOffset} with matching {@code searchStrs} replaced with corresponding {@code replaceStrs}
	 * @return the length change of {@code dst} after replacing the matching strings in {@code content}
	 */
	public static int replace(char[] content, int contentOffset, String search, String replace, StringBuilder dst) {
		dst.append(content, contentOffset, content.length-contentOffset);
		if(StringIndex.indexOf(content, 0, search, 0) == -1) {
			return 0;
		}
		int lengthChange = 0;
		int replaceLengthDiff = replace.length() - search.length();
		int index = dst.indexOf(search, 0);
		while(index > -1) {
			dst.replace(index, index+search.length(), replace);
			lengthChange += replaceLengthDiff;
			index = dst.indexOf(search, index+replace.length());
		}
		return lengthChange;
	}


	/** Replace all instances of a list of matching strings with a list of corresponding
	 * replace strings in a {@code content} string.<br>
	 * For example, {@code replaceStrings("&lt; or &amp;&gt;", Arrays.asList("&amp;", "&lt;", "&gt;"), Arrays.asList("&", "<", ">"))}<br>
	 * would return "< or &>"
	 * @param content the string to search and replace matching strings in
	 * @param contentOffset the offset into {@code content} at which to start replacing
	 * @param searchStrs the list of search strings to search for in {@code content},
	 * matching strings are replaced in the order they appear in the list
	 * @param replaceStrs the list of replacement strings, each index in this list
	 * corresponds to an index in {@code searchStrs}
	 * @param dst an empty string builder to store the subset of the {@code content} string specified
	 * by {@code contentOffset} with matching {@code searchStrs} replaced with corresponding {@code replaceStrs}
	 * @return the length change of {@code dst} after replacing the matching strings in {@code content}
	 */
	public static int replaceStrings(char[] content, int contentOffset, List<String> searchStrs, List<String> replaceStrs,
			StringBuilder dst) {
		if(searchStrs.size() != replaceStrs.size()) {
			throw new IllegalArgumentException("list of search strings must be equal in length to list of replace strings");
		}
		dst.append(content, contentOffset, content.length-contentOffset);
		int lengthChange = 0;
		if(searchStrs instanceof RandomAccess && replaceStrs instanceof RandomAccess) {
			for(int i = 0, size = searchStrs.size(); i < size; i++) {
				String search = searchStrs.get(i);
				String replace = replaceStrs.get(i);
				int replaceLengthDiff = replace.length() - search.length();
				int index = dst.indexOf(search, 0);
				while(index > -1) {
					dst.replace(index, index+search.length(), replace);
					lengthChange += replaceLengthDiff;
					index = dst.indexOf(search, index+replace.length());
				}
			}
		}
		else {
			Iterator<String> searchIter = searchStrs.iterator();
			Iterator<String> replaceIter = replaceStrs.iterator();
			while(searchIter.hasNext()) {
				String search = searchIter.next();
				String replace = replaceIter.next();
				int replaceLengthDiff = replace.length() - search.length();
				int index = dst.indexOf(search, 0);
				while(index > -1) {
					dst.replace(index, index+search.length(), replace);
					lengthChange += replaceLengthDiff;
					index = dst.indexOf(search, index+replace.length());
				}
			}
		}
		return lengthChange;
	}


	/** Replace the tokens in the specified string.<br/>
	 * For example {@code str = "a $string with $custom tokens and replace values"}<br/>
	 * and {@code tokens = ["$str"="token", "$string"="String", "$custom"="infinite", "replace values"="others"]}<br/>
	 * the {@code result = "a tokening with infinite tokens and others"}.<br>
	 * Differs from {@link #replaceStrings} by doing a binary search of the tokens, and replacing the closest match
	 * rather than simply replacing {@code tokens} sequentially.
	 * @param str the string to search and replace tokens in
	 * @param tokens a list of {@link java.util.Map.Entry Map.Entries}, the keys are the strings to
	 * search for and the values are the strings to replace found keys with
	 * @param isSorted true if the {@code tokens} entry array is sorted, false if it is not
	 * @return the resulting string with matching tokens replaced
	 */
	public static final String replaceTokens(String str, Map.Entry<String, String>[] tokens,
			boolean isSorted) {
		return replaceTokens(str, null, tokens, isSorted, true);
	}


	/** Replace the tokens in the specified string.<br/>
	 * For example {@code str = "a $string with $custom tokens and replace values"}<br/>
	 * and {@code tokens = ["$str"="token", "$string"="String", "$custom"="infinite", "replace values"="others"]}<br/>
	 * the {@code result = "a tokening with infinite tokens and others"}.<br>
	 * Differs from {@link #replaceStrings} by doing a binary search of the tokens, and replacing the closest match
	 * rather than simply replacing {@code tokens} sequentially.
	 * @param str the string to search and replace tokens in
	 * @param tokens a list of {@link java.util.Map.Entry Map.Entries}, the keys are the strings to
	 * search for and the values are the strings to replace found keys with
	 * @param isSorted true if the {@code tokens} entry array is sorted, false if it is not
	 * @param preserveOrder true if the {@code tokens} entry array's order should be preserved, false will
	 * cause this method to sort the {@code tokens} array by natural string order
	 * @return the resulting string with matching tokens replaced
	 */
	public static final String replaceTokens(String str, Map.Entry<String, String>[] tokens,
			boolean isSorted, boolean preserveOrder) {
		return replaceTokens(str, null, tokens, isSorted, preserveOrder);
	}


	/** Replace the tokens in the specified string.<br/>
	 * For example {@code str = "a $string with $custom tokens and replace values"}<br/>
	 * and {@code tokens = ["$str"="token", "$string"="String", "$custom"="infinite", "replace values"="others"]}<br/>
	 * the {@code result = "a String with infinite tokens and others"}.<br>
	 * Differs from {@link #replaceStrings} by doing a binary search of the tokens, and replacing the closest match
	 * rather than simply replacing {@code tokens} sequentially.
	 * @param str the string to search and replace tokens in
	 * @param comparator the {@link Comparator} to use to sort the {@code tokens} map entry array
	 * @param tokens a list of {@link java.util.Map.Entry Map.Entries}, the keys are the strings to
	 * search for and the values are the strings to replace found keys with
	 * @param isSorted true if the {@code tokens} entry array is sorted, false if it is not
	 * @param preserveOrder true if the {@code tokens} entry array's order should be preserved, false will
	 * cause this method to sort the {@code tokens} array by natural string order
	 * @return the resulting string with matching tokens replaced
	 */
	public static final String replaceTokens(String str, Comparator<Map.Entry<String, String>> comparator,
			Map.Entry<String, String>[] tokens, boolean isSorted, boolean preserveOrder) {
		if(!isSorted && !preserveOrder) {
			isSorted = true;
			if(comparator != null) {
				Arrays.sort(tokens, comparator);
			}
			else {
				Arrays.sort(tokens, (e1, e2) -> e1.getKey().compareTo(e2.getKey()));
			}
		}

		StringBuilder result = new StringBuilder(str);
		String prefix = StringCommonality.findPrefix(0, tokens);
		if(prefix.length() > 0) {
			int index = result.indexOf(prefix, 0);
			while(index > -1) {
				Map.Entry<String, String> match = StringCompare.closestMatch(result, index, tokens, isSorted);
				if(match != null) {
					result.replace(index, index+match.getKey().length(), match.getValue());
					index = result.indexOf(prefix, index+match.getValue().length());
				}
				else {
					index = result.indexOf(prefix, index+1);
				}
			}
		}
		else {
			for(int i = 0; i < result.length(); i++) {
				Map.Entry<String, String> match = StringCompare.closestMatch(result, i, tokens, isSorted);
				if(match != null) {
					result.replace(i, i+match.getKey().length(), match.getValue());
					i+=match.getValue().length()-1;
				}
			}
		}
		return result.toString();
	}

}
