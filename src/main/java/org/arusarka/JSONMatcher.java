package org.arusarka;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JSONMatcher extends TypeSafeMatcher<String> {
    private ObjectMapper objectMapper;

    private JsonNode expectedJson;
    private List<String> errorList = new ArrayList<String>();
    private JsonNode actualJson;

    public JSONMatcher(String expectedJsonString, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        try {
            this.expectedJson = this.objectMapper.readTree(expectedJsonString);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static Matcher<String> shouldContainJson(String expectedJsonString) {
        return new JSONMatcher(expectedJsonString, new ObjectMapper());
    }

    @Override
    protected boolean matchesSafely(String actualJsonString) {
        try {
            actualJson = objectMapper.readTree(actualJsonString);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        if (expectedJson.isObject())
            matchObjectNode(expectedJson, actualJson, "top level", errorList);
        else if (expectedJson.isArray())
            matchArrayNode(expectedJson, actualJson, "top level", errorList);
        if (errorList.isEmpty())
            return true;
        return false;
    }

    private void matchArrayNode(JsonNode expectedArrayNode, JsonNode actualArrayNode, String fieldName, List<String> errors) {
        Iterator<JsonNode> expectedArrayNodeIterator = expectedArrayNode.iterator();

        while (expectedArrayNodeIterator.hasNext()) {
            JsonNode expectedArrayNodeElement = expectedArrayNodeIterator.next();

            if(!checkMatchingNodeFound(actualArrayNode, expectedArrayNodeElement, fieldName)) {
                errors.add("Could not find " + expectedArrayNodeElement + " inside array field \"" + fieldName + "\".");
                return;
            }
        }
    }

    private boolean checkMatchingNodeFound(JsonNode actualArrayNode, JsonNode expectedArrayNodeElement, String fieldName) {
        Iterator<JsonNode> actualArrayNodeIterator = actualArrayNode.iterator();
        while (actualArrayNodeIterator.hasNext()) {
            JsonNode actualArrayNodeElement = actualArrayNodeIterator.next();
            List<String> errors = new ArrayList<String>();

            if (expectedArrayNodeElement.isValueNode())
                matchValueNode(expectedArrayNodeElement, actualArrayNodeElement, fieldName, errors);
            if (expectedArrayNodeElement.isObject())
                matchObjectNode(expectedArrayNodeElement, actualArrayNodeElement, fieldName, errors);
            if (errors.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void matchObjectNode(JsonNode expectedJson, JsonNode actualJson, String fieldName, List<String> errors) {
        List<String> fieldsMissingListWithCurrentJson = findFieldsMissingBetweenExpectedAndActual(expectedJson, actualJson);


        if (fieldsMissingListWithCurrentJson.isEmpty()) {
            matchChildrenNodes(expectedJson, actualJson, errors);
        } else {
            for (String fieldMissing : fieldsMissingListWithCurrentJson) {
                errors.add("Expected attribute \"" + fieldMissing + "\" inside \"" + fieldName +
                        "\" in response but is missing.\n");
                ((ObjectNode) expectedJson).remove(fieldMissing);
            }
            matchChildrenNodes(expectedJson, actualJson, errors);
        }
    }

    private void matchChildrenNodes(JsonNode expectedJson, JsonNode actualJson, List<String> errors) {
        Iterator<String> expectedFiledNameIterator = expectedJson.getFieldNames();
        while (expectedFiledNameIterator.hasNext()) {
            String expectedFieldName = expectedFiledNameIterator.next();

            checkIfItsAMatchingArrayNode(expectedFieldName, expectedJson.get(expectedFieldName), actualJson.get(expectedFieldName), errors);
            checkIfItsAMatchingObjectNode(expectedFieldName, expectedJson.get(expectedFieldName), actualJson.get(expectedFieldName), errors);
            checkIfItsAMatchingValueNode(expectedFieldName, expectedJson.get(expectedFieldName), actualJson.get(expectedFieldName), errors);
        }
    }

    private void matchValueNode(JsonNode expectedJson, JsonNode actualJson, String fieldName, List<String> errors) {
        if (expectedJson.equals(actualJson)) {
            return;
        } else {
            errors.add("Expected value for \"" + fieldName + "\" field is \"" +
                    expectedJson.asText() + "\". It does not match the response value (" + actualJson + ").\n");
            return;
        }
    }

    private List<String> findFieldsMissingBetweenExpectedAndActual(JsonNode expectedJson, JsonNode actualJson) {
        List<String> fieldsMissingListWithCurrentJson = new ArrayList<String>();

        Iterator<String> expectedFieldNamesIterator = expectedJson.getFieldNames();

        while (expectedFieldNamesIterator.hasNext()) {
            String expectedFieldName = expectedFieldNamesIterator.next();
            if (actualJson.get(expectedFieldName) == null) {
                fieldsMissingListWithCurrentJson.add(expectedFieldName);
            }
        }
        return fieldsMissingListWithCurrentJson;
    }

    private void checkIfItsAMatchingValueNode(String fieldName, JsonNode expectedNode, JsonNode actualNode, List<String> errors) {
        if (expectedNode.isValueNode())
            matchValueNode(expectedNode, actualNode, fieldName, errors);
    }

    private void checkIfItsAMatchingObjectNode(String fieldName, JsonNode expectedNode, JsonNode actualNode, List<String> errors) {
        if (expectedNode.isObject())
            matchObjectNode(expectedNode, actualNode, fieldName, errors);
    }

    private void checkIfItsAMatchingArrayNode(String fieldName, JsonNode expectedNode, JsonNode actualNode, List<String> errors) {
        if (expectedNode.isArray())
            matchArrayNode(expectedNode, actualNode, fieldName, errors);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("\n");
        for(int index = 0; index < errorList.size(); index++) {
            description.appendText((index+1) + ")" + " " + errorList.get(index));
        }
    }
}
