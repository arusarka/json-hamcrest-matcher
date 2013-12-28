package org.arusarka;

import org.junit.Test;

import static org.arusarka.JSONMatcher.shouldMatchJson;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class JSONMatcherDescriptionTest {
    private JSONTestHelper jsonTestHelper = new JSONTestHelper();


    @Test
    public void shouldDisplayErrorIfAttributeIsMissingInActual() {
        try {
            assertThat(jsonTestHelper.readActualJsonForScenario("attribute_missing_in_actual"),
                    shouldMatchJson(jsonTestHelper.readExpectedJsonForScenario("attribute_missing_in_actual")));
            fail("should not have matched the json");
        } catch (AssertionError assertionError) {
            final String errorMessage = assertionError.getLocalizedMessage();
            assertTrue("error message should contain missing attribute", errorMessage.contains("Expected attribute \"bar\" but is missing."));
        }
    }
}
