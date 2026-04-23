package stellar_burgers.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import stellar_burgers.dataObjects.User;

import static io.restassured.RestAssured.given;

public class UserSteps {
    private static final String CREATE = "auth/register";
    private static final String LOGIN = "auth/login";
    private static final String DELETE = "auth/user";

    @Step("Создание пользователя")
    public static Response  createUser(User user) {
        return given()
                .contentType("application/json")
                .header("Content-type", "application/json")
                .body(user)
                .post(CREATE);
    }

    @Step("Логин пользователя")
    public static Response loginUser(User user) {
        return given()
                .contentType("application/json")
                .header("Content-type", "application/json")
                .body(user)
                .post(LOGIN);
    }

    @Step("Логин пользователя")
    public static String getUserToken(User user) {
        return loginUser(user).then().extract().path("accessToken");
    }

    @Step("Удаление пользователя")
    public static Response deleteUser(String accessToken) {
        return given()
                .contentType("application/json")
                .header("Authorization", accessToken)
                .delete(DELETE);
    }

    @Step("Удаление пользователя по токену")
    public static Response deleteUserByToken(User user) {
        String accessToken = getUserToken(user);
        return given()
                .contentType("application/json")
                .header("Authorization", accessToken)
                .delete(DELETE);
    }
}
