package twg2.text.test;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import org.junit.Assert;
import org.junit.Test;

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


	@Test
	public void stringJoin() {
		List<String> strList = Arrays.asList("A", "B", "C");
		Set<String> strSet = new HashSet<>();
		strSet.add("A");
		strSet.add("B");
		strSet.add("C");
		Collection<Collection<String>> strColls = Arrays.asList(strList, strSet);

		final String delimiter = ", ";

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
		}

		// test List<String> -> String
		Assert.assertEquals("A, B, C", StringJoin.join(Arrays.asList("A", "B", "C"), ", "));
		Assert.assertEquals("", StringJoin.join(Arrays.asList(), ", "));

		// test (List<String>, dst) -> String
		StringJoin.join(strList, ", ", sb);
		Assert.assertEquals("A, B, C", sb.toString());
	}


	@Test
	public void joinStringArray() {
		final String delimiter = ", ";
		String[] strAry = new String[] { "glarg", "narg", "warg", "varg" };

		// test String[] -> String
		Assert.assertEquals(stringJoiner(delimiter, strAry, 0, strAry.length), StringJoin.join(strAry, delimiter));

		// test (String[], off, len) -> String
		Assert.assertEquals("null, Cc", StringJoin.join(new String[] { "A", null, "Cc", "Dd" }, 1, 2, ", "));

		// test (String[], off, len, dst) -> String
		StringBuilder sb = null;

		StringJoin.join(strAry, 2, 2, delimiter, sb = new StringBuilder());
		Assert.assertEquals(stringJoiner(delimiter, strAry, 2, 2), sb.toString());
	}


	@Test
	public void stringJoinObjects() {
		final String delimiter = ", ";

		Object[] objAry = new Object[] { new ToString(0), "subset", new ToString(4), new File("a\\b\\c.d") };
		List<Object> objList = Arrays.asList(objAry);
		Set<Object> objSet = new LinkedHashSet<Object>(objList);
		String[] objAryStrs = new String[] { "arg0", "subset", "arg4", "a\\b\\c.d" };

		StringBuilder sb = new StringBuilder();

		// test Object[] -> String
		Assert.assertEquals(stringJoiner(delimiter, objAryStrs, 0, objAryStrs.length), StringJoin.Objects.join(objAry, delimiter));
		Assert.assertEquals("", StringJoin.Objects.join(new Object[0], ", "));

		// test List<String> -> String
		Assert.assertEquals(stringJoiner(delimiter, objAryStrs, 0, objAryStrs.length), StringJoin.Objects.join(objList, delimiter));

		// test Iterable<String> -> String
		Assert.assertEquals(stringJoiner(delimiter, objAryStrs, 0, objAryStrs.length), StringJoin.Objects.join(objSet, delimiter));

		// test (List<String>, dst) -> String
		StringJoin.Objects.join(objList, delimiter, sb);
		Assert.assertEquals(stringJoiner(delimiter, objAryStrs, 0, objAryStrs.length), sb.toString());
		sb.setLength(0);

		// test (Iterable<String>, dst) -> String
		StringJoin.Objects.join(objSet, delimiter, sb);
		Assert.assertEquals(stringJoiner(delimiter, objAryStrs, 0, objAryStrs.length), sb.toString());
		sb.setLength(0);

		// test (Object[], off, len, dst) -> String
		StringJoin.Objects.join(objAry, 2, 2, delimiter, sb);
		Assert.assertEquals(stringJoiner(delimiter, objAryStrs, 2, 2), sb.toString());
		sb.setLength(0);
	}


	@Test
	public void stringJoinFunc() {
		String delimiter = ", ";

		Assert.assertEquals("", StringJoin.Func.join(Arrays.asList(), delimiter, (a) -> "t:" + a));
		Assert.assertEquals("t:a", StringJoin.Func.join(Arrays.asList("a"), delimiter, (a) -> "t:" + a));
		Assert.assertEquals("t:arg2, t:arg12", StringJoin.Func.join(Arrays.asList(new ToString(2), new ToString(12)), delimiter, (a) -> "t:" + a));
		Assert.assertEquals("t:arg2, null", StringJoin.Func.join(Arrays.asList(new ToString(2), null), delimiter, (a) -> "t:" + a));
		Assert.assertEquals("t:arg2, t:NIL", StringJoin.Func.joinManualNulls(Arrays.asList(new ToString(2), null), delimiter, (a) -> "t:" + (a != null ? a : "NIL")));
	}


	private static String stringJoiner(String delimiter, Object[] strs, int off, int len) {
		StringJoiner strJoiner = new StringJoiner(delimiter);
		for(int i = off, size = off + len; i < size; i++) {
			strJoiner.add(strs[i].toString());
		}
		return strJoiner.toString();
	}

}
