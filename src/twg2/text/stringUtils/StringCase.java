package twg2.text.stringUtils;


/** Check if a programming identifier name conforms to a specific case format.<br>
 * 
 * @author TeamworkGuy2
 * @since 2014-12-10
 */
public final class StringCase {
	private static final char underscore = '_';
	private static final char dash = '-';


	private StringCase() { throw new AssertionError("cannot instantiate static class StringCase"); }


	/** Check if a string is in {@code 'camelCase'} format
	 * @param str the string to check
	 * @return true if the string is camelCase, false otherwise
	 */
	public static final boolean isCamelCase(final String str) {
		return str.length() > 0 && Character.isLowerCase(str.charAt(0)) && str.indexOf(underscore, 0) == -1 && str.indexOf(dash, 0) == -1;
	}


	/** Check if a string is in {@code 'TitleCase'} format
	 * @param str the string to check
	 * @return true if the string is TitleCase, false otherwise
	 */
	public static final boolean isTitleCase(final String str) {
		return str.length() > 0 && Character.isUpperCase(str.charAt(0)) && str.indexOf(underscore, 0) == -1 && str.indexOf(dash, 0) == -1;
	}


	/** Check if a string is in {@code 'dash-css-case'} format
	 * @param str the string to check
	 * @return true if the string is dash-css-case, false otherwise
	 */
	public static final boolean isDashCase(final String str) {
		if(str.length() == 0) {
			return false;
		}

		for(int i = 0, size = str.length(); i < size; i++) {
			char ch = str.charAt(i);
			if(ch == underscore || Character.isUpperCase(ch)) {
				return false;
			}
		}
		return true;
	}


	/** Check if a string is in {@code 'under_score_lower_case'} format
	 * @param str the string to check
	 * @return true if the string is under_score_lower_case, false otherwise
	 */
	public static final boolean isUnderscoreLowerCase(final String str) {
		if(str.length() == 0) {
			return false;
		}

		for(int i = 0, size = str.length(); i < size; i++) {
			char ch = str.charAt(i);
			if(ch == dash || Character.isUpperCase(ch)) {
				return false;
			}
		}
		return true;
	}


	/** Check if a string is in {@code 'Under_Score_Title_Case'} format
	 * @param str the string to check
	 * @return true if the string is Under_Score_Title_Case, false otherwise
	 */
	public static final boolean isUnderscoreTitleCase(final String str) {
		char prevCh;
		if(str.length() == 0 || Character.isLowerCase(prevCh = str.charAt(0))) {
			return false;
		}

		for(int i = 1, size = str.length(); i < size; i++) {
			char ch = str.charAt(i);
			boolean isPrevUnderscore = prevCh == underscore;
			if(ch == dash || (Character.isUpperCase(ch) && !isPrevUnderscore) || (isPrevUnderscore && Character.isLowerCase(ch))) {
				return false;
			}
			prevCh = ch;
		}
		return true;
	}


	/** Convert a string to {@code 'camelCase'}
	 * @param str the string to convert
	 * @return the camelCase version of the string
	 */
	public static final String toCamelCase(final String str) {
		if(isCamelCase(str)) {
			return str;
		}
		else if(isTitleCase(str)) {
			StringBuilder sb = new StringBuilder(str.length()).append(str);
			sb.setCharAt(0, Character.toLowerCase(str.charAt(0)));
			return sb.toString();
		}
		else if(isDashCase(str)) {
			return tokenSeparatedToCamelCase(str, dash);
		}
		else if(isUnderscoreLowerCase(str)) {
			return tokenSeparatedToCamelCase(str, underscore);
		}
		else if(isUnderscoreTitleCase(str)) {
			StringBuilder sb = StringReplace.replace(new StringBuilder(str.length()).append(str), 0, "_", "");
			sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
			return sb.toString();
		}
		else {
			throw new IllegalArgumentException("unknown string case of string '" + str + "'");
		}
	}


