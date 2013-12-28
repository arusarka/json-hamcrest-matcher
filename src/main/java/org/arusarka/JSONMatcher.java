package org.arusarka;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JSONMatcher extends TypeSafeMatcher<String> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MissingAttributeChecker missingAttributeChecker = new MissingAttributeChecker();
    private JsonNode expectedJson;
    private List<String> errorList = new ArrayList<String>();

    public JSONMatcher(String expectedJson) {
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

        final Iterator<JsonNode> expectedJsonIterator = this.expectedJson.iterator();
        final Iterator<JsonNode> actualJsonNodeIterator = actualJson.iterator();

        if (!missingAttributeChecker.doesMatch(actualJson, this.expectedJson)) {
            this.errorList.addAll(missingAttributeChecker.errors());
            return false;
        }

        while (expectedJsonIterator.hasNext()) {
            final JsonNode currentExpectedJsonNode = expectedJsonIterator.next();
            final JsonNode currentActualJsonNode = actualJsonNodeIterator.next();

            if (!currentExpectedJsonNode.equals(currentExpectedJsonNode))
                return false;
        }

        return true;

    }

    @Override
    public void describeTo(Description description) {
        description.appendText("\n");
        for(int index = 0; index < errorList.size(); index++) {
            description.appendText((index+1) + ")" + " " + errorList.get(index));
        }
    }

    public static Matcher<String> shouldMatchJson(String expectedJson) {
        return new JSONMatcher(expectedJson);
    }
}
