package stellar_burgers.orderTests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import stellar_burgers.BaseTest;
import stellar_burgers.dataObjects.Order;
import stellar_burgers.dataObjects.User;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static stellar_burgers.steps.IngredientSteps.getIngredientsIds;
import static stellar_burgers.steps.OrderSteps.createOrder;
import static stellar_burgers.steps.UserSteps.createUserAndGetToken;

@RunWith(Parameterized.class)
public class CreateOrderParameterizedTest extends BaseTest {

    private final int ingredientsCount;

    public CreateOrderParameterizedTest(int ingredientsCount) {
        this.ingredientsCount = ingredientsCount;
    }

    @Parameterized.Parameters(name = "Количество ингредиентов: {0}")
    public static Object[][] data() {
        return new Object[][]{
                {1},
                {3},
                {5}
        };
    }

    @Test
    @DisplayName("Создание заказа с разным количеством ингредиентов")
    public void shouldCreateOrderWithDifferentIngredientsCount() {

        user = User.randomUser();
        String token = createUserAndGetToken(user);

        Order order = new Order(getIngredientsIds(ingredientsCount));

        Response response = createOrder(order, token);

        response.then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }
}
