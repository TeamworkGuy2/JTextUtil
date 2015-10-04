package twg2.text.stringUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author TeamworkGuy2
 * @since 2015-0-0
 */
public class StringJoin {


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


	/**
	 * @see #join(String[], String)
	 */
	public static final String join(Object[] objs, String delimiter) {
		StringBuilder strB = new StringBuilder();
		join(objs, 0, objs.length, delimiter, strB);
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


	/** Join a collection of strings using a delimiter
	 * @see #join(Iterable, String)
	 * @see StringJoiner
	 */
	public static final String join(List<String> strs, String delimiter) {
		StringBuilder strB = new StringBuilder();
		join(strs, delimiter, strB);
		return strB.toString();
	}


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

}
