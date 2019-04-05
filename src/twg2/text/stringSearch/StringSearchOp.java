package twg2.text.stringSearch;

/**
 * @author TeamworkGuy2
 * @since 2016-08-07
 */
public enum StringSearchOp {
	EQUALS() {
		@Override
		public boolean test(String a, String b) { return a != null ? a.equals(b) : b == null; }
	},
	CONTAINS() {
		@Override
		public boolean test(String a, String b) { return a != null ? a.contains(b) : b == null; }
	},
	STARTS_WITH() {
		@Override
		public boolean test(String a, String b) { return a != null ? a.startsWith(b) : b == null; }
	},
	ENDS_WITH() {
		@Override
		public boolean test(String a, String b) { return a != null ? a.endsWith(b) : b == null; }
	};


	public abstract boolean test(String a, String b);

}
