package twg2.text.test;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
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
	public void testStringJoin() {
		List<String> strList = Arrays.asList("A", "B", "C");
		Set<String> strSet = new HashSet<>();
		strSet.add("A");
		strSet.add("B");
		strSet.add("C");
		Collection<Collection<String>> strColls = Arrays.asList(strList, strSet);

		String delimiter = ", ";

		int off = 2;
		int len = 2;

		Object[] objAry = new Object[] { new ToString(0), "subset", new ToString(4), new File("a\\b\\c.d") };
		String[] objAryStrs = new String[] { "arg0", "subset", "arg4", "a\\b\\c.d" };

		String[] strAry = new String[] { "glarg", "narg", "warg", "varg" };

		StringBuilder strB = new StringBuilder();

		for(Collection<String> strColl : strColls) {
			Object[] ary = strColl.toArray();
			String expectedStr = stringJoiner(delimiter, ary, 0, ary.length);

			Assert.assertEquals(expectedStr, StringJoin.join(strColl, delimiter));

			StringJoin.join(strColl, delimiter, strB);
			Assert.assertEquals(expectedStr, strB.toString());
			strB.setLength(0);
		}

		Assert.assertEquals(stringJoiner(delimiter, objAryStrs, 0, objAryStrs.length), StringJoin.join(objAry, delimiter));
		Assert.assertEquals(stringJoiner(delimiter, strAry, 0, strAry.length), StringJoin.join(strAry, delimiter));

		StringJoin.join(strAry, off, len, delimiter, strB);
		Assert.assertEquals(stringJoiner(delimiter, strAry, off, len), strB.toString());
		strB.setLength(0);

		StringJoin.join(objAry, off, len, delimiter, strB);
		Assert.assertEquals(stringJoiner(delimiter, objAryStrs, off, len), strB.toString());
		strB.setLength(0);
	}


	private static String stringJoiner(String delimiter, Object[] strs, int off, int len) {
		StringJoiner strJoiner = new StringJoiner(delimiter);
		for(int i = off, size = off + len; i < size; i++) {
			strJoiner.add(strs[i].toString());
		}
		return strJoiner.toString();
	}

}
