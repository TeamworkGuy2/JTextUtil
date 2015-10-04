package twg2.text.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import twg2.text.stringUtils.StringTrim;

/**
 * @author TeamworkGuy2
 * @since 2015-5-26
 */
public class StringTrimTest {

	private static class CharQuotedStrings {
		private char quoteChar;
		private String str1;
		private String str1Res;
		private String str2;
		private String str2Res;
		private List<String> strs;
		private List<String> strsRes;

		public CharQuotedStrings(char quoteChar, String str1, String str1Res, String str2, String str2Res, List<String> strs, List<String> strsRes) {
			this.quoteChar = quoteChar;
			this.str1 = str1;
			this.str1Res = str1Res;
			this.str2 = str2;
			this.str2Res = str2Res;
			this.strs = strs;
			this.strsRes = strsRes;
		}

	}


	private static class StrQuotedStrings {
		private String quoteStr;
		private String str1;
		private String str1Res;
		private String str2;
		private String str2Res;
		private List<String> strs;
		private List<String> strsRes;

		public StrQuotedStrings(String quoteStr, String str1, String str1Res, String str2, String str2Res, List<String> strs, List<String> strsRes) {
			this.quoteStr = quoteStr;
			this.str1 = str1;
			this.str1Res = str1Res;
			this.str2 = str2;
			this.str2Res = str2Res;
			this.strs = strs;
			this.strsRes = strsRes;
		}

	}


	@Test
	public void testCharSurroundingTrim() {
		CharQuotedStrings[] tests = new CharQuotedStrings[] {
				new CharQuotedStrings('"', "\"", "\"", "\"a\"", "a", Arrays.asList("\"\"", "\n\"", "a\"b\"", "\"\"123\"\""), Arrays.asList("", "\n\"", "a\"b\"", "\"123\"")),
				new CharQuotedStrings('*', "*", "*", "*a*", "a", Arrays.asList("**", "\n*", "a*b*", "**123**"), Arrays.asList("", "\n*", "a*b*", "*123*"))
		};

		for(CharQuotedStrings test : tests) {
			String tmpStr = StringTrim.trimIfSurrounding(test.str1, test.quoteChar);
			Assert.assertEquals(test.str1Res, tmpStr);

			tmpStr = StringTrim.trimIfSurrounding(test.str2, test.quoteChar);
			Assert.assertEquals(test.str2Res, tmpStr);

			List<String> tmpStrs = new ArrayList<>(test.strs);
			StringTrim.trimIfSurrounding(tmpStrs, test.quoteChar);
			Assert.assertArrayEquals(test.strsRes.toArray(), tmpStrs.toArray());
		}
	}


	@Test
	public void testCharTrim() {
		CharQuotedStrings[] tests = new CharQuotedStrings[] {
				new CharQuotedStrings('"', "\"", "", "\"a\"", "a", Arrays.asList("\"\"", "\n\"", "a\"b\"", "\"\"123\"\""), Arrays.asList("", "\n", "a\"b", "\"123\"")),
				new CharQuotedStrings('*', "*", "", "*a*", "a", Arrays.asList("**", "\n*", "a*b*", "**123**"), Arrays.asList("", "\n", "a*b", "*123*"))
		};

		for(CharQuotedStrings test : tests) {
			String tmpStr = StringTrim.trim(test.str1, test.quoteChar);
			Assert.assertEquals(test.str1Res, tmpStr);

			tmpStr = StringTrim.trim(test.str2, test.quoteChar);
			Assert.assertEquals(test.str2Res, tmpStr);

			List<String> tmpStrs = new ArrayList<>(test.strs);
			StringTrim.trim(tmpStrs, test.quoteChar);
			Assert.assertArrayEquals(test.strsRes.toArray(), tmpStrs.toArray());
		}
	}


	@Test
	public void testStringSurroundingTrim() {
		StrQuotedStrings[] tests = new StrQuotedStrings[] {
				new StrQuotedStrings("<\"", "<\"", "<\"", "<\"a<\"", "a", Arrays.asList("<\"<\"", "\n<\"", "a<\"b<\"", "<\"<\"123<\"<\""), Arrays.asList("", "\n<\"", "a<\"b<\"", "<\"123<\"")),
				new StrQuotedStrings("**", "**", "**", "**a**", "a", Arrays.asList("****", "\n**", "a**b**", "**123**"), Arrays.asList("", "\n**", "a**b**", "123"))
		};

		for(StrQuotedStrings test : tests) {
			String tmpStr = StringTrim.trimIfSurrounding(test.str1, test.quoteStr);
			Assert.assertEquals(test.str1Res, tmpStr);

			tmpStr = StringTrim.trimIfSurrounding(test.str2, test.quoteStr);
			Assert.assertEquals(test.str2Res, tmpStr);

			List<String> tmpStrs = new ArrayList<>(test.strs);
			StringTrim.trimIfSurrounding(tmpStrs, test.quoteStr);
			Assert.assertArrayEquals(test.strsRes.toArray(), tmpStrs.toArray());
		}
	}


	@Test
	public void testStringTrim() {
		StrQuotedStrings[] tests = new StrQuotedStrings[] {
				new StrQuotedStrings("<\"", "<\"", "", "<\"a<\"", "a", Arrays.asList("<\"<\"", "\n", "a<\"b<\"", "<\"<\"123<\"<\""), Arrays.asList("", "\n", "a<\"b", "<\"123<\"")),
				new StrQuotedStrings("**", "**", "", "**a**", "a", Arrays.asList("****", "\n**", "a**b**", "**123**"), Arrays.asList("", "\n", "a**b", "123"))
		};

		for(StrQuotedStrings test : tests) {
			String tmpStr = StringTrim.trim(test.str1, test.quoteStr);
			Assert.assertEquals(test.str1Res, tmpStr);

			tmpStr = StringTrim.trim(test.str2, test.quoteStr);
			Assert.assertEquals(test.str2Res, tmpStr);

			List<String> tmpStrs = new ArrayList<>(test.strs);
			StringTrim.trim(tmpStrs, test.quoteStr);
			Assert.assertArrayEquals(test.strsRes.toArray(), tmpStrs.toArray());
		}
	}

}
