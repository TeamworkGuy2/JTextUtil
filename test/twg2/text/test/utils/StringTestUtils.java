package twg2.text.test.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TeamworkGuy2
 * @since 2014-8-24
 */
public class StringTestUtils {

	@SafeVarargs
	public static final String fromStringsAndCodePoints(Object... strsOrCodePoints) {
		List<Integer> codePoints = new ArrayList<>();
		for(Object strOrCp : strsOrCodePoints) {
			if(strOrCp instanceof String) {
				for(char ch : ((String)strOrCp).toCharArray()) {
					codePoints.add((int)ch);
				}
			}
			else if(strOrCp instanceof Integer) {
				codePoints.add((Integer)strOrCp);
			}
			else {
				throw new IllegalArgumentException("unknown value " + (strOrCp != null ? strOrCp.getClass() : null) + ", is not a string or int code-point");
			}
		}

		int[] cps = new int[codePoints.size()];
		for(int i = 0, size = cps.length; i < size; i++) {
			cps[i] = codePoints.get(i);
		}
		return new String(cps, 0, cps.length);
	}

}
