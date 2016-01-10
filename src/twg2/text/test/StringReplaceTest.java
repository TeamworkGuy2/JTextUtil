package twg2.text.test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import twg2.text.stringUtils.StringReplace;
import checks.CheckTask;

/**
 * @author TeamworkGuy2
 * @since 2015-4-4
 */
public class StringReplaceTest {

	@Test
	public void stringReplaceTest() {
		String[] strs = new String[] {   "&amp; with &lt; or &gt;", "*** or ** * ***", "***", "*** a six***seven***" };
		String[] expect = new String[] { "& with < or >",           "* or ** * *",     "*",   "* a six*seven*" };

		List<String> searchStrs = Arrays.asList("***", "&amp;", "&lt;", "&gt;");
		List<String> replaceStrs = Arrays.asList("*", "&", "<", ">");

		CheckTask.assertTests(strs, expect, (s) -> StringReplace.replaceStrings(s, 0, searchStrs, replaceStrs));

		CheckTask.assertTests(strs, expect, (s) -> StringReplace.replaceStrings(s, 0, new LinkedList<>(searchStrs), replaceStrs));
	}

	@Test
	public void stringReplaceOffsetTest() {
		String[] strs = new String[] {   "&lt;+=&lt; or &gt;", "*** or ** * ***", "***", "*** a six***seven***" };
		String[] expect = new String[] { "&lt;+=< or >",       "*** or ** * *",   "***", "*** a six*seven*" };

		List<String> searchStrs = Arrays.asList("***", "&amp;", "&lt;", "&gt;");
		List<String> replaceStrs = Arrays.asList("*", "&", "<", ">");

		int strOffset = 6;
		CheckTask.assertTests(strs, expect, (s) -> StringReplace.replaceStrings(s, strOffset, searchStrs, replaceStrs));

		CheckTask.assertTests(strs, expect, (s) -> StringReplace.replaceStrings(s, strOffset, new LinkedList<>(searchStrs), replaceStrs));
	}


	@Test
	public void stringBuilderReplaceTest1() {
		String[] strs = new String[] {   "#STRING##STRING# with(&ptr) { abc.func(); } or &gt;", "& or &and _._", "&_", "arg&str|var.val" };
		String[] expect = new String[] { "#STRING# with(&&ptr) { abc->func(); } or &&gt;",      "& or &&and ->", "&_", "arg&&str|var->val" };

		List<String> searchStrs = Arrays.asList("#STRING#", "&", ".", "_");
		List<String> replaceStrs = Arrays.asList("", "&&", "->", "");

		int strOffset = 3;
		CheckTask.assertTests(strs, expect, (s) -> {
			StringBuilder dst = new StringBuilder(s);
			StringReplace.replaceStrings(searchStrs, replaceStrs, dst, strOffset);
			return dst.toString();
		});

		CheckTask.assertTests(strs, expect, (s) -> {
			StringBuilder dst = new StringBuilder(s);
			StringReplace.replaceStrings(new LinkedList<>(searchStrs), replaceStrs, dst, strOffset);
			return dst.toString();
		});
	}


	@Test
	public void stringBuilderReplaceTest2() {
		String[] strs = new String[] {   "#!##!# with(&ptr) { abc.func(); } or &gt;", "& or &and _._", "&_", "arg&str|var.val" };
		String[] expect = new String[] { "#!# with(&&ptr) { abc->func(); } or &&gt;", "& or &&and ->", "&_", "arg&&str|var->val" };

		List<String> searchStrs = Arrays.asList("#!#", "&", ".", "_");
		List<String> replaceStrs = Arrays.asList("", "&&", "->", "");

		int strOffset = 3;
		CheckTask.assertTests(strs, expect, (s) -> {
			StringBuilder dst = new StringBuilder(s);
			StringReplace.replaceStrings(searchStrs, replaceStrs, dst, strOffset);
			return dst.toString();
		});

		CheckTask.assertTests(strs, expect, (s) -> {
			StringBuilder dst = new StringBuilder(s);
			StringReplace.replaceStrings(new LinkedList<>(searchStrs), replaceStrs, dst, strOffset);
			return dst.toString();
		});
	}


	@Test
	public void stringReplaceIterativelyTest() {
		String[] strs = new String[] {           "#!##!# with(#!#) { abc#!#func(); } or #!#", "::#!#and#!#", "#!#", "###!##!##!##!##!##!##!#" };
		String[] expect = new String[] {         "#!#-> with(.) { abc::func(); } or ->",      "::->and.",    "#!#", "##->.::->.::->" };
		String[] expectNoRepeat = new String[] { "#!#-> with(.) { abc::func(); } or #!#",     "::->and.",    "#!#", "##->.::#!##!##!##!#" };

		String searchStr = "#!#";
		List<String> replaceStrs = Arrays.asList("->", ".", "::");

		int strOffset = 2;
		CheckTask.assertTests(strs, expect, (s) -> {
			String str = StringReplace.replaceStrings(s, strOffset, searchStr, replaceStrs, true/*repeatReplacements*/);
			return str;
		});

		CheckTask.assertTests(strs, expect, (s) -> {
			String str = StringReplace.replaceStrings(s, strOffset, searchStr, new LinkedList<>(replaceStrs), true/*repeatReplacements*/);
			return str;
		});

		CheckTask.assertTests(strs, expectNoRepeat, (s) -> {
			String str = StringReplace.replaceStrings(s, strOffset, searchStr, replaceStrs, false/*repeatReplacements*/);
			return str;
		});

		CheckTask.assertTests(strs, expectNoRepeat, (s) -> {
			String str = StringReplace.replaceStrings(s, strOffset, searchStr, new LinkedList<>(replaceStrs), false/*repeatReplacements*/);
			return str;
		});
	}


	@Test
	public void stringReplaceMultipleMatchesWithOneTest() {
		String[] strs = new String[] {   "#!#.. with(::) { abc->func(); } or ::",   "::an->->#.",  "#!#", "##->::.->::.->;;::." };
		String[] expect = new String[] { "#!#%%%% with(%%) { abc%%func(); } or %%", "::an%%%%#%%", "#!#",    "##%%%%%%%%%%%%%%;;%%%%" };

		List<String> searchStrs = Arrays.asList("->", ".", "::");
		String replaceStr = "%%";

		int strOffset = 2;
		CheckTask.assertTests(strs, expect, (s) -> {
			String str = StringReplace.replaceStrings(s, strOffset, searchStrs, replaceStr);
			return str;
		});

		CheckTask.assertTests(strs, expect, (s) -> {
			String str = StringReplace.replaceStrings(s, strOffset, new LinkedList<>(searchStrs), replaceStr);
			return str;
		});
	}

}
