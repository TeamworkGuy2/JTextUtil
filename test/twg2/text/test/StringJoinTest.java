package twg2.text.test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import org.junit.Assert;
import org.junit.Test;

import checks.CheckTask;
import twg2.text.stringUtils.StringJoin;

/**
 * @author TeamworkGuy2
 * @since 2015-5-26
 */
public class StringJoinTest {

	private static class ToString {
		private int idx;

		public ToString(int idx) {
			this.idx = idx;
		}

		@Override
		public String toString() {
			return "arg" + idx;
		}

	}


	final String delimiter = ", ";
	final String[] strAry = new String[] { "glarg", "narg", "warg", "varg", "yarg" };
	final List<String> strList = Arrays.asList(strAry);
	final Set<String> strSet = new LinkedHashSet<>(strList);

	final Object[] objAry = new Object[] { new ToString(0), "subset", new ToString(4), null, new File("a\\b\\c.d") };
	final List<Object> objList = Arrays.asList(objAry);
	final Set<Object> objSet = new LinkedHashSet<>(objList);
	final String[] objAryStrs = new String[] { "arg0", "subset", "arg4", "null", "a\\b\\c.d" };


	@Test
	public void joinStringList() {
		List<String> strList = Arrays.asList("A", "B", "C");
		Set<String> strSet = new HashSet<>();
		strSet.add("A");
		strSet.add("B");
		strSet.add("C");
		strSet.add("D");
		Collection<Collection<String>> strColls = Arrays.asList(strList, strSet);

		StringBuilder sb = new StringBuilder();

		for(Collection<String> strColl : strColls) {
			// baseline using java.util.StringJoiner
			Object[] ary = strColl.toArray();
			String expectedStr = stringJoiner(delimiter, ary, 0, ary.length);

			// test Iterable<String> -> String
			Assert.assertEquals(expectedStr, StringJoin.join(strColl, delimiter));

			// test Iterable<String> -> StringBuilder
			StringJoin.join(strColl, delimiter, sb);
			Assert.assertEquals(expectedStr, sb.toString());
			sb.setLength(0);

			expectedStr = stringJoiner(delimiter, ary, 1, 2);

			// test Iterable<String> -> String
			Assert.assertEquals(expectedStr, StringJoin.join(strColl, 1 ,2, delimiter));

			// test Iterable<String> -> StringBuilder
			StringJoin.join(strColl, 1, 2, delimiter, sb);
			Assert.assertEquals(expectedStr, sb.toString());
			sb.setLength(0);
		}

		// test List<String> -> String
		Assert.assertEquals("A, B, C", StringJoin.join(Arrays.asList("A", "B", "C"), ", "));
		Assert.assertEquals("", StringJoin.join(Arrays.asList(), ", "));

		// test (List<String>, dst) -> String
		StringJoin.join(strList, ", ", sb);
		Assert.assertEquals("A, B, C", sb.toString());
	}


	@Test
	public void joinStringListOffLen() {
		List<String> strColl = Arrays.asList("0", "A", "B", "C", "D", "E");

		// baseline using java.util.StringJoiner
		Object[] ary = strColl.toArray();
		String expectedStr = stringJoiner(delimiter, ary, 1, 3);

		// test Iterable<String> -> String
		Assert.assertEquals(expectedStr, StringJoin.join(strColl, 1, 3, delimiter));

		// test Iterable<String> -> StringBuilder
		StringBuilder sb = new StringBuilder();

		StringJoin.join(strColl, 1, 3, delimiter, sb);
		Assert.assertEquals(expectedStr, sb.toString());
	}


	@Test
	public void joinStringsIterable() {
		// test Iterable<String> -> String
		Assert.assertEquals(stringJoiner(delimiter, strAry, 0, strAry.length), StringJoin.join(strSet, delimiter));
		Assert.assertEquals(stringJoiner(delimiter, strAry, 1, 2), StringJoin.join(strSet, 1, 2, delimiter));

		// test (Iterable<String>, dst) -> String
		StringBuilder sb = new StringBuilder();

		StringJoin.join(strSet, delimiter, sb);
		Assert.assertEquals(stringJoiner(delimiter, strAry, 0, strAry.length), sb.toString());
		sb.setLength(0);

		StringJoin.join(strSet, 2, 3, delimiter, sb);
		Assert.assertEquals(stringJoiner(delimiter, strAry, 2, 3), sb.toString());
	}


