package stellar_burgers.userTests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import stellar_burgers.BaseTest;
import stellar_burgers.dataObjects.User;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static stellar_burgers.steps.UserSteps.createUserAndGetToken;
import static stellar_burgers.steps.UserSteps.updateUser;

@RunWith(Parameterized.class)
public class UpdateUserParametrizedTest extends BaseTest {

    private final String caseName;
    private final boolean changeEmail;
    private final boolean changePassword;
    private final boolean changeName;

    public UpdateUserParametrizedTest(String caseName,
                                      boolean changeEmail,
                                      boolean changePassword,
                                      boolean changeName) {
        this.caseName = caseName;
        this.changeEmail = changeEmail;
        this.changePassword = changePassword;
        this.changeName = changeName;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Изменение всех полей", true, true, true},
                {"Изменение только email", true, false, false},
                {"Изменение только password", false, true, false},
                {"Изменение только name", false, false, true}
        });
    }

    @Test
    @DisplayName("Изменение данных пользователя")
    public void shouldUpdateUserFields() {
        super.user = User.randomUser();
        String token = createUserAndGetToken(super.user);

        String newEmail = changeEmail
                ? "new" + System.currentTimeMillis() + "@mail.com"
                : super.user.getEmail();

        String newPassword = changePassword
                ? "newPassword123"
                : super.user.getPassword();

        String newName = changeName
                ? "NewName"
                : super.user.getName();

        User updatedUser = new User(newEmail, newPassword, newName);

        Response response = updateUser(updatedUser, token);

        response.then()
                .statusCode(200)
                .body("success", equalTo(true));


        if (changeEmail) {
            response.then().body("user.email", equalTo(newEmail));
        }
        if (changeName) {
            response.then().body("user.name", equalTo(newName));
        }

        super.user = updatedUser;
    }
}
