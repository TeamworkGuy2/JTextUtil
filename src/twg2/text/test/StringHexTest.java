package twg2.text.test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.Assert;
import org.junit.Test;

import twg2.text.stringUtils.StringHex;

/**
 * @author TeamworkGuy2
 * @since 2015-5-26
 */
public class StringHexTest {


	@Test
	public void testEncodeDecodeHex() throws IOException {
		String hexStr = "9AF0E";
		String hexStrFull = "9AF0E0";
		byte[] hexBytes = { (byte)154, (byte)240, (byte)224 };
		byte[] hexBytesOff1Len2 = { (byte)175 };

		StringReader hexStrReader = new StringReader(hexStr);
		byte[] bytes = StringHex.decodeHexStream(hexStrReader);
		Assert.assertArrayEquals(hexBytes, bytes);

		bytes = StringHex.decodeHexString(hexStr);
		Assert.assertArrayEquals(hexBytes, bytes);

		bytes = StringHex.decodeHexString(hexStr, 1, 2);
		Assert.assertArrayEquals(hexBytesOff1Len2, bytes);

		char hexCh = 'B';
		byte b = StringHex.hexToByte(hexCh);
		Assert.assertEquals(10 + hexCh - 'A', b); // byte value of a hex letter like 'F' is: 10 + 'F' - 'A' => 10 + 70 - 65 (ascii values) => 15

		char resCh = StringHex.toHex(b);
		Assert.assertEquals(hexCh, resCh);

		String resStr = StringHex.toHexString(hexBytes);
		Assert.assertEquals(hexStrFull, resStr);

		resStr = StringHex.toHexString(hexBytes, 1, 2);
		Assert.assertEquals(hexStrFull.substring(2, 2 + 4), resStr);

		StringWriter output = new StringWriter();
		StringHex.writeHexString(hexBytes, 0, 3, output);
		Assert.assertEquals(hexStrFull, output.toString());
	}

}
