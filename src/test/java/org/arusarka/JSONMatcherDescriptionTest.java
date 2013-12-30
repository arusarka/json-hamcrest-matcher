package org.arusarka;

import org.junit.Test;

import static org.arusarka.JSONMatcher.shouldContainJson;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class JSONMatcherDescriptionTest {
    private TestHelper testHelper = new TestHelper();


    @Test
    public void shouldDisplayErrorIfAttributeIsMissingInActual() {
        assertScenarioWithErrorMessage("attribute_missing_in_actual", "Expected attribute \"bar\" but is missing.");
    }

    private void assertScenarioWithErrorMessage(String scenarioName, String message) {
        try {
            assertThat(testHelper.readActualJsonForScenario(scenarioName),
                    shouldContainJson(testHelper.readExpectedJsonForScenario(scenarioName)));
            fail("should not have matched the json");
        } catch (AssertionError assertionError) {
            final String errorMessage = assertionError.getLocalizedMessage();
            assertTrue("Missing error message ->" + message + "" , errorMessage.contains(message));
        }
    }
}
