package org.arusarka;

import org.codehaus.jackson.JsonNode;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class JSONObjectNodeMatcherTest {
    private TestHelper testHelper = new TestHelper();

    @Test
    public void shouldDelegateAttributeMissingCheck() {
        final MissingAttributeChecker missingAttributeCheckerMock = mock(MissingAttributeChecker.class);
        final JSONObjectNodeMatcher jSONObjectNodeMatcher = new JSONObjectNodeMatcher(missingAttributeCheckerMock);
        final JsonNode expectedJsonTree = mock(JsonNode.class);
        final JsonNode actualJsonTree = mock(JsonNode.class);
        final List<String> errors = mock(List.class);

        when(missingAttributeCheckerMock.doesMatch(eq(actualJsonTree), eq(expectedJsonTree), eq(errors))).thenReturn(true);

        assertTrue(jSONObjectNodeMatcher.doesMatch(actualJsonTree, expectedJsonTree, errors));

        verify(missingAttributeCheckerMock).doesMatch(eq(actualJsonTree), eq(expectedJsonTree), eq(errors));
    }
}
