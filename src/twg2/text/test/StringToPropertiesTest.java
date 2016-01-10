package twg2.text.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import twg2.text.stringUtils.StringToProperties;

/**
 * @author TeamworkGuy2
 * @since 2015-8-3
 */
public class StringToPropertiesTest {


	public void writePropertiesTestFile(Properties props, String fileName) throws IOException {
		try(FileWriter fileWriter = new FileWriter(new File(fileName))) {
			writePropertiesTo(props, fileWriter, true);
		}
	}


	public void writePropertiesTo(Properties props, Writer writer, boolean closeWhenDone) throws IOException {
		try {
			props.store(writer, null);
		} finally {
			if(closeWhenDone && writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					throw new UncheckedIOException(e);
				}
			}
		}
	}


	public Properties readPropertiesTestFile(String fileName) throws FileNotFoundException, IOException {
		Properties props = new Properties();

		try(FileReader fileReader = new FileReader(new File(fileName))) {
			readPropertiesFromTo(fileReader, props, true);
		}
		return props;
	}


	public void readPropertiesFromTo(Reader reader, Properties props, boolean closeWhenDone) throws IOException {
		try {
			props.load(reader);
		} finally {
			if(closeWhenDone && reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new UncheckedIOException(e);
				}
			}
		}
	}


	public void readPropertiesCustomFromTo(Reader reader, Properties props, boolean closeWhenDone) throws IOException {
		try {
			List<Map.Entry<String, String>> dst = new ArrayList<>();
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

			for(Map.Entry<String, String> entry : dst) {
				props.setProperty(entry.getKey(), entry.getValue());
			}
		} finally {
			if(closeWhenDone && reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new UncheckedIOException(e);
				}
			}
		}
	}


	public Properties createTestProperties() {
		Properties props = new Properties();
		props.setProperty("indexStart", "2");
		props.setProperty("indexCount", "3");
		props.setProperty("2", "two");
		props.setProperty("3", "three");
		props.setProperty("4", "four");
		props.setProperty("\"stop,star\"", "\\\",=\r\nabc");
		props.setProperty("key=\"value\"", "pair");

		return props;
	}


	@Test
	public void testReadWriteProps() throws IOException {
		Properties initialProps = createTestProperties();
		String fileName = "testProps.properties";

		writePropertiesTestFile(initialProps, fileName);

		StringWriter strWriterCustom = new StringWriter();
		writePropertiesTo(initialProps, strWriterCustom, false);

		Properties defaultResProps = readPropertiesTestFile(fileName);

		String strPropsCustom = strWriterCustom.toString();
		StringReader strReaderCustom = new StringReader(strPropsCustom);
		Properties customResProps = new Properties();
		readPropertiesCustomFromTo(strReaderCustom, customResProps, false);

		Assert.assertEquals(defaultResProps, customResProps);
	}

}
