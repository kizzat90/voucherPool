package com.boost.voucherpool.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Utilities {
    private static Utilities instance;

    private Utilities() {}

    public static Utilities getInstance() {
        if (instance == null) {
            instance = new Utilities();
        }
        return instance;
    }

    public <T> T retrieveResourceFromResponse(HttpResponse response, Class<T> clazz) throws IOException {
        String jsonFromResponse = EntityUtils.toString(response.getEntity());
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(jsonFromResponse, clazz);
    }

    public String getJsonOutputFromResponse(HttpResponse response) throws IOException {

        Object data = retrieveResourceFromResponse(response, Object.class);

        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    }
}
