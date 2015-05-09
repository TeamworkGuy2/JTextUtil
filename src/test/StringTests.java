package test;

import org.junit.Assert;
import org.junit.Test;

import stringUtils.StringConvert;
import stringUtils.StringModify;
import stringUtils.StringReplace;
import stringUtils.StringSplit;
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
	public void stringSplitTest() {
		String[] matches = new String[] {
				"a somewhat very long string without any matching values except at the end //",
				"something shorter//split into pieces//at least I hope//so",
				"//beginning split",
				"////split////",
				"",
				"//a//b//c//d//e//f//g",
				"1 2",
				"//",
				"5//",
		};
		for(String match : matches) {
			Assert.assertArrayEquals(match.split("//", 5), StringSplit.split(match, "//", 5));
		}
	}


	@Test
	public void stringJoin() {
		String[][] strs = new String[][] { { "Aa", "Bb" }, { "123", "_", ".." }, { "", "" }, { "" } };
		String[] delimiters = new String[] { "-", "||", "=", "=" };
		String[] expect = new String[] { "Aa-Bb", "123||_||..", "=", "" };

		Check.assertTests(strs, expect, "", "", (s, i) -> StringModify.join(s, delimiters[i]));
	}


	public static void arrayUtilTest() {
		;
	}

}
