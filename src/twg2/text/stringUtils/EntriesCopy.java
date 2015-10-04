package twg2.text.stringUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

/** Copy of methods from JDataUtility dataUtils.Entries
 * @author TeamworkGuy2
 * @since 2015-1-25
 */
// package-private
class EntriesCopy {

	/** Carry out a binary search on the keys in an array of {@link java.util.Map.Entry Map.Entries}
	 * @param a the map entry array to search
	 * @param key the key to search for
	 * @return the index of the matching key or {@code -(expected index) - 1} if the key
	 * was not found in the array
	 * @see Arrays#binarySearch(Object[], Object, Comparator)
	 */
	public static final <K extends Comparable<K>, V> int binarySearch(Map.Entry<K, V>[] a, K key) {
		int low = 0;
		int high = a.length-1;

		while(low <= high) {
			int mid = (low + high) >>> 1;
			Map.Entry<K, V> midVal = a[mid];
			int compare = midVal.getKey().compareTo(key);

			if(compare < 0) {
				low = mid + 1;
			}
			else if(compare > 0) {
				high = mid - 1;
			}
			else {
				return mid;
			}
		}
		return -(low + 1);
	}

}
