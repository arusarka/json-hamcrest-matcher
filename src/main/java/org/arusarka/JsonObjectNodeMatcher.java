package org.arusarka;

import org.codehaus.jackson.JsonNode;

import java.util.List;

public class JSONObjectNodeMatcher {
    private final MissingAttributeChecker missingAttributeChecker;

    public JSONObjectNodeMatcher(MissingAttributeChecker missingAttributeChecker) {
        this.missingAttributeChecker = missingAttributeChecker;
    }

    public Boolean doesMatch(JsonNode actualNode, JsonNode expectedNode, List<String> errors) {
       return this.missingAttributeChecker.doesMatch(actualNode, expectedNode, errors);
    }
}
