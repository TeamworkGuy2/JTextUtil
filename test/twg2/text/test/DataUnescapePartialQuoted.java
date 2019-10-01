package twg2.text.test;

import java.util.Arrays;
import java.util.List;

/** Publicly available, meant to be used by other libraries, not just this
 * @author TeamworkGuy2
 */
public class DataUnescapePartialQuoted {

	public static int inputsOffset = 3;

	public static List<String> inputs = Arrays.asList(
		"0. with \"quoted block\" end",
		"1.  \\abc, xyz",
		"2.  abc, xyz",
		"3.  \"abc, xyz\"], ",
		"4. \\\"\"",
		"5. \"a, b\", c",
		"6. , "
	);

	public static List<String> expected = Arrays.asList(
		"with \"quoted block\"",
		" \\abc",
		" abc",
		" \"abc, xyz\"",
		"\\\"\"",
		"a, b",
		""
	);

	/** The index of the first ending character (i.e. if the string is a quoted string followed by an ending char, return the index of the closing char, not the ending char */
	public static List<Integer> expectedIndexesIncludingQuote = Arrays.asList(
		inputs.get(0).lastIndexOf("\"") + 1,
		inputs.get(1).lastIndexOf(','),
		inputs.get(2).lastIndexOf(','),
		inputs.get(3).lastIndexOf("]"),
		inputs.get(4).lastIndexOf("\"") + 1,
		inputs.get(5).lastIndexOf("\"") + 1,
		inputs.get(6).lastIndexOf(',')
	);


	/** The index of the last ending character (i.e. if the string is a quoted string followed by an ending char, return the index of the ending char, not the closing quote */
	public static List<Integer> _expectedIndexesIncludingTrueEndingChar = Arrays.asList(
		inputs.get(0).lastIndexOf("\"") + 1,
		inputs.get(1).lastIndexOf(','),
		inputs.get(2).lastIndexOf(','),
		inputs.get(3).lastIndexOf("]") + 1,
		inputs.get(4).lastIndexOf("\"") + 1,
		inputs.get(5).lastIndexOf("\"") + 1,
		inputs.get(6).lastIndexOf(',')
	);


	/** inputs which contain one quote char (i.e. are missing an end quote) */
	public static List<String> inputsNoClosingQuote = Arrays.asList(
		"un\"closed",
		"\"abc,",
		"\""
	);

	/** expected parse results for {@link #inputsNoClosingQuote} */
	public static List<String> expectedNoClosingQuote = Arrays.asList(
		"un\"closed",
		"\"abc,",
		"\""
	);

}