	/** Convert a string to {@code 'TitleCase'}
	 * @param str the string to convert
	 * @return the TitleCase version of the string
	 */
	public static final String toTitleCase(final String str) {
		if(isCamelCase(str)) {
			StringBuilder sb = new StringBuilder(str.length()).append(str);
			sb.setCharAt(0, Character.toUpperCase(str.charAt(0)));
			return sb.toString();
		}
		else if(isTitleCase(str)) {
			return str;
		}
		else if(isDashCase(str)) {
			return tokenSeparatedToTitleCase(str, dash);
		}
		else if(isUnderscoreLowerCase(str)) {
			return tokenSeparatedToTitleCase(str, underscore);
		}
		else if(isUnderscoreTitleCase(str)) {
			return StringReplace.replace(str, "_", "");
		}
		else {
			throw new IllegalArgumentException("unknown string case of string '" + str + "'");
		}
	}


	/** Convert a string to {@code 'dash-css-case'}
	 * @param str the string to convert
	 * @return the dash-css-case version of the string
	 */
	public static final String toDashCase(final String str) {
		if(isCamelCase(str)) {
			StringBuilder sb = new StringBuilder();
			for(int i = 0, size = str.length(); i < size; i++) {
				char ch = str.charAt(i);
				if(Character.isUpperCase(ch)) {
					sb.append(dash);
					ch = Character.toLowerCase(ch);
				}
				sb.append(ch);
			}
			return sb.toString();
		}
		else if(isTitleCase(str)) {
			StringBuilder sb = new StringBuilder();
			sb.append(Character.toLowerCase(str.charAt(0)));
			for(int i = 1, size = str.length(); i < size; i++) {
				char ch = str.charAt(i);
				if(Character.isUpperCase(ch)) {
					sb.append(dash);
					ch = Character.toLowerCase(ch);
				}
				sb.append(ch);
			}
			return sb.toString();
		}
		else if(isDashCase(str)) {
			return str;
		}
		else if(isUnderscoreLowerCase(str)) {
			return str.replace(underscore, dash);
		}
		else if(isUnderscoreTitleCase(str)) {
			StringBuilder sb = new StringBuilder();
			char prevCh = str.charAt(0);
			sb.append(prevCh == underscore ? dash : Character.toLowerCase(prevCh));
			for(int i = 1, size = str.length(); i < size; i++) {
				char ch = str.charAt(i);
				if(prevCh == underscore) {
					ch = Character.toLowerCase(ch);
				}
				prevCh = ch;
				sb.append(ch == underscore ? dash : ch);
			}
			return sb.toString();
		}
		else {
			throw new IllegalArgumentException("unknown string case of string '" + str + "'");
		}
	}


	/** Convert a string to {@code 'under_score_lower_case'}
	 * @param str the string to convert
	 * @return the under_score_lower_case version of the string
	 */
	public static final String toUnderscoreLowerCase(final String str) {
		if(isCamelCase(str)) {
			StringBuilder sb = new StringBuilder();
			for(int i = 0, size = str.length(); i < size; i++) {
				char ch = str.charAt(i);
				if(Character.isUpperCase(ch)) {
					sb.append(underscore);
					ch = Character.toLowerCase(ch);
				}
				sb.append(ch);
			}
			return sb.toString();
		}
		else if(isTitleCase(str)) {
			StringBuilder sb = new StringBuilder();
			sb.append(Character.toLowerCase(str.charAt(0)));
			for(int i = 1, size = str.length(); i < size; i++) {
				char ch = str.charAt(i);
				if(Character.isUpperCase(ch)) {
					sb.append(underscore);
					ch = Character.toLowerCase(ch);
				}
				sb.append(ch);
			}
			return sb.toString();
		}
		else if(isDashCase(str)) {
			return str.replace(dash, underscore);
		}
		else if(isUnderscoreLowerCase(str)) {
			return str;
		}
		else if(isUnderscoreTitleCase(str)) {
			StringBuilder sb = new StringBuilder();
			char prevCh = str.charAt(0);
			sb.append(Character.toLowerCase(prevCh));
			for(int i = 1, size = str.length(); i < size; i++) {
				char ch = str.charAt(i);
				if(prevCh == underscore) {
					ch = Character.toLowerCase(ch);
				}
				prevCh = ch;
				sb.append(ch);
			}
			return sb.toString();
		}
		else {
			throw new IllegalArgumentException("unknown string case of string '" + str + "'");
		}
	}


