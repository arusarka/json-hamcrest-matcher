package org.arusarka;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

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

    public JsonNode readExpectedToJsonTree(final String scenarioName) {
        return convertToJsonTree(readExpectedJsonForScenario(scenarioName));
    }

    public JsonNode readActualToJsonTree(final String scenarioName) {
        return convertToJsonTree(readActualJsonForScenario(scenarioName));
    }

    private JsonNode convertToJsonTree(String jsonString) {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readTree(jsonString);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }
}
