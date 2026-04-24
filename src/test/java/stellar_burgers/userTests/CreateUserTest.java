package stellar_burgers.userTests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import stellar_burgers.BaseTest;
import stellar_burgers.dataObjects.User;

import static org.hamcrest.CoreMatchers.equalTo;
import static stellar_burgers.steps.UserSteps.createUser;


public class CreateUserTest extends BaseTest {

    @Test
    @DisplayName("Создание уникального пользователя")
    public void shouldCreateUser() {
        super.user = User.randomUser();

        Response response = createUser(super.user);

        response.then()
                .statusCode(200)
                .body("success", equalTo(true));
    }


    @Test
    @DisplayName("Создание уже существующего пользователя")
    public void shouldNotCreateExistingUser() {
        super.user = User.randomUser();

        createUser(super.user);
        Response response = createUser(super.user);

        response.then()
                .statusCode(403)
                .body("message", equalTo("User already exists"));
    }

}
