package twg2.text.test;

import org.junit.Test;

import twg2.text.stringUtils.StringCompare;
import checks.CheckTask;

/**
 * @author TeamworkGuy2
 * @since 2015-2-21
 */
public class StringCompareTest {

	@Test
	public void testContains() {
		String[] strs = { "alpha", "beta", "gamma", "int", "Integer", "IntList" };
		String[] contains = { "gamma", "nt", "Int", "IS" };
		Boolean[] expectEqualIgnoreCase = { true, false, true, false };
		Boolean[] expectContainsIgnoreCase = { true, true, true, true };

		CheckTask.assertTests(contains, expectEqualIgnoreCase, (s) -> StringCompare.containsEqualIgnoreCase(strs, s));

		CheckTask.assertTests(contains, expectContainsIgnoreCase, (s) -> StringCompare.containsIgnoreCase(strs, s));
	}

}
