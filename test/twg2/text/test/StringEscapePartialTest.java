package twg2.text.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import checks.CheckTask;
import twg2.text.stringEscape.StringEscapePartial;

/**
 * @author TeamworkGuy2
 * @since 2016-2-28
 */
public class StringEscapePartialTest {


	public static class UnescapePartialQuoted {

		public static List<String> inputs = Arrays.asList(
			"0. with \"quoted block\" end",
			"1.  \\abc, xyz",
			"2.  abc, xyz",
			"3.  \"abc, xyz\"], ",
			"4. \\\"\"",
			"5. , "
		);

		public static List<String> expected = Arrays.asList(
			"with \"quoted block\"",
			" \\abc",
			" abc",
			" \"abc, xyz\"",
			"\\\"\"",
			""
		);

		public static List<Integer> expectedIndexes = Arrays.asList(
			inputs.get(0).lastIndexOf("\"") + 1,
			inputs.get(1).lastIndexOf(','),
			inputs.get(2).lastIndexOf(','),
			inputs.get(3).lastIndexOf("]"),
			inputs.get(4).lastIndexOf("\"") + 1,
			inputs.get(5).lastIndexOf(',')
		);

	}


	@Test
	public void unescapePartialQuotedTest() {
		int offset = 3;
		List<String> inputs = UnescapePartialQuoted.inputs;
		List<String> expect = UnescapePartialQuoted.expected;
		List<Integer> expectIndex = UnescapePartialQuoted.expectedIndexes;
		StringBuilder dst = new StringBuilder();

		for(int i = 0, size = inputs.size(); i < size; i++) {
			int index = StringEscapePartial.unescapePartialQuoted(inputs.get(i), offset, inputs.get(i).length() - offset, '\\', '"', ',', ']', false, dst);
			Assert.assertEquals(expect.get(i), dst.toString());
			Assert.assertEquals(i + ". expect (" + expectIndex.get(i) + "): " + expect.get(i) + ", result (" + index + "): " + dst.toString(), (int)expectIndex.get(i), index);
			dst.setLength(0);
		}
	}



	@Test
	public void unescapePartialQuotedFailureTest() {
		StringBuilder dst = new StringBuilder();

		CheckTask.assertException(() -> {
			String str = "\"abc,";
			StringEscapePartial.unescapePartialQuoted(str, 0, str.length(), '\\', '"', ',', ']', true, dst);
		});
		dst.setLength(0);

		CheckTask.assertException(() -> {
			String str = "A \"";
			StringEscapePartial.unescapePartialQuoted(str, 0, str.length(), '\\', '"', ',', ']', true, dst);
		});
		dst.setLength(0);
	}

}
