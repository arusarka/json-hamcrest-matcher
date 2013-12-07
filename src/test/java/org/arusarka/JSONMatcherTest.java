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
    public void shouldMatchSimpleJson() throws IOException {
        String actual = readFile("src/test/resources/simple/actual.json");
        String expected = readFile("src/test/resources/simple/expected.json");

        assertThat(actual, shouldMatchJson(expected));
    }

    @Test
    public void shouldMatchEvenIfAttributeIsAbsentInExpected() {
        String actual = readFile("src/test/resources/attribute_absent_in_expected/actual.json");
        String expected = readFile("src/test/resources/attribute_absent_in_expected/expected.json");

        assertThat(actual, shouldMatchJson(expected));
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
