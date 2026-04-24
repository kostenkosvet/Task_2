package stellar_burgers.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import stellar_burgers.dataObjects.Order;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    private static final String ORDERS = "orders";

    @Step("Создание заказа")
    public static Response createOrder(Order order, String token) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .body(order)
                .post(ORDERS);
    }

    @Step("Создание заказа без авторизации")
    public static Response createOrderWithoutAuth(Order order) {
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .post(ORDERS);
    }

    @Step("Получение списка заказов пользователя")
    public static Response getOrders(String token) {
        return given()
                .header("Authorization", token)
                .get(ORDERS);
    }

    @Step("Получение списка заказов без авторизации")
    public static Response getOrdersWithoutAuth() {
        return given()
                .get(ORDERS);
    }
}
