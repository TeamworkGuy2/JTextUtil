package twg2.text.test.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StringChunkReader extends StringReader {
	private List<String> chunks;


	public StringChunkReader(Iterator<String> s) {
		this(toList(s));
	}


	public StringChunkReader(List<String> s) {
		super(join(s));
		this.chunks = new ArrayList<>(s);
	}


	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		if(chunks.size() > 0) {
			String chunk = chunks.get(0);
			if(chunk.length() > len) {
				String remaining = chunk.substring(len);
				chunks.set(0, remaining);
			}
			else /*if(chunk.length() <= len)*/ {
				len = chunk.length();
				chunks.remove(0);
			}
			System.arraycopy(chunk.toCharArray(), 0, cbuf, off, len);
			return len;
		}
		return -1;
	}


	private static List<String> toList(Iterator<String> strs) {
		List<String> chunks = new ArrayList<>();
		while(strs.hasNext()) {
			String chunk = strs.next();
			chunks.add(chunk);
		}
		return chunks;
	}


	private static String join(List<String> strs) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0, size = strs.size(); i < size; i++) {
			sb.append(strs.get(i));
		}
		return sb.toString();
	}

}
