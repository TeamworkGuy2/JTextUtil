package twg2.text.test;

import org.junit.Assert;
import org.junit.Test;

import twg2.text.stringSearch.StringSearchOp;

public class StringSearchOpTest {

	@Test
	public void contains() {
		Assert.assertFalse(StringSearchOp.CONTAINS.test(null, "X"));
		//Assert.assertFalse(StringSearchOp.CONTAINS.test("X", null));
		Assert.assertFalse(StringSearchOp.CONTAINS.test("Abc", "X"));
		Assert.assertTrue(StringSearchOp.CONTAINS.test(null, null));
		Assert.assertTrue(StringSearchOp.CONTAINS.test("Abc", "bc"));
		Assert.assertTrue(StringSearchOp.CONTAINS.test("Abc", "Abc"));
	}


	@Test
	public void endsWith() {
		Assert.assertFalse(StringSearchOp.ENDS_WITH.test(null, "X"));
		//Assert.assertFalse(StringSearchOp.ENDS_WITH.test("X", null));
		Assert.assertFalse(StringSearchOp.ENDS_WITH.test("Abc", "X"));
		Assert.assertFalse(StringSearchOp.ENDS_WITH.test("Abc", "b"));
		Assert.assertTrue(StringSearchOp.ENDS_WITH.test(null, null));
		Assert.assertTrue(StringSearchOp.ENDS_WITH.test("Abc", "bc"));
		Assert.assertTrue(StringSearchOp.ENDS_WITH.test("Abc", "Abc"));
	}


	@Test
	public void equals() {
		Assert.assertFalse(StringSearchOp.EQUALS.test(null, "X"));
		Assert.assertFalse(StringSearchOp.EQUALS.test("X", null));
		Assert.assertFalse(StringSearchOp.EQUALS.test("Abc", "X"));
		Assert.assertFalse(StringSearchOp.EQUALS.test("Abc", "b"));
		Assert.assertTrue(StringSearchOp.EQUALS.test(null, null));
		Assert.assertTrue(StringSearchOp.EQUALS.test("Abc", "Abc"));
	}


	@Test
	public void startsWith() {
		Assert.assertFalse(StringSearchOp.STARTS_WITH.test(null, "X"));
		//Assert.assertFalse(StringSearchOp.STARTS_WITH.test("X", null));
		Assert.assertFalse(StringSearchOp.STARTS_WITH.test("Abc", "X"));
		Assert.assertFalse(StringSearchOp.STARTS_WITH.test("Abc", "b"));
		Assert.assertTrue(StringSearchOp.STARTS_WITH.test(null, null));
		Assert.assertTrue(StringSearchOp.STARTS_WITH.test("Abc", "Ab"));
		Assert.assertTrue(StringSearchOp.STARTS_WITH.test("Abc", "Abc"));
	}
}
