package stringUtils;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
	public static final void saveKeyValueStrings(List<Map.Entry<String, String>> keyValueStrings,
			List<Map.Entry<String, String>> dst) {
		StringBuilder buf = new StringBuilder();
		for(int i = 0, size = keyValueStrings.size(); i < size; i++) {
			Map.Entry<String, String> keyValuePair = keyValueStrings.get(i);
			String key = saveKeyValueString(keyValuePair.getKey(), true, false, buf).toString();
			buf.setLength(0);
			String value = saveKeyValueString(keyValuePair.getValue(), false, false, buf).toString();
			buf.setLength(0);

			Map.Entry<String, String> resultEntry = new AbstractMap.SimpleImmutableEntry<String, String>(key, value);
			if(dst.size() <= i) {
				dst.set(i, resultEntry);
			}
			else {
				dst.add(resultEntry);
			}
		}
	}


	private static final <T extends Appendable> T saveKeyValueString(String str, boolean escSpaces,
			boolean escUnicode, T dst) {
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
					if(x == 0 || escSpaces) {
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
					if(((a < 0x0020) || (a > 0x007E)) && escUnicode) {
						dst.append("\\u");
						dst.append(StringModify.toHex((a >> 12) & 0xF));
						dst.append(StringModify.toHex((a >> 8) & 0xF));
						dst.append(StringModify.toHex((a >> 4) & 0xF));
						dst.append(StringModify.toHex(a & 0xF));
					}
					else {
						dst.append(a);
					}
				}
			}
		} catch(IOException ioe) {
			throw new RuntimeException(ioe);
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
	public static final void loadKeyValueStrings(List<String> strs, Collection<Map.Entry<String, String>> dst) {
		StringBuilder strB = new StringBuilder();
		int totalLength;
		int keyLength;
		int valueStart;
		boolean hasSeparator = false;
		boolean precedingBackslash = false;

		for(int i = 0, size = strs.size(); i < size; i++) {
			String str = strs.get(i);
			totalLength = str.length();
			keyLength = 0;
			valueStart = totalLength;
			for(int ii = 0; ii < totalLength; ii++) {
				char c = str.charAt(ii);
				if((c == '=' || c == ':') && !precedingBackslash) {
					valueStart = ii+1;
					hasSeparator = true;
					break;
				}
				else if((c == ' ' || c == '\t' || c == '\f') && !precedingBackslash) {
					valueStart = ii+1;
					break;
				}
				if(c == '\\') {
					precedingBackslash = true;
				}
				else {
					precedingBackslash = false;
				}
			}
			for( ; valueStart < totalLength; valueStart++) {
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
			try {
				loadKeyValueString(str, 0, keyLength, strB);
				String key = strB.toString();
				strB.setLength(0);
				loadKeyValueString(str, valueStart, totalLength - valueStart, strB);
				String value = strB.toString();
				strB.setLength(0);
				dst.add(new AbstractMap.SimpleImmutableEntry<String, String>(key, value));
			} catch (IOException e) {
				throw new Error("StringBuilder threw IOException", e);
			}
		}
	}


	private static final <T extends Appendable> void loadKeyValueString(String str, int off, int len, T dst)
			throws IOException {
		char a;
		int end = off + len;
		while(off < end) {
			a = str.charAt(off++);
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
