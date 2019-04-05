JTextUtil
==============

Dependencies: none

### twg2.text.stringEscape
Escape and unescape methods for JSON (`StringEscapeJson`), XML (`StringEscapeXml`), and strings with non-ASCII characters, quotes, or `\t \b \f \r` characters (`StringEscape` and `StringEscapePartial`).

### twg2.text.stringSearch
String search methods for indexOf/lastIndexOf (`StringIndex`), endsWith, startsWith, compare, contains (`StringCompare`), and common prefix/suffix (`StringCommonality`).

### twg2.text.stringUtils
String utility methods for join and repeat (`StringJoin`), pad (`StringPad`), split, substring, and Nth match (`StringSplit`), trim (`StringTrim`), replace (`StringReplace`), hex conversion (`StringHex`), empty/whitespace checking (`StringCheck`), code identifier case checking for camelCase, TitleCase, snake_case, etc (`StringCase`), and conversion to and from java.util.Properties format (`StringToProperties`).
Meant to provide more options than available in the Java API as of Java 1.8. 

### twg2.text.test
JUnit tests and examples (requires [JUnit 5](http://junit.org/))