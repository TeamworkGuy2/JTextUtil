package twg2.text.test;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
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
	public void anyStartsWith() {
		List<String> strs = Arrays.asList("String A", "String B", "Thing C");

		Assert.assertTrue(StringCompare.anyStartWith(strs, charSeq("String")));
		Assert.assertTrue(StringCompare.anyStartWith(strs, charSeq("Thing")));
		Assert.assertFalse(StringCompare.anyStartWith(strs, charSeq("ing")));
		Assert.assertTrue(StringCompare.anyStartWith(strs, charSeq("A=String"), 2));
		Assert.assertFalse(StringCompare.anyStartWith(strs, charSeq("A=ing"), 2));
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
		Assert.assertTrue(StringCompare.containsEqualIgnoreCase(iter(strs), "Alpha"));
		Assert.assertTrue(StringCompare.containsEqualIgnoreCase(iter(strs), "alpha"));
		Assert.assertFalse(StringCompare.containsEqualIgnoreCase(iter(strs), "alpha="));

		Assert.assertTrue(StringCompare.containsIgnoreCase(strs, "Char"));
		Assert.assertTrue(StringCompare.containsIgnoreCase(strs, "char"));
		Assert.assertTrue(StringCompare.containsIgnoreCase(strs, "elta"));
		Assert.assertFalse(StringCompare.containsIgnoreCase(strs, "char="));
		Assert.assertTrue(StringCompare.containsIgnoreCase(Arrays.asList(strs), "Char"));
		Assert.assertTrue(StringCompare.containsIgnoreCase(Arrays.asList(strs), "char"));
		Assert.assertTrue(StringCompare.containsIgnoreCase(Arrays.asList(strs), "elta"));
		Assert.assertFalse(StringCompare.containsIgnoreCase(Arrays.asList(strs), "char="));
		Assert.assertTrue(StringCompare.containsIgnoreCase(iter(strs), "Char"));
		Assert.assertTrue(StringCompare.containsIgnoreCase(iter(strs), "char"));
		Assert.assertTrue(StringCompare.containsIgnoreCase(iter(strs), "elta"));
		Assert.assertFalse(StringCompare.containsIgnoreCase(iter(strs), "char="));
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

		Assert.assertFalse(StringCompare.containsAll("My Test String", list()));
		Assert.assertFalse(StringCompare.containsAll("My Test String", list("z")));
		Assert.assertFalse(StringCompare.containsAll("My Test String", list("ing", "Bb")));

		Assert.assertTrue(StringCompare.containsAll("My Test String", list("")));
		Assert.assertTrue(StringCompare.containsAll("My Test String", list("ing", " ")));
		Assert.assertTrue(StringCompare.containsAll("My Test String", list("Test", "My Test String", " ")));

		Assert.assertFalse(StringCompare.containsAll("My Test String", iter()));
		Assert.assertFalse(StringCompare.containsAll("My Test String", iter("z")));
		Assert.assertFalse(StringCompare.containsAll("My Test String", iter("ing", "Bb")));

		Assert.assertTrue(StringCompare.containsAll("My Test String", iter("")));
		Assert.assertTrue(StringCompare.containsAll("My Test String", iter("ing", " ")));
		Assert.assertTrue(StringCompare.containsAll("My Test String", iter("Test", "My Test String", " ")));
	}


	@Test
	public void containsAllIgnoreCase() {
		Assert.assertFalse(StringCompare.containsAllIgnoreCase("My Test String", new String[] { }));
		Assert.assertFalse(StringCompare.containsAllIgnoreCase("My Test String", new String[] { "z" }));
		Assert.assertFalse(StringCompare.containsAllIgnoreCase("My Test String", new String[] { "ing", "Bb" }));

		Assert.assertTrue(StringCompare.containsAllIgnoreCase("My Test String", new String[] { "" }));
		Assert.assertTrue(StringCompare.containsAllIgnoreCase("My Test String", new String[] { "ING", " " }));
		Assert.assertTrue(StringCompare.containsAllIgnoreCase("My Test String", new String[] { "test", "my Test STRing", " " }));

		Assert.assertFalse(StringCompare.containsAllIgnoreCase("My Test String", list()));
		Assert.assertFalse(StringCompare.containsAllIgnoreCase("My Test String", list("z")));
		Assert.assertFalse(StringCompare.containsAllIgnoreCase("My Test String", list("ing", "Bb")));

		Assert.assertTrue(StringCompare.containsAllIgnoreCase("My Test String", list("")));
		Assert.assertTrue(StringCompare.containsAllIgnoreCase("My Test String", list("ING", " ")));
		Assert.assertTrue(StringCompare.containsAllIgnoreCase("My Test String", list("test", "my Test STRing", " ")));

		Assert.assertFalse(StringCompare.containsAllIgnoreCase("My Test String", iter()));
		Assert.assertFalse(StringCompare.containsAllIgnoreCase("My Test String", iter("z")));
		Assert.assertFalse(StringCompare.containsAllIgnoreCase("My Test String", iter("ing", "Bb")));

		Assert.assertTrue(StringCompare.containsAllIgnoreCase("My Test String", iter("")));
		Assert.assertTrue(StringCompare.containsAllIgnoreCase("My Test String", iter("ING", " ")));
		Assert.assertTrue(StringCompare.containsAllIgnoreCase("My Test String", iter("test", "my Test STRing", " ")));
	}


	@Test
	public void containsCount() {
		Assert.assertEquals(0, StringCompare.containsCount("My Test String", new String[] { }));
		Assert.assertEquals(0, StringCompare.containsCount("My Test String", new String[] { "z" }));
		Assert.assertEquals(0, StringCompare.containsCount("My Test String", new String[] { "Abc", "B" }));

		Assert.assertEquals(1, StringCompare.containsCount("My Test String", new String[] { "" }));
		Assert.assertEquals(1, StringCompare.containsCount("My Test String", new String[] { "Thing", " " }));
		Assert.assertEquals(2, StringCompare.containsCount("My Test String", new String[] { "X", "My Test String", " " }));

		Assert.assertEquals(0, StringCompare.containsCount("My Test String", list()));
		Assert.assertEquals(0, StringCompare.containsCount("My Test String", list("z")));
		Assert.assertEquals(0, StringCompare.containsCount("My Test String", list("Abc", "B")));

		Assert.assertEquals(1, StringCompare.containsCount("My Test String", list("")));
		Assert.assertEquals(1, StringCompare.containsCount("My Test String", list("Thing", " ")));
		Assert.assertEquals(2, StringCompare.containsCount("My Test String", list("X", "My Test String", " ")));

		Assert.assertEquals(0, StringCompare.containsCount("My Test String", iter()));
		Assert.assertEquals(0, StringCompare.containsCount("My Test String", iter("z")));
		Assert.assertEquals(0, StringCompare.containsCount("My Test String", iter("Abc", "B")));

		Assert.assertEquals(1, StringCompare.containsCount("My Test String", iter("")));
		Assert.assertEquals(1, StringCompare.containsCount("My Test String", iter("Thing", " ")));
		Assert.assertEquals(2, StringCompare.containsCount("My Test String", iter("X", "My Test String", " ")));
	}


	@Test
	public void containsIgnoreCaseCount() {
		Assert.assertEquals(0, StringCompare.containsIgnoreCaseCount("My Test String", new String[] { }));
		Assert.assertEquals(0, StringCompare.containsIgnoreCaseCount("My Test String", new String[] { "z" }));
		Assert.assertEquals(0, StringCompare.containsIgnoreCaseCount("My Test String", new String[] { "Abc", "B" }));

		Assert.assertEquals(1, StringCompare.containsIgnoreCaseCount("My Test String", new String[] { "" }));
		Assert.assertEquals(1, StringCompare.containsIgnoreCaseCount("My Test String", new String[] { "thing", " " }));
		Assert.assertEquals(2, StringCompare.containsIgnoreCaseCount("My Test String", new String[] { "X", "MY Test string", " " }));

		Assert.assertEquals(0, StringCompare.containsIgnoreCaseCount("My Test String", list()));
		Assert.assertEquals(0, StringCompare.containsIgnoreCaseCount("My Test String", list("z")));
		Assert.assertEquals(0, StringCompare.containsIgnoreCaseCount("My Test String", list("Abc", "B")));

		Assert.assertEquals(1, StringCompare.containsIgnoreCaseCount("My Test String", list("")));
		Assert.assertEquals(1, StringCompare.containsIgnoreCaseCount("My Test String", list("thing", " ")));
		Assert.assertEquals(2, StringCompare.containsIgnoreCaseCount("My Test String", list("X", "MY Test string", " ")));

		Assert.assertEquals(0, StringCompare.containsIgnoreCaseCount("My Test String", iter()));
		Assert.assertEquals(0, StringCompare.containsIgnoreCaseCount("My Test String", iter("z")));
		Assert.assertEquals(0, StringCompare.containsIgnoreCaseCount("My Test String", iter("Abc", "B")));

		Assert.assertEquals(1, StringCompare.containsIgnoreCaseCount("My Test String", iter("")));
		Assert.assertEquals(1, StringCompare.containsIgnoreCaseCount("My Test String", iter("thing", " ")));
		Assert.assertEquals(2, StringCompare.containsIgnoreCaseCount("My Test String", iter("X", "MY Test string", " ")));
	}


	@Test
	public void containsAny() {
		Assert.assertFalse(StringCompare.containsAny("My Test String", new String[] { }));
		Assert.assertFalse(StringCompare.containsAny("My Test String", new String[] { "z" }));
		Assert.assertFalse(StringCompare.containsAny("My Test String", new String[] { "Abc", "B" }));

		Assert.assertTrue(StringCompare.containsAny("My Test String", new String[] { "" }));
		Assert.assertTrue(StringCompare.containsAny("My Test String", new String[] { "Thing", " " }));
		Assert.assertTrue(StringCompare.containsAny("My Test String", new String[] { "X", "My Test String", " " }));

		Assert.assertFalse(StringCompare.containsAny("My Test String", list()));
		Assert.assertFalse(StringCompare.containsAny("My Test String", list("z")));
		Assert.assertFalse(StringCompare.containsAny("My Test String", list("Abc", "B")));

		Assert.assertTrue(StringCompare.containsAny("My Test String", list("")));
		Assert.assertTrue(StringCompare.containsAny("My Test String", list("Thing", " ")));
		Assert.assertTrue(StringCompare.containsAny("My Test String", list("X", "My Test String", " ")));

		Assert.assertFalse(StringCompare.containsAny("My Test String", iter()));
		Assert.assertFalse(StringCompare.containsAny("My Test String", iter("z")));
		Assert.assertFalse(StringCompare.containsAny("My Test String", iter("Abc", "B")));

		Assert.assertTrue(StringCompare.containsAny("My Test String", iter("")));
		Assert.assertTrue(StringCompare.containsAny("My Test String", iter("Thing", " ")));
		Assert.assertTrue(StringCompare.containsAny("My Test String", iter("X", "My Test String", " ")));
	}


	@Test
	public void containsAnyIgnoreCase() {
		Assert.assertFalse(StringCompare.containsAnyIgnoreCase("My Test String", new String[] { }));
		Assert.assertFalse(StringCompare.containsAnyIgnoreCase("My Test String", new String[] { "z" }));
		Assert.assertFalse(StringCompare.containsAnyIgnoreCase("My Test String", new String[] { "Abc", "B" }));

		Assert.assertTrue(StringCompare.containsAnyIgnoreCase("My Test String", new String[] { "" }));
		Assert.assertTrue(StringCompare.containsAnyIgnoreCase("My Test String", new String[] { "thing", " " }));
		Assert.assertTrue(StringCompare.containsAnyIgnoreCase("My Test String", new String[] { "X", "MY Test string", " " }));

		Assert.assertFalse(StringCompare.containsAnyIgnoreCase("My Test String", list()));
		Assert.assertFalse(StringCompare.containsAnyIgnoreCase("My Test String", list("z")));
		Assert.assertFalse(StringCompare.containsAnyIgnoreCase("My Test String", list("Abc", "B")));

		Assert.assertTrue(StringCompare.containsAnyIgnoreCase("My Test String", list("")));
		Assert.assertTrue(StringCompare.containsAnyIgnoreCase("My Test String", list("thing", " ")));
		Assert.assertTrue(StringCompare.containsAnyIgnoreCase("My Test String", list("X", "MY Test string", " ")));

		Assert.assertFalse(StringCompare.containsAnyIgnoreCase("My Test String", iter()));
		Assert.assertFalse(StringCompare.containsAnyIgnoreCase("My Test String", iter("z")));
		Assert.assertFalse(StringCompare.containsAnyIgnoreCase("My Test String", iter("Abc", "B")));

		Assert.assertTrue(StringCompare.containsAnyIgnoreCase("My Test String", iter("")));
		Assert.assertTrue(StringCompare.containsAnyIgnoreCase("My Test String", iter("thing", " ")));
		Assert.assertTrue(StringCompare.containsAnyIgnoreCase("My Test String", iter("X", "MY Test string", " ")));
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


	public static List<String> list(final String...strs) {
		return Arrays.asList(strs);
	}


	private static Iterable<String> iter(final String... strs) {
		return new Iterable<String>() {
			
			@Override
			public Iterator<String> iterator() {
				return new LinkedHashSet<>(Arrays.asList(strs)).iterator();
			}
		};
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
