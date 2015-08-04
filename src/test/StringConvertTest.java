package test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import stringUtils.StringConvert;

/**
 * @author TeamworkGuy2
 * @since 2015-1-2
 */
public class StringConvertTest {

	public static class Escape {

		public static List<String> getInputs() {
			return Arrays.asList(
				" -a \"block\" char '\"'",
				"1. \\abc",
				"2. \\\\abc",
				"= \\\""
			);
		}


		public static List<String> getExpectedResults() {
			return Arrays.asList(
				"a \\\"block\\\" char '\\\"'",
				" \\\\abc",
				" \\\\abc",
				"\\\""
			);
		}

	}


	public static class Unescape {

		public static List<String> getInputs() {
			return Arrays.asList(
				" -a \\\"block\\\" char '\\\"'",
				"1. \\\\abc",
				"2. \\abc",
				"= \\\""
			);
		}

		public static List<String> getExpectedResults() {
			return Arrays.asList(
				"a \"block\" char '\"'",
				" \\abc",
				" abc",
				"\""
			);
		}

	}


	public static class UnescapeDoubleChar {

		public static List<String> getInputs() {
			return Arrays.asList(
				" -a \"\"block\"\" char '\"\"'",
				"1. \"\"abc",
				"2. \"abc",
				"= \"\""
			);
		}

		public static List<String> getExpectedResults() {
			return Arrays.asList(
				"a \"block\" char '\"'",
				" \"abc",
				" abc",
				"\""
			);
		}

	}


	public static class UnescapePartialQuoted {

		public static List<String> getInputs() {
			return Arrays.asList(
				"0. with \"quoted block\" end",
				"1.  \\abc, xyz",
				"2.  abc, xyz",
				"3. \\\"\"",
				"4. , "
			);
		}

		public static List<String> getExpectedResults() {
			return Arrays.asList(
				"with \"quoted block\"",
				" \\abc",
				" abc",
				"\\\"\"",
				""
			);
		}

		public static List<Integer> getExpectedIndices() {
			List<String> inputs = getInputs();
			return Arrays.asList(
				inputs.get(0).lastIndexOf("\"") + 1,
				inputs.get(1).lastIndexOf(','),
				inputs.get(2).lastIndexOf(','),
				inputs.get(3).lastIndexOf("\"") + 1,
				inputs.get(4).lastIndexOf(',')
			);
		}

	}


	public static class EscapeXml {

		public static List<String> getInputs() {
			return Arrays.asList(
				"<!-- xml comment -->",
				"<tag>with & in</tag>",
				"text~='string'",
				"<\"",
				""
			);
		}

		public static List<String> getExpectedResults() {
			return Arrays.asList(
				"&lt;!-- xml comment --&gt;",
				"&lt;tag&gt;with &amp; in&lt;/tag&gt;",
				"text~=&apos;string&apos;",
				"&lt;&quot;",
				""
			);
		}

	}


	public static class UnescapeXml {

		public static List<String> getInputs() {
			return Arrays.asList(
				"&lt;!-- xml comment --&gt;",
				"&lt;tag&gt;with &amp; in&lt;/tag&gt;",
				"text~=&apos;string&apos;",
				"&lt;&quot;",
				""
			);
		}

		public static List<String> getExpectedResults() {
			return Arrays.asList(
				"<!-- xml comment -->",
				"<tag>with & in</tag>",
				"text~='string'",
				"<\"",
				""
			);
		}

	}




	@Test
	public void escapeTest() {
		int offset = 2;
		List<String> inputs = Escape.getInputs();
		List<String> expect = Escape.getExpectedResults();
		StringBuilder dst = new StringBuilder();

		for(int i = 0, size = inputs.size(); i < size; i++) {
			StringConvert.escape(inputs.get(i), offset, '\\', '"', '\\', dst);
			Assert.assertEquals(expect.get(i), dst.toString());
			dst.setLength(0);
		}
	}


	@Test
	public void unescapeTest() {
		int offset = 2;
		List<String> inputs = Unescape.getInputs();
		List<String> expect = Unescape.getExpectedResults();
		StringBuilder dst = new StringBuilder();

		for(int i = 0, size = inputs.size(); i < size; i++) {
			StringConvert.unescape(inputs.get(i), offset, '\\', '"', dst);
			Assert.assertEquals(expect.get(i), dst.toString());
			dst.setLength(0);
		}
	}


	@Test
	public void unescapeDoubleSameCharTest() {
		int offset = 2;
		List<String> inputs = UnescapeDoubleChar.getInputs();
		List<String> expect = UnescapeDoubleChar.getExpectedResults();
		StringBuilder dst = new StringBuilder();

		for(int i = 0, size = inputs.size(); i < size; i++) {
			StringConvert.unescape(inputs.get(i), offset, '"', '"', dst);
			Assert.assertEquals(expect.get(i), dst.toString());
			dst.setLength(0);
		}
	}


	@Test
	public void unescapePartialQuotedTest() {
		int offset = 3;
		List<String> inputs = UnescapePartialQuoted.getInputs();
		List<String> expect = UnescapePartialQuoted.getExpectedResults();
		List<Integer> expectIndex = UnescapePartialQuoted.getExpectedIndices();
		StringBuilder dst = new StringBuilder();

		for(int i = 0, size = inputs.size(); i < size; i++) {
			int index = StringConvert.unescapePartialQuoted(inputs.get(i), offset, inputs.get(i).length() - offset, '\\', '"', ',', ']', false, dst);
			Assert.assertEquals(expect.get(i), dst.toString());
			Assert.assertEquals(i + ". expect (" + expectIndex.get(i) + "): " + expect.get(i) + ", result (" + index + "): " + dst.toString(), (int)expectIndex.get(i), index);
			dst.setLength(0);
		}
	}


	@Test
	public void escapeXml() {
		List<String> inputs = EscapeXml.getInputs();
		List<String> expect = EscapeXml.getExpectedResults();
		StringBuilder dst = new StringBuilder();

		for(int i = 0, size = inputs.size(); i < size; i++) {
			StringConvert.escapeXml(inputs.get(i), dst);
			Assert.assertEquals(expect.get(i), dst.toString());
			dst.setLength(0);
		}
	}


	@Test
	public void unescapeXml() {
		List<String> inputs = UnescapeXml.getInputs();
		List<String> expect = UnescapeXml.getExpectedResults();
		StringBuilder dst = new StringBuilder();

		for(int i = 0, size = inputs.size(); i < size; i++) {
			StringConvert.unescapeXml(inputs.get(i), dst);
			Assert.assertEquals(expect.get(i), dst.toString());
			dst.setLength(0);
		}
	}

}
