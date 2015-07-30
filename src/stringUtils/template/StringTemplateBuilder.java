package stringUtils.template;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import typeInfo.JavaPrimitives;

public class StringTemplateBuilder implements StringTemplate, SingleIntTemplate, SingleVarTemplate<Object> {
	private List<Object> templateParts = new ArrayList<>();
	private List<Class<?>> partType = new ArrayList<>();
	private List<Boolean> partIsLiteral = new ArrayList<>();
	private List<Function<Object, String>> partToString = new ArrayList<Function<Object,String>>();
	private List<Map.Entry<Integer, Class<?>>> nonLiteralTypesByIndex = new ArrayList<>();


	public StringTemplateBuilder() {
	}


	@Override
	public int getArgCount() {
		return templateParts.size();
	}


	@Override
	public boolean isArgLiteral(int idx) {
		return partIsLiteral.get(idx);
	}


	@Override
	public Object getArgLiteralValue(int idx) {
		return templateParts.get(idx);
	}


	@Override
	public Class<?> getArgType(int idx) {
		return partType.get(idx);
	}


	public StringTemplateBuilder and(String str) {
		templateParts.add(str);
		partType.add(String.class);
		partIsLiteral.add(true);
		partToString.add(null);
		return this;
	}


	public <T> StringTemplateBuilder and(Class<T> type, Function<T, String> toString) {
		templateParts.add(null);
		partType.add(type);
		partIsLiteral.add(false);
		@SuppressWarnings("unchecked")
		Function<Object, String> toStringFunc = (Function<Object, String>)toString;
		partToString.add(toStringFunc);
		nonLiteralTypesByIndex.add(new AbstractMap.SimpleImmutableEntry<>(templateParts.size() - 1, type));
		return this;
	}


	public StringTemplateBuilder and(Class<?> type) {
		templateParts.add(null);
		partType.add(type);
		partIsLiteral.add(false);
		partToString.add(null);
		nonLiteralTypesByIndex.add(new AbstractMap.SimpleImmutableEntry<>(templateParts.size() - 1, type));
		return this;
	}


	public StringTemplateBuilder andInt() {
		return this.and(Integer.TYPE);
	}


	public StringTemplateBuilder andString() {
		return this.and(String.class);
	}


	@Override
	public void compileTo(List<Object> args, Appendable dst) {
		try {
			int varIdx = 0;
			for(int i = 0, size = templateParts.size(); i < size; i++) {
				if(partIsLiteral.get(i)) {
					dst.append(templateParts.get(i).toString());
				}
				else {
					Object arg = args.get(varIdx);
					if(arg == null) {
						Function<Object, String> toString = partToString.get(i);
						dst.append(toString != null ? toString.apply(arg) : "null");
					}
					else {
						Class<?> argType = arg.getClass();
						JavaPrimitives primitiveType = null;

						if(!partType.get(i).isAssignableFrom(argType) && ((primitiveType = JavaPrimitives.tryGetByType(argType)) == null) || (primitiveType != null && !partType.get(i).isAssignableFrom(primitiveType.getType()))) {
							throw new IllegalArgumentException("arg " + varIdx + " expected type '" + partType.get(i) + "' but arg type was '" + arg + "'");
						}
						Function<Object, String> toString = partToString.get(i);
						dst.append(toString != null ? toString.apply(arg) : arg.toString());
					}
					varIdx++;
				}
			}
		} catch(IOException ioe) {
			throwUnchecked(ioe);
		}
	}


	public static boolean isSingleInt(StringTemplateBuilder strTmpl) {
		List<Map.Entry<Integer, Class<?>>> types = strTmpl.nonLiteralTypesByIndex;
		Class<?> type0 = null;
		return types.size() == 1 && ((type0 = types.get(0).getValue()) == Integer.class || type0 == Integer.TYPE);
	}


	public static <T> boolean isSingleType(StringTemplateBuilder strTmpl, Class<T> type) {
		List<Map.Entry<Integer, Class<?>>> types = strTmpl.nonLiteralTypesByIndex;
		return types.size() == 1 && (types.get(0).getValue() == type);
	}


	private static <T> RuntimeException throwUnchecked(T err) {
		return (RuntimeException)err;
	}

}
