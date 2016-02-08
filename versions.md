--------
####0.7.2

date: 2016-1-16

commit: ?

* Added StringJoin repeat() and repeatJoin() methods to allow for repeating a string several times and returning a string result or appending to a destination
* Added StringTrim countAndTrimLeading() and countAndTrimTrailing() for returning a trimmed string as well as how many leading/trailing characters or strings were trimmed
* Fixed some StringTrim bugs (incorrectly trimming 1 char instead of trim string length)


--------
####0.7.1

date: 2016-1-16

commit: 07ef4c94a2ec576cc8aeb55ef9b6871ff304f304

* Added more JUnit tests
* Bug fixes in: StringCompare.closestMatch() and StringCompare.compareStartsWith()
* Minor refactoring to StringReplace.replaceTokens() signature


--------
####0.7.0

date: 2016-1-10

commit: 94a5ebba1b9c37887dd017f87b3849eaa261ac56

* Additional tests and cleaning of old debugging statements and variable names
* Minor bug fixes in: closestMatch() and compareEqualCount()
* Added StringTrim trimLeading() and trimTrailing()