package test;

import org.junit.Assert;
import org.junit.Test;

import stringUtils.StringCase;

/** Tests for {@link StringCase}
 * @author TeamworkGuy2
 * @since 2015-0-0
 */
public class StringCaseTest {

	@Test
	public void stringCaseTest() {
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
