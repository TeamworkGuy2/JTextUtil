package twg2.text.stringUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;

/**
 * @author TeamworkGuy2
 * @since 2015-0-0
 */
public class StringJoin {

	// ==== String[] ====
	/** Join an array of strings using a delimiter
	 * @param strs the array of strings
	 * @param delimiter the delimiter to place between strings
	 * @return a string consisting of each of {@code strs} separated by {@code delimiter}
	 * @see StringJoiner
	 */
	public static final String join(String[] strs, String delimiter) {
		StringBuilder strB = new StringBuilder();
		join(strs, 0, strs.length, delimiter, strB);
		return strB.toString();
	}


	/** Join an array of strings using a delimiter
	 * @param strs the array of strings
	 * @param off the offset into {@code strs} at which start combining strings
	 * @param len the number of strings from {@code strs} to combine
	 * @param delimiter the delimiter to place between strings
	 * @return a string consisting of each of {@code strs} separated by {@code delimiter}
	 * @see StringJoiner
	 */
	public static final String join(String[] strs, int off, int len, String delimiter) {
		StringBuilder strB = new StringBuilder();
		join(strs, off, len, delimiter, strB);
		return strB.toString();
	}


	public static final void join(String[] strs, int off, int len, String delimiter, StringBuilder dst) {
		try {
			join(strs, off, len, delimiter, (Appendable)dst);
		} catch(IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}


	public static final void join(String[] strs, int off, int len, String delimiter, Appendable dst) throws IOException {
		int countTo = off + len - 1;
		if(countTo > -1) {
			for(int i = off; i < countTo; i++) {
				dst.append(strs[i]);
				dst.append(delimiter);
			}
			dst.append(strs[countTo]);
		}
	}


	// ==== List<String> ====
	/** Join a collection of strings using a delimiter
	 * @see #join(Iterable, String)
	 * @see StringJoiner
	 */
	public static final String join(List<String> strs, String delimiter) {
		StringBuilder strB = new StringBuilder();
		join(strs, delimiter, strB);
		return strB.toString();
	}


	public static final void join(List<String> strs, String delimiter, StringBuilder dst) {
		try {
			join(strs, delimiter, (Appendable)dst);
		} catch(IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}


	public static final void join(List<String> strs, String delimiter, Appendable dst) throws IOException {
		List<String> strsList = (List<String>)strs;
		int countTo = strsList.size() - 1;
		if(countTo > -1) {
			for(int i = 0; i < countTo; i++) {
				dst.append(strsList.get(i));
				dst.append(delimiter);
			}
			dst.append(strsList.get(countTo));
		}
	}


	// ==== Iterable<String> ====
	/** Join a collection of strings using a delimiter
	 * @param strs the collection of strings
	 * @param delimiter the delimiter to place between strings
	 * @return a string consisting of each of {@code strs} separated by {@code delimiter}
	 * @see StringJoiner
	 */
	public static final String join(Iterable<String> strs, String delimiter) {
		StringBuilder strB = new StringBuilder();
		join(strs, delimiter, strB);
		return strB.toString();
	}


	public static final void join(Iterable<String> strs, String delimiter, StringBuilder dst) {
		try {
			join(strs, delimiter, (Appendable)dst);
		} catch(IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}


	public static final void join(Iterable<String> strs, String delimiter, Appendable dst) throws IOException {
		boolean firstLoop = true;
		for(String str : strs) {
			if(!firstLoop) {
				dst.append(delimiter);
			}
			dst.append(str);
			firstLoop = false;
		}
	}


	// ==== Miscellaneous ====
	public static final String repeat(char ch, int repeats) {
		char[] chs = new char[repeats];
		for(int i = 0; i < repeats; i++) {
			chs[i] = ch;
		}
		return new String(chs, 0, repeats);
	}


	public static final String repeat(String str, int repeats) {
		int strLen = str.length();
		char[] chs = new char[strLen * repeats];
		for(int i = 0; i < repeats; i++) {
			str.getChars(0, strLen, chs, i * strLen);
		}
		return new String(chs, 0, chs.length);
	}


	public static final void repeat(char ch, int repeats, Appendable dst) throws IOException {
		for(int i = 0; i < repeats; i++) {
			dst.append(ch);
		}
	}


	public static final void repeat(String str, int repeats, Appendable dst) throws IOException {
		for(int i = 0; i < repeats; i++) {
			dst.append(str);
		}
	}


	public static final String repeatJoin(String str, String joiner, int repeats) {
		int strLen = str.length();
		int joinerLen = joiner.length();
		int off = 0;
		if(repeats > 0) {
			char[] chs = new char[strLen * repeats + joinerLen * (repeats - 1)];
			for(int i = 0, size = repeats - 1; i < size; i++) {
				str.getChars(0, strLen, chs, off);
				joiner.getChars(0, joinerLen, chs, off + strLen);
				off += strLen + joinerLen;
			}
			str.getChars(0, strLen, chs, off);
			return new String(chs, 0, chs.length);
		}
		return "";
	}


	public static final String repeatJoin(String str, char joiner, int repeats) {
		int strLen = str.length();
		int off = 0;
		if(repeats > 0) {
			char[] chs = new char[strLen * repeats + (repeats - 1)];
			for(int i = 0, size = repeats - 1; i < size; i++) {
				str.getChars(0, strLen, chs, off);
				chs[off + strLen] = joiner;
				off += strLen + 1;
			}
			str.getChars(0, strLen, chs, off);
			return new String(chs, 0, chs.length);
		}
		return "";
	}


	public static final void repeatJoin(String str, String joiner, int repeats, Appendable dst) throws IOException {
		if(repeats > 0) {
			for(int i = 0, size = repeats - 1; i < size; i++) {
				dst.append(str);
				dst.append(joiner);
			}
			dst.append(str);
		}
	}


	public static final void repeatJoin(String str, char joiner, int repeats, Appendable dst) throws IOException {
		if(repeats > 0) {
			for(int i = 0, size = repeats - 1; i < size; i++) {
				dst.append(str);
				dst.append(joiner);
			}
			dst.append(str);
		}
	}




	/** Static methods for joining groups of objects
	 * @author TeamworkGuy2
	 * @since 2015-11-26
	 */
	public static class Objects {

		// ==== Object[] ====
		/** Joins an array of objects with a delimiter using {@link Object#toString() toString()}.
		 * Null values are converted to {@code "null"}
		 * @see StringJoin#join(String[], String)
		 */
		public static final String join(Object[] objs, String delimiter) {
			StringBuilder strB = new StringBuilder();
			join(objs, 0, objs.length, delimiter, strB);
			return strB.toString();
		}


		public static final void join(Object[] objs, int off, int len, String delimiter, StringBuilder dst) {
			try {
				join(objs, off, len, delimiter, (Appendable)dst);
			} catch(IOException ioe) {
				throw new UncheckedIOException(ioe);
			}
		}


		public static final void join(Object[] objs, int off, int len, String delimiter, Appendable dst) throws IOException {
			int countTo = off + len - 1;
			if(countTo > -1) {
				for(int i = off; i < countTo; i++) {
					dst.append(objs[i] != null ? objs[i].toString() : "null");
					dst.append(delimiter);
				}
				dst.append(objs[countTo] != null ? objs[countTo].toString() : "null");
			}
		}


		// ==== List<Object> ====
		/** Joins a list of objects with a delimiter using the default {@link Object#toString() toString()}.
		 * Null values are converted to {@code "null"}
		 * @see StringJoin#join(List, String)
		 */
		public static final String join(List<? extends Object> objs, String delimiter) {
			StringBuilder strB = new StringBuilder();
			join(objs, delimiter, strB);
			
			return strB.toString();
		}


		public static final void join(List<? extends Object> objs, String delimiter, StringBuilder dst) {
			try {
				join(objs, delimiter, (Appendable)dst);
			} catch(IOException ioe) {
				throw new UncheckedIOException(ioe);
			}
		}


		public static final void join(List<? extends Object> objs, String delimiter, Appendable dst) throws IOException {
			List<? extends Object> strsList = (List<? extends Object>)objs;
			int countTo = strsList.size() - 1;
			if(countTo > -1) {
				for(int i = 0; i < countTo; i++) {
					Object obj = strsList.get(i);
					dst.append(obj != null ? obj.toString() : "null");
					dst.append(delimiter);
				}
				Object lastObj = strsList.get(countTo);
				dst.append(lastObj != null ? lastObj.toString() : "null");
			}
		}


		// ==== Iterable<Object> ====
		/** Joins a group of objects with a delimiter using the default {@link Object#toString() toString()}.
		 * Null values are converted to {@code "null"}
		 * @see StringJoin#join(Iterable, String)
		 */
		public static final String join(Iterable<? extends Object> objs, String delimiter) {
			StringBuilder strB = new StringBuilder();
			join(objs, delimiter, strB);
			return strB.toString();
		}


		public static final void join(Iterable<? extends Object> objs, String delimiter, StringBuilder dst) {
			try {
				join(objs, delimiter, (Appendable)dst);
			} catch(IOException ioe) {
				throw new UncheckedIOException(ioe);
			}
		}


		public static final void join(Iterable<? extends Object> objs, String delimiter, Appendable dst) throws IOException {
			boolean firstLoop = true;
			for(Object obj : objs) {
				if(!firstLoop) {
					dst.append(delimiter);
				}
				dst.append(obj != null ? obj.toString() : "null");
				firstLoop = false;
			}
		}

	}




	/** Join collections of objects using a string delimiter and custom toString functions
	 * @author TeamworkGuy2
	 * @since 2015-12-13
	 */
	public static class Func {

		// ==== custom Function func ====
		/** Joins a group of objects with a delimiter using a custom toString function, which takes an object and returns its string representation.
		 * Null values are converted to {@code "null"}
		 * @see StringJoin#join(List, String)
		 */
		public static final <T extends Object> String join(Iterable<T> objs, String delimiter, Function<T, String> toString) {
			StringBuilder strB = new StringBuilder();
			join(objs, delimiter, strB, toString);
			return strB.toString();
		}


		public static final <T extends Object> void join(Iterable<T> objs, String delimiter, StringBuilder dst, Function<T, String> toString) {
			try {
				join(objs, delimiter, (Appendable)dst, toString);
			} catch(IOException ioe) {
				throw new UncheckedIOException(ioe);
			}
		}


		public static final <T extends Object> void join(Iterable<T> objs, String delimiter, Appendable dst, Function<T, String> toString) throws IOException {
			boolean firstLoop = true;
			for(T obj : objs) {
				if(!firstLoop) {
					dst.append(delimiter);
				}
				dst.append(obj != null ? toString.apply(obj) : "null");
				firstLoop = false;
			}
		}


		public static final <T extends Object> String joinManualNulls(Iterable<T> objs, String delimiter, Function<T, String> toString) {
			StringBuilder strB = new StringBuilder();
			joinManualNulls(objs, delimiter, strB, toString);
			return strB.toString();
		}


		public static final <T extends Object> void joinManualNulls(Iterable<T> objs, String delimiter, StringBuilder dst, Function<T, String> toString) {
			try {
				joinManualNulls(objs, delimiter, (Appendable)dst, toString);
			} catch(IOException ioe) {
				throw new UncheckedIOException(ioe);
			}
		}


		public static final <T extends Object> void joinManualNulls(Iterable<T> objs, String delimiter, Appendable dst, Function<T, String> toString) throws IOException {
			boolean firstLoop = true;
			for(T obj : objs) {
				if(!firstLoop) {
					dst.append(delimiter);
				}
				dst.append(toString.apply(obj));
				firstLoop = false;
			}
		}

	}

}
