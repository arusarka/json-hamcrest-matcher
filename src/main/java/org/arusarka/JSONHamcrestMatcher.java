package org.arusarka;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.io.IOException;

public class JSONHamcrestMatcher extends TypeSafeMatcher<String> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private JsonNode expectedJson;

    public JSONHamcrestMatcher(String expectedJson) {
        try {
            this.expectedJson = objectMapper.readTree(expectedJson);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    protected boolean matchesSafely(String actualJsonString) {
        JsonNode actualJson;
        try {
            actualJson = objectMapper.readTree(actualJsonString);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return expectedJson.equals(actualJson);
    }

    @Override
    public void describeTo(Description description) {

    }

    public static Matcher<String> shouldMatchJson(String expectedJson) {
        return new JSONHamcrestMatcher(expectedJson);
    }
}
