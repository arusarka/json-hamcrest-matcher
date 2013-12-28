package org.arusarka;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MissingAttributeCheckerTest {
    private TestHelper testHelper = new TestHelper();

    @Test
    public void shouldMatchIfFieldNameIsMissingInExpected() {
        final MissingAttributeChecker missingAttributeChecker = new MissingAttributeChecker();

        assertTrue("Should match if attribute is missing in expected",
                missingAttributeChecker.doesMatch(testHelper.readActualToJsonTree("attribute_missing_in_expected"),
                        testHelper.readExpectedToJsonTree("attribute_missing_in_expected")));

    }

    @Test
    public void shouldNotMatchIfFieldNameIsMissingInActual() {
        final MissingAttributeChecker missingAttributeChecker = new MissingAttributeChecker();

        assertFalse("Should not match if attribute is missing in actual",
                missingAttributeChecker.doesMatch(testHelper.readActualToJsonTree("attribute_missing_in_actual"),
                        testHelper.readExpectedToJsonTree("attribute_missing_in_actual")));

        assertFalse(missingAttributeChecker.errors().isEmpty());

        final String firstError = missingAttributeChecker.errors().get(0);

        assertTrue(firstError.contains("Expected attribute \"bar\" but is missing."));
    }

}
