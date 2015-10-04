package twg2.text.test;

import org.junit.Assert;
import org.junit.Test;

import twg2.text.stringUtils.StringSplit;
import checks.CheckTask;

/**
 * @author TeamworkGuy2
 * @since 2015-5-11
 */
public class StringSplitTest {


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
	public void stringSplitNthChildTest() {
		Assert.assertEquals(" def", StringSplit.findNthMatch("abc, def, ghi", ",", 1, 3));

		CheckTask.assertException(() -> StringSplit.findNthMatch("abc, def, ghi", ",", 1, 2));
		CheckTask.assertException(() -> StringSplit.findNthMatch("abc, def, ghi", ",", 1, 4));
	}


	@Test
	public void countMatchesTest() {
		Assert.assertEquals(2, StringSplit.countMatches("abc, def, ghi", ","));
		Assert.assertEquals(3, StringSplit.countMatches("aaa", "a"));
		Assert.assertEquals(3, StringSplit.countMatches("aaaaaa", "aa"));
		Assert.assertEquals(0, StringSplit.countMatches("", "-"));
		Assert.assertEquals(0, StringSplit.countMatches("123", "-"));
		Assert.assertEquals(1, StringSplit.countMatches("-", "-"));
	}


	@Test
	public void firstMatchTest() {
		Assert.assertEquals("abc", StringSplit.firstMatch("abc-def-ghi", "-"));
		Assert.assertEquals("abc", StringSplit.firstMatch("abc--def--ghi", "--"));
		Assert.assertEquals("", StringSplit.firstMatch("", "-"));
		Assert.assertEquals("", StringSplit.firstMatch("-a-", "-"));
	}


	@Test
	public void lastMatchTest() {
		Assert.assertEquals("ghi", StringSplit.lastMatch("abc-def-ghi", "-"));
		Assert.assertEquals("ghi", StringSplit.lastMatch("abc--def--ghi", "--"));
		Assert.assertEquals("", StringSplit.lastMatch("", "-"));
		Assert.assertEquals("", StringSplit.lastMatch("a-", "-"));
	}

}
