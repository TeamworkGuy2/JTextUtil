package stringUtils;


/** Check if a programming identifier name conforms to a specific case format.<br>
 * 
 * @author TeamworkGuy2
 * @since 2014-12-10
 */
public final class StringCase {
	private static final char underscore = '_';


	private StringCase() { throw new AssertionError("cannot instantiate static class StringCase"); }


	/** Check if a string is in {@code 'camelCase'} format
	 * @param str the string to check
	 * @return true if the string is camelCase, false otherwise
	 */
	public static final boolean isCamelCase(String str) {
		return str.length() > 0 && Character.isLowerCase(str.charAt(0)) && str.indexOf(underscore) == -1;
	}


	/** Check if a string is in {@code 'TitleCase'} format
	 * @param str the string to check
	 * @return true if the string is TitleCase, false otherwise
	 */
	public static final boolean isTitleCase(String str) {
		return str.length() > 0 && Character.isUpperCase(str.charAt(0)) && str.indexOf(underscore) == -1;
	}


	/** Check if a string is in {@code 'under_score_lower_case'} format
	 * @param str the string to check
	 * @return true if the string is under_score_lower_case, false otherwise
	 */
	public static final boolean isUnderscoreLowerCase(String str) {
		if(str.length() == 0) {
			return false;
		}

		for(int i = 0, size = str.length(); i < size; i++) {
			if(Character.isUpperCase(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}


	/** Check if a string is in {@code 'Under_Score_Title_Case'} format
	 * @param str the string to check
	 * @return true if the string is Under_Score_Title_Case, false otherwise
	 */
	public static final boolean isUnderscoreTitleCase(String str) {
		if(str.length() == 0 || Character.isLowerCase(str.charAt(0))) {
			return false;
		}

		for(int i = 1, size = str.length(); i < size; i++) {
			if(Character.isUpperCase(str.charAt(i)) && str.charAt(i - 1) != underscore) {
				return false;
			}
		}
		return true;
	}


	/** Convert a string to {@code 'camelCase'}
	 * @param str the string to convert
	 * @return the camelCase version of the string
	 */
	public static final String toCamelCase(String str) {
		if(isCamelCase(str)) {
			return str;
		}
		else if(isTitleCase(str)) {
			StringBuilder strB = new StringBuilder(str);
			strB.setCharAt(0, Character.toLowerCase(str.charAt(0)));
			return strB.toString();
		}
		else if(isUnderscoreLowerCase(str)) {
			StringBuilder strB = new StringBuilder();
			char prevChar = str.charAt(0);
			strB.append(prevChar);
			for(int i = 1, size = str.length(); i < size; i++) {
				char ch = str.charAt(i);
				if(prevChar == underscore) {
					strB.setLength(strB.length() - 1);
					ch = Character.toUpperCase(ch);
				}
				prevChar = ch;
				strB.append(ch);
			}
			if(strB.length() > 1) {
				char lastChar = strB.charAt(strB.length() - 1);
				if(lastChar == underscore) {
					strB.setLength(strB.length() - 1);
				}
			}
			return strB.toString();
		}
		else if(isUnderscoreTitleCase(str)) {
			StringBuilder strB = StringReplace.replace(new StringBuilder(str), 0, "_", "");
			strB.setCharAt(0, Character.toLowerCase(strB.charAt(0)));
			return strB.toString();
		}
		else {
			throw new IllegalArgumentException("unknown string case of string '" + str + "'");
		}
	}


	/** Convert a string to {@code 'TitleCase'}
	 * @param str the string to convert
	 * @return the TitleCase version of the string
	 */
	public static final String toTitleCase(String str) {
		if(isCamelCase(str)) {
			StringBuilder strB = new StringBuilder(str);
			strB.setCharAt(0, Character.toUpperCase(str.charAt(0)));
			return strB.toString();
		}
		else if(isTitleCase(str)) {
			return str;
		}
		else if(isUnderscoreLowerCase(str)) {
			StringBuilder strB = new StringBuilder();
			strB.append(Character.toUpperCase(str.charAt(0)));
			char prevChar = 0;
			for(int i = 1, size = str.length(); i < size; i++) {
				char ch = str.charAt(i);
				if(prevChar == underscore) {
					strB.setLength(strB.length() - 1);
					ch = Character.toUpperCase(ch);
				}
				prevChar = ch;
				strB.append(ch);
			}
			if(strB.length() > 1) {
				char lastChar = strB.charAt(strB.length() - 1);
				if(lastChar == underscore) {
					strB.setLength(strB.length() - 1);
				}
			}
			return strB.toString();
		}
		else if(isUnderscoreTitleCase(str)) {
			return StringReplace.replace(str, "_", "");
		}
		else {
			throw new IllegalArgumentException("unknown string case of string '" + str + "'");
		}
	}


	/** Convert a string to {@code 'under_score_lower_case'}
	 * @param str the string to convert
	 * @return the under_score_lower_case version of the string
	 */
	public static final String toUnderscoreLowerCase(String str) {
		if(isCamelCase(str)) {
			StringBuilder strB = new StringBuilder();
			for(int i = 0, size = str.length(); i < size; i++) {
				char ch = str.charAt(i);
				if(Character.isUpperCase(ch)) {
					strB.append(underscore);
					ch = Character.toLowerCase(ch);
				}
				strB.append(ch);
			}
			return strB.toString();
		}
		else if(isTitleCase(str)) {
			StringBuilder strB = new StringBuilder();
			strB.append(Character.toLowerCase(str.charAt(0)));
			for(int i = 1, size = str.length(); i < size; i++) {
				char ch = str.charAt(i);
				if(Character.isUpperCase(ch)) {
					strB.append(underscore);
					ch = Character.toLowerCase(ch);
				}
				strB.append(ch);
			}
			return strB.toString();
		}
		else if(isUnderscoreLowerCase(str)) {
			return str;
		}
		else if(isUnderscoreTitleCase(str)) {
			StringBuilder strB = new StringBuilder();
			char prevChar = str.charAt(0);
			strB.append(Character.toLowerCase(prevChar));
			for(int i = 1, size = str.length(); i < size; i++) {
				char ch = str.charAt(i);
				if(prevChar == underscore) {
					ch = Character.toLowerCase(ch);
				}
				prevChar = ch;
				strB.append(ch);
			}
			return strB.toString();
		}
		else {
			throw new IllegalArgumentException("unknown string case of string '" + str + "'");
		}
	}


	/** Convert a string to {@code 'Under_Score_Title_Case'}
	 * @param str the string to convert
	 * @return the Under_Score_Title_Case version of the string
	 */
	public static final String toUnderscoreTitleCase(String str) {
		if(isCamelCase(str)) {
			StringBuilder strB = new StringBuilder();
			strB.append(Character.toUpperCase(str.charAt(0)));
			for(int i = 1, size = str.length(); i < size; i++) {
				char ch = str.charAt(i);
				if(Character.isUpperCase(ch)) {
					strB.append(underscore);
				}
				strB.append(ch);
			}
			return strB.toString();
		}
		else if(isTitleCase(str)) {
			StringBuilder strB = new StringBuilder();
			strB.append(str.charAt(0));
			for(int i = 1, size = str.length(); i < size; i++) {
				char ch = str.charAt(i);
				if(Character.isUpperCase(ch)) {
					strB.append(underscore);
				}
				strB.append(ch);
			}
			return strB.toString();
		}
		else if(isUnderscoreLowerCase(str)) {
			StringBuilder strB = new StringBuilder();
			char prevChar = str.charAt(0);
			strB.append(Character.toUpperCase(prevChar));
			for(int i = 1, size = str.length(); i < size; i++) {
				char ch = str.charAt(i);
				if(prevChar == underscore) {
					ch = Character.toUpperCase(ch);
				}
				prevChar = ch;
				strB.append(ch);
			}
			return strB.toString();
		}
		else if(isUnderscoreTitleCase(str)) {
			return str;
		}
		else {
			throw new IllegalArgumentException("unknown string case of string '" + str + "'");
		}
	}

}
