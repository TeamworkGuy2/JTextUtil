# Change Log
All notable changes to this project will be documented in this file.
This project does its best to adhere to [Semantic Versioning](http://semver.org/).


--------
###[0.10.4](N/A) - 2016-09-24
#### Added
* Added StringSplit.splitAtBoundary()


--------
###[0.10.3](https://github.com/TeamworkGuy2/JTextUtil/commit/cb0f1e114944175eafe06cc8311609187a2cc93e) - 2016-09-11
#### Added
* Added StringSplit.countMatches(char[], int, int, char[], int, int)


--------
###[0.10.2](https://github.com/TeamworkGuy2/JTextUtil/commit/573390227bb299d442f19b76f5761c8bc80e5a3c) - 2016-08-27
#### Changed
* Changed remote git repository name


--------
###[0.10.1](https://github.com/TeamworkGuy2/JTextFluff/commit/1d91013ca264434f32d59ac84b2e720444433689) - 2016-08-15
#### Added
* StringPad


--------
###[0.10.0](https://github.com/TeamworkGuy2/JTextFluff/commit/621ad58574853f831ce5bc7e669e1447b8f306f9) - 2016-08-07
#### Added
* StringSearchOp for string comparison

#### Changed
* Added new twg2.text.stringSearch package
  * moved StringCommonality, StringCompare, StringIndex into this package


--------
###[0.9.1](https://github.com/TeamworkGuy2/JTextFluff/commit/cd020a56da7a9549c2be5814bda830e59c23d245) - 2016-07-07
#### Added
* StringJoin join() overloads with 'off' and 'len' parameters
* Additional unit tests

#### Fixed
* StringEscapeJson offset/length being ignored


--------
###[0.9.0](https://github.com/TeamworkGuy2/JTextFluff/commit/d48b7163392bbfab2a5eca4bc6f06d2143b29a0f) - 2016-06-26
#### Added
* StringSplit substring*() and lastSubstring*() methods

#### Changed
* Moved twg2.text.stringTemplate package to new [JTextTemplate] (https://github.com/TeamworkGuy2/JTextTemplate) library
* Changed versions.md to CHANGELOG.md format, see http://keepachangelog.com/
* Changed some JUnit tests
* Moved StringEscapePartialTest.UnescapePartialQuoted -> DataUnescapePartialQuoted


--------
###[0.8.0](https://github.com/TeamworkGuy2/JTextFluff/commit/78acc7e47201b572db507634e5b3517b874e9c8f) - 2016-02-28
#### Added
* Added StringIndex lastIndexOf()

#### Changed
* Split StringEscape into a base, Json, Partial, and Xml specific files and moved to new twg2.text.stringEscape package
* Moved test classes to separate test directory


--------
###[0.7.2](https://github.com/TeamworkGuy2/JTextFluff/commit/9103614630787018da70515f6f519dc485dfdc63) - 2016-02-08
#### Added
* Added StringJoin repeat() and repeatJoin() methods to repeat a string several times and return a string result or appending to a destination
* Added StringTrim countAndTrimLeading() and countAndTrimTrailing() to return a trimmed string as well as how many leading/trailing characters or strings were trimmed

#### Fixed
* Fixed some StringTrim bugs (incorrectly trimming 1 char instead of trim string length)


--------
###[0.7.1](https://github.com/TeamworkGuy2/JTextFluff/commit/07ef4c94a2ec576cc8aeb55ef9b6871ff304f304) -  2016-01-16
#### Added
* Added more JUnit tests

#### Fixed
* Bug fixes in: StringCompare.closestMatch() and StringCompare.compareStartsWith()
* Minor refactoring to StringReplace.replaceTokens() signature


--------
###[0.7.0](https://github.com/TeamworkGuy2/JTextFluff/commit/94a5ebba1b9c37887dd017f87b3849eaa261ac56) - 2016-01-10
#### Added
* Additional tests and cleaning of old debugging statements and variable names
* Added StringTrim trimLeading() and trimTrailing()

#### Fixed
* Minor bug fixes in: closestMatch() and compareEqualCount()
