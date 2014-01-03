package com.arusarka;

import org.junit.Test;

import static com.arusarka.JSONMatcher.shouldContainJson;
import static org.junit.Assert.*;

public class JSONMatcherDescriptionTest {
    private TestHelper testHelper = new TestHelper();


    @Test
    public void shouldDisplayErrorIfAttributeIsMissingInActual() {
        assertScenarioWithErrorMessage("fails_when_attribute_missing_in_actual",
                "Expected attribute \"bar\" inside \"top level\" in response but is missing.");
    }

    private void assertScenarioWithErrorMessage(String scenarioName, String message) {
        try {
            assertThat(testHelper.readActualJsonForScenario(scenarioName),
                    shouldContainJson(testHelper.readExpectedJsonForScenario(scenarioName)));
            fail("should not have matched the json");
        } catch (AssertionError assertionError) {
            final String errorMessage = assertionError.getLocalizedMessage();
            assertTrue("\nExpected error message -> " + message + "\nGot error message -> " + errorMessage , errorMessage.contains(message));
        }
    }
}
