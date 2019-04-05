package twg2.text.stringUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;

/**
 * @author TeamworkGuy2
 * @since 2015-0-0
 */
public class StringJoin {

	private StringJoin() { throw new AssertionError("cannot instantiate static class StringJoin"); }


	// ==== Object[] ====

	/** Joins an array of objects with a delimiter using {@link Object#toString() toString()}.
	 * Null values are converted to {@code "null"}
	 * @param objs the array of objects
	 * @param delimiter the delimiter to place between object strings
	 * @return a string consisting of each of {@code objs} coerced to string using {@code toString()} separated by {@code delimiter}
	 * @see StringJoiner
	 */
	public static <T extends Object> String join(T[] objs, String delimiter) {
		StringBuilder sb = new StringBuilder();
		join(objs, 0, objs.length, delimiter, sb);
		return sb.toString();
	}


	/** Join an array of objects with a delimiter between each.
	 * Null values are converted to {@code "null"}
	 * @param objs the array of objects
	 * @param off the offset into {@code objs} at which start joining strings
	 * @param len the number of values from {@code objs} to join
	 * @param delimiter the delimiter to place between object strings
	 * @return a string consisting of each of {@code objs} coerced to string using {@code toString()} separated by {@code delimiter}
	 * @see StringJoiner
	 */
	public static <T extends Object> String join(T[] objs, int off, int len, String delimiter) {
		StringBuilder sb = new StringBuilder();
		join(objs, off, len, delimiter, sb);
		return sb.toString();
	}