	@Test
	public void joinStringsIterator() {
		// test Iterator<String> -> String
		Assert.assertEquals(stringJoiner(delimiter, strAry, 0, strAry.length), StringJoin.join(strList.iterator(), delimiter));
		Assert.assertEquals(stringJoiner(delimiter, strAry, 1, 2), StringJoin.join(strList.iterator(), 1, 2, delimiter));

		// test (Iterator<String>, dst) -> String
		StringBuilder sb = new StringBuilder();

		StringJoin.join(strList.iterator(), delimiter, sb);
		Assert.assertEquals(stringJoiner(delimiter, strAry, 0, strAry.length), sb.toString());
		sb.setLength(0);

		StringJoin.join(strList.iterator(), 2, 3, delimiter, sb);
		Assert.assertEquals(stringJoiner(delimiter, strAry, 2, 3), sb.toString());
	}


	@Test
	public void joinStringArray() {
		// test String[] -> String
		Assert.assertEquals(stringJoiner(delimiter, strAry, 0, strAry.length), StringJoin.join(strAry, delimiter));
	}


	@Test
	public void joinStringArrayOffLen() {
		// test (String[], off, len) -> String
		Assert.assertEquals("null, Cc", StringJoin.join(new String[] { "A", null, "Cc", "Dd" }, 1, 2, ", "));

		// test (String[], off, len, dst) -> String
		StringBuilder sb  = new StringBuilder();

		StringJoin.join(strAry, 2, 2, delimiter, sb);
		Assert.assertEquals(stringJoiner(delimiter, strAry, 2, 2), sb.toString());
	}


	@Test
	public void joinObjectsArray() {
		// test Object[] -> String
		Assert.assertEquals(stringJoiner(delimiter, objAryStrs, 0, objAryStrs.length), StringJoin.Objects.join(objAry, delimiter));
		Assert.assertEquals(stringJoiner(delimiter, objAryStrs, 2, 3), StringJoin.Objects.join(objAry, 2, 3, delimiter));
		Assert.assertEquals("", StringJoin.Objects.join(new Object[0], ", "));

		// test (Object[], off, len, dst) -> String
		StringBuilder sb = new StringBuilder();

		StringJoin.Objects.join(objAry, 2, 2, delimiter, sb);
		Assert.assertEquals(stringJoiner(delimiter, objAryStrs, 2, 2), sb.toString());
	}


	@Test
	public void joinObjectsList() {
		// test List<Object> -> String
		Assert.assertEquals(stringJoiner(delimiter, objAryStrs, 0, objAryStrs.length), StringJoin.Objects.join(objList, delimiter));
		Assert.assertEquals(stringJoiner(delimiter, objAryStrs, 2, 2), StringJoin.Objects.join(objList, 2, 2, delimiter));

		// test (List<Object>, dst) -> String
		StringBuilder sb = new StringBuilder();

		StringJoin.Objects.join(objList, delimiter, sb);
		Assert.assertEquals(stringJoiner(delimiter, objAryStrs, 0, objAryStrs.length), sb.toString());
	}


	@Test
	public void joinObjectsIterable() {
		// test Iterable<Object> -> String
		Assert.assertEquals(stringJoiner(delimiter, objAryStrs, 0, objAryStrs.length), StringJoin.Objects.join(objSet, delimiter));
		Assert.assertEquals(stringJoiner(delimiter, objAryStrs, 1, 2), StringJoin.Objects.join(objSet, 1, 2, delimiter));

		// test (Iterable<Object>, dst) -> String
		StringBuilder sb = new StringBuilder();

		StringJoin.Objects.join(objSet, delimiter, sb);
		Assert.assertEquals(stringJoiner(delimiter, objAryStrs, 0, objAryStrs.length), sb.toString());
		sb.setLength(0);

		StringJoin.Objects.join(objSet, 2, 3, delimiter, sb);
		Assert.assertEquals(stringJoiner(delimiter, objAryStrs, 2, 3), sb.toString());
	}


	@Test
	public void joinObjectsIterator() {
		// test Iterator<Object> -> String
		Assert.assertEquals(stringJoiner(delimiter, objAryStrs, 0, objAryStrs.length), StringJoin.Objects.join(objList.iterator(), delimiter));
		Assert.assertEquals(stringJoiner(delimiter, objAryStrs, 1, 2), StringJoin.Objects.join(objList.iterator(), 1, 2, delimiter));

		// test (Iterator<Object>, dst) -> String
		StringBuilder sb = new StringBuilder();

		StringJoin.Objects.join(objList.iterator(), delimiter, sb);
		Assert.assertEquals(stringJoiner(delimiter, objAryStrs, 0, objAryStrs.length), sb.toString());
		sb.setLength(0);

		StringJoin.Objects.join(objList.iterator(), 2, 3, delimiter, sb);
		Assert.assertEquals(stringJoiner(delimiter, objAryStrs, 2, 3), sb.toString());
	}


