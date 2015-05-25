package stringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

/** A utility for simple string manipulation.
 * For example splitting a string, joining strings, find common string prefixes
 * or suffixes, and (un)escaping XML strings.
 * @author TeamworkGuy2
 * @since 2013-12-21
 */
public final class StringModify {
	public static final char escapeStart = '\\';


	private StringModify() { throw new AssertionError("cannot instantiate static class StringUtility"); }


	/** Trim quotes from the start and end of a list of strings if there
	 * are quotes {@code "} at both the beginning and end of the string.
	 * @param strs the list of strings to trim quotes from, the
	 * modified strings are replaced in the list by their trimmed versions.
	 * This list must be modifiable, its size will not be changed.
	 */
	public static final void trimQuotes(List<String> strs) {
		trimSurrounding(strs, '"');
	}


	public static final void trimSurrounding(List<String> strs, char ch) {
		for(int i = 0, size = strs.size(); i < size; i++) {
			String str = strs.get(i);
			if(str.length() > 1 && str.charAt(0) == ch && str.charAt(str.length() - 1) == ch) {
				strs.set(i, str.substring(1, str.length() - 1));
			}
		}
	}


	/** Trim quotes from the start and end of a string if there
	 * are quotes {@code "} at both the beginning and end of the string.
	 * @param str the string to trim quotes from, the modified strings are
	 * replaced in the list by their trimmed versions.  This list must be
	 * modifiable, its size will not be changed.
	 */
	public static final String trimQuotes(String str) {
		return trimSurrounding(str, '"');
	}


	public static final String trimSurrounding(String str, char ch) {
		if(str.length() > 1 && str.charAt(0) == ch && str.charAt(str.length() - 1) == ch) {
			return str.substring(1, str.length() - 1);
		}
		return str;
	}


	/** Write the hexadecimal value of the specified byte array to the specified
	 * appendable output.
	 * @param hexBytes the array of bytes to read values from
	 * @param offset the offset into the byte array to start converting bytes to hexadecimal digits
	 * @param length the number of bytes to convert, starting at the offset index in the array
	 * @param output the {@link Appendable} output to write the hexadecimal digits to
	 * @throws IOException if there is an error writing to the output stream
	 */
	public static final void writeHexString(final byte[] hexBytes, final int offset, final int length,
			final Appendable output) throws IOException {
		final int size = offset+length;
		for(int i = offset; i < size; i++) {
			byte b = hexBytes[i];
			byte v = (byte)((b >>> 4) & 0x0F);
			//output.append((char)(v < 10 ? (v+48) : (v+55)));
			output.append((char)(55 + v + (((v-10) >> 31) & -7)));
			v = (byte)(b & 0x0F);
			//output.append((char)(v < 10 ? (v+48) : (v+55)));
			output.append((char)(55 + v + (((v-10) >> 31) & -7)));
		}
	}


	/** Write the hexadecimal value of the specified byte array to a string
	 * @param hexBytes the array of bytes to convert to a hexadecimal string
	 * @return a string containing the hexadecimal value of the input byte array
	 */
	public static final String toHexString(final byte[] hexBytes) {
		return toHexString(hexBytes, 0, hexBytes.length);
	}


	/** Write the hexadecimal value of the specified byte array to a string
	 * @param hexBytes the array of bytes to convert to a hexadecimal string
	 * @param offset the offset into the byte array to start converting bytes to hexadecimal digits
	 * @param length the number of bytes to convert, starting at the offset index in the array
	 * @return a string containing the hexadecimal value of the input byte array
	 */
	public static final String toHexString(final byte[] hexBytes, final int offset, final int length) {
		final int size = offset+length;
		char[] c = new char[size << 1];
		for(int i = offset, a = 0; i < size; i++, a+=2) {
			byte b = hexBytes[i];
			byte v = (byte)((b >>> 4) & 0x0F);
			//output.append((char)(v < 10 ? (v+48) : (v+55)));
			c[a] = (char)(55 + v + (((v-10) >> 31) & -7));
			v = (byte)(b & 0x0F);
			//output.append((char)(v < 10 ? (v+48) : (v+55)));
			c[a+1] = (char)(55 + v + (((v-10) >> 31) & -7));
		}
		return new String(c, 0, size << 1);
	}


	/**
	 * @see #decodeHexString(String, int, int)
	 */
	public static final byte[] decodeHexString(final String input) {
		return decodeHexString(input, 0, input.length());
	}


