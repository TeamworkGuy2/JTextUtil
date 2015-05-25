package test;

import org.junit.Assert;
import org.junit.Test;

import stringUtils.StringConvert;
import stringUtils.StringJoin;
import stringUtils.StringReplace;
import checks.Check;

/**
 * @author TeamworkGuy2
 * @since 2014-8-24
 */
public class StringTests {

	@Test
	public void testEscapeUnescape() {
		String src = "a \\\"block\\\" char '\\\"'";
		StringBuilder strDst = new StringBuilder();
		StringConvert.unescapeChar(src, 0, '\\', '"', strDst);
		String unwrapped = strDst.toString();
		Assert.assertTrue("a \"block\" char '\"'".equals(unwrapped));

		strDst.setLength(0);
		StringConvert.escapeChar(unwrapped, '\\', '"', (char)0, strDst);
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

		Check.assertTests(strs, expect, "", "", (s, i) -> StringJoin.join(s, delimiters[i]));
	}


	public static void arrayUtilTest() {
		;
	}

}
