package stringUtils.template;

import java.util.Arrays;

/**
 * @author TeamworkGuy2
 * @since 2015-7-28
 */
public interface SingleVarTemplate<T> extends StringTemplate {


	public default String compile(T value) {
		return this.compile(Arrays.asList(value));
	}


	public default void compile(T value, Appendable dst) {
		this.compile(Arrays.asList(value), dst);
	}


	public static <R> SingleVarTemplate<R> of(StringTemplateBuilder strTmpl, Class<R> type) {
		if(StringTemplateBuilder.isSingleType(strTmpl, type)) {
			@SuppressWarnings("unchecked")
			SingleVarTemplate<R> resTmpl = (SingleVarTemplate<R>) strTmpl;
			return resTmpl;
		}
		else {
			throw new IllegalArgumentException("string template does not match single " + type + " argument");
		}
	}

}
