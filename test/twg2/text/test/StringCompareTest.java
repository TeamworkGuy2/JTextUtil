package twg2.text.test;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;

import twg2.junitassist.checks.CheckTask;
import twg2.text.stringSearch.StringCompare;

/**
 * @author TeamworkGuy2
 * @since 2015-2-21
 */
public class StringCompareTest {

	@Test
	public void anyEndsWith() {
		List<String> strs = Arrays.asList("String A", "String B", "Thing C");

		Assert.assertTrue(StringCompare.anyStartWith(strs, charSeq("String")));
		Assert.assertTrue(StringCompare.anyStartWith(strs, charSeq("Thing")));
		Assert.assertFalse(StringCompare.anyStartWith(strs, charSeq("ing")));
		Assert.assertTrue(StringCompare.anyStartWith(strs, charSeq("A=String"), 2));
	}


	@Test
	public void ignoreCase() {
		Assert.assertTrue(StringCompare.containsIgnoreCase("Char", "Char"));
		Assert.assertTrue(StringCompare.containsIgnoreCase("Chars", "char"));
		Assert.assertFalse(StringCompare.containsIgnoreCase("Chars", "char="));
		Assert.assertFalse(StringCompare.containsIgnoreCase("", "char="));

		String[] strs = new String[] { "Alpha", "Beta", "Charlie", "Delta" };

		Assert.assertTrue(StringCompare.containsEqualIgnoreCase(strs, "Alpha"));
		Assert.assertTrue(StringCompare.containsEqualIgnoreCase(strs, "alpha"));
		Assert.assertFalse(StringCompare.containsEqualIgnoreCase(strs, "alpha="));
		Assert.assertTrue(StringCompare.containsEqualIgnoreCase(Arrays.asList(strs), "Alpha"));
		Assert.assertTrue(StringCompare.containsEqualIgnoreCase(Arrays.asList(strs), "alpha"));
		Assert.assertFalse(StringCompare.containsEqualIgnoreCase(Arrays.asList(strs), "alpha="));

		Assert.assertTrue(StringCompare.containsIgnoreCase(strs, "Char"));
		Assert.assertTrue(StringCompare.containsIgnoreCase(strs, "char"));
		Assert.assertTrue(StringCompare.containsIgnoreCase(strs, "elta"));
		Assert.assertFalse(StringCompare.containsIgnoreCase(strs, "char="));
		Assert.assertTrue(StringCompare.containsIgnoreCase(Arrays.asList(strs), "Char"));
		Assert.assertTrue(StringCompare.containsIgnoreCase(Arrays.asList(strs), "char"));
		Assert.assertTrue(StringCompare.containsIgnoreCase(Arrays.asList(strs), "elta"));
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
	public void contains() {
		String[] strs = { "alpha", "beta", "gamma", "int", "Integer", "IntList" };
		String[] contains = { "gamma", "nt", "Int", "IS" };
		Boolean[] expectEqualIgnoreCase = { true, false, true, false };
		Boolean[] expectContainsIgnoreCase = { true, true, true, true };

		CheckTask.assertTests(contains, expectEqualIgnoreCase, (s) -> StringCompare.containsEqualIgnoreCase(strs, s));

		CheckTask.assertTests(contains, expectContainsIgnoreCase, (s) -> StringCompare.containsIgnoreCase(strs, s));
	}


	@Test
	public void compareEqualCount() {
		Assert.assertEquals(3, StringCompare.compareEqualCount("Abcd", "Abc"));
		Assert.assertEquals(0, StringCompare.compareEqualCount("Abcd", ""));

		Assert.assertEquals(3, StringCompare.compareEqualCount("==Abcd", 2, "-Abc", 1));
		Assert.assertEquals(0, StringCompare.compareEqualCount("==Abcd", 2, "-", 1));

		Assert.assertEquals(3, StringCompare.compareEqualCount("==Abcd", 2, "-Abc", 1, 5));
		Assert.assertEquals(2, StringCompare.compareEqualCount("==Abcd", 2, "-Abc", 1, 2));
		Assert.assertEquals(0, StringCompare.compareEqualCount("==Abcd", 2, "-", 1, 2));
	}


	@Test
	public void closestMatch() {
		@SuppressWarnings("unchecked")
		Entry<String, Integer>[] entriesSorted = new Entry[] {
				of("Car", 1),
				of("Char", 2),
				of("Character", 3),
				of("Charm", 4),
				of("Csharp", 5),
		};

		Assert.assertEquals(entriesSorted[1], StringCompare.closestMatch("Char", 0, entriesSorted, true));
		Assert.assertEquals(entriesSorted[2], StringCompare.closestMatch("Characterized", 0, entriesSorted, true));
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


	@Test
	public void compareStartsWith() {
		Assert.assertTrue(0 > StringCompare.compareStartsWith("", charSeq("J"), 0));
		Assert.assertTrue(0 > StringCompare.compareStartsWith("J", charSeq("Ja"), 0));
		Assert.assertTrue(0 == StringCompare.compareStartsWith("Java", charSeq("J"), 0));
		Assert.assertTrue(0 > StringCompare.compareStartsWith("Java", charSeq("-Jazz"), 1));
		Assert.assertTrue(0 < StringCompare.compareStartsWith("Java", charSeq("==Jab"), 2));
	}


	@Test
	public void startsWithAny() {
		Assert.assertFalse(StringCompare.startsWithAny("GT450", "gpu", "GA", null));

		Assert.assertTrue(StringCompare.startsWithAny("GT450", "gpu", "GT", "GT"));
		Assert.assertTrue(StringCompare.startsWithAny("GT450", "gpu", "GT", "GT450"));
	}


	@Test
	public void startsWith() {
		Assert.assertFalse(StringCompare.startsWith(charAry("GT450"), 0, charAry("A"), 0));
		Assert.assertFalse(StringCompare.startsWith(charAry("GT450"), 0, charAry("GA"), 1));

		Assert.assertTrue(StringCompare.startsWith(charAry("GT450"), 0, charAry("GT"), 0));
		Assert.assertTrue(StringCompare.startsWith(charAry("GT450"), 0, charAry("GT450"), 0));
		Assert.assertTrue(StringCompare.startsWith(charAry("-GT450"), 1, charAry("==GT"), 2));
	}


	@Test
	public void containsAll() {
		Assert.assertFalse(StringCompare.containsAll("My Test String", new String[] { }));
		Assert.assertFalse(StringCompare.containsAll("My Test String", new String[] { "z" }));
		Assert.assertFalse(StringCompare.containsAll("My Test String", new String[] { "ing", "Bb" }));

		Assert.assertTrue(StringCompare.containsAll("My Test String", new String[] { "" }));
		Assert.assertTrue(StringCompare.containsAll("My Test String", new String[] { "ing", " " }));
		Assert.assertTrue(StringCompare.containsAll("My Test String", new String[] { "Test", "My Test String", " " }));

		Assert.assertFalse(StringCompare.containsAll("My Test String", Arrays.asList()));
		Assert.assertFalse(StringCompare.containsAll("My Test String", Arrays.asList("z")));
		Assert.assertFalse(StringCompare.containsAll("My Test String", Arrays.asList("ing", "Bb")));

		Assert.assertTrue(StringCompare.containsAll("My Test String", Arrays.asList("")));
		Assert.assertTrue(StringCompare.containsAll("My Test String", Arrays.asList("ing", " ")));
		Assert.assertTrue(StringCompare.containsAll("My Test String", Arrays.asList("Test", "My Test String", " ")));
	}


	@Test
	public void containsAny() {
		Assert.assertFalse(StringCompare.containsAny("My Test String", new String[] { }));
		Assert.assertFalse(StringCompare.containsAny("My Test String", new String[] { "z" }));
		Assert.assertFalse(StringCompare.containsAny("My Test String", new String[] { "Abc", "B" }));

		Assert.assertTrue(StringCompare.containsAny("My Test String", new String[] { "" }));
		Assert.assertTrue(StringCompare.containsAny("My Test String", new String[] { "Thing", " " }));
		Assert.assertTrue(StringCompare.containsAny("My Test String", new String[] { "X", "My Test String", " " }));

		Assert.assertFalse(StringCompare.containsAny("My Test String", Arrays.asList()));
		Assert.assertFalse(StringCompare.containsAny("My Test String", Arrays.asList("z")));
		Assert.assertFalse(StringCompare.containsAny("My Test String", Arrays.asList("Abc", "B")));

		Assert.assertTrue(StringCompare.containsAny("My Test String", Arrays.asList("")));
		Assert.assertTrue(StringCompare.containsAny("My Test String", Arrays.asList("Thing", " ")));
		Assert.assertTrue(StringCompare.containsAny("My Test String", Arrays.asList("X", "My Test String", " ")));
	}


	@Test
	public void equal() {
		Assert.assertFalse(StringCompare.equal("My Test String", 0, "My Test", 0, 14));
		Assert.assertFalse(StringCompare.equal("My Test String", charSeq("Abc")));
		Assert.assertFalse(StringCompare.equal(charAry("My Test String"), 0, charAry("Abc"), 0, 3));

		Assert.assertTrue(StringCompare.equal("My Test String", 0, "My Test String", 0, 14));
		Assert.assertTrue(StringCompare.equal("My Test String", charSeq("My Test String")));
		Assert.assertTrue(StringCompare.equal(charAry("My Test String"), 0, charAry("My "), 0, 3));
		Assert.assertTrue(StringCompare.equal(charAry("My Test String"), 2, charAry("Your Test"), 4, 5));
	}


	private static char[] charAry(String str) {
		return str.toCharArray();
	}


	private static CharSequence charSeq(String str) {
		return new StringBuilder(str);
	}


	private static <K, V> Entry<K, V> of(K key, V val) {
		return new AbstractMap.SimpleImmutableEntry<>(key, val);
	}

}
