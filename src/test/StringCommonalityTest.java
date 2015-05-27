package test;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;

import stringUtils.StringCommonality;
import checks.CheckTask;

/**
 * @author TeamworkGuy2
 * @since 2015-5-9
 */
public class StringCommonalityTest {

	@Test
	public void findPrefixTest() {
		new CommonalityData(true, Arrays.asList(of("alp", 0), of("p", 2), of("", 4)), Arrays.asList(
				"alpha, beta, gamma",
				"alphabet",
				"alpine"
		)).test();

		new CommonalityData(true, Arrays.asList(of("123", 0), of("", 3), of("", 6)), Arrays.asList(
				"12345678",
				"12345",
				"123"
		)).test();
	}


	@Test
	public void findSuffixTest() {
		new CommonalityData(false, Arrays.asList(of("ing", 0), of("i", 2), of("", 4)), Arrays.asList(
				"sing",
				"alphabetizing",
				"-ing"
		)).test();

		new CommonalityData(false, Arrays.asList(of("abc", 0), of("a", 5), of("", 6)), Arrays.asList(
				"zabcabc",
				"alphabetan-abc",
				"a--abc"
		)).test();
	}


	@Test
	public void findStringPortionTest() {
		List<Entry<String, Integer>> suffixesAndOffsets = Arrays.asList(of("wxy", 4), of("w", 2), of("", 5));
		List<String> strs = Arrays.asList(
				"-1wxyxy",
				"-2wxy",
				"-3wxyz"
		);

		for(Entry<String, Integer> suffixOffset : suffixesAndOffsets) {
			Assert.assertEquals(suffixOffset.getKey(), StringCommonality.commonStringPortion(1, 6, suffixOffset.getValue(), strs, false, false));
		}
	}


	@Test
	public void commonPrefixSuffixTest() {
		// prefix
		{
			String[][] strs = new String[][] { { "" }, { "a_b", "a_b_c" }, { "this.that", "this_that" }, { "abc", "c" } };
			String[] expect = new String[] { "", "a_b", "this", "" };

			CheckTask.assertTests(strs, expect, "", "", (strSet) -> StringCommonality.findPrefix(0, Arrays.asList(strSet)));
		}
		// suffix
		{
			String[][] strs = new String[][] { { "" }, { "a_b", "a_b_c" }, { "this.that", "this_that" }, { "abc", "c" } };
			String[] expect = new String[] { "", "", "that", "c" };

			CheckTask.assertTests(strs, expect, "", "", (strSet) -> StringCommonality.findSuffix(0, Arrays.asList(strSet)));
		}
	}


	private static <K, V> Entry<K, V> of(K key, V val) {
		return new AbstractMap.SimpleImmutableEntry<>(key, val);
	}

}




/**
 * @author TeamworkGuy2
 * @since 2015-5-9
 */
class CommonalityData {
	boolean prefix;
	List<Entry<String, Integer>> suffixesAndOffsets;
	List<String> strs;


	public CommonalityData(boolean prefix, List<Entry<String, Integer>> suffixesAndOffsets, List<String> strs) {
		this.prefix = prefix;
		this.suffixesAndOffsets = suffixesAndOffsets;
		this.strs = strs;
	}


	void test() {
		for(Entry<String, Integer> suffixOffset : suffixesAndOffsets) {
			if(prefix) {
				Assert.assertEquals(suffixOffset.getKey(), StringCommonality.findPrefix(suffixOffset.getValue(), strs));
			}
			else {
				Assert.assertEquals(suffixOffset.getKey(), StringCommonality.findSuffix(suffixOffset.getValue(), strs));
			}
		}
	}

}
