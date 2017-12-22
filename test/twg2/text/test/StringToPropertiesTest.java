package twg2.text.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import twg2.text.stringUtils.StringToProperties;

/**
 * @author TeamworkGuy2
 * @since 2015-8-3
 */
public class StringToPropertiesTest {


	public void writePropertiesJavaTo(Properties props, Writer writer) throws IOException {
		props.store(writer, null);
	}


	public Properties readPropertiesJavaFromTo(Reader reader) throws IOException {
		Properties props = new Properties();
		props.load(reader);
		return props;
	}


	public void readPropertiesCustomFromTo(Reader reader, Properties props) throws IOException {
		List<Entry<String, String>> dst = new ArrayList<>();
		List<String> dstComments = new ArrayList<>();

		BufferedReader bufReader = new BufferedReader(reader);
		List<String> lines = new ArrayList<>();
		while(true) {
			String line = bufReader.readLine();
			if(line == null) {
				break;
			}
			lines.add(line);
		}

		StringToProperties.loadKeyValueStrings(lines, dst, dstComments);

		for(Entry<String, String> entry : dst) {
			props.setProperty(entry.getKey(), entry.getValue());
		}
	}


	public Properties createTestProperties() {
		Properties props = new Properties();
		props.setProperty("indexStart", "2");
		props.setProperty("indexCount", "3");
		props.setProperty("2", "two");
		props.setProperty("3", "\u0007\u2460");
		props.setProperty("4", "four!");
		props.setProperty("\"stop,star\"", "\\\",=\r\nabc");
		props.setProperty("key=\"value\"", "pair");

		return props;
	}


	@Test
	public void readProps() throws IOException {
		Properties initialProps = createTestProperties();

		StringWriter propertiesJavaWriter = new StringWriter();
		writePropertiesJavaTo(initialProps, propertiesJavaWriter);
		String propertiesJavaStr = propertiesJavaWriter.toString();
		Properties defaultResProps = readPropertiesJavaFromTo(new StringReader(propertiesJavaStr));

		StringReader strReaderCustom = new StringReader(propertiesJavaStr);
		Properties customResProps = new Properties();
		readPropertiesCustomFromTo(strReaderCustom, customResProps);

		Assert.assertEquals(defaultResProps, customResProps);
	}


	@Test
	public void toEntries() throws IOException {
		Properties initialProps = createTestProperties();
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Collection<Entry<String, String>> propEntries = (Collection<Entry<String, String>>)(Collection)initialProps.entrySet();

		List<Entry<String, String>> customResProps = new ArrayList<>();
		StringToProperties._saveKeyValueStrings(propEntries, customResProps);

		int i = 0;
		for(Entry<Object, Object> prop : initialProps.entrySet()) {
			// create a 'Properties' contain a single prop, write it, compare it to the custom written value
			Properties initialProp = new Properties();
			initialProp.setProperty((String)prop.getKey(), (String)prop.getValue());
			StringWriter propertiesJavaWriter = new StringWriter();
			writePropertiesJavaTo(initialProp, propertiesJavaWriter);
			String propertiesJavaStr = propertiesJavaWriter.toString();
			String propJavaStr = trimTrailingNewlines(last(propertiesJavaStr.split("\n")));

			String propCustomStr = customResProps.get(i).getKey() + "=" + customResProps.get(i).getValue();

			Assert.assertEquals(propJavaStr, propCustomStr);
			i++;
		}
	}


	@SafeVarargs
	private final <T> T last(T... ts) {
		return ts != null && ts.length > 0 ? ts[ts.length - 1] : null;
	}


	private static final String trimTrailingNewlines(String str) {
		StringBuilder sb = new StringBuilder(str);

		char lastChar = sb.charAt(sb.length() - 1);
		while(lastChar == '\n' || lastChar == '\r') {
			sb.setLength(sb.length() - 1);
			lastChar = sb.charAt(sb.length() - 1);
		}

		return sb.toString();
	}

}
