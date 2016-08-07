package twg2.text.templates.generators;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

import org.stringtemplate.v4.ST;

import twg2.template.codeTemplate.render.STTemplates;
import twg2.template.codeTemplate.render.TemplateFilesIo;
import twg2.template.codeTemplate.render.TemplateImports;
import twg2.template.codeTemplate.render.TemplateRenderBuilder;
import twg2.text.templates.MultiTypeInfo;
import twg2.text.templates.RandomAccessTypeInfo;

/**
 * @author TeamworkGuy2
 * @since 2015-2-1
 */
public class GenerateStringIndex {
	private static String tmplDir = "src/twg2/text/templates/";
	private static String pkgName = "twg2.text.stringSearch";


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

		RandomAccessTypeInfo stringBuilderType = new RandomAccessTypeInfo();
		stringBuilderType.type = "StringBuilder";
		stringBuilderType.getLength = ".length()";
		stringBuilderType.getElement = ".charAt(";
		stringBuilderType.getElementEnd = ")";

		MultiTypeInfo<RandomAccessTypeInfo> info = new MultiTypeInfo<>();
		info.className = "StringIndex";
		info.importStatements = Arrays.asList("java.util.List", "java.util.RandomAccess");
		info.packageName = pkgName;

		info.types.add(new MultiTypeInfo.MultiType<>(stringType, stringType));
		info.types.add(new MultiTypeInfo.MultiType<>(stringType, charArrayType));
		info.types.add(new MultiTypeInfo.MultiType<>(charArrayType, stringType));
		info.types.add(new MultiTypeInfo.MultiType<>(charArrayType, charArrayType));
		//info.types.add(new MultiTypeInfo.MultiType<>(stringBuilderType, stringType));
		//info.types.add(new MultiTypeInfo.MultiType<>(stringBuilderType, charArrayType));

		Writer out = new FileWriter(TemplateFilesIo.getDefaultInst().getSrcRelativePath(info).toFile());
		ST stTmpl = STTemplates.fromFile(tmplDir + "TStringIndex.stg", "TStringIndex", TemplateImports.emptyInst());
		TemplateRenderBuilder.newInst()
				.addParam("var", info)
				.addParam("singleTypes", Arrays.asList(stringType, charArrayType, stringBuilderType))
				.writeDst(out)
				.render(stTmpl);
		out.close();
	}


	public static void main(String[] args) throws IOException {
		generateStringIndex();
	}

}
