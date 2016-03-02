package twg2.text.test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;

import twg2.text.stringUtils.StringCase;

/** Tests for {@link StringCase}
 * @author TeamworkGuy2
 * @since 2015-0-0
 */
public class StringCaseTest {

	static class StringCases {
		List<String> shouldPass;
		List<String> shouldFail;

		public StringCases(List<String> shouldPass, List<String> shouldFail) {
			this.shouldPass = shouldPass;
			this.shouldFail = shouldFail;
		}

	}


	@Test
	public void stringCaseCheck() {
		StringCases camelCase = new StringCases(Arrays.asList("abc"), Arrays.asList("a_c", "Abc"));
		StringCases titleCase = new StringCases(Arrays.asList("AlphaChar"), Arrays.asList("alphaChar"));
		StringCases underscoreLowerCase = new StringCases(Arrays.asList("moon_base"), Arrays.asList("moon_Base", "Moon_base", "Moon_Base"));
		StringCases underscoreTitleCase = new StringCases(Arrays.asList("Nanite_Cloud"), Arrays.asList("nanite_cloud", "Nanite_clound", "nanite_Cloud"));

		toStringCases(camelCase, "camelCase", StringCase::isCamelCase);
		toStringCases(titleCase, "titleCase", StringCase::isTitleCase);
		toStringCases(underscoreLowerCase, "underscoreLowerCase", StringCase::isUnderscoreLowerCase);
		toStringCases(underscoreTitleCase, "underscoreUpperCase", StringCase::isUnderscoreTitleCase);
	}


	private static <T> void toStringCases(StringCases cases, String converterName, Function<String, Boolean> converter) {
		convertTest(cases.shouldPass, converterName, converter, true);
		convertTest(cases.shouldFail, converterName, converter, false);
	}


	private static <T> void convertTest(List<String> cases, String converterName, Function<String, T> converter, T expectedResult) {
		for(int i = 0, size = cases.size(); i < size; i++) {
			T res = converter.apply(cases.get(i));
			Assert.assertEquals(converterName + " '" + cases.get(i) + "'", expectedResult, res);
		}
	}


	@Test
	public void stringToCase() {
		String[] strs = new String[] { "abc", "Alpha", "Subpar", "SupPar", "Al_Cid", "var_val_byte", "A_", "_A", "a", "_" };
		String[] camelCase = new String[] { "abc", "alpha", "subpar", "supPar", "alCid", "varValByte", "a", "a", "a", "_" };
		String[] titleCase = new String[] { "Abc", "Alpha", "Subpar", "SupPar", "AlCid", "VarValByte", "A", "A", "A", "_" };
		String[] underscoreLowerCase = new String[] { "abc", "alpha", "subpar", "sup_par", "al_cid", "var_val_byte", "a_", "_a", "a", "_" };
		String[] underscoreTitleCase = new String[] { "Abc", "Alpha", "Subpar", "Sup_Par", "Al_Cid", "Var_Val_Byte", "A_", "_A", "A", "_" };

		for(int i = 0, size = strs.length; i < size; i++) {
			Assert.assertEquals("camelCase", StringCase.toCamelCase(strs[i]), camelCase[i]);
			Assert.assertEquals("TitleCase", StringCase.toTitleCase(strs[i]), titleCase[i]);
			Assert.assertEquals("underscoreLowerCase", StringCase.toUnderscoreLowerCase(strs[i]), underscoreLowerCase[i]);
			Assert.assertEquals("underscoreTitleCase", StringCase.toUnderscoreTitleCase(strs[i]), underscoreTitleCase[i]);
			/*System.out.println("str: " + str +
					",\tcamelCase: " + StringCase.toCamelCase(str) +
					",\tTitleCase: " + StringCase.toTitleCase(str) +
					",\tunderscore_lower_case: " + StringCase.toUnderscoreLowerCase(str) +
					",\tUnderscore_Upper_Case: " + StringCase.toUnderscoreTitleCase(str));*/
		}
	}

}
