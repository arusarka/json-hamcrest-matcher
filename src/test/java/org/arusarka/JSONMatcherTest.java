package org.arusarka;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.arusarka.JSONMatcher.shouldMatchJson;
import static org.junit.Assert.assertThat;

public class JSONMatcherTest {
    @Test
    public void shouldMatchSameJson() throws IOException {
        assertThat(readActualJsonForScenario("same_json"), shouldMatchJson(readExpectedJsonForScenario("same_json")));
    }

    @Test
    public void shouldMatchEvenIfAttributeIsAbsentInExpected() {
        assertThat(readActualJsonForScenario("attribute_absent_in_expected"), shouldMatchJson(readExpectedJsonForScenario("attribute_absent_in_expected")));
    }

    private String readExpectedJsonForScenario(final String scenarioName) {
        return readFile("src/test/resources/fixtures/" + scenarioName + "/expected.json");
    }

    private String readActualJsonForScenario(final String scenarioName) {
        return readFile("src/test/resources/fixtures/" + scenarioName + "/actual.json");
    }

    private String readFile(String filePath) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(filePath));
            return Charset.defaultCharset().decode(ByteBuffer.wrap(encoded)).toString();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
