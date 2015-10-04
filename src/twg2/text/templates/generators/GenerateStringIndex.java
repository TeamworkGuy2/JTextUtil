package twg2.text.templates.generators;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.AbstractMap;
import java.util.Arrays;

import twg2.text.templates.MultiTypeInfo;
import twg2.text.templates.RandomAccessTypeInfo;
import codeTemplate.render.TemplateFilesIo;
import codeTemplate.render.TemplateRenders;

/**
 * @author TeamworkGuy2
 * @since 2015-2-1
 */
public class GenerateStringIndex {


	public static final void generateStringIndex() throws IOException {
		RandomAccessTypeInfo charArrayType = new RandomAccessTypeInfo();
		charArrayType.type = "char[]";
		charArrayType.getLength = ".length";
		charArrayType.getElement = "[";
		charArrayType.getElementEnd = "]";
		RandomAccessTypeInfo stringType = new RandomAccessTypeInfo();
		stringType.type = "String";
		stringType.getLength = ".length()";
		stringType.getElement = ".charAt(";
		stringType.getElementEnd = ")";

		MultiTypeInfo<RandomAccessTypeInfo> info = new MultiTypeInfo<>();
		info.className = "StringIndex";
		info.importStatements = Arrays.asList("java.util.List", "java.util.RandomAccess");
		info.packageName = "twg2.text.stringUtils";

		info.types.add(new MultiTypeInfo.MultiType<>(stringType, stringType));
		info.types.add(new MultiTypeInfo.MultiType<>(stringType, charArrayType));
		info.types.add(new MultiTypeInfo.MultiType<>(charArrayType, stringType));
		info.types.add(new MultiTypeInfo.MultiType<>(charArrayType, charArrayType));

		Writer out = new FileWriter(TemplateFilesIo.getDefaultInst().getSrcRelativePath(info).toFile());
		TemplateRenders.writeWithParameters("src/twg2.text.templates/TStringIndex.stg", "TStringIndex", out,
				new AbstractMap.SimpleImmutableEntry<>("var", info),
				new AbstractMap.SimpleImmutableEntry<>("singleTypes", Arrays.asList(stringType, charArrayType)));
		out.close();
	}


	public static void main(String[] args) throws IOException {
		generateStringIndex();
	}

}