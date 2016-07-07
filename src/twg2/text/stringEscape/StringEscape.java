package twg2.text.stringEscape;

import java.io.IOException;
import java.io.UncheckedIOException;

/** Convert strings to and from various formats.<br>
 * This class includes methods for XML character escaping and user definable char escaping.
 * For example, convert XML special characters like {@code '&'} and {@code '<', '>'}
 * to their escape values {@code "&amp;"}, {@code "&lt;", "&gt;"}.
 * @author TeamworkGuy2
 * @since 2014-12-19
 */
public final class StringEscape {

	private StringEscape() { throw new AssertionError("cannot instantiate static class StringEscape"); }


	/**
	 * @see #escape(CharSequence, int, char, char, char, Appendable)
	 */
	public static final void escape(CharSequence str, char escapeChar, char escape1, char escape2, Appendable dst) {
		escape(str, 0, escapeChar, escape1, escape2, dst);
	}


	/** Add escape characters to special characters in a {@link CharSequence}<br>
	 * For example, given:<br>
	 * {@code str = "a \"block\" char '\"'"}<br>
	 * For example:<br>
	 * {@code escape(str, '\\', '\"', '\\', new StringBuilder())}<br>
	 * appends the following to 'dst':<br>
	 * {@code a \\\"block\\\" char '\\\"'}
	 * @param str the input character sequence to escape
	 * @param escapeChar the escape character to add before {@code escape1} and {@code escape2}
	 * @param escape1 the first character to escape, this is normally a special character, like {@code quote "}
	 * @param escape2 the second character to escape, this is normally the escape character itself
	 * @param dst the destination to write the escape characters to
	 * @see StringEscape#unescape(CharSequence, int, char, char, Appendable)
	 */
	public static final void escape(CharSequence str, int offset, char escapeChar, char escape1, char escape2, Appendable dst) {
		// TODO repeating escapeChar when escapeChar equals escape1 or escape2 produces ambigious results
		try {
			char prevChar = 0;
			char nextChar = 0;
			for(int i = offset, size = str.length(); i < size; i++) {
				char chI = str.charAt(i);
				nextChar = i < size - 1 ? str.charAt(i + 1) : 0;

				if(chI == escape1 && (i == offset || prevChar != escapeChar) && (i == size - 1 || (nextChar != escapeChar && nextChar != escape1 && nextChar != escape2))) {
					dst.append(escapeChar);
					dst.append(escape1);
				}
				else if(chI == escape2 && (i == offset || prevChar != escapeChar) && (i == size - 1 || (nextChar != escapeChar && nextChar != escape1 && nextChar != escape2))) {
					dst.append(escapeChar);
					dst.append(escape2);
				}
				else {
					dst.append(chI);
				}
				prevChar = chI;
			}
		} catch(IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}


	/**
	 * @see #unescape(CharSequence, int, int, char, char, Appendable)
	 */
	public static final int unescape(CharSequence src, int offset, char escapeChar, char chEnd, Appendable dst) {
		return unescape(src, offset, src.length() - offset, escapeChar, chEnd, dst);
	}


	/** Unwrap a sequence of escaped characters.<br>
	 * For example:<br>
	 * {@code unescape("a \\\"block\\\" char '\\\"'", 0, '\\', '"', new StringBuilder())}<br>
	 * would return {@code 21} (the index of the end character or end of the string)<br>
	 * appends the following to 'dst':<br>
	 * {@code a "block" char '"'}
	 * @param src the input character sequence to read characters from
	 * @param offset the offset into {@code src} at which to start unwrapping characters
	 * @param length the number of characters to unwrap from {@code src} starting at {@code offset}
	 * @param escapeChar the escape character to drop 
	 * @param chEnd stop parsing when this character is encountered in the {@code src} stream
	 * @param dst the destination to write unwrapped characters to
	 * @return the index of the {@code chEnd} that parsing stopped at,
	 * or the length of the {@code src} string if no {@code chEnd} character was encountered
	 * @see StringEscape#escape(CharSequence, char, char, char, Appendable) 
	 */
	public static final int unescape(CharSequence src, int offset, int length, char escapeChar, char chEnd, Appendable dst) {
		int i = offset;
		try {
			for(int size = offset + length; i < size; i++) {
				char chI = src.charAt(i);
				if(chI == escapeChar) {
					i++;
					if(i >= size) {
						return i;
					}
					chI = src.charAt(i);
				}
				else if(chI == chEnd) {
					return i;
				}
				dst.append(chI);
			}
		} catch(IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
		return i;
	}


	// TODO what does this and unwrapChar() do, provide examples
	public static final void escapeChar(String str, char chReplaceBefore, char ch1, char ch2, Appendable dst) {
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
		} catch(IOException e) {
			throw new UncheckedIOException(e);
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
	public static final int unescapeChar(CharSequence strSrc, int offset, char chReplace, char chEnd, Appendable dst) {
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
		} catch(IOException e) {
			throw new UncheckedIOException(e);
		}
		return i;
	}

}
