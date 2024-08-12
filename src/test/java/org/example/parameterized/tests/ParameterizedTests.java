package org.example.parameterized.tests;

import com.google.gson.JsonObject;
import io.restassured.response.ValidatableResponse;
import org.example.BaseTest;
import org.example.JsonFileClient;
import org.example.orders.ColoursEnum;
import org.example.orders.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParameterizedTests extends BaseTest {

    //создание заказов
    @ParameterizedTest
    @EnumSource(ColoursEnum.class)
    public void createOrderWithoutColorTest(ColoursEnum coloursEnum) {

        String[] colours = coloursEnum.getColours();
        Order createOrder = order;
        createOrder.setColour(colours);

        ValidatableResponse createResponse = orderClient.create(createOrder);
        JsonObject bodyJsonObject = createResponse.extract().body().as(JsonObject.class);
        assertTrue(bodyJsonObject.has("track"));
        int track = createResponse.extract().body().jsonPath().getInt("track");
        assertTrue(track > 0);

        ValidatableResponse getOrderResponse = orderClient.getOrder(track);
        JsonObject jsonObject = getOrderResponse.extract().body().as(JsonObject.class);
        assertTrue(jsonObject.getAsJsonObject("order").has("track"));

    }

}