	/** Convert a string to {@code 'Under_Score_Title_Case'}
	 * @param str the string to convert
	 * @return the Under_Score_Title_Case version of the string
	 */
	public static final String toUnderscoreTitleCase(final String str) {
		if(isCamelCase(str)) {
			StringBuilder sb = new StringBuilder();
			sb.append(Character.toUpperCase(str.charAt(0)));
			for(int i = 1, size = str.length(); i < size; i++) {
				char ch = str.charAt(i);
				if(Character.isUpperCase(ch)) {
					sb.append(underscore);
				}
				sb.append(ch);
			}
			return sb.toString();
		}
		else if(isTitleCase(str)) {
			StringBuilder sb = new StringBuilder();
			sb.append(str.charAt(0));
			for(int i = 1, size = str.length(); i < size; i++) {
				char ch = str.charAt(i);
				if(Character.isUpperCase(ch)) {
					sb.append(underscore);
				}
				sb.append(ch);
			}
			return sb.toString();
		}
		else if(isDashCase(str)) {
			return tokenSeparatedToUnderscoreTitleCase(str, dash);
		}
		else if(isUnderscoreLowerCase(str)) {
			return tokenSeparatedToUnderscoreTitleCase(str, underscore);
		}
		else if(isUnderscoreTitleCase(str)) {
			return str;
		}
		else {
			throw new IllegalArgumentException("unknown string case of string '" + str + "'");
		}
	}


	private static final String tokenSeparatedToCamelCase(final String str, final char separator) {
		StringBuilder sb = new StringBuilder();
		char prevCh = str.charAt(0);
		sb.append(prevCh);
		for(int i = 1, size = str.length(); i < size; i++) {
			char ch = str.charAt(i);
			if(prevCh == separator) {
				sb.setLength(sb.length() - 1);
				ch = Character.toUpperCase(ch);
			}
			prevCh = ch;
			sb.append(ch);
		}
		if(sb.length() > 1) {
			char lastCh = sb.charAt(sb.length() - 1);
			if(lastCh == separator) {
				sb.setLength(sb.length() - 1);
			}
		}
		return sb.toString();
	}


	private static final String tokenSeparatedToTitleCase(final String str, final char separator) {
		StringBuilder sb = new StringBuilder();
		char prevCh = str.charAt(0);
		sb.append(Character.toUpperCase(prevCh));
		for(int i = 1, size = str.length(); i < size; i++) {
			char ch = str.charAt(i);
			if(prevCh == separator) {
				sb.setLength(sb.length() - 1);
				ch = Character.toUpperCase(ch);
			}
			prevCh = ch;
			sb.append(ch);
		}
		if(sb.length() > 1) {
			char lastCh = sb.charAt(sb.length() - 1);
			if(lastCh == separator) {
				sb.setLength(sb.length() - 1);
			}
		}
		return sb.toString();
	}


	private static final String tokenSeparatedToUnderscoreTitleCase(final String str, final char separator) {
		StringBuilder sb = new StringBuilder();
		char prevCh = str.charAt(0);
		sb.append(prevCh == separator ? underscore : Character.toUpperCase(prevCh));
		for(int i = 1, size = str.length(); i < size; i++) {
			char ch = str.charAt(i);
			if(prevCh == separator) {
				ch = Character.toUpperCase(ch);
			}
			prevCh = ch;
			sb.append(ch == separator ? underscore : ch);
		}
		return sb.toString();
	}

}
