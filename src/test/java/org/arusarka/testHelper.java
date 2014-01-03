package org.arusarka;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestHelper {
    public String readExpectedJsonForScenario(final String scenarioName) {
        return readFile("src/test/resources/fixtures/" + scenarioName + "/expected.json");
    }

    public String readActualJsonForScenario(final String scenarioName) {
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
