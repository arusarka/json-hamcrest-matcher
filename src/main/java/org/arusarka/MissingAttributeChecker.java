package org.arusarka;

import org.codehaus.jackson.JsonNode;

import java.util.Iterator;
import java.util.List;

public class MissingAttributeChecker {
    boolean doesMatch(JsonNode actualJson, JsonNode expectedJson, List<String> errors) {
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
}
