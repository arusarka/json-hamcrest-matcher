package org.arusarka;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MissingAttributeCheckerTest {
    private TestHelper testHelper = new TestHelper();
    private final MissingAttributeChecker missingAttributeChecker = new MissingAttributeChecker();

    @Test
    public void shouldMatchIfFieldNameIsMissingInExpected() {
        assertTrue("Should match if attribute is missing in expected",
                missingAttributeChecker.doesMatch(testHelper.readActualToJsonTree("attribute_missing_in_expected"),
                        testHelper.readExpectedToJsonTree("attribute_missing_in_expected")));
    }

    @Test
    public void shouldNotMatchIfFieldNameIsMissingInActual() {
        assertFalse("Should not match if attribute is missing in actual",
                missingAttributeChecker.doesMatch(testHelper.readActualToJsonTree("attribute_missing_in_actual"),
                        testHelper.readExpectedToJsonTree("attribute_missing_in_actual")));

        assertFalse(missingAttributeChecker.errors().isEmpty());

        final String firstError = missingAttributeChecker.errors().get(0);

        assertTrue(firstError.contains("Expected attribute \"bar\" but is missing."));
    }

    @Test
    public void shouldResetErrorsBeforeComparing() {
        missingAttributeChecker.doesMatch(testHelper.readActualToJsonTree("attribute_missing_in_actual"),
                testHelper.readExpectedToJsonTree("attribute_missing_in_actual"));

        missingAttributeChecker.doesMatch(testHelper.readActualToJsonTree("attribute_missing_in_actual"),
                testHelper.readExpectedToJsonTree("attribute_missing_in_actual"));


        assertTrue("Should have only one error.", missingAttributeChecker.errors().size() == 1);
    }
}
