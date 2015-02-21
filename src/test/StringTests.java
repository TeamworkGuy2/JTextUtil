package test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import stringUtils.StringCase;
import stringUtils.StringCommonality;
import stringUtils.StringConvert;
import stringUtils.StringModify;
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
			Assert.assertArrayEquals(match.split("//", 5), StringModify.split(match, "//", 5));
		}
	}


	@Test
	public void stringJoin() {
		String[][] strs = new String[][] { { "Aa", "Bb" }, { "123", "_", ".." }, { "", "" }, { "" } };
		String[] delimiters = new String[] { "-", "||", "=", "=" };
		String[] expect = new String[] { "Aa-Bb", "123||_||..", "=", "" };

		Check.assertTests(strs, expect, "", "", (s, i) -> StringModify.join(s, delimiters[i]));
	}


	@Test
	public void stringReplaceTest() {
		{
			String[] strs = new String[] {   "&amp; with &lt; or &gt;", "*** or ** * ***", "***", "*** a six***seven***" };
			String[] expect = new String[] { "& with < or >",           "* or ** * *",     "*",   "* a six*seven*" };

			List<String> searchStrs = Arrays.asList("***", "&amp;", "&lt;", "&gt;");
			List<String> replaceStrs = Arrays.asList("*", "&", "<", ">");

			Check.assertTests(strs, expect, "", "",
					(s) -> StringReplace.replaceStrings(s, 0, searchStrs, replaceStrs));
		}

		{
			String[] strs2 = new String[] {   "&lt;+=&lt; or &gt;", "*** or ** * ***", "***", "*** a six***seven***" };
			String[] expect2 = new String[] { "&lt;+=< or >",       "*** or ** * *",   "***", "*** a six*seven*" };

			List<String> searchStrs2 = Arrays.asList("***", "&amp;", "&lt;", "&gt;");
			List<String> replaceStrs2 = Arrays.asList("*", "&", "<", ">");

			int strOffset = 6;
			Check.assertTests(strs2, expect2, "", "",
					(s) -> StringReplace.replaceStrings(s, strOffset, searchStrs2, replaceStrs2));
		}
	}


	@Test
	public void commonPrefixSuffixTest() {
		// prefix
		{
			String[][] strs = new String[][] { { "" }, { "a_b", "a_b_c" }, { "this.that", "this_that" }, { "abc", "c" } };
			String[] expect = new String[] { "", "a_b", "this", "" };

			Check.assertTests(strs, expect, "", "", (strSet) -> StringCommonality.findPrefix(0, strSet));
		}
		// suffix
		{
			String[][] strs = new String[][] { { "" }, { "a_b", "a_b_c" }, { "this.that", "this_that" }, { "abc", "c" } };
			String[] expect = new String[] { "", "", "that", "c" };

			Check.assertTests(strs, expect, "", "", (strSet) -> StringCommonality.findSuffix(0, strSet));
		}
	}


	@Test
	public void stringCaseTest() {
		String[] strs = new String[] { "abc", "Alpha", "Subpar", "SupPar", "Al_Cid", "var_val_byte", "A_", "_A", "a", "_" };
		String[] camelCase = new String[] { "abc", "alpha", "subpar", "supPar", "alCid", "varValByte", "a", "a", "a", "_" };
		String[] titleCase = new String[] { "Abc", "Alpha", "Subpar", "SupPar", "AlCid", "VarValByte", "A", "A", "A", "_" };
		String[] underscoreLowerCase = new String[] { "abc", "alpha", "subpar", "sup_par", "al_cid", "var_val_byte", "a_", "_a", "a", "_" };
		String[] underscoreTitleCase = new String[] { "Abc", "Alpha", "Subpar", "Sup_Par", "Al_Cid", "Var_Val_Byte", "A_", "_A", "A", "_" };

		for(int i = 0, size = strs.length; i < size; i++) {
			Assert.assertEquals("camelCase", StringCase.toCamelCase(strs[i]), camelCase[i]);
			Assert.assertEquals("TitleCase", StringCase.toTitleCase(strs[i]), titleCase[i]);
			Assert.assertEquals("underscoreLowerCase", StringCase.toUnderscoreLowerCase(strs[i]), underscoreLowerCase[i]);
			Assert.assertEquals("underscoreTitleCase", StringCase.toUnderscoreTitleCase(strs[i]), underscoreTitleCase[i]);
			/*System.out.println("str: " + str +
					",\tcamelCase: " + StringCase.toCamelCase(str) +
					",\tTitleCase: " + StringCase.toTitleCase(str) +
					",\tunderscore_lower_case: " + StringCase.toUnderscoreLowerCase(str) +
					",\tUnderscore_Upper_Case: " + StringCase.toUnderscoreTitleCase(str));*/
		}
	}


	public static void arrayUtilTest() {
		;
	}

}
