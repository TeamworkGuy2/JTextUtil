package twg2.text.test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
				"a string without any matching values except at the end //",
				"something//split into pieces//3rd part//so",
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
	public void stringStringSplitDst() {
		String str = "something//split into pieces//3rd part//so";
		List<String> strSplit3 = Arrays.asList("something", "split into pieces", "3rd part//so");

		// test String pattern, dst List<String>
		List<String> dstList = new ArrayList<>();
		StringSplit.split(str, "//", dstList);
		Assert.assertEquals(Arrays.asList(str.split("//")), dstList);

		// test String pattern, dst List<String>, limit
		dstList.clear();
		StringSplit.split(str, "//", 3, dstList);
		Assert.assertEquals(strSplit3, dstList);

		// test String pattern, dst String[], large enough
		String[] dstAry = new String[4];
		StringSplit.split(str, "//", dstAry);
		Assert.assertArrayEquals(str.split("//"), dstAry);

		// test String pattern, dst String[], not large enough
		dstAry = new String[3];
		StringSplit.split(str, "//", dstAry);
		Assert.assertArrayEquals(strSplit3.toArray(new String[3]), dstAry);
	}


	@Test
	public void stringCharSplitDst() {
		String str = "something#split into pieces#3rd part#so";
		List<String> strSplit3 = Arrays.asList("something", "split into pieces", "3rd part#so");

		// test char pattern, dst List<String>
		List<String> dstList = new ArrayList<>();
		StringSplit.split(str, '#', dstList);
		Assert.assertEquals(Arrays.asList(str.split("#")), dstList);

		// test char pattern, dst List<String>, limit
		dstList.clear();
		StringSplit.split(str, '#', 3, dstList);
		Assert.assertEquals(strSplit3, dstList);

		// test char pattern, dst String[], large enough
		String[] dstAry = new String[4];
		StringSplit.split(str, '#', dstAry);
		Assert.assertArrayEquals(str.split("#"), dstAry);

		// test char pattern, dst String[], not large enough
		dstAry = new String[3];
		StringSplit.split(str, '#', dstAry);
		Assert.assertArrayEquals(strSplit3.toArray(new String[3]), dstAry);
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
	public void postFirstMatchTest() {
		Assert.assertEquals("def-ghi", StringSplit.postFirstMatch("abc-def-ghi", "-"));
		Assert.assertEquals("def--ghi", StringSplit.postFirstMatch("abc--def--ghi", "--"));
		Assert.assertEquals("", StringSplit.postFirstMatch("", "-"));
		Assert.assertEquals("a-", StringSplit.postFirstMatch("-a-", "-"));
	}


	@Test
	public void firstMatchPartsTest() {
		Assert.assertEquals(entry("abc", "def-ghi"), StringSplit.firstMatchParts("abc-def-ghi", "-"));
		Assert.assertEquals(entry("abc", "def--ghi"), StringSplit.firstMatchParts("abc--def--ghi", "--"));
		Assert.assertEquals(entry("", ""), StringSplit.firstMatchParts("", "-"));
		Assert.assertEquals(entry("", "a-"), StringSplit.firstMatchParts("-a-", "-"));
		Assert.assertEquals(entry("abc", ""), StringSplit.firstMatchParts("abc", "-"));
	}


	@Test
	public void lastMatchTest() {
		Assert.assertEquals("ghi", StringSplit.lastMatch("abc-def-ghi", "-"));
		Assert.assertEquals("ghi", StringSplit.lastMatch("abc--def--ghi", "--"));
		Assert.assertEquals("", StringSplit.lastMatch("", "-"));
		Assert.assertEquals("", StringSplit.lastMatch("a-", "-"));
	}


	@Test
	public void preLastMatchTest() {
		Assert.assertEquals("abc-def", StringSplit.preLastMatch("abc-def-ghi", "-"));
		Assert.assertEquals("abc--def", StringSplit.preLastMatch("abc--def--ghi", "--"));
		Assert.assertEquals("", StringSplit.preLastMatch("", "-"));
		Assert.assertEquals("a", StringSplit.preLastMatch("a-", "-"));
	}


	@Test
	public void lastMatchPartsTest() {
		Assert.assertEquals(entry("abc-def", "ghi"), StringSplit.lastMatchParts("abc-def-ghi", "-"));
		Assert.assertEquals(entry("abc--def", "ghi"), StringSplit.lastMatchParts("abc--def--ghi", "--"));
		Assert.assertEquals(entry("", ""), StringSplit.lastMatchParts("", "-"));
		Assert.assertEquals(entry("a", ""), StringSplit.lastMatchParts("a-", "-"));
		Assert.assertEquals(entry("abc", ""), StringSplit.lastMatchParts("abc", "-"));
	}


	private static <K, V> Map.Entry<K, V> entry(K key, V value) {
		return new AbstractMap.SimpleImmutableEntry<>(key, value);
	}

}
