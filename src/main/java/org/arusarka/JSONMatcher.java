package org.arusarka;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONMatcher extends TypeSafeMatcher<String> {
    private final ObjectMapper objectMapper;
    private final MissingAttributeChecker missingAttributeChecker = new MissingAttributeChecker();
    private JsonNode expectedJson;
    private List<String> errorList = new ArrayList<String>();
    private JSONObjectNodeMatcher jSONObjectNodeMatcher;

    public JSONMatcher(String expectedJson, JSONObjectNodeMatcher jSONObjectNodeMatcher, ObjectMapper objectMapper) {
        this.jSONObjectNodeMatcher = jSONObjectNodeMatcher;
        this.objectMapper = objectMapper;
        try {
            this.expectedJson = this.objectMapper.readTree(expectedJson);
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

        if (!jSONObjectNodeMatcher.doesMatch(actualJson, this.expectedJson, this.errorList)) {
            return false;
        }

        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("\n");
        for (int index = 0; index < errorList.size(); index++) {
            description.appendText((index + 1) + ")" + " " + errorList.get(index));
        }
    }

    public static Matcher<String> shouldContainJson(String expectedJson) {
        return new JSONMatcher(expectedJson, new JSONObjectNodeMatcher(new MissingAttributeChecker()), new ObjectMapper());
    }
}
