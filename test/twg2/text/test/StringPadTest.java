package twg2.text.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import checks.CheckTask;
import twg2.text.stringUtils.StringPad;

/**
 * @author TeamworkGuy2
 * @since 2016-08-15
 */
public class StringPadTest {

	@Test
	public void padLeftTest() {
		List<String> inputs = Arrays.asList(
			"A",
			"AB",
			"ABC",
			"ABCD",
			""
		);

		CheckTask.assertTests(inputs, Arrays.asList("A", "AB", "ABC", "ABCD", ""), (s) -> StringPad.padLeft(s, 0, '0'));

		CheckTask.assertTests(inputs, Arrays.asList("A", "AB", "ABC", "ABCD", "0"), (s) -> StringPad.padLeft(s, 1, '0'));

		CheckTask.assertTests(inputs, Arrays.asList("0A", "AB", "ABC", "ABCD", "00"), (s) -> StringPad.padLeft(s, 2, '0'));

		CheckTask.assertTests(inputs, Arrays.asList("00A", "0AB", "ABC", "ABCD", "000"), (s) -> StringPad.padLeft(s, 3, '0'));
	}


	@Test
	public void padRightTest() {
		List<String> inputs = Arrays.asList(
			"A",
			"AB",
			"ABC",
			"ABCD",
			""
		);

		CheckTask.assertTests(inputs, Arrays.asList("A", "AB", "ABC", "ABCD", ""), (s) -> StringPad.padRight(s, 0, '0'));

		CheckTask.assertTests(inputs, Arrays.asList("A", "AB", "ABC", "ABCD", "0"), (s) -> StringPad.padRight(s, 1, '0'));

		CheckTask.assertTests(inputs, Arrays.asList("A0", "AB", "ABC", "ABCD", "00"), (s) -> StringPad.padRight(s, 2, '0'));

		CheckTask.assertTests(inputs, Arrays.asList("A00", "AB0", "ABC", "ABCD", "000"), (s) -> StringPad.padRight(s, 3, '0'));
	}

}
