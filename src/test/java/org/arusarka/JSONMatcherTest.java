package org.arusarka;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.arusarka.JSONHamcrestMatcher.shouldMatchJson;

public class JSONMatcherTest {
    @Test
    public void shouldMatchSimpleJson() throws IOException {
        String actual = readFile("src/test/resources/simple_actual.json");

        String expected = readFile("src/test/resources/simple_actual.json");

        Assert.assertThat(actual, shouldMatchJson(expected));
    }

    private String readFile(String filePath) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(filePath));
        return Charset.defaultCharset().decode(ByteBuffer.wrap(encoded)).toString();
    }
}
