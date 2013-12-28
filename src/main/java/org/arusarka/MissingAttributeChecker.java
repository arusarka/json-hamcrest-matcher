package org.arusarka;

import org.codehaus.jackson.JsonNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MissingAttributeChecker {

    private final List<String> errors = new ArrayList<String>();

    boolean doesMatch(JsonNode actualJson, JsonNode expectedJson) {
        errors.clear();
        boolean retVal = true;

        final Iterator<String> expectedFieldNames = expectedJson.getFieldNames();

        while (expectedFieldNames.hasNext()) {
            final String expectedFieldName = expectedFieldNames.next();
            if (actualJson.get(expectedFieldName) == null) {
                errors.add("Expected attribute \"" + expectedFieldName + "\" but is missing.");
                retVal = false;
            }
        }
        return retVal;
    }

    public List<String> errors() {
        return errors;
    }
}
