Hamcrest Matcher for JSON
=========================

This library provides a [hamcrest matcher](http://hamcrest.org/JavaHamcrest/javadoc/1.3/) for json. The idea of this matcher is to provide partial matching of json objects. The most important aspect of the matcher is that the relaxed (or partial) matching style works at any level nested inside a json object. What that means is, as long as the expected json is *contained* inside the actual json, it would match.


Maven dependency
----------------


    <dependency>
        <groupId>com.arusarka</groupId>
        <artifactId>json-hamcrest-matcher</artifactId>
        <version>1.0</version>
    </dependency>


Basic usage
-----------

Using with JUnit 4


    Assert.assertThat(actual, JSONMatcher.shouldContainJson(expected));


where ***actual*** and ***expected*** are both json string variables.


Given below are a bunch of different cases for which the matcher will work.


Json object attribute absent in expected
---------------------------------------------


Following jsons would match since the matcher does a partial match.


|Actual|Expected|
|:----:|:------:|
|```{"foo" : "bar","baz" : "qux"}```|```{"foo" : "bar"}```|


However, the following **would not.**


|Actual|Expected|
|:----:|:------:|
|```{"foo" : "bar"}```|```{"foo" : "bar","bar" : "qux"}```|


Json arrays matching
--------------------


The matcher is very relaxed about array matching. It would match if expected array elements match partially with any element inside actual json array. It does not require that the sizes of expected and actual array to match. Nor does not require the order to be the same.

Following jsons would match.


|Actual|Expected|
|:----:|:------:|
|```[ { "foo": "bar", "bar": "qux" }, { "hello": "world", "lorem": "ipsum" } ]```|```[ { "foo": "bar", "bar": "qux" } ]```|
|```[ { "foo": "bar", "bar": "qux" } ]```|```[ { "foo": "bar" } ]```|
