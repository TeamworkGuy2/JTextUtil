package twg2.text.test;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;

import twg2.junitassist.checks.CheckTask;
import twg2.text.stringSearch.StringCommonality;

/**
 * @author TeamworkGuy2
 * @since 2015-5-9
 */
public class StringCommonalityTest {

	@Test
	public void findPrefixTest() {
		List<String> strs = Arrays.asList(
				"alpha, beta, gamma",
				"alphabet",
				"alpine"
		);
		Assert.assertEquals("alp", StringCommonality.findPrefix(0, strs));
		Assert.assertEquals("p",   StringCommonality.findPrefix(2, strs));
		Assert.assertEquals("",    StringCommonality.findPrefix(8, strs));

		strs = Arrays.asList(
				"12345678",
				"12345",
				"123"
		);
		Assert.assertEquals("123", StringCommonality.findPrefix(0, strs));
		Assert.assertEquals("",    StringCommonality.findPrefix(3, strs));
		Assert.assertEquals("",    StringCommonality.findPrefix(6, strs));
	}


	@Test
	public void findSuffixTest() {
		List<String> strs = Arrays.asList(
				"sing",
				"alphabetizing",
				"-ing"
		);
		Assert.assertEquals("ing", StringCommonality.findSuffix(0, strs));
		Assert.assertEquals("i",   StringCommonality.findSuffix(2, strs));
		Assert.assertEquals("",    StringCommonality.findSuffix(4, strs));

		strs = Arrays.asList(
				"zabcabc",
				"alphabetan-abc",
				"a--abc"
		);
		Assert.assertEquals("abc", StringCommonality.findSuffix(0, strs));
		Assert.assertEquals("a",   StringCommonality.findSuffix(5, strs));
		Assert.assertEquals("",    StringCommonality.findSuffix(6, strs));
	}


	@Test
	public void findStringPortionTest() {
		List<String> strs = Arrays.asList(
				"-1wxyxy",
				"-2wxy",
				"-3wxyz"
		);

		// forward
		Assert.assertEquals("y",   StringCommonality.commonStringPortion(1, 6, 4, strs, true, false));
		Assert.assertEquals("wxy", StringCommonality.commonStringPortion(1, 6, 2, strs, true, false));
		Assert.assertEquals("",    StringCommonality.commonStringPortion(1, 6, 5, strs, true, false));

		// backward
		Assert.assertEquals("wxy", StringCommonality.commonStringPortion(1, 6, 4, strs, false, false));
		Assert.assertEquals("w",   StringCommonality.commonStringPortion(1, 6, 2, strs, false, false));
		Assert.assertEquals("",    StringCommonality.commonStringPortion(1, 6, 5, strs, false, false));
		Assert.assertEquals("-",   StringCommonality.commonStringPortion(0, 6, 0, strs, false, false));

		// backward from end of string (won't match anything)
		Assert.assertEquals("",    StringCommonality.commonStringPortion(1, 6, 1, strs, false, true));
		Assert.assertEquals("",    StringCommonality.commonStringPortion(1, 6, 4, strs, false, true));
	}


	@Test
	public void commonPrefixTest() {
		String[][] strs = new String[][] { { "" }, { "", "a" }, { "a_b", "a_b_c" }, { "this.that", "this_that" }, { "abc", "c" } };
		String[] expect = new String[] { "", "", "a_b", "this", "" };

		CheckTask.assertTests(strs, expect, (strSet) -> StringCommonality.findPrefix(0, Arrays.asList(strSet)));
	}


	@Test
	public void commonSuffixTest() {
		String[][] strs = new String[][] { { "" }, { "", "a" }, { "a_b", "a_b_c" }, { "this.that", "this_that" }, { "abc", "c" } };
		String[] expect = new String[] { "", "", "", "that", "c" };

		CheckTask.assertTests(strs, expect, (strSet) -> StringCommonality.findSuffix(0, Arrays.asList(strSet)));
	}


	@SuppressWarnings("unchecked")
	@Test
	public void findPrefixEntriesTest() {
		Entry<String, Integer>[] entries = new Entry[] {
				of("String-Aa", 1),
				of("String=Ab", 2),
				of("String Ac", 3),
				of("String Ad", 4),
				of("String+Ae", 5),
		};

		Assert.assertEquals("String", StringCommonality.findPrefix(0, entries));
		Assert.assertEquals("tring", StringCommonality.findPrefix(1, entries));
		Assert.assertEquals("A", StringCommonality.findPrefix(7, entries));

		entries = new Entry[] {
				of("-aaa", 1),
				of("-aa", 2),
				of("-a", 3),
		};

		Assert.assertEquals("-a", StringCommonality.findPrefix(0, entries));

		entries = new Entry[] {
				of("-a", 3),
				of("-aa", 2),
				of("-aaa", 1),
		};

		Assert.assertEquals("-a", StringCommonality.findPrefix(0, entries));
	}


	private static <K, V> Entry<K, V> of(K key, V val) {
		return new AbstractMap.SimpleImmutableEntry<>(key, val);
	}

}
