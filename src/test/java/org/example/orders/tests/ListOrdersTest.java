package org.example.orders.tests;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.restassured.response.ValidatableResponse;
import org.example.BaseTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ListOrdersTest extends BaseTest {

    //тест получения списка заказов
    @Test
    public void getListOrdersOfCourierTest() {

            ValidatableResponse getCourierOrderListResponse = orderClient.getOrderList();
            JsonObject responseBodyObject = getCourierOrderListResponse.extract().body().as(JsonObject.class);
            JsonArray ordersArray = responseBodyObject.getAsJsonArray("orders");
            assertNotNull(ordersArray);
            assertTrue(ordersArray.size() > 0);

    }

}
