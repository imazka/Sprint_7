package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonFileClient {
    public static final String COURIER_DATA_JSON_FILE_PATH = "src/test/resources/courier_data.json";
    public static final String NEW_ORDER_DATA_JSON_FILE_PATH = "src/test/resources/new_oder_data.json";

    public JsonElement dataTestFileRead(String path) {

        JsonElement jsonElement = null;

        try (FileReader jsonData = new FileReader(path)) {
            jsonElement = JsonParser.parseReader(jsonData).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonElement;

    }

}
