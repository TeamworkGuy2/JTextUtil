package twg2.text.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import twg2.text.stringUtils.StringIndex;
import checks.CheckTask;

/**
 * @author TeamworkGuy2
 * @since 2015-2-21
 */
public class StringIndexTest {

	@Test
	public void charOccurenceN() {
		String[] inputs = {
				"val val val", "acac", "aaa",
				"a", "a", "",
				"uuv", ""
		};
		int[] matchChars = {
				'v', 'c', 'a',
				'a', 'b', 't',
				'u', 'v'
				
		};
		int[] occurenceN = {
				3, 0, 2,
				0, 1, 0,
				0, 1
		};
		Integer[] expectIndices = {
				8, 3, 1,
				0, -1, -1,
				1, -1
		};

		CheckTask.assertTests(inputs, expectIndices, (str, i) -> {
			int idx = StringIndex.indexOfOccurrenceN(str, occurenceN[i], matchChars[i]);
			return idx;
		});

	}


	@Test
	public void stringOccurenceN() {
		String[] inputs = {
				"val val val", "acac", "aaaaa",
				"a", "a", "",
				"uuuuu", ""
		};
		String[] matchStrs = {
				"val", "c", "aa",
				"a", "b", "t",
				"uu", "v"
				
		};
		int[] occurenceN = {
				3, 0, 2,
				0, 1, 0,
				0, 1
		};
		Integer[] expectIndices = {
				8, 3, 2,
				0, -1, -1,
				2, -1
		};

		CheckTask.assertTests(inputs, expectIndices, (str, i) -> {
			int idx = StringIndex.indexOfOccurrenceN(str, occurenceN[i], matchStrs[i]);
			return idx;
		});

	}


	@Test
	public void indexOfNotPrefixedByTest() {
		{
			String[] strs = new String[] {	"string //!#with !#text", "!#", "//!#!#", "and //!#///!#", "/!#//!#"};
			Integer[] expect = new Integer[] {16, 0, 4, -1, 1};

			String str = "!#";
			String prefix = "//";
			int strOff = 0;
			int prefixOff = 0;
			char[] strC = str.toCharArray();
			char[] prefixC = prefix.toCharArray();

			CheckTask.assertTests(strs, expect, (s) -> StringIndex.indexOfNotPrefixedBy(s.toCharArray(), 0, strC, strOff, prefixC, prefixOff));
		}

		{
			String[] strs2 = new String[] {	"string //!#with !#text", "!#", "//!#!#", "and //!#///!#", "/!#//!#"};
			Integer[] expect2 = new Integer[] {16, 0, 4, -1, 1};

			String str2 = "!#";
			String prefix2 = "//";
			int strOff2 = 0;
			int prefixOff2 = 0;
			char[] strC2 = str2.toCharArray();
			char[] prefixC2 = prefix2.toCharArray();

			CheckTask.assertTests(strs2, expect2, (s) -> StringIndex.indexOfNotPrefixedBy(s.toCharArray(), 0, strC2, strOff2, prefixC2, prefixOff2));
		}

		{
			String[] strs2 = new String[] {	"string <%-with text-%>", "<%-a", "-%>", "and <!--<%-%>", "<!--%>"};
			Integer[] expect2 = new Integer[] {7, 0, 0, 10, 3};

			String str2 = "!#";
			String prefix = "<!--";
			List<String> matchStrs = Arrays.asList("<%-", "-%>");
			int prefixOff = 0;
			char[] prefixC = prefix.toCharArray();

			CheckTask.assertTests(strs2, expect2, (s) -> {
				int index = StringIndex.indexOfMatchNotPrefixedBy(s.toCharArray(), 0, matchStrs, prefixC, prefixOff);
				if(index > -1) {
					index = StringIndex.indexOfNotPrefixedBy(s.toCharArray(), 0, matchStrs.get(index), 0, prefixC, prefixOff);
				}
				return index;
			});
		}
	}


