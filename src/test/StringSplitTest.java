package test;

import org.junit.Assert;
import org.junit.Test;

import stringUtils.StringSplit;
import checks.CheckTask;

/**
 * @author TeamworkGuy2
 * @since 2015-5-11
 */
public class StringSplitTest {


	@Test
	public void stringSplitTest() {
		String[] matches = new String[] {
				"a somewhat very long string without any matching values except at the end //",
				"something shorter//split into pieces//at least I hope//so",
				"//beginning split",
				"////split////",
				"",
				"//a//b//c//d//e//f//g",
				"1 2",
				"//",
				"5//",
		};

		for(String match : matches) {
			Assert.assertArrayEquals(match.split("//", 5), StringSplit.split(match, "//", 5));
		}
	}


	@Test
	public void stringSplitNthChildTest() {
		Assert.assertEquals(StringSplit.findNthMatch("abc, def, ghi", ",", 1, 3), " def");

		CheckTask.assertException(() -> StringSplit.findNthMatch("abc, def, ghi", ",", 1, 2));
		CheckTask.assertException(() -> StringSplit.findNthMatch("abc, def, ghi", ",", 1, 4));
	}

}
