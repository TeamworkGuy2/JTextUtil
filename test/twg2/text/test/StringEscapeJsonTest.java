package twg2.text.test;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import twg2.text.stringEscape.StringEscapeJson;

/**
 * @author TeamworkGuy2
 * @since 2016-2-28
 */
public class StringEscapeJsonTest {

	@Test
	public void jsonStr() {
		String[] raw = new String[] {
			"a \\\\ -\" str \b\n",
			"arc \u2460"
		};

		String[] json = new String[] {
			"a \\\\\\\\ -\\\" str \\b\\n",
			"arc \u2460",
		};

		for(int i = 0, size = raw.length; i < size; i++) {
			String jsonFromRaw = StringEscapeJson.toJsonString(raw[i]);
			Assert.assertEquals(json[i], jsonFromRaw);

			String rawFromJson = StringEscapeJson.fromJsonString(jsonFromRaw);
			Assert.assertEquals(raw[i], rawFromJson);
		}
	}


	@Test
	public void jsonStrOffLen() {
		String[] raw = new String[] {
			"a \\\\ -\" \b\n s",
			"--arc \u2460 =||=",
			"\t\tA\t=\tB\f1\r\n"
		};

		String[] json = new String[] {
			"\\\\\\\\ -\\\" \\b\\n",
			"arc \u2460 =|",
			"A\\t=\\tB\\f1\\r"
		};

		int off = 2;
		int len = 8;

		for(int i = 0, size = raw.length; i < size; i++) {
			String jsonFromRaw = StringEscapeJson.toJsonString(raw[i], off, len);
			Assert.assertEquals(json[i], jsonFromRaw);

			String prefix = randStr(0, 10);
			String suffix = randStr(0, 10);
			String rawFromJson = StringEscapeJson.fromJsonString(prefix + jsonFromRaw + suffix, prefix.length(), jsonFromRaw.length());
			Assert.assertEquals(raw[i].substring(off, off + len), rawFromJson);
		}
	}


	private String randStr(int minInclusive, int maxInclusive) {
		int len = minInclusive + (int)Math.round(Math.random() * (maxInclusive - minInclusive));
		char[] chs = new char[len];
		Arrays.fill(chs, '-');
		return new String(chs);
	}

}
