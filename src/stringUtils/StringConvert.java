package stringUtils;

import java.io.IOException;

/** Convert strings to and from various formats.<br>
 * This class includes methods for XML character escaping and user definable char escaping.
 * For example, convert XML special characters like {@code '&'} and {@code '<', '>'}
 * to their escape values {@code "&amp;"}, {@code "&lt;", "&gt;"}.
 * @author TeamworkGuy2
 * @since 2014-12-19
 */
public final class StringConvert {

	private StringConvert() { throw new AssertionError("cannot instantiate static class StringConvert"); }


	/** Convert an XML string containing invalid XML characters into XML values (&amp; &apos; etc.) by replacing
	 * invalid characters with their corresponding character codes
	 * @param content the String to convert non-XML character to XML characters
	 * @return String with invalid XML characters replaced with XML character codes
	 * @see #escapeXml(String, StringBuilder)
	 */
	public static String escapeXml(String content) {
		if(content.indexOf("&") == -1 && content.indexOf("'") == -1 && content.indexOf("\"") == -1 &&
				content.indexOf("<") == -1 && content.indexOf(">") == -1) {
			return content;
		}
		return escapeXml(content, null).toString();
	}


	/** Convert an XML string containing invalid XML characters into XML values (&amp; &apos; etc.) by replacing
	 * invalid characters with their corresponding character codes
	 * @param content the String to convert non-XML character to XML characters
	 * @param dst the destination string builder to store the escaped string in
	 * @return the {@code dst} string builder with {@code content} appended with
	 * invalid XML characters replaced with XML character codes
	 */
	public static StringBuilder escapeXml(String content, StringBuilder dst) {
		int offset = 0;
		if(dst == null) {
			dst = new StringBuilder(content);
		}
		else {
			offset = dst.length();
			dst.append(content);
		}
		int index = 0;
		index = dst.indexOf("&", offset);
		while(index > -1) {
			dst.replace(index, index+1, "&amp;");
			index = dst.indexOf("&", index+1);
		}
		index = dst.indexOf("'", offset);
		while(index > -1) {
			dst.replace(index, index+1, "&apos;");
			index = dst.indexOf("'", index+1);
		}
		index = dst.indexOf("\"", offset);
		while(index > -1) {
			dst.replace(index, index+1, "&quot;");
			index = dst.indexOf("\"", index+1);
		}
		index = dst.indexOf("<", offset);
		while(index > -1) {
			dst.replace(index, index+1, "&lt;");
			index = dst.indexOf("<", index+1);
		}
		index = dst.indexOf(">", offset);
		while(index > -1) {
			dst.replace(index, index+1, "&gt;");
			index = dst.indexOf(">", index+1);
		}
		return dst;
	}


	/** Convert an XML string containing XML character codes (&amp; &apos; etc.) by replacing
	 * them with the corresponding character
	 * @param content the String to convert XML to non-XML characters (&amp; &quot; etc.)
	 * @return String with XML characters replaced with normal characters
	 * @return a string containing {@code content} with XML characters replaced
	 * with normal characters
	 * @see #unescapeXml(String, StringBuilder)
	 */
	public static final String unescapeXml(String content) {
		if(content.indexOf("&") == -1) {
			return content;
		}
		return unescapeXml(content, null).toString();
	}


	/** Convert an XML string containing XML character codes (&amp; &apos; etc.) by replacing
	 * them with the corresponding character
	 * @param content the String to convert XML to non-XML characters (&amp; &quot; etc.)
	 * @param dst the destination string builder to store the replaced characters in
	 * @return String with XML characters replaced with normal characters
	 * @return the {@code dst} string builder with {@code content} appended with
	 * XML characters replaced with normal characters
	 */
	public static StringBuilder unescapeXml(String content, StringBuilder dst) {
		int offset = 0;
		if(dst == null) {
			dst = new StringBuilder(content);
		}
		else {
			offset = dst.length();
			dst.append(content);
		}
		int index = 0;
		index = dst.indexOf("&amp;", offset);
		while(index > -1) {
			dst.replace(index, index+5, "&");
			index = dst.indexOf("&amp;", index+1);
		}
		index = dst.indexOf("&apos;", offset);
		while(index > -1) {
			dst.replace(index, index+6, "'");
			index = dst.indexOf("&apos;", index+1);
		}
		index = dst.indexOf("&quot;", offset);
		while(index > -1) {
			dst.replace(index, index+6, "\"");
			index = dst.indexOf("&quot;", index+1);
		}
		index = dst.indexOf("&lt;", offset);
		while(index > -1) {
			dst.replace(index, index+4, "<");
			index = dst.indexOf("&lt;", index+1);
		}
		index = dst.indexOf("&gt;", offset);
		while(index > -1) {
			dst.replace(index, index+4, ">");
			index = dst.indexOf("&gt;", index+1);
		}
		return dst;
	}


