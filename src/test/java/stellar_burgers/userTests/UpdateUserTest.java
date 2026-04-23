package stellar_burgers.userTests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import stellar_burgers.BaseTest;
import stellar_burgers.dataObjects.User;

import static org.hamcrest.CoreMatchers.equalTo;
import static stellar_burgers.steps.UserSteps.*;

public class UpdateUserTest extends BaseTest {

    @Test
    @DisplayName("Изменение данных без авторизации")
    public void shouldNotUpdateUserWithoutAuth() {
        super.user = User.randomUser();

        createUser(super.user);

        User updatedUser = new User(
                super.user.getEmail(),
                "newPassword123",
                "NewName"
        );

        Response response = updateUserWithoutAuth(updatedUser);

        response.then()
                .statusCode(401)
                .body("message", equalTo("You should be authorised"));
    }


    @Test
    @DisplayName("Нельзя изменить email на уже существующий")
    public void shouldNotUpdateToExistingEmail() {
        super.users.add(User.randomUser());
        super.users.add(User.randomUser());

        createUser(users.get(0));
        createUser(users.get(1));

        String token = getUserToken(users.get(0));

        User updatedUser = new User(
                users.get(1).getEmail(),
                users.get(0).getPassword(),
                users.get(0).getName()
        );

        Response response = updateUser(updatedUser, token);

        response.then()
                .statusCode(403)
                .body("message", equalTo("User with such email already exists"));
    }
}