	public static <T extends Object> void join(T[] objs, int off, int len, String delimiter, StringBuilder dst) {
		try {
			join(objs, off, len, delimiter, (Appendable)dst);
		} catch(IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}


	public static <T extends Object> void join(T[] objs, int off, int len, String delimiter, Appendable dst) throws IOException {
		int countTo = off + len - 1;
		if(countTo > -1) {
			for(int i = off; i < countTo; i++) {
				T obj = objs[i];
				dst.append(obj != null ? obj.toString() : "null");
				dst.append(delimiter);
			}
			dst.append(objs[countTo] != null ? objs[countTo].toString() : "null");
		}
	}


	// ==== List<? extends Object> ====

	/** Joins a list of objects with a delimiter using {@link Object#toString() toString()} to coerce the objects to strings.
	 * Null values are converted to {@code "null"}
	 * @see StringJoin#join(Iterable, String)
	 * @see StringJoiner
	 */
	public static String join(List<? extends Object> objs, String delimiter) {
		return join(objs, 0, objs.size(), delimiter);
	}


	public static String join(List<? extends Object> objs, int off, int len, String delimiter) {
		StringBuilder sb = new StringBuilder();
		join(objs, off, len, delimiter, sb);
		return sb.toString();
	}


	public static void join(List<? extends Object> objs, int off, int len, String delimiter, StringBuilder dst) {
		try {
			join(objs, off, len, delimiter, (Appendable)dst);
		} catch(IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}


	public static void join(List<? extends Object> objs, int off, int len, String delimiter, Appendable dst) throws IOException {
		List<? extends Object> strsList = (List<? extends Object>)objs;
		int countTo = off + len - 1;
		if(len > 0) {
			for(int i = off; i < countTo; i++) {
				Object obj = strsList.get(i);
				dst.append(obj != null ? obj.toString() : "null");
				dst.append(delimiter);
			}
			Object lastObj = strsList.get(countTo);
			dst.append(lastObj != null ? lastObj.toString() : "null");
		}
	}


	// ==== Iterable<? extends Object> ====

	/** Joins a group of objects with a delimiter using {@link Object#toString() toString()} to coerce the objects to strings.
	 * Null values are converted to {@code "null"}
	 * @param objs the collection of strings
	 * @return a string consisting of each of {@code objs} coerced to string using {@code toString()} separated by {@code delimiter}
	 * @see StringJoin#join(Iterable, String)
	 * @see StringJoiner
	 */
	public static String join(Iterable<? extends Object> objs, String delimiter) {
		StringBuilder sb = new StringBuilder();
		join(objs, delimiter, sb);
		return sb.toString();
	}


	public static String join(Iterable<? extends Object> objs, int off, int len, String delimiter) {
		StringBuilder sb = new StringBuilder();
		join(objs, off, len, delimiter, sb);
		return sb.toString();
	}


	public static void join(Iterable<? extends Object> objs, String delimiter, StringBuilder dst) {
		try {
			join(objs, delimiter, (Appendable)dst);
		} catch(IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}


	public static void join(Iterable<? extends Object> objs, int off, int len, String delimiter, StringBuilder dst) {
		try {
			join(objs, off, len, delimiter, (Appendable)dst);
		} catch(IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}


	public static void join(Iterable<? extends Object> objs, String delimiter, Appendable dst) throws IOException {
		boolean firstLoop = true;
		for(Object obj : objs) {
			if(!firstLoop) {
				dst.append(delimiter);
			}
			dst.append(obj != null ? obj.toString() : "null");
			firstLoop = false;
		}
	}


	public static void join(Iterable<? extends Object> objs, int off, int len, String delimiter, Appendable dst) throws IOException {
		join(objs.iterator(), off, len, delimiter, dst);
	}


	// ==== Iterator<? extends Object> ====

	/** Joins a group of objects with a delimiter using the default {@link Object#toString() toString()} to coerce the objects to strings.
	 * Null values are converted to {@code "null"}
	 * @param objs the collection of strings
	 * @return a string consisting of each of {@code objs} coerced to string using {@code toString()} separated by {@code delimiter}
	 * @see StringJoin#join(Iterable, String)
	 * @see StringJoiner
	 */
	public static String join(Iterator<? extends Object> objs, String delimiter) {
		StringBuilder sb = new StringBuilder();
		join(objs, delimiter, sb);
		return sb.toString();
	}


	public static String join(Iterator<? extends Object> objs, int off, int len, String delimiter) {
		StringBuilder sb = new StringBuilder();
		join(objs, off, len, delimiter, sb);
		return sb.toString();
	}


	public static void join(Iterator<? extends Object> objs, String delimiter, StringBuilder dst) {
		try {
			join(objs, delimiter, (Appendable)dst);
		} catch(IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}


	public static void join(Iterator<? extends Object> objs, int off, int len, String delimiter, StringBuilder dst) {
		try {
			join(objs, off, len, delimiter, (Appendable)dst);
		} catch(IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}


	public static void join(Iterator<? extends Object> objs, String delimiter, Appendable dst) throws IOException {
		boolean firstLoop = true;
		while(objs.hasNext()) {
			Object obj = objs.next();
			if(!firstLoop) {
				dst.append(delimiter);
			}
			dst.append(obj != null ? obj.toString() : "null");
			firstLoop = false;
		}
	}


	public static void join(Iterator<? extends Object> objs, int off, int len, String delimiter, Appendable dst) throws IOException {
		boolean firstLoop = true;
		int i = 0;
		while(objs.hasNext() && i < off) { objs.next(); i++; }

		int size = off + len;
		while(objs.hasNext() && i < size) {
			Object obj = objs.next();
			if(!firstLoop) {
				dst.append(delimiter);
			}
			dst.append(obj != null ? obj.toString() : "null");
			firstLoop = false;
			i++;
		}
	}


	// ==== custom toString func - Join collections of objects using a string delimiter and custom toString functions ====

	/** Joins a group of objects with a delimiter using a custom toString function, which takes an object and returns its string representation.
	 * Null values are converted to {@code "null"}
	 * @see StringJoin#join(List, String)
	 */
	public static <T extends Object> String join(Iterable<T> objs, String delimiter, Function<T, String> toString) {
		StringBuilder sb = new StringBuilder();
		join(objs, delimiter, sb, toString);
		return sb.toString();
	}


	public static <T extends Object> void join(Iterable<T> objs, String delimiter, StringBuilder dst, Function<T, String> toString) {
		try {
			join(objs, delimiter, (Appendable)dst, toString);
		} catch(IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}


	public static <T extends Object> void join(Iterable<T> objs, String delimiter, Appendable dst, Function<T, String> toString) throws IOException {
		boolean firstLoop = true;
		for(T obj : objs) {
			if(!firstLoop) {
				dst.append(delimiter);
			}
			dst.append(obj != null ? toString.apply(obj) : "null");
			firstLoop = false;
		}
	}


	public static <T extends Object> String joinManualNulls(Iterable<T> objs, String delimiter, Function<T, String> toString) {
		StringBuilder sb = new StringBuilder();
		joinManualNulls(objs, delimiter, sb, toString);
		return sb.toString();
	}


	public static <T extends Object> void joinManualNulls(Iterable<T> objs, String delimiter, StringBuilder dst, Function<T, String> toString) {
		try {
			joinManualNulls(objs, delimiter, (Appendable)dst, toString);
		} catch(IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}


	public static <T extends Object> void joinManualNulls(Iterable<T> objs, String delimiter, Appendable dst, Function<T, String> toString) throws IOException {
		boolean firstLoop = true;
		for(T obj : objs) {
			if(!firstLoop) {
				dst.append(delimiter);
			}
			dst.append(toString.apply(obj));
			firstLoop = false;
		}
	}


	// ==== Repeat and repeatJoin ====
	public static String repeat(char ch, int repeats) {
		char[] chs = new char[repeats];
		for(int i = 0; i < repeats; i++) {
			chs[i] = ch;
		}
		return new String(chs, 0, repeats);
	}


	public static String repeat(String str, int repeats) {
		int strLen = str.length();
		char[] chs = new char[strLen * repeats];
		for(int i = 0; i < repeats; i++) {
			str.getChars(0, strLen, chs, i * strLen);
		}
		return new String(chs, 0, chs.length);
	}


	public static void repeat(char ch, int repeats, Appendable dst) throws IOException {
		for(int i = 0; i < repeats; i++) {
			dst.append(ch);
		}
	}


	public static void repeat(String str, int repeats, Appendable dst) throws IOException {
		for(int i = 0; i < repeats; i++) {
			dst.append(str);
		}
	}


	public static String repeatJoin(String str, String separator, int repeats) {
		int strLen = str.length();
		int joinerLen = separator.length();
		int off = 0;
		if(repeats > 0) {
			char[] chs = new char[strLen * repeats + joinerLen * (repeats - 1)];
			for(int i = 0, size = repeats - 1; i < size; i++) {
				str.getChars(0, strLen, chs, off);
				separator.getChars(0, joinerLen, chs, off + strLen);
				off += strLen + joinerLen;
			}
			str.getChars(0, strLen, chs, off);
			return new String(chs, 0, chs.length);
		}
		return "";
	}


	public static String repeatJoin(String str, char separator, int repeats) {
		int strLen = str.length();
		int off = 0;
		if(repeats > 0) {
			char[] chs = new char[strLen * repeats + (repeats - 1)];
			for(int i = 0, size = repeats - 1; i < size; i++) {
				str.getChars(0, strLen, chs, off);
				chs[off + strLen] = separator;
				off += strLen + 1;
			}
			str.getChars(0, strLen, chs, off);
			return new String(chs, 0, chs.length);
		}
		return "";
	}


	public static void repeatJoin(String str, String separator, int repeats, Appendable dst) throws IOException {
		if(repeats > 0) {
			for(int i = 0, size = repeats - 1; i < size; i++) {
				dst.append(str);
				dst.append(separator);
			}
			dst.append(str);
		}
	}


	public static void repeatJoin(String str, char separator, int repeats, Appendable dst) throws IOException {
		if(repeats > 0) {
			for(int i = 0, size = repeats - 1; i < size; i++) {
				dst.append(str);
				dst.append(separator);
			}
			dst.append(str);
		}
	}

}