	/** Add escapes to special characters in a sequence of characters<br>
	 * For example, given:<br>
	 * {@code str = "a \"block\" char '\"'"}<br>
	 * a call to:<br>
	 * {@code escape(str, '\\', '\"', '\\', new StringBuilder())}<br>
	 * would return with the contents of the last, appendable, parameter equal to:<br>
	 * {@code a \\\"block\\\" char '\\\"'}
	 * @param str the input character sequence to escape
	 * @param escapeChar the escape character to add before {@code escape1} and {@code escape2}
	 * @param escape1 the first character to escape, this is normally a special character, like {@code quote "}
	 * @param escape2 the second character to escape, this is normally the escape character itself
	 * @param dst the destination to write the escape characters to
	 * @see StringModify#unescape(CharSequence, int, char, char, Appendable)
	 */
	public static final void escape(CharSequence str, char escapeChar, char escape1, char escape2, Appendable dst) {
		try {
			for(int i = 0, size = str.length(); i < size; i++) {
				char chI = str.charAt(i);
				if(chI == escape1) {
					dst.append(escapeChar);
					dst.append(escape1);
				}
				else if(chI == escape2) {
					dst.append(escapeChar);
					dst.append(escape2);
				}
				else {
					dst.append(chI);
				}
			}
		} catch(IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}


	/**
	 * @see #unescape(CharSequence, int, int, char, char, Appendable)
	 */
	public static final int unescape(CharSequence src, int offset, char escapeChar, char chEnd, Appendable dst) {
		return unescape(src, offset, src.length() - offset, escapeChar, chEnd, dst);
	}


	/** Unwrap a sequence of escaped characters.<br>
	 * For example, a call:<br>
	 * {@code unescape("a \\\"block\\\" char '\\\"'", 0, '\\', '"', new StringBuilder())}<br>
	 * would return {@code 21} (the index of the end character or end of the string)<br>
	 * and the last, appendable, parameter would contain:<br>
	 * {@code a "block" char '"'}
	 * @param src the input character sequence to read characters from
	 * @param offset the offset into {@code src} at which to start unwrapping characters
	 * @param length the number of characters to unwrap from {@code src} starting at {@code offset}
	 * @param escapeChar the escape character to drop 
	 * @param chEnd stop parsing when this character is encountered in the {@code src} stream
	 * @param dst the destination to write unwrapped characters to
	 * @return the index of the {@code chEnd} that parsing stopped at,
	 * or the length of the {@code src} string if no {@code chEnd} character was encountered
	 * @see StringModify#escape(CharSequence, char, char, char, Appendable) 
	 */
	public static final int unescape(CharSequence src, int offset, int length, char escapeChar, char chEnd, Appendable dst) {
		int i = offset;
		try {
			for(int size = offset + length; i < size; i++) {
				char chI = src.charAt(i);
				if(chI == chEnd) {
					return i;
				}
				if(chI == escapeChar) {
					i++;
					if(i >= size) {
						return i;
					}
					chI = src.charAt(i);
				}
				dst.append(chI);
			}
		} catch(IOException ioe) {
			throw new RuntimeException(ioe);
		}
		return i;
	}


	/**
	 * @see #unescapePartialQuoted(String, int, int, char, char, char, char, boolean, Appendable)
	 */
	public static final int unescapePartialQuoted(String src, int offset, char escapeChar, char quote, char endCh1, Appendable dst) {
		return unescapePartialQuoted(src, offset, src.length() - offset, escapeChar, quote, endCh1, endCh1, false, dst);
	}


	/**
	 * @see #unescapePartialQuoted(String, int, int, char, char, char, char, boolean, Appendable)
	 */
	public static final int unescapePartialQuoted(String src, int offset, char escapeChar, char quote, char endCh1, char endCh2, Appendable dst) {
		return unescapePartialQuoted(src, offset, src.length() - offset, escapeChar, quote, endCh1, endCh2, false, dst);
	}


	/** Parse a sub-string until an ending character is reached, unless the ending
	 * character appears in a quoted section.
	 * For example, if the ending characters are {@code ','} and {@code ']'}, and the {@code str} is:<br>
	 * {@code "a string containing "quotes, [], and commas", further string"}<br>
	 * the result is:<br>
	 * {@code "a string containing "quotes, [], and commas"}
	 * 
	 * @param src
	 * @param offset
	 * @param length
	 * @param escapeChar
	 * @param quote
	 * @param endCh1
	 * @param endCh2
	 * @param throwIfNoEndChar true to throw an error if the string ends without an end char,
	 * false read until the end of the string
	 * @param dst
	 * @return the index of the {@code endCh#} that parsing stopped at, or the length
	 * of the {@code src} string if no {@code endCh#} character was encountered 
	 */
	public static final int unescapePartialQuoted(String src, int offset, int length, char escapeChar, char quote,
			char endCh1, char endCh2, boolean throwIfNoEndChar, Appendable dst) {
		final int offLen = offset + length;
		boolean added = false;
		int endIndex = StringIndex.indexOf(src, offset, length, endCh1);
		if(endIndex == -1) {
			endIndex = StringIndex.indexOf(src, offset, length, endCh2);
		}
		if(!throwIfNoEndChar && endIndex == -1) {
			endIndex = offLen;
		}
		int quoteIndex = StringIndex.indexOf(src, offset, endIndex - offset, quote);
		if(quoteIndex > -1 && quoteIndex < endIndex) {
			// append the portion of the string up to the quote
			if(offset < quoteIndex) {
				try {
					dst.append(src, offset, quoteIndex + 1);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			endIndex = StringConvert.unescape(src, quoteIndex + 1, offLen - (quoteIndex + 1), escapeChar, quote, dst) + 1;
			if(endIndex > offLen) { endIndex = -1; }
			if(offset < quoteIndex) {
				try {
					dst.append('"');
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			added = true;
		}

		if(endIndex == -1) {
			if(throwIfNoEndChar) {
				throw new IllegalArgumentException("string does not end properly, unquoted value did not end with '" + endCh1 + "' or '" + endCh2 + "'");
			}
			else {
				endIndex = offLen;
			}
		}
		else if(!added) {
			try {
				dst.append(src, offset, endIndex);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return endIndex;
	}


	// TODO what does this and unwrapChar() do, provide examples
	public static final void wrapChar(String str, char chReplaceBefore, char ch1, char ch2, Appendable dst) {
		try {
			for(int i = 0, size = str.length(); i < size; i++) {
				char chI = str.charAt(i);
				if(chI == ch1) {
					dst.append(chReplaceBefore);
					dst.append(ch1);
				}
				else if(chI == ch2) {
					dst.append(chReplaceBefore);
					dst.append(ch2);
				}
				else {
					dst.append(chI);
				}
			}
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}


	/**
	 * @param strSrc the source to read characters from
	 * @param offset the offset into {@code strSrc} at which to start reading characters
	 * @param chReplace the char to replace
	 * @param chEnd stop reading when this char is reached
	 * @param dst the destination to store the read characters in
	 * @return the index of the {@code chEnd} that parsing stopped at
	 */
	public static final int unwrapChar(CharSequence strSrc, int offset, char chReplace, char chEnd, Appendable dst) {
		int i = offset;
		try {
			for(int size = strSrc.length(); i < size; i++) {
				char chI = strSrc.charAt(i);
				if(chI == chEnd) {
					return i;
				}
				if(chI == chReplace) {
					i++;
					if(i >= size) {
						return i;
					}
					chI = strSrc.charAt(i);
				}
				dst.append(chI);
			}
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		return i;
	}

}
