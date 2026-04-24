package stellar_burgers.userTests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import stellar_burgers.BaseTest;
import stellar_burgers.dataObjects.User;

import static org.hamcrest.CoreMatchers.equalTo;
import static stellar_burgers.steps.UserSteps.createUser;
import static stellar_burgers.steps.UserSteps.loginUser;

public class LoginUserTest extends BaseTest {

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void shouldLoginUser() {
        super.user = User.randomUser();

        createUser(super.user);

        Response response = loginUser(super.user);

        response.then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("user.email", equalTo(super.user.getEmail()));
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    public void shouldNotLoginWithWrongPassword() {
        super.user = User.randomUser();

        createUser(super.user);

        User wrongUser = new User(
                super.user.getEmail(),
                "wrongPassword",
                super.user.getName()
        );

        Response response = loginUser(wrongUser);

        response.then()
                .statusCode(401)
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Логин с неверным email")
    public void shouldNotLoginWithWrongEmail() {
        super.user = User.randomUser();

        createUser(super.user);

        User wrongUser = new User(
                "wrongemail@gmail.com",
                super.user.getPassword(),
                super.user.getName()
        );

        Response response = loginUser(wrongUser);

        response.then()
                .statusCode(401)
                .body("message", equalTo("email or password are incorrect"));
    }
}
