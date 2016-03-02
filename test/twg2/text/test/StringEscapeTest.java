package twg2.text.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import twg2.text.stringEscape.StringEscape;

/**
 * @author TeamworkGuy2
 * @since 2015-1-2
 */
public class StringEscapeTest {

	public static class Escape {

		public static List<String> inputs = Arrays.asList(
			" -a \"block\" char '\"'",
			"1. \\abc",
			"2. \\\\abc",
			"= \\\""
		);

		public static List<String> expected = Arrays.asList(
			"a \\\"block\\\" char '\\\"'",
			" \\\\abc",
			" \\\\abc",
			"\\\""
		);

	}


	public static class Unescape {

		public static List<String> inputs = Arrays.asList(
			" -a \\\"block\\\" char '\\\"'",
			"1. \\\\abc",
			"2. \\abc",
			"= \\\""
		);

		public static List<String> expected = Arrays.asList(
			"a \"block\" char '\"'",
			" \\abc",
			" abc",
			"\""
		);

	}


	public static class UnescapeDoubleChar {

		public static List<String> inputs = Arrays.asList(
			" -a \"\"block\"\" char '\"\"'",
			"1. \"\"abc",
			"2. \"abc",
			"= \"\""
		);

		public static List<String> expected = Arrays.asList(
			"a \"block\" char '\"'",
			" \"abc",
			" abc",
			"\""
		);

	}




	@Test
	public void escapeTest() {
		int offset = 2;
		List<String> inputs = Escape.inputs;
		List<String> expect = Escape.expected;
		StringBuilder dst = new StringBuilder();

		for(int i = 0, size = inputs.size(); i < size; i++) {
			StringEscape.escape(inputs.get(i), offset, '\\', '"', '\\', dst);
			Assert.assertEquals(expect.get(i), dst.toString());
			dst.setLength(0);
		}
	}


	@Test
	public void unescapeTest() {
		int offset = 2;
		List<String> inputs = Unescape.inputs;
		List<String> expect = Unescape.expected;
		StringBuilder dst = new StringBuilder();

		for(int i = 0, size = inputs.size(); i < size; i++) {
			StringEscape.unescape(inputs.get(i), offset, '\\', '"', dst);
			Assert.assertEquals(expect.get(i), dst.toString());
			dst.setLength(0);
		}
	}


	@Test
	public void unescapeDoubleSameCharTest() {
		int offset = 2;
		List<String> inputs = UnescapeDoubleChar.inputs;
		List<String> expect = UnescapeDoubleChar.expected;
		StringBuilder dst = new StringBuilder();

		for(int i = 0, size = inputs.size(); i < size; i++) {
			StringEscape.unescape(inputs.get(i), offset, '"', '"', dst);
			Assert.assertEquals(expect.get(i), dst.toString());
			dst.setLength(0);
		}
	}


	@Test
	public void escapeUnescapeMiscellaneous() {
		String src = "a \\\"block\\\" char '\\\"'";
		StringBuilder strDst = new StringBuilder();
		StringEscape.unescapeChar(src, 0, '\\', '"', strDst);
		String unwrapped = strDst.toString();
		Assert.assertTrue("a \"block\" char '\"'".equals(unwrapped));

		strDst.setLength(0);
		StringEscape.escapeChar(unwrapped, '\\', '"', (char)0, strDst);
		String wrapped = strDst.toString();
		Assert.assertTrue(src.equals(wrapped));
	}


}
