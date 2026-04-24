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
import static stellar_burgers.steps.UserSteps.createUser;

@RunWith(Parameterized.class)
public class CreateUserParametrizedTest extends BaseTest {
    private final String fieldName;
    private final User incompleteUser;


    public CreateUserParametrizedTest(String fieldName, User incompleteUser) {
        this.fieldName = fieldName;
        this.incompleteUser = incompleteUser;
    }

    @Parameterized.Parameters(name = "Отсутствует поле: {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"email", User.randomUser().withEmail(null)},
                {"password", User.randomUser().withPassword(null)},
                {"name", User.randomUser().withName(null)}
        });
    }

    @Test
    @DisplayName("Создание пользователя без обязательного поля")
    public void shouldNotCreateUserWithoutRequiredField() {
        Response response = createUser(incompleteUser);
        response.then()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }
}
