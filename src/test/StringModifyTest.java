package test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import stringUtils.StringModify;

/**
 * @author TeamworkGuy2
 * @since 2015-5-26
 */
public class StringModifyTest {

	private static class QuotedStrings {
		private char quoteChar;
		private String str1;
		private String str1Res;
		private String str2;
		private String str2Res;
		private List<String> strs;
		private List<String> strsRes;

		public QuotedStrings(char quoteChar, String str1, String str1Res, String str2, String str2Res, List<String> strs, List<String> strsRes) {
			this.quoteChar = quoteChar;
			this.str1 = str1;
			this.str1Res = str1Res;
			this.str2 = str2;
			this.str2Res = str2Res;
			this.strs = strs;
			this.strsRes = strsRes;
		}

	}


	@Test
	public void testStringModifyTrim() {
		QuotedStrings[] tests = new QuotedStrings[] {
				new QuotedStrings('"', "\"", "\"", "\"a\"", "a", Arrays.asList("\"\"", "\n\"", "a\"b\"", "\"\"123\"\""), Arrays.asList("", "\n\"", "a\"b\"", "\"123\"")),
				new QuotedStrings('*', "*", "*", "*a*", "a", Arrays.asList("**", "\n*", "a*b*", "**123**"), Arrays.asList("", "\n*", "a*b*", "*123*"))
		};

		for(QuotedStrings test : tests) {
			String tmpStr = StringModify.trimSurrounding(test.str1, test.quoteChar);
			Assert.assertEquals(test.str1Res, tmpStr);

			tmpStr = StringModify.trimSurrounding(test.str2, test.quoteChar);
			Assert.assertEquals(test.str2Res, tmpStr);

			List<String> tmpStrs = new ArrayList<>(test.strs);
			StringModify.trimSurrounding(tmpStrs, test.quoteChar);
			Assert.assertArrayEquals(test.strsRes.toArray(), tmpStrs.toArray());
		}
	}


	@Test
	public void testStringModifyHex() throws IOException {
		String hexStr = "9AF0E";
		String hexStrFull = "9AF0E0";
		byte[] hexBytes = { (byte)154, (byte)240, (byte)224 };
		byte[] hexBytesOff1Len2 = { (byte)175 };

		StringReader hexStrReader = new StringReader(hexStr);
		byte[] bytes = StringModify.decodeHexStream(hexStrReader);
		Assert.assertArrayEquals(hexBytes, bytes);

		bytes = StringModify.decodeHexString(hexStr);
		Assert.assertArrayEquals(hexBytes, bytes);

		bytes = StringModify.decodeHexString(hexStr, 1, 2);
		Assert.assertArrayEquals(hexBytesOff1Len2, bytes);

		char hexCh = 'B';
		byte b = StringModify.hexToByte(hexCh);
		Assert.assertEquals(10 + hexCh - 'A', b); // byte value of a hex letter like 'F' is: 10 + 'F' - 'A' => 10 + 70 - 65 (ascii values) => 15

		char resCh = StringModify.toHex(b);
		Assert.assertEquals(hexCh, resCh);

		String resStr = StringModify.toHexString(hexBytes);
		Assert.assertEquals(hexStrFull, resStr);

		resStr = StringModify.toHexString(hexBytes, 1, 2);
		Assert.assertEquals(hexStrFull.substring(2, 2 + 4), resStr);

		StringWriter output = new StringWriter();
		StringModify.writeHexString(hexBytes, 0, 3, output);
		Assert.assertEquals(hexStrFull, output.toString());
	}

}