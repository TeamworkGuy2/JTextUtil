package twg2.text.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import twg2.text.stringEscape.StringEscapeXml;

/**
 * @author TeamworkGuy2
 * @since 2016-2-28
 */
public class StringEscapeXmlTest {


	public static class EscapeXml {

		public static List<String> inputs = Arrays.asList(
			"<!-- xml comment -->",
			"<tag>with & in</tag>",
			"text~='string'",
			"<\"",
			""
		);

		public static List<String> expected = Arrays.asList(
			"&lt;!-- xml comment --&gt;",
			"&lt;tag&gt;with &amp; in&lt;/tag&gt;",
			"text~=&apos;string&apos;",
			"&lt;&quot;",
			""
		);

	}


	public static class UnescapeXml {

		public static List<String> inputs = Arrays.asList(
			"&lt;!-- xml comment --&gt;",
			"&lt;tag&gt;with &amp; in&lt;/tag&gt;",
			"text~=&apos;string&apos;",
			"&lt;&quot;",
			""
		);

		public static List<String> expected = Arrays.asList(
			"<!-- xml comment -->",
			"<tag>with & in</tag>",
			"text~='string'",
			"<\"",
			""
		);

	}




	@Test
	public void escapeXmlTest() {
		List<String> inputs = EscapeXml.inputs;
		List<String> expect = EscapeXml.expected;
		StringBuilder dst = new StringBuilder();

		for(int i = 0, size = inputs.size(); i < size; i++) {
			StringEscapeXml.escapeXml(inputs.get(i), dst);
			Assert.assertEquals(expect.get(i), dst.toString());

			String res = StringEscapeXml.escapeXml(inputs.get(i));
			Assert.assertEquals(expect.get(i), res);

			dst.setLength(0);
		}
	}


	@Test
	public void unescapeXmlTest() {
		List<String> inputs = UnescapeXml.inputs;
		List<String> expect = UnescapeXml.expected;
		StringBuilder dst = new StringBuilder();

		for(int i = 0, size = inputs.size(); i < size; i++) {
			StringEscapeXml.unescapeXml(inputs.get(i), dst);
			Assert.assertEquals(expect.get(i), dst.toString());

			String res = StringEscapeXml.unescapeXml(inputs.get(i));
			Assert.assertEquals(expect.get(i), res);

			dst.setLength(0);
		}
	}

}
