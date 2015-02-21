package test;

import org.junit.Test;

import stringUtils.StringCompare;
import checks.Check;

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

		Check.assertTests(contains, expectEqualIgnoreCase, "", "", (s) -> {
			return StringCompare.containsEqualIgnoreCase(strs, s);
		});

		Check.assertTests(contains, expectContainsIgnoreCase, "", "", (s) -> {
			return StringCompare.containsIgnoreCase(strs, s);
		});
	}

}