	@Test
	public void indexOfMatch() {
		String[] matchStrs = new String[] {
				"Abc",
				"Str",
				"Thing",
				"int",
				"end",
				""
		};

		Assert.assertEquals(5, StringIndex.indexOfMatch("".toCharArray(), 0, matchStrs));
		Assert.assertEquals(4, StringIndex.indexOfMatch("end".toCharArray(), 0, matchStrs));
		Assert.assertEquals(1, StringIndex.indexOfMatch("=Str".toCharArray(), 1, matchStrs));

		Assert.assertEquals(5, StringIndex.indexOfMatch("".toCharArray(), 0, Arrays.asList(matchStrs)));
		Assert.assertEquals(4, StringIndex.indexOfMatch("end".toCharArray(), 0, Arrays.asList(matchStrs)));
		Assert.assertEquals(1, StringIndex.indexOfMatch("=Str".toCharArray(), 1, Arrays.asList(matchStrs)));
	}


	@Test
	public void indexOf_String_Char() {
		Assert.assertEquals(10, StringIndex.indexOf("Aa Bb Ccc 1".toCharArray(), 3, 8, (int)'1'));
	}


	@Test
	public void indexOf_StringOrCharAry_Char() {
		Assert.assertEquals(10, StringIndex.indexOf("Aa Bb Ccc \u2460", 3, 8, (int)'\u2460'));
		Assert.assertEquals(10, StringIndex.indexOf(StringTestUtils.fromStringsAndCodePoints("Aa Bb Ccc ", 0x1F3B8), 3, 9, (int)0x1F3B8));

		Assert.assertEquals(10, StringIndex.indexOf("Aa Bb Ccc \u2460".toCharArray(), 3, 8, (int)'\u2460'));
		Assert.assertEquals(10, StringIndex.indexOf(StringTestUtils.fromStringsAndCodePoints("Aa Bb Ccc ", 0x1F3B8).toCharArray(), 3, 9, (int)0x1F3B8));
	}


	@Test
	public void lastIndexOf_StringOrCharAry_Char() {
		// TODO add support for supplementary lastIndexOf() chars at some point
		Assert.assertEquals(12, StringIndex.lastIndexOf("\u2460 Aa Bb Ccc \u2460 \u2460", 3, 10, (int)'\u2460'));
		//Assert.assertEquals(14, StringIndex.lastIndexOf(fromStringsAndCodePoints("Aa Bb Ccc ", 0x1F3B8, "-", 0x1F3B8), 6, 9, (int)0x1F3B8));

		Assert.assertEquals(12, StringIndex.lastIndexOf("\u2460 Aa Bb Ccc \u2460 \u2460".toCharArray(), 3, 10, (int)'\u2460'));
		//Assert.assertEquals(14, StringIndex.lastIndexOf(fromStringsAndCodePoints("Aa Bb Ccc ", 0x1F3B8, "-", 0x1F3B8).toCharArray(), 6, 9, (int)0x1F3B8));
	}


	@Test
	public void indexOf_CharSeq_CharSeq() {
		Assert.assertEquals(3, StringIndex.indexOf((CharSequence)"Aa Bb Ccc 1", 3, (CharSequence)"Bb Ccc 2", 0, 7));
	}


	@Test
	public void indexOf_StringOrCharAry_StringOrCharAry() {
		Assert.assertEquals(3, StringIndex.indexOf("Aa Bb Ccc 1".toCharArray(), 3, 7, "Bb Ccc 2", 0, 7));

		Assert.assertEquals(3, StringIndex.indexOf("Aa Bb Ccc 1", 3, 7, "Bb Ccc 2".toCharArray(), 0, 7));

		Assert.assertEquals(3, StringIndex.indexOf("Aa Bb Ccc 1".toCharArray(), 3, 7, "Bb Ccc 2".toCharArray(), 0, 7));
	}

}