	/** Convert a hexadecimal string to an array of bytes.
	 * If the {@code len} is not divisible by 2, the last hexadecimal character
	 * is stored in the high nibble of the last byte of the returned byte array.
	 * For example, if the string contains the characters "{@code 3A9}", the byte array
	 * returned is {@code [0x3A, 0x90]}.
	 * @param input string to convert to an array of bytes.
	 * @param off the character offset into the {@code input} string at which to start decoding
	 * @param len the number of characters to decode
	 * @return an array of bytes containing the interpreted byte values of the hexadecimal string
	 */
	public static final byte[] decodeHexString(final String input, final int off, final int len) {
		// Note whether the string length is odd
		boolean oddCount = (len & 0x1) == 1;
		int hexLen = len / 2;
		byte[] bytes = new byte[hexLen + (len & 0x1)];
		// Convert pairs of hex characters from the string into bytes
		for(int i = 0; i < hexLen; i++) {
			char c = input.charAt(off + (i << 1));
			byte v1 = (byte)((c-48+(((64-c) >> 31) & -7)) << 4);
			c = input.charAt(off + (i << 1) + 1);
			byte v2 = (byte)((c-48+(((64-c) >> 31) & -7)) & 0xF);
			bytes[i] = (byte)(v1 | v2);
		}
		// If the string length was odd, save it until this point
		// and write it as one character
		if(oddCount) {
			char c = input.charAt(off + (hexLen << 1));
			byte v1 = (byte)((c-48+(((64-c) >> 31) & -7)) << 4);
			bytes[hexLen] = (byte)(v1);
		}

		return bytes;
	}


	/** Convert a stream of US-ASCII encoded hexadecimal characters to an array of bytes.
	 * If the stream's length is not divisible by 2, the last hexadecimal character
	 * is stored in the high nibble of the last byte of the returned byte array.
	 * For example, if the reader contains the data "{@code 3A9}", the byte array
	 * returned is {@code [0x3A, 0x90]}.
	 * @param input string to convert to an array of bytes.
	 * @return an array of bytes containing the interpreted byte values of the hexadecimal stream
	 * @throws IOException if there is an error reading from the input reader
	 */
	public static final byte[] decodeHexStream(final Reader input) throws IOException {
		int size = 8192; // must be divisible by 2
		ByteArrayOutputStream out = new ByteArrayOutputStream(size);
		char[] cbuf = new char[size];
		boolean lastReadOdd = false;
		byte lastByte = 0;
		int read = -1;
		// Read blocks of the stream and convert to hex, two chararacters at a time
		while((read = input.read(cbuf)) != -1) {
			int offset = 0;
			// If the last block was an odd length, combine it's last character and this block's first character
			if(lastReadOdd == true) {
				char c = cbuf[0];
				byte v2 = (byte)((c-48+(((64-c) >> 31) & -7)) & 0xF);
				out.write((byte)(lastByte | v2));
				offset = 1;
			}
			// Convert the rest of the block two characters at a time
			lastReadOdd = false;
			// If the block length is odd, don't write the last character,
			// wait to see if it is the end of the stream
			if(((read-offset) & 0x1) == 1) {
				read--;
				for(int i = offset; i < read; i+=2) {
					char c = cbuf[i];
					byte v1 = (byte)((c-48+(((64-c) >> 31) & -7)) << 4);
					c = cbuf[i+1];
					byte v2 = (byte)((c-48+(((64-c) >> 31) & -7)) & 0xF);
					out.write((byte)(v1 | v2));
				}
				char c = cbuf[read];
				lastByte = (byte)((c-48+(((64-c) >> 31) & -7)) << 4);
				lastReadOdd = true;
			}
			else {
				for(int i = offset; i < read; i+=2) {
					char c = cbuf[i];
					byte v1 = (byte)((c-48+(((64-c) >> 31) & -7)) << 4);
					c = cbuf[i+1];
					byte v2 = (byte)((c-48+(((64-c) >> 31) & -7)) & 0xF);
					out.write((byte)(v1 | v2));
				}
			}
		}
		// If the block length was odd on the last block, write the last character now
		if(lastReadOdd == true) {
			out.write((byte)(lastByte));
		}
		return out.toByteArray();
	}


	/** Convert the lower four bits of a value to a hex character
	 * @param b the value to convert to a hex character, only the lowest four bits are used to calculate the hex value
	 * @return the hex character of the four lowest bits of the value
	 */
	public static final char toHex(int b) {
		return (char)((b = (b & 0xF)) < 10 ? (b+48) : (b+55));
	}


	/** Convert a hexadecimal character to a byte value between [0, 15]
	 * @param c the character, one of: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, A, B, C, D, E, F, to convert to a integer value
	 * @return the value of the hex digit, or 0 if the digit is not a valid hex digit
	 */
	public static final byte hexToByte(char c) {
		return (byte)((c > 47 && c < 58) ? (c-48) : (c > 64 && c < 71) ? (c-55) : 0);
	}

}
