package twg2.text.test;

import org.junit.Assert;
import org.junit.Test;

import twg2.text.stringUtils.StringEscape;
import twg2.text.stringUtils.StringJoin;
import twg2.text.stringUtils.StringReplace;
import checks.CheckTask;

/**
 * @author TeamworkGuy2
 * @since 2014-8-24
 */
public class StringTests {

	@Test
	public void testEscapeUnescape() {
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


	@Test
	public void stringUtilTest() {
		String str = StringReplace.replaceEscapeLiterals("kd\\t\\nwith\\\\and \\\"\\f\\\'");
		Assert.assertTrue("replace escape literals: " + str, str.equals("kd\t\nwith\\and \"\f\'"));
	}


	@Test
	public void stringJoin() {
		String[][] strs = new String[][] { { "Aa", "Bb" }, { "123", "_", ".." }, { "", "" }, { "" } };
		String[] delimiters = new String[] { "-", "||", "=", "=" };
		String[] expect = new String[] { "Aa-Bb", "123||_||..", "=", "" };

		CheckTask.assertTests(strs, expect, (s, i) -> StringJoin.join(s, delimiters[i]));
	}


	public static void arrayUtilTest() {
		;
	}

}
