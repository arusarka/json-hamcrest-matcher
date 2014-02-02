package com.arusarka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static com.arusarka.JSONMatcher.shouldContainJson;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class JSONMatcherTest {
    private TestHelper testHelper = new TestHelper();

    @Test
    public void shouldMatchSameJson() throws IOException {
        assertThat(testHelper.readActualJsonForScenario("passes_when_same_json"),
                shouldContainJson(testHelper.readExpectedJsonForScenario("passes_when_same_json")));
    }

    @Test
    public void shouldMatchEvenIfAttributeIsAbsentInExpected() {
        assertThat(testHelper.readActualJsonForScenario("passes_when_attribute_missing_in_expected"),
                shouldContainJson(testHelper.readExpectedJsonForScenario("passes_when_attribute_missing_in_expected")));
    }

    @Test
    public void shouldFailToMatchIfAttributeIsMissingInActual() {
        final JSONMatcher jsonMatcher = new JSONMatcher(testHelper.readExpectedJsonForScenario("fails_when_attribute_missing_in_actual"), new ObjectMapper());
        assertFalse(jsonMatcher.matchesSafely(testHelper.readActualJsonForScenario("fails_when_attribute_missing_in_actual")));
    }

    @Test
    public void shouldMatchArrayAtTopLevel() {
        assertThat(testHelper.readActualJsonForScenario("passes_when_expected_array_contains_matching_elements"),
                shouldContainJson(testHelper.readExpectedJsonForScenario("passes_when_expected_array_contains_matching_elements")));

    }

    @Test
    public void shouldMatchIfActualArrayContainsAllTheExpectedElements() {
        assertThat(testHelper.readActualJsonForScenario("passes_when_expected_array_contains_lesser_number_of_matching_object"),
                shouldContainJson(testHelper.readExpectedJsonForScenario("passes_when_expected_array_contains_lesser_number_of_matching_object")));

    }
}
