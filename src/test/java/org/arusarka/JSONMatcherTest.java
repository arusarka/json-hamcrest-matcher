package org.arusarka;

import org.junit.Test;

import java.io.IOException;

import static org.arusarka.JSONMatcher.shouldContainJson;
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
}
