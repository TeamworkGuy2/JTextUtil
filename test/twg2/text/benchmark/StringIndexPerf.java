package twg2.text.benchmark;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import twg2.text.stringSearch.StringIndex;

/**
 * @author TeamworkGuy2
 * @since 2019-10-03
 */
public class StringIndexPerf {
	public List<String> strs = Arrays.asList(
			"Alpha", "", "alphA", "-==-AAA-==-", "none", "----------", "AAAAAAAAAA", "longer-than-normal-string-of-long-length-a", "1234567890-with-another-A-", "java.util.Arrays",
			"null", "[]", "Calm", "+==+ZZZ+==+", "none", "========", "**********=[]=**********", "abcdefghijklmnopqrstuvwxyz--A--", "abcdefghijklmnopqrstuvwxyz--Z--", "java.util.List"
	);
	public int loops = 10000;


	@Test
	public void indexOfTwg2() {
		int res = 0;

		for(int i = 0; i < loops; i++) {
			int sub = 0;
			for(int j = 0, size = strs.size(); j < size; j++) {
				sub += StringIndex.indexOf(strs.get(j), 0, "A") + (j < size - 1 ? StringIndex.indexOf(strs.get(j + 1), 0, '-') : 0);
			}
			if(i % 3 == 0 || i % 5 == 0) {
				res += sub;
			}
		}

		System.out.println(res);
	}


	@Test
	public void indexOfJava() {
		int res = 0;

		for(int i = 0; i < loops; i++) {
			int sub = 0;
			for(int j = 0, size = strs.size(); j < size; j++) {
				sub += strs.get(j).indexOf("A") + (j < size - 1 ? strs.get(j + 1).indexOf('-') : 0);
			}
			if(i % 3 == 0 || i % 5 == 0) {
				res += sub;
			}
		}

		System.out.println(res);
	}

}
