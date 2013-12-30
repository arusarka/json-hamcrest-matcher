package org.arusarka;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.arusarka.JSONMatcher.shouldContainJson;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

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
    public void shouldDelegateMatchingToObjectMatcher() throws IOException {
        final JSONObjectNodeMatcher jsonObjectNodeMatcherMock = mock(JSONObjectNodeMatcher.class);
        final ObjectMapper objectMapperMock = mock(ObjectMapper.class);

        final String expectedJsonString = testHelper.readExpectedJsonForScenario("simple_non_matching");
        final String actualJsonString = testHelper.readActualJsonForScenario("simple_non_matching");

        final JsonNode expectedJsonTree = mock(JsonNode.class);
        final JsonNode actualJsonTree = mock(JsonNode.class);

        when(objectMapperMock.readTree(expectedJsonString)).thenReturn(expectedJsonTree);
        when(objectMapperMock.readTree(actualJsonString)).thenReturn(actualJsonTree);
        when(jsonObjectNodeMatcherMock.doesMatch(eq(actualJsonTree), eq(expectedJsonTree), any(List.class)))
                .thenReturn(false);

        final JSONMatcher jsonMatcher = new JSONMatcher(expectedJsonString, jsonObjectNodeMatcherMock, objectMapperMock);
        assertFalse("Should be \"false\" as object node match is setup to fail", jsonMatcher.matchesSafely(actualJsonString));

        verify(jsonObjectNodeMatcherMock).doesMatch(eq(actualJsonTree), eq(expectedJsonTree), any(List.class));
    }
}
