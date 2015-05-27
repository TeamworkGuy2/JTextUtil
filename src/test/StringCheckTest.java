package test;

import org.junit.Test;

import stringUtils.StringCheck;
import checks.CheckTask;

/**
 * @author TeamworkGuy2
 * @since 2015-5-26
 */
public class StringCheckTest {

	@Test
	public void testStringCheck() {

		CheckTask.assertTests(new String[] { "A\nB", " ", "", null  }, new Boolean[] { false, false, true, true }, StringCheck::isNullOrEmpty);

		CheckTask.assertTests(new String[] { "A\nB", " ", "", null  }, new Boolean[] { false, true, true, true }, StringCheck::isNullOrWhitespace);

		CheckTask.assertTests(new String[] { "A\nB", " ", ""  }, new Boolean[] { false, true, true }, StringCheck::isWhitespace);

		CheckTask.assertTests(new String[] { "==A\nB", "-- \t ", "..   -="  }, new Boolean[] { false, true, true }, (str) -> StringCheck.isWhitespace(str.toCharArray(), 2, 3));

		CheckTask.assertTests(new String[] { "==A\nB", "-- \t ", "..   12"  }, new Boolean[] { false, true, true }, (str) -> StringCheck.isWhitespace(str.toCharArray(), 2, 3));

		CheckTask.assertTests(new String[] { "==A\nB", "-- \t ", "..   ab"  }, new Boolean[] { false, true, true }, (str) -> StringCheck.isWhitespace(new StringBuilder(str), 2, 3));

	}

}
