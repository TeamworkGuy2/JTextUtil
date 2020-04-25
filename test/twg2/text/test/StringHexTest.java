package twg2.text.test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import twg2.text.stringUtils.StringHex;
import twg2.text.test.utils.StringChunkReader;

/**
 * @author TeamworkGuy2
 * @since 2015-5-26
 */
public class StringHexTest {


	@Test
	public void testEncodeDecodeHex() throws IOException {
		String hexStr = "9AF0E";
		String hexStrFull = "9AF0E0";
		byte[] hexBytes = bytes(154, 240, 224);
		byte[] hexBytesOff1Len2 = bytes(175);

		List<String> hexChunks = Arrays.asList("5B06", "4D5A", "3", "09", "4412", "0504B0304504B0304", "E8");
		byte[] hexByteChunks = bytes(91, 6, 77, 90, 48/*3", "0*/, (byte)148, 65, 32, 80, 75, 3, 4, 80, 75, 3, 4, (byte)232);

		StringReader hexStrReader = new StringReader(hexStr);
		byte[] bytes = StringHex.decodeHexStream(hexStrReader);
		Assert.assertArrayEquals(hexBytes, bytes);

		StringChunkReader hexChunkReader = new StringChunkReader(hexChunks);
		bytes = StringHex.decodeHexStream(hexChunkReader, 16);
		Assert.assertArrayEquals(hexByteChunks, bytes);

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

		StringBuilder sb = new StringBuilder();
		StringHex.writeHexString(hexBytes, 0, 3, sb);
		Assert.assertEquals(hexStrFull, sb.toString());

		StringWriter output = new StringWriter();
		StringHex.writeHexString(hexBytes, 0, 3, output);
		Assert.assertEquals(hexStrFull, output.toString());
	}


	private static byte[] bytes(int... bytes) {
		byte[] b = new byte[bytes.length];
		for(int i = 0, size = bytes.length; i < size; i++) {
			if(bytes[i] > 255 || bytes[i] < -128) {
				throw new IllegalArgumentException("argument out of range, byte[" + i + "] = " + bytes[i] + " is not a byte between -128 and 127");
			}
			b[i] = (byte)bytes[i];
		}
		return b;
	}

}
