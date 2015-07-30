package stringUtils.template;

import java.util.Arrays;

/**
 * @author TeamworkGuy2
 * @since 2015-7-28
 */
public interface SingleIntTemplate extends StringTemplate {


	public default String compile(int value) {
		return this.compile(Arrays.asList(value));
	}


	public default void compile(int value, Appendable dst) {
		this.compile(Arrays.asList(value), dst);
	}


	public static SingleIntTemplate of(StringTemplateBuilder strTmpl) {
		if(StringTemplateBuilder.isSingleInt(strTmpl)) {
			return strTmpl;
		}
		else {
			throw new IllegalArgumentException("string template does not match single int argument");
		}
	}

}
