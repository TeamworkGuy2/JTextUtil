# Change Log
All notable changes to this project will be documented in this file.
This project does its best to adhere to [Semantic Versioning](http://semver.org/).


--------
###[0.9.0](N/A) - 2016-06-23
####Added
* StringSplit substring*() and lastSubstring*() methods

####Changed
* Moved twg2.text.stringTemplate package to new [JTextTemplate] (https://github.com/TeamworkGuy2/JTextTemplate) library
* Changed versions.md to CHANGELOG.md format, see http://keepachangelog.com/
* Changed some JUnit tests
* Moved StringEscapePartialTest.UnescapePartialQuoted -> DataUnescapePartialQuoted


--------
###[0.8.0](https://github.com/TeamworkGuy2/JTextFluff/commit/78acc7e47201b572db507634e5b3517b874e9c8f) - 2016-02-28
####Added
* Added StringIndex lastIndexOf()

####Changed
* Split StringEscape into a base, Json, Partial, and Xml specific files and moved to new twg2.text.stringEscape package
* Moved test classes to separate test directory


--------
###[0.7.2](https://github.com/TeamworkGuy2/JTextFluff/commit/9103614630787018da70515f6f519dc485dfdc63) - 2016-02-08
####Added
* Added StringJoin repeat() and repeatJoin() methods to repeat a string several times and return a string result or appending to a destination
* Added StringTrim countAndTrimLeading() and countAndTrimTrailing() to return a trimmed string as well as how many leading/trailing characters or strings were trimmed

####Fixed
* Fixed some StringTrim bugs (incorrectly trimming 1 char instead of trim string length)


--------
###[0.7.1](https://github.com/TeamworkGuy2/JTextFluff/commit/07ef4c94a2ec576cc8aeb55ef9b6871ff304f304) -  2016-01-16
####Added
* Added more JUnit tests

####Fixed
* Bug fixes in: StringCompare.closestMatch() and StringCompare.compareStartsWith()
* Minor refactoring to StringReplace.replaceTokens() signature


--------
###[0.7.0](https://github.com/TeamworkGuy2/JTextFluff/commit/94a5ebba1b9c37887dd017f87b3849eaa261ac56) - 2016-01-10
####Added
* Additional tests and cleaning of old debugging statements and variable names
* Added StringTrim trimLeading() and trimTrailing()

####Fixed
* Minor bug fixes in: closestMatch() and compareEqualCount()
