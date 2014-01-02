package org.arusarka;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.arusarka.JSONMatcher.shouldContainJson;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class JSONMatcherTest {
    private TestHelper testHelper = new TestHelper();

    @Test
    public void shouldMatchSameJson() throws IOException {
        assertThat(testHelper.readActualJsonForScenario("same_json"),
                shouldContainJson(testHelper.readExpectedJsonForScenario("same_json")));
    }

    @Test
    public void shouldMatchEvenIfAttributeIsAbsentInExpected() {
        assertThat(testHelper.readActualJsonForScenario("attribute_missing_in_expected"),
                shouldContainJson(testHelper.readExpectedJsonForScenario("attribute_missing_in_expected")));
    }

    @Test
    public void shouldFailToMatchIfAttributeIsMissingInActual() {
        final JSONMatcher jsonMatcher = new JSONMatcher(testHelper.readExpectedJsonForScenario("attribute_missing_in_actual"), new ObjectMapper());
        assertFalse(jsonMatcher.matchesSafely(testHelper.readActualJsonForScenario("attribute_missing_in_actual")));
    }

    @Test
    public void shouldMatchArrayAtTopLevel() {
        assertThat(testHelper.readActualJsonForScenario("simple_array"),
                shouldContainJson(testHelper.readExpectedJsonForScenario("simple_array")));

    }

    @Test
    public void shouldMatchIfActualArrayContainsAllTheExpectedElements() {
        assertThat(testHelper.readActualJsonForScenario("expected_array_contains_matching_object"),
                shouldContainJson(testHelper.readExpectedJsonForScenario("expected_array_contains_matching_object")));

    }
}
