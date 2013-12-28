package org.arusarka;

import org.junit.Test;

import java.io.IOException;

import static org.arusarka.JSONMatcher.shouldMatchJson;
import static org.junit.Assert.assertThat;

public class JSONMatcherTest {
    private JSONTestHelper jsonTestHelper = new JSONTestHelper();

    @Test
    public void shouldMatchSameJson() throws IOException {
        assertThat(jsonTestHelper.readActualJsonForScenario("same_json"),
                shouldMatchJson(jsonTestHelper.readExpectedJsonForScenario("same_json")));
    }

    @Test
    public void shouldMatchEvenIfAttributeIsAbsentInExpected() {
        assertThat(jsonTestHelper.readActualJsonForScenario("attribute_absent_in_expected"),
                shouldMatchJson(jsonTestHelper.readExpectedJsonForScenario("attribute_absent_in_expected")));
    }
}
