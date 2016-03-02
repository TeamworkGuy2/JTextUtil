package twg2.text.test;

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

}
