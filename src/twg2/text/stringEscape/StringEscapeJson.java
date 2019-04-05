package twg2.text.stringEscape;

import java.io.IOException;
import java.io.UncheckedIOException;

/** Convert Java strings to and from JSON strings (note: this implementation does not yet support \\u four-hex-digits escape sequences, but does support \", \\, \/, \b, \f, \n, \r, \t)
 * @author TeamworkGuy2
 * @since 2016-2-28
 */
public class StringEscapeJson {

	private StringEscapeJson() { throw new AssertionError("cannot instantiate static class StringEscapeJson"); }


	/** Convert a string to a valid JSON string (i.e. '"', '\', and escape characters are escaped with '\')
	 * @param str the string to convert
	 * @return the resulting string, not quoted
	 */
	public static String toJsonString(String str) {
		StringBuilder sb = new StringBuilder();
		toJsonString(str, 0, str.length(), sb);
		return sb.toString();
	}


	public static String toJsonString(String str, int off, int len) {
		StringBuilder sb = new StringBuilder();
		toJsonString(str, off, len, sb);
		return sb.toString();
	}


	public static void toJsonString(String str, StringBuilder dst) {
		toJsonString(str, 0, str.length(), dst);
	}


	public static void toJsonString(String str, int off, int len, StringBuilder dst) {
		try {
			toJsonString(str, off, len, (Appendable)dst);
		} catch (IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}


	public static void toJsonString(String str, Appendable dst) throws IOException {
		toJsonString(str, 0, str.length(), dst);
	}


	public static void toJsonString(String str, int off, int len, Appendable dst) throws IOException {
		for(int i = off, size = off + len; i < size; i++) {
			char ch = str.charAt(i);
			if(ch == '"' || ch == '\\' || ch == '\b' || ch == '\f' || ch == '\n' || ch == '\r' || ch == '\t') {
				dst.append('\\');
				switch(ch) {
					case '\b': ch = 'b'; break;
					case '\f': ch = 'f'; break;
					case '\n': ch = 'n'; break;
					case '\r': ch = 'r'; break;
					case '\t': ch = 't'; break;
					default: break;
				}
			}
			dst.append(ch);
		}
	}


	public static void toJsonString(char ch, Appendable dst) throws IOException {
		if(ch == '"' || ch == '\\' || ch == '\b' || ch == '\f' || ch == '\n' || ch == '\r' || ch == '\t') {
			dst.append('\\');
			switch(ch) {
				case '\b': ch = 'b'; break;
				case '\f': ch = 'f'; break;
				case '\n': ch = 'n'; break;
				case '\r': ch = 'r'; break;
				case '\t': ch = 't'; break;
				default: break;
			}
		}
		dst.append(ch);
	}


	/** Convert a JSON string to a normal string (i.e. '\"', '\\', '\b', etc. are un-escaped)
	 * @param str the string to convert
	 * @return the resulting string
	 */
	public static String fromJsonString(String str) {
		StringBuilder sb = new StringBuilder();
		fromJsonString(str, 0, str.length(), sb);
		return sb.toString();
	}


	public static String fromJsonString(String str, int off, int len) {
		StringBuilder sb = new StringBuilder();
		fromJsonString(str, off, len, sb);
		return sb.toString();
	}


	public static void fromJsonString(String str, StringBuilder dst) {
		fromJsonString(str, 0, str.length(), dst);
	}


	public static void fromJsonString(String str, int off, int len, StringBuilder dst) {
		try {
			fromJsonString(str, off, len, (Appendable)dst);
		} catch (IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}


	public static void fromJsonString(String str, Appendable dst) throws IOException {
		fromJsonString(str, 0, str.length(), dst);
	}


	public static void fromJsonString(String str, int off, int len, Appendable dst) throws IOException {
		char prevCh = 0;
		for(int i = off, size = off + len; i < size; i++) {
			char ch = str.charAt(i);
			boolean wasEscaped = false;
			if(prevCh == '\\') {
				if(ch == '"' || ch == '\\' || ch == 'b' || ch == 'f' || ch == 'n' || ch == 'r' || ch == 't') {
					char c = (ch == '"' ? '"' : (ch == '\\' ? '\\' : (ch == 'b' ? '\b' : (ch == 't' ? '\t' : (ch == 'f' ? '\f' : (ch == 'n' ? '\n' : (ch == 'r' ? '\r' : 0)))))));
					dst.append(c);
				}
				else {
					throw new IllegalStateException("character after '\\' must be '\"', '\\', or a control char, found '" + ch + "' in: " + str.substring(off, off + len));
				}
				// because the escape char has been handled, if this is not done, double backslash causes issues
				// because if(prevCh == '\\') is true for the character after the second backslash and this code runs
				wasEscaped = true;
			}
			if(ch != '\\' && !wasEscaped) {
				dst.append(ch);
			}
			prevCh = !wasEscaped ? ch : 0;
		}
	}

}
