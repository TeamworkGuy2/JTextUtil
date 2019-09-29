# Change Log
All notable changes to this project will be documented in this file.
This project does its best to adhere to [Semantic Versioning](http://semver.org/).


--------
### [0.13.0](N/A) - 2019-09-29
#### Added
* `StringSplit.lastMatchParts(String, char)`
* Additional unit tests

#### Changed
* Change methods to `private`:
  * `StringSplit._postFirstMatch()`
  * `StringSplit._lastMatch()`
  * `StringSplit._preLastMatch()`


--------
### [0.12.0](https://github.com/TeamworkGuy2/JTextUtil/commit/1aaa95edd8e0ef41bb5f0cced94045ce54d95780) - 2019-04-04
#### Added
* `StringCompare.containsCount()`, `containsIgnoreCaseCount()` and overloads

#### Changed
* Renamed `StringSearchOpt.EXACT` -> `EQUALS`
* Removed `final` modifier from classes and static methods
* Ensured all classes containing only static methods, have a private default constructor which throws `AssertionError`
* Additional unit tests


--------
### [0.11.5](https://github.com/TeamworkGuy2/JTextUtil/commit/0e025e4ac7fcb5cff0dae9c282281b99293c1aec) - 2019-02-02
#### Added
* `StringCompare.containsAny(String, Iterable)`
* `StringCompare.containsAll()` to mirror `containsAny()`

#### Changed
* Widened `StringCompare` `containsIgnoreCase()` and `containsEqualIgnoreCase()` parameter from `Collection` to `Iterable`
* Additional unit tests

#### Fixed
* `StringCompare.compareStartsWith()` not handling case when `startStr` is longer then `str`
* `StringCompare.equal()` error when str2 is shorter than `str2Off + len`, correctly returns false now


--------
### [0.11.4](https://github.com/TeamworkGuy2/JTextUtil/commit/06d67ab9b738d78580688270eaf1e09db8fda339) - 2017-12-22
#### Fixed
* Correctly upgrade `.classpath` to Java 9
* Fix some test warnings


--------
### [0.11.3](https://github.com/TeamworkGuy2/JTextUtil/commit/3224c1d31c9d7d6350356494947f64b3b0dbd32a) - 2017-12-20
#### Changed
* Upgrade to Java 9
* Upgraded to JUnit 5
* Changed `StringCompare` `anyStartWith()`, `compareStartsWith()`, and `equal()` `StringBuilder` parameters to `CharSequence`
* Changed `StringIndex` `indexOf()`, `lastIndexOf()`, `indexOfSupplement()`, and `startsWithIndex()` `StringBuilder` parameters to `CharSequence`


--------
### [0.11.2](https://github.com/TeamworkGuy2/JTextUtil/commit/874950940409f0cd71e62c499504abb5a56c0f0f) - 2017-08-12
#### Added
* Added StringCompare.compareIgnoreCase(String, String) overload and unit test


--------
### [0.11.1](https://github.com/TeamworkGuy2/JTextUtil/commit/295e57a6016ecedbf3f68eb7f65aff98b27961c7) - 2016-10-30
#### Added
* Added StringEscapeJson toJsonString() and fromJsonString() overloads which don't require string offset and length


--------
### [0.11.0](https://github.com/TeamworkGuy2/JTextUtil/commit/9f1c9bd4af821ebe504919c430d639037558ac6c) - 2016-10-08
#### Added
* Added StringCase isDashCase() and toDashCase() with additional unit tests

#### Changed
* Moved StringJoin.Objects methods into base StringJoin class
* Moved StringJoin.Func methods into base StringJoin class
* Changed StringEscape and StringEscapePartial:
  * methods which take 'Appendable dst' params to throw IOException
  * added additional overloaded methods which take StringBuilder and do not throw IOException


--------
### [0.10.6](https://github.com/TeamworkGuy2/JTextUtil/commit/1b81c02b2af0ac1186c1bab9c8534c6f851be2e9) - 2016-10-02
#### Added
* Added StringEscapeJson.toJsonString(char, Appendable) override


--------
### [0.10.5](https://github.com/TeamworkGuy2/JTextUtil/commit/d0299113a4f3478320321a7e9dfa8ffac84ce9c6) - 2016-09-26
#### Added
* Added StringJoin.join(Iterator, ...) and StringJoin.Objects.join(Iterator, ...)

#### Changed
* Improved StringJoin and StringHex unit tests


--------
### [0.10.4](https://github.com/TeamworkGuy2/JTextUtil/commit/6d7676e216cd63cfed9fb4f96180a5d647cb8372) - 2016-09-24
#### Added
* Added StringSplit.splitAtBoundary()


--------
### [0.10.3](https://github.com/TeamworkGuy2/JTextUtil/commit/cb0f1e114944175eafe06cc8311609187a2cc93e) - 2016-09-11
#### Added
* Added StringSplit.countMatches(char[], int, int, char[], int, int)


--------
### [0.10.2](https://github.com/TeamworkGuy2/JTextUtil/commit/573390227bb299d442f19b76f5761c8bc80e5a3c) - 2016-08-27
#### Changed
* Changed remote git repository name


--------
### [0.10.1](https://github.com/TeamworkGuy2/JTextFluff/commit/1d91013ca264434f32d59ac84b2e720444433689) - 2016-08-15
#### Added
* StringPad


--------
### [0.10.0](https://github.com/TeamworkGuy2/JTextFluff/commit/621ad58574853f831ce5bc7e669e1447b8f306f9) - 2016-08-07
#### Added
* StringSearchOp for string comparison

#### Changed
* Added new twg2.text.stringSearch package
  * moved StringCommonality, StringCompare, StringIndex into this package


--------
### [0.9.1](https://github.com/TeamworkGuy2/JTextFluff/commit/cd020a56da7a9549c2be5814bda830e59c23d245) - 2016-07-07
#### Added
* StringJoin join() overloads with 'off' and 'len' parameters
* Additional unit tests

#### Fixed
* StringEscapeJson offset/length being ignored


--------
### [0.9.0](https://github.com/TeamworkGuy2/JTextFluff/commit/d48b7163392bbfab2a5eca4bc6f06d2143b29a0f) - 2016-06-26
#### Added
* StringSplit substring*() and lastSubstring*() methods

#### Changed
* Moved twg2.text.stringTemplate package to new [JTextTemplate] (https://github.com/TeamworkGuy2/JTextTemplate) library
* Changed versions.md to CHANGELOG.md format, see http://keepachangelog.com/
* Changed some JUnit tests
* Moved StringEscapePartialTest.UnescapePartialQuoted -> DataUnescapePartialQuoted


--------
### [0.8.0](https://github.com/TeamworkGuy2/JTextFluff/commit/78acc7e47201b572db507634e5b3517b874e9c8f) - 2016-02-28
#### Added
* Added StringIndex lastIndexOf()

#### Changed
* Split StringEscape into a base, Json, Partial, and Xml specific files and moved to new twg2.text.stringEscape package
* Moved test classes to separate test directory


--------
### [0.7.2](https://github.com/TeamworkGuy2/JTextFluff/commit/9103614630787018da70515f6f519dc485dfdc63) - 2016-02-08
#### Added
* Added StringJoin repeat() and repeatJoin() methods to repeat a string several times and return a string result or appending to a destination
* Added StringTrim countAndTrimLeading() and countAndTrimTrailing() to return a trimmed string as well as how many leading/trailing characters or strings were trimmed

#### Fixed
* Fixed some StringTrim bugs (incorrectly trimming 1 char instead of trim string length)


--------
### [0.7.1](https://github.com/TeamworkGuy2/JTextFluff/commit/07ef4c94a2ec576cc8aeb55ef9b6871ff304f304) -  2016-01-16
#### Added
* Added more JUnit tests

#### Fixed
* Bug fixes in: StringCompare.closestMatch() and StringCompare.compareStartsWith()
* Minor refactoring to StringReplace.replaceTokens() signature


--------
### [0.7.0](https://github.com/TeamworkGuy2/JTextFluff/commit/94a5ebba1b9c37887dd017f87b3849eaa261ac56) - 2016-01-10
#### Added
* Additional tests and cleaning of old debugging statements and variable names
* Added StringTrim trimLeading() and trimTrailing()

#### Fixed
* Minor bug fixes in: closestMatch() and compareEqualCount()
