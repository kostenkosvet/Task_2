package stellar_burgers.orderTests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import stellar_burgers.BaseTest;
import stellar_burgers.dataObjects.Order;
import stellar_burgers.dataObjects.User;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsEqual.equalTo;
import static stellar_burgers.steps.IngredientSteps.getIngredientsIds;
import static stellar_burgers.steps.OrderSteps.*;
import static stellar_burgers.steps.UserSteps.createUserAndGetToken;

public class GetUserOrdersTest extends BaseTest {

    @Test
    @DisplayName("Получение заказов авторизованным пользователем")
    public void shouldGetOrdersWithAuth() {

        super.user = User.randomUser();
        String token = createUserAndGetToken(user);

        Order order = new Order(getIngredientsIds(2));
        createOrder(order, token);

        Response response = getOrders(token);

        response.then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("orders.size()", greaterThan(0));
    }

    @Test
    @DisplayName("Получение заказов без авторизации")
    public void shouldNotGetOrdersWithoutAuth() {

        Response response = getOrdersWithoutAuth();

        response.then()
                .statusCode(401)
                .body("message", equalTo("You should be authorised"));
    }
}
