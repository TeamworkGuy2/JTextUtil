package twg2.text.test;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;

import twg2.text.stringUtils.StringCompare;
import checks.CheckTask;

/**
 * @author TeamworkGuy2
 * @since 2015-2-21
 */
public class StringCompareTest {

	@Test
	public void anyEndsWith() {
		List<String> strs = Arrays.asList("String A", "String B", "Thing C");

		Assert.assertTrue(StringCompare.anyStartWith(strs, new StringBuilder("String")));
		Assert.assertTrue(StringCompare.anyStartWith(strs, new StringBuilder("Thing")));
		Assert.assertFalse(StringCompare.anyStartWith(strs, new StringBuilder("ing")));
		Assert.assertTrue(StringCompare.anyStartWith(strs, new StringBuilder("A=String"), 2));
	}


	@Test
	public void ignoreCaseTest() {
		String[] strs = new String[] { "Alpha", "Beta", "Charlie", "Delta" };

		Assert.assertTrue(StringCompare.containsEqualIgnoreCase(strs, "Alpha"));
		Assert.assertTrue(StringCompare.containsEqualIgnoreCase(strs, "alpha"));
		Assert.assertFalse(StringCompare.containsEqualIgnoreCase(strs, "alpha="));
		Assert.assertTrue(StringCompare.containsEqualIgnoreCase(Arrays.asList(strs), "Alpha"));
		Assert.assertTrue(StringCompare.containsEqualIgnoreCase(Arrays.asList(strs), "alpha"));
		Assert.assertFalse(StringCompare.containsEqualIgnoreCase(Arrays.asList(strs), "alpha="));

		Assert.assertTrue(StringCompare.containsIgnoreCase(strs, "Char"));
		Assert.assertTrue(StringCompare.containsIgnoreCase(strs, "char"));
		Assert.assertFalse(StringCompare.containsIgnoreCase(strs, "char="));
		Assert.assertTrue(StringCompare.containsIgnoreCase(Arrays.asList(strs), "Char"));
		Assert.assertTrue(StringCompare.containsIgnoreCase(Arrays.asList(strs), "char"));
		Assert.assertFalse(StringCompare.containsIgnoreCase(Arrays.asList(strs), "char="));
	}


	@Test
	public void endsWithAny() {
		String[] suffixes = new String[] { "cs", "java", "js", "json", "ts", "txt", "xml" };
		Assert.assertTrue(StringCompare.endsWithAny("cs", suffixes));
		Assert.assertTrue(StringCompare.endsWithAny("class.cs", suffixes));
		Assert.assertFalse(StringCompare.endsWithAny("Str", suffixes));
	}


	@Test
	public void testContains() {
		String[] strs = { "alpha", "beta", "gamma", "int", "Integer", "IntList" };
		String[] contains = { "gamma", "nt", "Int", "IS" };
		Boolean[] expectEqualIgnoreCase = { true, false, true, false };
		Boolean[] expectContainsIgnoreCase = { true, true, true, true };

		CheckTask.assertTests(contains, expectEqualIgnoreCase, (s) -> StringCompare.containsEqualIgnoreCase(strs, s));

		CheckTask.assertTests(contains, expectContainsIgnoreCase, (s) -> StringCompare.containsIgnoreCase(strs, s));
	}


	@Test
	public void compareEqualCountTest() {
		Assert.assertEquals(3, StringCompare.compareEqualCount("Abcd", "Abc"));
		Assert.assertEquals(0, StringCompare.compareEqualCount("Abcd", ""));

		Assert.assertEquals(3, StringCompare.compareEqualCount("==Abcd", 2, "-Abc", 1));
		Assert.assertEquals(0, StringCompare.compareEqualCount("==Abcd", 2, "-", 1));

		Assert.assertEquals(3, StringCompare.compareEqualCount("==Abcd", 2, "-Abc", 1, 5));
		Assert.assertEquals(2, StringCompare.compareEqualCount("==Abcd", 2, "-Abc", 1, 2));
		Assert.assertEquals(0, StringCompare.compareEqualCount("==Abcd", 2, "-", 1, 2));
	}


	@Test
	public void closestMatchTest() {
		@SuppressWarnings("unchecked")
		Entry<String, Integer>[] entriesSorted = new Entry[] {
				of("Car", 1),
				of("Chap", 2),
				of("Character", 3),
				of("Charm", 4),
				of("Csharp", 5),
		};

		Assert.assertEquals(entriesSorted[2], StringCompare.closestMatch("Char", 0, entriesSorted, true));
		Assert.assertEquals(entriesSorted[1],  StringCompare.closestMatch("=Cha=", 1, entriesSorted, true));

		@SuppressWarnings("unchecked")
		Entry<String, Integer>[] entries = new Entry[] {
				of("Chap", 2),
				of("Car", 1),
				of("Csharp", 5),
				of("Charm", 4),
				of("Character", 3),
		};

		Assert.assertEquals(entries[3], StringCompare.closestMatch("Char", 0, entries, false));
		Assert.assertEquals(entries[0],  StringCompare.closestMatch("=Cha=", 1, entries, false));
	}


	private static <K, V> Entry<K, V> of(K key, V val) {
		return new AbstractMap.SimpleImmutableEntry<>(key, val);
	}

}
