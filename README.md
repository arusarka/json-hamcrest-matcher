THIS PROJECT IS A WORK IN PROGRESS. DO NOT USE IT YET!
======================================================

Hamcrest Matcher for JSON
=========================

This library provides a [hamcrest matcher](http://hamcrest.org/JavaHamcrest/javadoc/1.3/). Described below are the different scenarios which will match.

Basic usage
-----------

Using with JUnit 4

    Assert.assertThat(actual, JSONMatcher.shouldMatchJson(expected));

where "***actual***" is a string for the actual json to verify and "***expected***" is the expected json.
