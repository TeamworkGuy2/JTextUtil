package twg2.text.stringUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

/** Save and load string to and from the Java {@link Properties} format.
 * @author TeamworkGuy2
 * @since 2014-12-0
 */
public final class StringToProperties {

	private StringToProperties() { throw new AssertionError("cannot instantiate static class StringToProperties"); }


	/** Convert a list of key-value pair strings to valid key-value strings
	 * that can be written to an output destination and later read and parsed
	 * by {@link #loadKeyValueStrings(List, Collection)}.
	 * The key-value pairs stored in the list should be written in the format:
	 * {@code key + '=' + value}.
	 * @param keyValueStrings a list of key-value pairs to convert to valid
	 * strings that can be written in the format {@code (key + "=" + value)}.
	 * Each value in the list is overwritten with a new key-value pair containing the
	 * previous key-value converted to valid output strings.
	 * @param dst the list of key-value strings to write the resulting key-value strings to.
	 * This parameter may be the same object as {@code keyValueStrings}
	 * @see java.util.Properties#saveConvert()
	 */
	public static final void _saveKeyValueStrings(Collection<? extends Entry<String, String>> keyValueStrings, Collection<? super Entry<String, String>> dst) {
		StringBuilder buf = new StringBuilder();
		for(Entry<String, String> keyValuePair : keyValueStrings) {
			String key = saveKeyValueString(keyValuePair.getKey(), true, false, buf).toString();
			buf.setLength(0);
			String value = saveKeyValueString(keyValuePair.getValue(), false, false, buf).toString();
			buf.setLength(0);

			Entry<String, String> resultEntry = new AbstractMap.SimpleImmutableEntry<>(key, value);
			dst.add(resultEntry);
		}
	}


	private static final <T extends Appendable> T saveKeyValueString(String str, boolean escapeSpaces, boolean escapeUnicode, T dst) {
		int len = str.length();
		try {
			for(int x = 0; x < len; x++) {
				char a = str.charAt(x);
				if((a > 61) && (a < 127)) {
					if(a == '\\') {
						dst.append("\\\\");
						continue;
					}
					dst.append(a);
					continue;
				}
				switch(a) {
				case ' ':
					if(x == 0 || escapeSpaces) {
						dst.append('\\');
					}
					dst.append(' ');
					break;
				case '\t': dst.append("\\t");
				break;
				case '\n': dst.append("\\n");
				break;
				case '\r': dst.append("\\r");
				break;
				case '\f': dst.append("\\f");
				break;
				case '=': // fall through
				case ':': // fall through
				case '#': // fall through
				case '!': // handle {= : # !}
					dst.append('\\').append(a);
					break;
				default:
					if(((a < 0x0020) || (a > 0x007E)) && escapeUnicode) {
						dst.append("\\u");
						dst.append(StringHex.toHex((a >> 12) & 0xF));
						dst.append(StringHex.toHex((a >> 8) & 0xF));
						dst.append(StringHex.toHex((a >> 4) & 0xF));
						dst.append(StringHex.toHex(a & 0xF));
					}
					else {
						dst.append(a);
					}
				}
			}
		} catch(IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
		return dst;
	}


	/** Parse a list of strings into a collection of key-value pairs.
	 * Each string should contain a '=' which separates the key (on the left side of '=')
	 * from the value (on the right side of '='). The string is split into a pair of strings
	 * and each pair is added to the {@code dst} collection of {@link java.util.Map.Entry Map.Entries}.
	 * @param strs a list of strings, each of which contains a '=' character splitting the
	 * string into a key-value pair
	 * @param dst the destination collection of key-value entries in which to store the parsed
	 * key-value string pairs
	 * @see java.util.Properties#load0()
	 */
	public static final void loadKeyValueStrings(List<String> strs, Collection<? super Entry<String, String>> dst, Collection<String> dstComments) {
		StringBuilder sb = new StringBuilder();
		boolean hasSeparator = false;
		boolean precedingBackslash = false;

		for(int i = 0, size = strs.size(); i < size; i++) {
			String str = strs.get(i);
			int strLen = str.length();
			int keyLength = 0;
			int valueStart = strLen;

			if(str.length() == 0 || str.charAt(0) == '#' || str.charAt(0) == '!') {
				dstComments.add(str.substring(1));
				continue;
			}

			for(keyLength = 0; keyLength < strLen; keyLength++) {
				char c = str.charAt(keyLength);
				if(!precedingBackslash) {
					if(c == '=' || c == ':') {
						valueStart = keyLength + 1;
						hasSeparator = true;
						break;
					}
					else if((c == ' ' || c == '\t' || c == '\f')) {
						valueStart = keyLength + 1;
						break;
					}
				}

				if(c == '\\') {
					precedingBackslash = !precedingBackslash;
				}
				else {
					precedingBackslash = false;
				}
			}

			for( ; valueStart < strLen; valueStart++) {
				char c = str.charAt(valueStart);
				if(c != ' ' && c != '\t' && c != '\f') {
					if(!hasSeparator && (c == '=' || c == ':')) {
						hasSeparator = true;
					}
					else {
						break;
					}
				}
			}

			loadKeyValueString(str, 0, keyLength, sb);
			String key = sb.toString();
			sb.setLength(0);
			loadKeyValueString(str, valueStart, strLen - valueStart, sb);
			String value = sb.toString();
			sb.setLength(0);
			dst.add(new AbstractMap.SimpleImmutableEntry<>(key, value));
		}
	}


	private static final void loadKeyValueString(String str, int off, int len, StringBuilder dst) {
		try {
			loadKeyValueString(str, off, len, (Appendable)dst);
		} catch (IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}


	private static final void loadKeyValueString(String str, int off, int len, Appendable dst) throws IOException {
		int end = off + len;
		while(off < end) {
			char a = str.charAt(off++);
			if(a == '\\') {
				a = str.charAt(off++);
				if(a == 'u') {
					int value = 0;
					for(int i = 0; i < 4; i++) {
						a = str.charAt(off++);
						switch(a) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + a - '0';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + a - 'A';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + a - 'a';
							break;
						default:
							throw new IllegalArgumentException("illegal \\uxxxx character encoding");
						}
					}
					dst.append((char)value);
				}
				else {
					if(a == 't') { a = '\t'; }
					else if(a == 'r') { a = '\r'; }
					else if(a == 'n') { a = '\n'; }
					else if(a == 'f') { a = '\f'; }
					dst.append(a);
				}
			}
			else {
				dst.append(a);
			}
		}
	}

}
