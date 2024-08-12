package org.example.courier.tests;

import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.example.BaseTest;
import org.example.courier.Courier;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginCourierTest extends BaseTest {

    //авторизация зарегистрированного курьера
    @Test
    public void loginCourierWithCorrectDataTest() {

        Courier registrationCourier = new Courier(courier.getLogin(), courier.getPassword(), courier.getFirstName());
        ValidatableResponse registrationResponse = courierClient.create(registrationCourier);
        assertEquals(HttpStatus.SC_CREATED, registrationResponse.extract().statusCode());
        assertTrue(registrationResponse.extract().body().jsonPath().getBoolean("ok"));

        Courier loginCourier = new Courier(courier.getLogin(), courier.getPassword());
        ValidatableResponse loginResponse = courierClient.login(loginCourier);

        assertEquals(HttpStatus.SC_OK, loginResponse.extract().statusCode());
        int id = loginResponse.extract().body().jsonPath().getInt("id");
        assertTrue(id > 0);

        Courier deleteCourier = new Courier(id);
        ValidatableResponse deleteResponse = courierClient.delete(deleteCourier);
        int statusCode = deleteResponse.extract().statusCode();
        assertEquals(HttpStatus.SC_OK, statusCode);
        assertTrue(deleteResponse.extract().body().jsonPath().getBoolean("ok"));

    }

    //авторизация только с логином зарегистрированного курьера
    @Test
    public void loginCourierOnlyWithLoginTest() {

        Courier registrationCourier = new Courier(courier.getLogin(), courier.getPassword(), courier.getFirstName());
        ValidatableResponse registrationResponse = courierClient.create(registrationCourier);
        assertEquals(HttpStatus.SC_CREATED, registrationResponse.extract().statusCode());
        assertTrue(registrationResponse.extract().body().jsonPath().getBoolean("ok"));

        Courier loginCourierWithLogin = new Courier(courier.getLogin());
        ValidatableResponse loginResponseWithLogin = courierClient.login(loginCourierWithLogin);

        Courier loginCourier = new Courier(courier.getLogin(), courier.getPassword());
        ValidatableResponse loginResponse = courierClient.login(loginCourier);

        assertEquals(HttpStatus.SC_OK, loginResponse.extract().statusCode());
        int id = loginResponse.extract().body().jsonPath().getInt("id");
        assertTrue(id > 0);

        Courier deleteCourier = new Courier(id);
        ValidatableResponse deleteResponse = courierClient.delete(deleteCourier);
        int statusCode = deleteResponse.extract().statusCode();
        assertEquals(HttpStatus.SC_OK, statusCode);
        assertTrue(deleteResponse.extract().body().jsonPath().getBoolean("ok"));

        String expectadMessage = "Недостаточно данных для входа";
        assertEquals(HttpStatus.SC_BAD_REQUEST, loginResponseWithLogin.extract().statusCode());
        assertEquals(expectadMessage, loginResponseWithLogin.extract().body().jsonPath().getString("message"));

    }

    //авторизация незарегистрированного курьера
    @Test
    public void loginNotRegisteredCourierTest() {

        Courier loginCourier = new Courier(courier.getLogin() + r, courier.getPassword() + new Random().nextInt(90000));
        ValidatableResponse loginResponse = courierClient.login(loginCourier);

        String expectadMessage = "Учетная запись не найдена";
        assertEquals(HttpStatus.SC_NOT_FOUND, loginResponse.extract().statusCode());
        assertEquals(expectadMessage, loginResponse.extract().body().jsonPath().getString("message"));

    }

    //авторизация зарегистрированного курьера c неправильным паролем
    @Test
    public void loginCourierWithIncorrectLoginTest() {

        Courier loginCourier = new Courier(courier.getLogin(), courier.getPassword() + new Random().nextInt(90000));
        ValidatableResponse loginResponse = courierClient.login(loginCourier);

        String expectadMessage = "Учетная запись не найдена";
        assertEquals(HttpStatus.SC_NOT_FOUND, loginResponse.extract().statusCode());
        assertEquals(expectadMessage, loginResponse.extract().body().jsonPath().getString("message"));

    }
}
