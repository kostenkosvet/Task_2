package stellar_burgers.orderTests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import stellar_burgers.BaseTest;
import stellar_burgers.dataObjects.Order;
import stellar_burgers.dataObjects.User;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static stellar_burgers.steps.IngredientSteps.getIngredientsIds;
import static stellar_burgers.steps.OrderSteps.createOrder;
import static stellar_burgers.steps.OrderSteps.createOrderWithoutAuth;
import static stellar_burgers.steps.UserSteps.createUserAndGetToken;

public class CreateOrderTest extends BaseTest {

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void shouldCreateOrderWithoutAuth() {
        Order order = new Order(getIngredientsIds(3));

        Response response = createOrderWithoutAuth(order);

        response.then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void shouldNotCreateOrderWithoutIngredients() {

        super.user = User.randomUser();
        String token = createUserAndGetToken(super.user);

        Order order = new Order(null);

        Response response = createOrder(order, token);

        response.then()
                .statusCode(400)
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    public void shouldNotCreateOrderWithInvalidIngredients() {

        super.user = User.randomUser();
        String token = createUserAndGetToken(super.user);

        Order order = new Order(List.of("61c0c5a71d1f820bdaaa6d"));

        Response response = createOrder(order, token);

        response.then()
                .statusCode(500);
    }

}
