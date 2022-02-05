package dgroomes.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonUtil {

    public static final ObjectWriter writer = new ObjectMapper().writerWithDefaultPrettyPrinter();

    public static String toJson(Object obj) {
        try {
            return writer.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to serialize to JSON", e);
        }
    }
}
