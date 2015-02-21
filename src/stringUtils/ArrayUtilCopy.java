package stringUtils;

/** Copy of methods from JCollectionUtility arrayUtils.ArrayUtil
 * @author TeamworkGuy2
 * @since 2015-1-24
 */
// package-private
class ArrayUtilCopy {

	/** Search for a matching char in an array of chars
	 * @param ary the array of values to search
	 * @param value the value to search for
	 * @return the index of the matching value, or -1 if the {@code value} could not be found
	 */
	public static final int indexOf(char[] ary, char value) {
		return indexOf(ary, 0, ary.length, value);
	}


	/** Search for a matching char in an array of chars
	 * @param ary the array of values to search
	 * @param off the offset into {@code ary} at which to start searching for values
	 * @param len the number of values to search for starting at {@code off} in {@code ary}
	 * @param value the value to search for
	 * @return the index of the matching value, or -1 if the {@code value} could not be found
	 */
	public static final int indexOf(char[] ary, int off, int len, char value) {
		for(int i = off, size = off + len; i < size; i++) {
			if(ary[i] == (value)) { return i; }
		}
		return -1;
	}

}
