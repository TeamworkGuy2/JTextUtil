package twg2.text.stringEscape;

/**
 * @author TeamworkGuy2
 * @since 2016-2-28
 */
public class StringEscapeXml {

	private StringEscapeXml() { throw new AssertionError("cannot instantiate static class StringEscapeXml"); }


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
	 * @return the {@code dst} string builder with {@code content} appended with
	 * XML characters replaced with normal characters
	 */
	public static StringBuilder unescapeXml(String content, StringBuilder dst) {
		int offset = 0;
		if(dst == null) {
			dst = new StringBuilder(content.length()).append(content);
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

}
