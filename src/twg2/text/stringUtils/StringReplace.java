package twg2.text.stringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;

import twg2.text.stringSearch.StringCommonality;
import twg2.text.stringSearch.StringCompare;
import twg2.text.stringSearch.StringIndex;

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
	 * @return the result of replacing escape literals in {@code str} with the
	 * escape literals values, for example "\n" is replaced with a single newline character
	 */
	public static final String replaceEscapeLiterals(String str) {
		StringBuilder converted = new StringBuilder(str);
		int index = converted.indexOf(escapeStartStr, 0);
		int escapeIdIndex = -1;
		while(index > -1) {
			if(index + 1 >= converted.length() ||
					(escapeIdIndex = ArrayUtilCopy.indexOf(escapeIdentifiers, converted.charAt(index + 1))) == -1) {
				break;
			}
			converted.setCharAt(index, escapeChars[escapeIdIndex]);
			converted.deleteCharAt(index + 1);
			index = converted.indexOf(escapeStartStr, index + 1);
		}
		return converted.toString();
	}


	/** Replace all instances of a matching string with a replace strings in a {@code content} string.<br>
	 * For example, {@code replaceStrings("*** or ** * with ***", "***", "*")}<br>
	 * would return "* or ** * with *"
	 * @param content the string to search and replace the matching string in
	 * @param search the search string to search for in {@code content}
	 * @param replace the replacement string to replace {@code search} for in {@code content}
	 * @return the {@code content} string with matching {@code search} string
	 * replaced with {@code replace}
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
	 * @return the entire {@code content} string with matching {@code search}
	 * string replaced with {@code replace}
	 */
	public static String replace(String content, int contentOffset, String search, String replace) {
		int index = content.indexOf(search, contentOffset);
		if(index == -1) {
			return content;
		}
		StringBuilder validated = new StringBuilder(content);
		while(index > -1) {
			validated.replace(index, index + search.length(), replace);
			index = validated.indexOf(search, index + replace.length());
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
	 * @return the entire {@code content} string with matching {@code search}
	 * string replaced with {@code replace}
	 */
	public static StringBuilder replace(StringBuilder content, int contentOffset, String search, String replace) {
		int index = content.indexOf(search, contentOffset);
		if(index == -1) {
			return content;
		}
		while(index > -1) {
			content.replace(index, index + search.length(), replace);
			index = content.indexOf(search, index + replace.length());
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
	 * @return the {@code content} string with matching {@code searchStrs} replaced
	 * with corresponding {@code replaceStrs}
	 */
	public static String replaceStrings(String content, int contentOffset, Collection<String> searchStrs, Collection<String> replaceStrs) {
		if(searchStrs.size() != replaceStrs.size()) {
			throw new IllegalArgumentException("list of search strings must be equal in length to list of replace strings");
		}
		StringBuilder validated = new StringBuilder(content);
		if(searchStrs instanceof List && searchStrs instanceof RandomAccess &&
				replaceStrs instanceof List && replaceStrs instanceof RandomAccess) {
			List<String> searchList = (List<String>)searchStrs;
			List<String> replaceList = (List<String>)replaceStrs;
			for(int i = 0, size = searchList.size(); i < size; i++) {
				String search = searchList.get(i);
				String replace = replaceList.get(i);
				int index = validated.indexOf(search, contentOffset);
				while(index > -1) {
					validated.replace(index, index + search.length(), replace);
					index = validated.indexOf(search, index + replace.length());
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
					validated.replace(index, index + search.length(), replace);
					index = validated.indexOf(search, index + replace.length());
				}
			}
		}
		return validated.toString();
	}


	/** Replace all instances of a list of matching strings with a replacement string.<br>
	 * For example, {@code replaceStrings("&lt; or &amp;&gt;", Arrays.asList("&amp;", "&lt;", "&gt;"), "#")}<br>
	 * would return "# or ##"
	 * @param content the string to search and replace matching strings in
	 * @param contentOffset the offset into {@code content} at which to start replacing
	 * @param searchStrs the list of search strings to search for in {@code content},
	 * matching strings are replaced in the order they appear in the list
	 * @param replaceStr the replacement string, all matching strings are replaced with this string
	 * @return the {@code content} string with matching {@code searchStrs} replaced
	 * by {@code replaceStr}
	 */
	public static String replaceStrings(String content, int contentOffset, Collection<String> searchStrs, String replaceStr) {
		StringBuilder validated = new StringBuilder(content);
		if(searchStrs instanceof List && searchStrs instanceof RandomAccess) {
			List<String> searchList = (List<String>)searchStrs;
			for(int i = 0, size = searchList.size(); i < size; i++) {
				String search = searchList.get(i);
				int index = validated.indexOf(search, contentOffset);
				while(index > -1) {
					validated.replace(index, index + search.length(), replaceStr);
					index = validated.indexOf(search, index + replaceStr.length());
				}
			}
		}
		else {
			Iterator<String> searchIter = searchStrs.iterator();
			while(searchIter.hasNext()) {
				String search = searchIter.next();
				int index = validated.indexOf(search, contentOffset);
				while(index > -1) {
					validated.replace(index, index + search.length(), replaceStr);
					index = validated.indexOf(search, index + replaceStr.length());
				}
			}
		}
		return validated.toString();
	}


	/** Replace each instance of a matching sub-string with a list of corresponding
	 * replace strings in a {@code content} string.<br>
	 * For example, {@code replaceStrings("%% or %%%%", "%%", Arrays.asList("&", "<", ">"))}<br>
	 * would return "< or &>"
	 * For example, {@code replaceStrings("%% or %%%%", "%%", Arrays.asList("<", ">"))}<br>
	 * would return "< or >%%" if {@code repeatReplacements == false}
	 * would return "< or ><" if {@code repeatReplacements == true}
	 * @param content the string to search and replace matching strings in
	 * @param contentOffset the offset into {@code content} at which to start replacing
	 * @param searchStr the search string to search for in {@code content}
	 * @param replaceStrs the list of replacement strings, each index in this list
	 * corresponds to a matching {@code searchStr} sub-string in {@code content}
	 * @param repeatReplacements true to reuse {@code replaceStrs} if all of them are used
	 * once to replace matching {@code searchStr} sub-strings in {@code content},
	 * false to stop replacing once each of {@code replaceStrs} has been used once
	 * @return the {@code content} string with each matching {@code searchStr} replaced
	 * with one of the {@code replaceStrs} in order
	 */
	public static String replaceStrings(String content, int contentOffset, String searchStr, Collection<String> replaceStrs, boolean repeatReplacements) {
		StringBuilder validated = new StringBuilder(content);
		if(replaceStrs instanceof List && replaceStrs instanceof RandomAccess) {
			List<String> replaceList = (List<String>)replaceStrs;
			int index = validated.indexOf(searchStr, contentOffset);
			int replaceStrsSize = replaceList.size();
			int i = 0;
			while(index > -1) {
				String replace = replaceList.get(i);
				validated.replace(index, index + searchStr.length(), replace);
				index = validated.indexOf(searchStr, index + replace.length());
				if(i + 1 == replaceStrsSize && !repeatReplacements) {
					break;
				}
				i = (i + 1) % replaceStrsSize;
			}
		}
		else {
			Iterator<String> replaceIter = replaceStrs.iterator();
			int index = validated.indexOf(searchStr, contentOffset);
			while(index > -1) {
				String replace = replaceIter.next();
				validated.replace(index, index + searchStr.length(), replace);
				index = validated.indexOf(searchStr, index + replace.length());
				if(!replaceIter.hasNext()) {
					if(repeatReplacements) {
						replaceIter = replaceStrs.iterator();
					}
					else {
						break;
					}
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
	 * @param dst an empty string builder to store the subset of the {@code content}
	 * string specified by {@code contentOffset} with matching {@code searchStrs}
	 * replaced with corresponding {@code replaceStrs}
	 * @return the length change of {@code dst} after replacing the matching strings in {@code content}
	 */
	public static int replace(char[] content, int contentOffset, String search, String replace, StringBuilder dst) {
		dst.append(content, contentOffset, content.length - contentOffset);
		if(StringIndex.indexOf(content, 0, search, 0) == -1) {
			return 0;
		}
		int lengthChange = 0;
		int replaceLenDiff = replace.length() - search.length();
		int index = dst.indexOf(search, 0);
		while(index > -1) {
			dst.replace(index, index + search.length(), replace);
			lengthChange += replaceLenDiff;
			index = dst.indexOf(search, index + replace.length());
		}
		return lengthChange;
	}


	/**
	 * @see #replaceStrings(char[], int, Collection, Collection, StringBuilder)
	 */
	public static int replaceStrings(String content, Map<String, String> searchReplaceStrs, StringBuilder dst) {
		dst.append(content);
		return replaceStrings(searchReplaceStrs.entrySet().iterator(), dst, dst.length());
	}


	/**
	 * @see #replaceStrings(char[], int, Collection, Collection, StringBuilder)
	 */
	public static int replaceStrings(Map<String, String> searchReplaceStrs, StringBuilder contentAndDst) {
		return replaceStrings(searchReplaceStrs.entrySet().iterator(), contentAndDst, contentAndDst.length());
	}


	/**
	 * @see #replaceStrings(char[], int, Collection, Collection, StringBuilder)
	 */
	public static int replaceStrings(String content, Collection<String> searchStrs, Collection<String> replaceStrs,
			StringBuilder dst) {
		dst.append(content);
		return replaceStrings(searchStrs, replaceStrs, dst, dst.length());
	}


	/**
	 * @see #replaceStrings(char[], int, Collection, Collection, StringBuilder)
	 */
	public static int replaceStrings(String content, int contentOff, Collection<String> searchStrs, Collection<String> replaceStrs,
			StringBuilder dst) {
		dst.append(content, contentOff, content.length() - contentOff);
		return replaceStrings(searchStrs, replaceStrs, dst, dst.length());
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
	 * @param dst an empty string builder to store the subset of the {@code content}
	 * string specified by {@code contentOffset} with matching {@code searchStrs}
	 * replaced with corresponding {@code replaceStrs}
	 * @return the length change of {@code dst} after replacing the matching strings in {@code content}
	 */
	public static int replaceStrings(char[] content, int contentOffset, Collection<String> searchStrs, Collection<String> replaceStrs,
			StringBuilder dst) {
		dst.append(content, contentOffset, content.length - contentOffset);
		return replaceStrings(searchStrs, replaceStrs, dst, dst.length());
	}


	/**
	 * @see #replaceStrings(char[], int, Collection, Collection, StringBuilder)
	 */
	public static int replaceStrings(Collection<String> searchStrs, Collection<String> replaceStrs, StringBuilder contentAndDst, int contentAndDstOffset) {
		if(searchStrs.size() != replaceStrs.size()) {
			throw new IllegalArgumentException("list of search strings must be equal in length to list of replace strings");
		}
		int lengthChange = 0;
		if(searchStrs instanceof List && searchStrs instanceof RandomAccess && replaceStrs instanceof List && replaceStrs instanceof RandomAccess) {
			List<String> searchList = (List<String>)searchStrs;
			List<String> replaceList = (List<String>)replaceStrs;
			for(int i = 0, size = searchStrs.size(); i < size; i++) {
				String search = searchList.get(i);
				String replace = replaceList.get(i);
				int replaceLenDiff = replace.length() - search.length();
				int index = contentAndDst.indexOf(search, contentAndDstOffset);
				while(index > -1) {
					contentAndDst.replace(index, index + search.length(), replace);
					lengthChange += replaceLenDiff;
					index = contentAndDst.indexOf(search, index + replace.length());
				}
			}
		}
		else {
			Iterator<String> searchIter = searchStrs.iterator();
			Iterator<String> replaceIter = replaceStrs.iterator();
			while(searchIter.hasNext()) {
				String search = searchIter.next();
				String replace = replaceIter.next();
				int replaceLenDiff = replace.length() - search.length();
				int index = contentAndDst.indexOf(search, contentAndDstOffset);
				while(index > -1) {
					contentAndDst.replace(index, index + search.length(), replace);
					lengthChange += replaceLenDiff;
					index = contentAndDst.indexOf(search, index + replace.length());
				}
			}
		}
		return lengthChange;
	}



	/**
	 * @see #replaceStrings(char[], int, Collection, Collection, StringBuilder)
	 */
	public static int replaceStrings(Iterator<Map.Entry<String, String>> searchAndReplaceStrs, StringBuilder contentAndDst, int contentAndDstOffset) {
		int lengthChange = 0;
		while(searchAndReplaceStrs.hasNext()) {
			Map.Entry<String, String> entry = searchAndReplaceStrs.next();
			String search = entry.getKey();
			String replace = entry.getValue();
			int replaceLenDiff = replace.length() - search.length();
			int index = contentAndDst.indexOf(search, contentAndDstOffset);
			while(index > -1) {
				contentAndDst.replace(index, index + search.length(), replace);
				lengthChange += replaceLenDiff;
				index = contentAndDst.indexOf(search, index + replace.length());
			}
		}
		return lengthChange;
	}


	/** Replace the tokens in the specified string.<br/>
	 * For example {@code str = "a $string with $custom tokens and replace values"}<br/>
	 * and {@code tokens = ["$str"="token", "$string"="String", "$custom"="infinite", "replace values"="others"]}<br/>
	 * the {@code result = "a tokening with infinite tokens and others"}.<br>
	 * Differs from {@link #replaceStrings} by doing a binary search of the tokens,
	 * and replacing the closest match rather than simply replacing {@code tokens} sequentially.
	 * @param str the string to search and replace tokens in
	 * @param tokens a list of {@link java.util.Map.Entry Map.Entries}, the keys are the
	 * strings to search for and the values are the strings to replace found keys with
	 * @param isSorted true if the {@code tokens} entry array is sorted, false if it is not
	 * @return the resulting string with matching tokens replaced
	 */
	public static final String replaceTokens(String str, Map.Entry<String, String>[] tokens, boolean isSorted) {
		StringBuilder sb = new StringBuilder(str);
		replaceTokens(null, tokens, isSorted, true, sb);
		return sb.toString();
	}


	/** Replace the tokens in the specified string.<br/>
	 * For example {@code str = "a $string with $custom tokens and replace values"}<br/>
	 * and {@code tokens = ["$str"="token", "$string"="String", "$custom"="infinite", "replace values"="others"]}<br/>
	 * the {@code result = "a tokening with infinite tokens and others"}.<br>
	 * Differs from {@link #replaceStrings} by doing a binary search of the tokens,
	 * and replacing the closest match rather than simply replacing {@code tokens} sequentially.
	 * @param str the string to search and replace tokens in
	 * @param tokens a list of {@link java.util.Map.Entry Map.Entries}, the keys are the
	 * strings to search for and the values are the strings to replace found keys with
	 * @param isSorted true if the {@code tokens} entry array is sorted, false if it is not
	 * @param preserveOrder true if the {@code tokens} entry array's order should be
	 * preserved, false will cause this method to sort the {@code tokens} array
	 * by natural string order
	 * @return the resulting string with matching tokens replaced
	 */
	public static final String replaceTokens(String str, Map.Entry<String, String>[] tokens,
			boolean isSorted, boolean preserveOrder) {
		StringBuilder sb = new StringBuilder(str);
		replaceTokens(null, tokens, isSorted, preserveOrder, sb);
		return sb.toString();
	}


	public static final StringBuilder replaceTokens(Map.Entry<String, String>[] tokens,
			boolean isSorted, boolean preserveOrder, StringBuilder srcAndDst) {
		return replaceTokens((a, b) -> a.getKey().compareTo(b.getKey()), tokens, isSorted, preserveOrder, srcAndDst);
	}


	/** Replace the tokens in the specified string.<br/>
	 * For example: {@code str = "a $string with $custom tokens and replace values"}<br/>
	 * and {@code tokens = ["$str"="token", "$string"="String", "$custom"="infinite", "replace values"="others"]}<br/>
	 * returns: {@code "a String with infinite tokens and others"}<br>
	 *
	 * Differs from {@link #replaceStrings} by doing a binary search of the tokens,
	 * and replacing the closest match rather than simply replacing {@code tokens} sequentially.
	 * @param comparator the {@link Comparator} to use to sort the {@code tokens}
	 * map entry array
	 * @param tokens a list of {@link java.util.Map.Entry Map.Entries}, the keys
	 * are the strings to search for and the values are the strings to replace found keys with
	 * @param isSorted true if the {@code tokens} entry array is sorted, false if it is not
	 * @param preserveOrder true if the {@code tokens} entry array's order should be
	 * preserved, false will cause this method to sort the {@code tokens} array
	 * by natural string order
	 * @param srcAndDst the source to search and replace tokens in, sub-strings are directly replaced in this builder
	 * @return the {@code srcAndDst} builder
	 */
	public static final StringBuilder replaceTokens(Comparator<Map.Entry<String, String>> comparator,
			Map.Entry<String, String>[] tokens, boolean isSorted, boolean preserveOrder, StringBuilder srcAndDst) {
		if(!isSorted && !preserveOrder) {
			isSorted = true;
			if(comparator != null) {
				Arrays.sort(tokens, comparator);
			}
			else {
				Arrays.sort(tokens, (e1, e2) -> e1.getKey().compareTo(e2.getKey()));
			}
		}

		String prefix = StringCommonality.findPrefix(0, tokens);
		if(prefix.length() > 0) {
			int index = srcAndDst.indexOf(prefix, 0);
			while(index > -1) {
				Map.Entry<String, String> match = StringCompare.closestMatch(srcAndDst, index, tokens, isSorted);
				if(match != null) {
					srcAndDst.replace(index, index + match.getKey().length(), match.getValue());
					index = srcAndDst.indexOf(prefix, index + match.getValue().length());
				}
				else {
					index = srcAndDst.indexOf(prefix, index + 1);
				}
			}
		}
		else {
			for(int i = 0; i < srcAndDst.length(); i++) {
				Map.Entry<String, String> match = StringCompare.closestMatch(srcAndDst, i, tokens, isSorted);
				if(match != null) {
					srcAndDst.replace(i, i + match.getKey().length(), match.getValue());
					i += match.getValue().length() - 1;
				}
			}
		}
		return srcAndDst;
	}

}
