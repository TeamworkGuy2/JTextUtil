package test;

import org.junit.Assert;
import org.junit.Test;

import stringUtils.StringConvert;

/**
 * @author TeamworkGuy2
 * @since 2015-1-2
 */
public class StringConvertTest {

	@Test
	public void unescapeTest() {
		int offset = 2;
		String[] inputs = new String[] {
				" -a \\\"block\\\" char '\\\"'",
				"1. \\\\abc",
				"2. \\abc",
				"= \\\"",
		};
		String[] expect = new String[] {
				"a \"block\" char '\"'",
				" \\abc",
				" abc",
				"\"",
		};
		StringBuilder dst = new StringBuilder();

		for(int i = 0, size = inputs.length; i < size; i++) {
			StringConvert.unescape(inputs[i], offset, '\\', '"', dst);
			Assert.assertEquals(expect[i], dst.toString());
			dst.setLength(0);
		}
	}


	@Test
	public void unescapePartialQuotedTest() {
		{
			int offset = 3;
			String[] inputs = new String[] {
					"0. with \"quoted block\"",
					"1.  \\abc, xyz",
					"2.  abc, xyz",
					"3. \\\"\"",
			};
			String[] expect = new String[] {
					"with \"quoted block\"",
					" \\abc",
					" abc",
					"\\\"\"",
			};
			int[] expectIndex = {
					inputs[0].length(),
					inputs[1].lastIndexOf(','),
					inputs[2].lastIndexOf(','),
					inputs[3].length()
			};
			StringBuilder dst = new StringBuilder();
	
			for(int i = 0, size = inputs.length; i < size; i++) {
				int index = StringConvert.unescapePartialQuoted(inputs[i], offset, inputs[i].length() - offset, '\\', '"', ',', ']', false, dst);
				Assert.assertEquals(expect[i], dst.toString());
				Assert.assertEquals("expect (" + expectIndex[i] + "): " + expect[i] + ", result (" + index + "): " + dst.toString(), expectIndex[i], index);
				dst.setLength(0);
			}
		}
	}


	@Test
	public void escapeXml() {
		String[] inputs = new String[] {
				"<!-- xml comment -->",
				"<tag>with & in</tag>",
				"text~='string'",
				"<\"",
				""
		};
		String[] expect = new String[] {
				"&lt;!-- xml comment --&gt;",
				"&lt;tag&gt;with &amp; in&lt;/tag&gt;",
				"text~=&apos;string&apos;",
				"&lt;&quot;",
				""
		};
		StringBuilder dst = new StringBuilder();

		for(int i = 0, size = inputs.length; i < size; i++) {
			StringConvert.escapeXml(inputs[i], dst);
			Assert.assertEquals(expect[i], dst.toString());
			dst.setLength(0);
		}
	}


	@Test
	public void unescapeXml() {
		String[] inputs = new String[] {
				"&lt;!-- xml comment --&gt;",
				"&lt;tag&gt;with &amp; in&lt;/tag&gt;",
				"text~=&apos;string&apos;",
				"&lt;&quot;",
				""
		};
		String[] expect = new String[] {
				"<!-- xml comment -->",
				"<tag>with & in</tag>",
				"text~='string'",
				"<\"",
				""
		};
		StringBuilder dst = new StringBuilder();

		for(int i = 0, size = inputs.length; i < size; i++) {
			StringConvert.unescapeXml(inputs[i], dst);
			Assert.assertEquals(expect[i], dst.toString());
			dst.setLength(0);
		}
	}

}
