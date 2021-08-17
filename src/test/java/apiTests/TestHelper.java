package apiTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class TestHelper {
    private static ObjectMapper objectMapper = new ObjectMapper();



    public static Object getObject(HttpResponse httpResponse, Class clazz) {
        try {
            return objectMapper.readValue(httpResponse.getEntity().getContent(), clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