	@Test
	public void stringJoinFunc() {
		Assert.assertEquals("", StringJoin.Func.join(Arrays.asList(), delimiter, (a) -> "t:" + a));
		Assert.assertEquals("t:a", StringJoin.Func.join(Arrays.asList("a"), delimiter, (a) -> "t:" + a));
		Assert.assertEquals("t:arg2, t:arg12", StringJoin.Func.join(Arrays.asList(new ToString(2), new ToString(12)), delimiter, (a) -> "t:" + a));
		Assert.assertEquals("t:arg2, null", StringJoin.Func.join(Arrays.asList(new ToString(2), null), delimiter, (a) -> "t:" + a));
		Assert.assertEquals("t:arg2, t:NIL", StringJoin.Func.joinManualNulls(Arrays.asList(new ToString(2), null), delimiter, (a) -> "t:" + (a != null ? a : "NIL")));
	}


	@Test
	public void repeatTest() throws IOException {
		Assert.assertEquals("---", StringJoin.repeat('-', 3));
		Assert.assertEquals("t+t+", StringJoin.repeat("t+", 2));
		Assert.assertEquals("str", StringJoin.repeat("str", 1));
		Assert.assertEquals("", StringJoin.repeat("str", 0));

		StringBuilder sb = new StringBuilder();
		StringJoin.repeat('5', 2, sb);
		Assert.assertEquals("55", sb.toString());
		sb.setLength(0);
		StringJoin.repeat("5*", 0, sb);
		Assert.assertEquals("", sb.toString());
		sb.setLength(0);
		StringJoin.repeat("5*", 3, sb);
		Assert.assertEquals("5*5*5*", sb.toString());
		sb.setLength(0);
	}


	@Test
	public void repeatJoinTest() throws IOException {
		Assert.assertEquals("A-A-A", StringJoin.repeatJoin("A", '-', 3));
		Assert.assertEquals("A", StringJoin.repeatJoin("A", '-', 1));
		Assert.assertEquals("str-str-str", StringJoin.repeatJoin("str", '-', 3));
		Assert.assertEquals("str", StringJoin.repeatJoin("str", '-', 1));
		Assert.assertEquals("", StringJoin.repeatJoin("str", '-', 0));

		Assert.assertEquals("A||A||A", StringJoin.repeatJoin("A", "||", 3));
		Assert.assertEquals("A", StringJoin.repeatJoin("A", "||", 1));
		Assert.assertEquals("str||str||str", StringJoin.repeatJoin("str", "||", 3));
		Assert.assertEquals("str", StringJoin.repeatJoin("str", "||", 1));
		Assert.assertEquals("", StringJoin.repeatJoin("str", "||", 0));
		Assert.assertEquals("A|A", StringJoin.repeatJoin("A", "|", 2));

		StringBuilder sb = new StringBuilder();
		StringJoin.repeatJoin("5", '-', 2, sb);
		Assert.assertEquals("5-5", sb.toString());
		sb.setLength(0);
		StringJoin.repeatJoin("5*", '-', 0, sb);
		Assert.assertEquals("", sb.toString());
		sb.setLength(0);
		StringJoin.repeatJoin("5*", '-', 3, sb);
		Assert.assertEquals("5*-5*-5*", sb.toString());
		sb.setLength(0);

		StringJoin.repeatJoin("5", "|", 2, sb);
		Assert.assertEquals("5|5", sb.toString());
		sb.setLength(0);
		StringJoin.repeatJoin("5*", "||", 0, sb);
		Assert.assertEquals("", sb.toString());
		sb.setLength(0);
		StringJoin.repeatJoin("5*", "||", 3, sb);
		Assert.assertEquals("5*||5*||5*", sb.toString());
		sb.setLength(0);
	}


	@Test
	public void stringJoinMiscellaneous() {
		String[][] strs = new String[][] { { "Aa", "Bb" }, { "123", "_", ".." }, { "", "" }, { "" } };
		String[] delimiters = new String[] { "-", "||", "=", "=" };
		String[] expect = new String[] { "Aa-Bb", "123||_||..", "=", "" };

		CheckTask.assertTests(strs, expect, (s, i) -> StringJoin.join(s, delimiters[i]));
	}


	private static String stringJoiner(String delimiter, Object[] strs, int off, int len) {
		StringJoiner strJoiner = new StringJoiner(delimiter);
		for(int i = off, size = off + len; i < size; i++) {
			strJoiner.add(strs[i].toString());
		}
		return strJoiner.toString();
	}

}
