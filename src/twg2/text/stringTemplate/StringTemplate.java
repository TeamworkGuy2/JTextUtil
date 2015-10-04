package twg2.text.stringTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * @author TeamworkGuy2
 * @since 2015-7-28
 */
public interface StringTemplate {

	public int getArgCount();


	public boolean isArgLiteral(int idx);


	public Object getArgLiteralValue(int idx);


	public default String getArgLiteralString(int idx) {
		if(isArgLiteral(idx)) {
			Class<?> argType = getArgType(idx);
			if(argType == String.class) {
				return (String)getArgLiteralValue(idx);
			}
			else {
				throw new IllegalArgumentException("arg " + idx + " expected type 'String', found '" + argType + "'");
			}
		}
		else {
			throw new IllegalArgumentException("arg " + idx + " is not a literal value");
		}
	}


	public Class<?> getArgType(int idx);


	public void compileTo(List<Object> args, Appendable dst);


	public default void compileTo(Object[] args, Appendable dst) {
		compileTo(Arrays.asList(args), dst);
	}


	public default String compile(Object... args) {
		StringBuilder strB = new StringBuilder();
		compileTo(args, strB);
		return strB.toString();
	}


	public default String compile(List<Object> args) {
		StringBuilder strB = new StringBuilder();
		compileTo(args, strB);
		return strB.toString();
	}

}
