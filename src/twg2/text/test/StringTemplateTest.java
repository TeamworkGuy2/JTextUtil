package twg2.text.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import twg2.text.stringTemplate.SingleIntTemplate;
import twg2.text.stringTemplate.SingleVarTemplate;
import twg2.text.stringTemplate.StringTemplateBuilder;
import checks.CheckTask;

/**
 * @author TeamworkGuy2
 * @since 2015-7-28
 */
public class StringTemplateTest {

	private static class StrTmplData {
		private StringTemplateBuilder strTmplBldr;
		private List<List<Object>> testArgs;
		private List<String> expectResults;

		public StrTmplData(StringTemplateBuilder strTmplBldr, List<List<Object>> testArgs, List<String> expectResults) {
			this.strTmplBldr = strTmplBldr;
			this.testArgs = testArgs;
			this.expectResults = expectResults;
		}

	}


	@Test
	public void testStringTemplateCompile() {
		for(StrTmplData data : createTemplateBuilders()) {
			int i = 0;
			for(List<Object> args : data.testArgs) {
				Assert.assertEquals(data.expectResults.get(i), data.strTmplBldr.compile(args));
				i++;
			}
		}
	}


	@Test
	public void testStringTemplateVar() {
		CheckTask.assertException(() -> SingleVarTemplate.of(new StringTemplateBuilder().and("none"), String.class));
		CheckTask.assertException(() -> SingleIntTemplate.of(new StringTemplateBuilder().and("none")));

		CheckTask.assertException(() -> SingleVarTemplate.of(new StringTemplateBuilder().and("none"), String.class).compile(new Object()));
		CheckTask.assertException(() -> SingleIntTemplate.of(new StringTemplateBuilder().and("none")).compile("25"));

		Assert.assertEquals("none", SingleVarTemplate.of(new StringTemplateBuilder().and("none").and(String.class), String.class).compile(new String()));
		Assert.assertEquals("none25", SingleIntTemplate.of(new StringTemplateBuilder().and("none").andInt()).compile(25));
	}


	private static List<StrTmplData> createTemplateBuilders() {
		return Arrays.asList(
				new StrTmplData(new StringTemplateBuilder().and(":: ").andInt().and("-arc"),
						Arrays.asList(Arrays.asList(1), Arrays.asList(3), Arrays.asList(5), Arrays.asList((Object)null)),
						Arrays.asList(":: 1-arc", ":: 3-arc", ":: 5-arc", ":: null-arc")),

				new StrTmplData(new StringTemplateBuilder().and("most ").andString().and(byte[].class, Arrays::toString).and("!").and(Boolean.class),
						Arrays.asList(Arrays.asList("= ", new byte[] {}, true), Arrays.asList("()", new byte[] { 3 }, true), Arrays.asList("", new byte[] { 10, 20 }, false), Arrays.asList(",", null, true)),
						Arrays.asList("most = []!true", "most ()[3]!true", "most [10, 20]!false", "most ,null!true"))
		);
	}

}
