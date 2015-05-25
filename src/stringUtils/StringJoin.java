package stringUtils;

import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
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


	/** Join a list of strings using a delimiter
	 * @param strs the list of strings
	 * @param delimiter the delimiter to place between strings
	 * @return a string consisting of each of {@code strs} separated by {@code delimiter}
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
	public static final String join(Collection<String> strs, String delimiter) {
		StringBuilder strB = new StringBuilder();
		join(strs, delimiter, strB);
		return strB.toString();
	}


	public static final void join(String[] strs, int off, int len, String delimiter, StringBuilder dst) {
		boolean firstLoop = true;
		for(int i = off, size = off + len; i < size; i++) {
			if(!firstLoop) {
				dst.append(delimiter);
			}
			dst.append(strs[i]);
			firstLoop = false;
		}
	}


	public static final void join(Object[] objs, int off, int len, String delimiter, StringBuilder dst) {
		boolean firstLoop = true;
		for(int i = off, size = off + len; i < size; i++) {
			if(!firstLoop) {
				dst.append(delimiter);
			}
			dst.append(objs[i]);
			firstLoop = false;
		}
	}


	public static final void join(List<String> strs, String delimiter, StringBuilder dst) {
		boolean firstLoop = true;
		if(strs instanceof RandomAccess) {
			for(int i = 0, size = strs.size(); i < size; i++) {
				if(!firstLoop) {
					dst.append(delimiter);
				}
				dst.append(strs.get(i));
				firstLoop = false;
			}
		}
		else {
			join((Collection<String>)strs, delimiter, dst);
		}
	}


	public static final void join(Collection<String> strs, String delimiter, StringBuilder dst) {
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
