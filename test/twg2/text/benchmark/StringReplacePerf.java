package twg2.text.benchmark;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import twg2.text.stringUtils.StringReplace;

/**
 * @author TeamworkGuy2
 * @since 2019-10-03
 */
public class StringReplacePerf {
	public List<String> strs = Arrays.asList(
			"Alpha", "", "alphA", "-==-AAA-==-", "none", "----------", "AAAAAAAAAA", "longer-than-normal-string-of-long-length-a", "1234567890-with-another-A-", "java.util.Arrays",
			"null", "[]", "Calm", "+==+ZZZ+==+", "none", "========", "**********=[]=**********", "abcdefghijklmnopqrstuvwxyz--A--", "abcdefghijklmnopqrstuvwxyz--Z--", "java.util.List"
	);
	public int loops = 10000;


	@Test
	public void replaceTwg2() {
		int res = 0;

		for(int i = 0; i < loops; i++) {
			int sub = 0;
			for(int j = 0, size = strs.size(); j < size; j++) {
				sub += StringReplace.replace(StringReplace.replace(strs.get(j), "-a", "-A"), "A", "B").length() - (j < size - 1 ? StringReplace.replace(strs.get(j + 1), "A", "B").length() : 0);
			}
			if(i % 3 == 0 || i % 5 == 0) {
				res += sub;
			}
		}

		System.out.println(this.getClass().getSimpleName() + "-" + res);
	}


	@Test
	public void replaceJava() {
		int res = 0;

		for(int i = 0; i < loops; i++) {
			int sub = 0;
			for(int j = 0, size = strs.size(); j < size; j++) {
				sub += strs.get(j).replace("-a", "-A").replace("A", "B").length() - (j < size - 1 ? strs.get(j + 1).replace("A", "B").length() : 0);
			}
			if(i % 3 == 0 || i % 5 == 0) {
				res += sub;
			}
		}

		System.out.println(this.getClass().getSimpleName() + "-" + res);
	}

}
