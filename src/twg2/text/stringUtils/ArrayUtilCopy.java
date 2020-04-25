package twg2.text.stringUtils;

/** Copy of methods from JArrays twg2.arrays.ArrayUtil
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
		for(int i = 0, size = ary.length; i < size; i++) {
			if(ary[i] == (value)) { return i; }
		}
		return -1;
	}

}
