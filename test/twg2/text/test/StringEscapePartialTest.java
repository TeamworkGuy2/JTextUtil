package twg2.text.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import twg2.junitassist.checks.CheckTask;
import twg2.text.stringEscape.StringEscapePartial;

/**
 * @author TeamworkGuy2
 * @since 2016-2-28
 */
public class StringEscapePartialTest {

	@Test
	public void unescapePartialQuotedTest() {
		int offset = DataUnescapePartialQuoted.inputsOffset;
		List<String> inputs = DataUnescapePartialQuoted.inputs;
		List<String> expect = DataUnescapePartialQuoted.expected;
		List<Integer> expectIdxs = DataUnescapePartialQuoted.expectedIndexesIncludingQuote;
		StringBuilder dst = new StringBuilder();

		for(int i = 0, size = inputs.size(); i < size; i++) {
			int index = StringEscapePartial.unescapePartialQuoted(inputs.get(i), offset, inputs.get(i).length() - offset, '\\', '"', ',', ']', false, dst);
			Assert.assertEquals(expect.get(i), dst.toString());
			Assert.assertEquals(i + ". expect (" + expectIdxs.get(i) + "): " + expect.get(i) + ", result (" + index + "): " + dst.toString(), (int)expectIdxs.get(i), index);
			dst.setLength(0);
		}

		int index = StringEscapePartial.unescapePartialQuoted("'none' -", 0, 4, '\\', '\'', ',', ']', false, dst);
		Assert.assertEquals("non", dst.toString());
		Assert.assertEquals(4, index);
		dst.setLength(0);

		index = StringEscapePartial.unescapePartialQuoted("'none' -", 0, '\\', '\'', ',', dst);
		Assert.assertEquals("none", dst.toString());
		Assert.assertEquals(6, index);
		dst.setLength(0);

		index = StringEscapePartial.unescapePartialQuoted("'none' -", 0, '\\', '\'', ',', ']', dst);
		Assert.assertEquals("none", dst.toString());
		Assert.assertEquals(6, index);
		dst.setLength(0);
	}


	@Test
	public void unescapePartialQuotedFailureTest() {
		StringBuilder dst = new StringBuilder();

		int i = 0;
		for(String errInput : DataUnescapePartialQuoted.inputsNoClosingQuote) {
			CheckTask.assertException("(" + i + ") " + errInput, () -> {
				StringEscapePartial.unescapePartialQuoted(errInput, 0, errInput.length(), '\\', '"', ',', ']', true, dst);
			});
			i++;
			dst.setLength(0);
		}
	}

}
