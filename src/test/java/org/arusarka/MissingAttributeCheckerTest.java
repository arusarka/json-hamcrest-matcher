package org.arusarka;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MissingAttributeCheckerTest {
    private TestHelper testHelper = new TestHelper();
    private final MissingAttributeChecker missingAttributeChecker = new MissingAttributeChecker();

    @Test
    public void shouldMatchIfFieldNameIsMissingInExpected() {
        final List<String> errors = Collections.emptyList();
        assertTrue("Should match if attribute is missing in expected",
                missingAttributeChecker.doesMatch(testHelper.readActualToJsonTree("attribute_missing_in_expected"),
                        testHelper.readExpectedToJsonTree("attribute_missing_in_expected"), errors));
    }

    @Test
    public void shouldNotMatchIfFieldNameIsMissingInActual() {
        final List<String> errors = new ArrayList<String>();
        assertFalse("Should not match if attribute is missing in actual",
                missingAttributeChecker.doesMatch(testHelper.readActualToJsonTree("attribute_missing_in_actual"),
                        testHelper.readExpectedToJsonTree("attribute_missing_in_actual"), errors));

        assertFalse("Errors should not be empty.", errors.isEmpty());

        final String firstError = errors.get(0);

        assertTrue(firstError.contains("Expected attribute \"bar\" but is missing."));
    }
}
