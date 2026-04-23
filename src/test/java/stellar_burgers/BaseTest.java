package stellar_burgers;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import stellar_burgers.dataObjects.User;

import static stellar_burgers.steps.UserSteps.deleteUserByToken;

public class BaseTest {
    private final String BASE_URL = "https://stellarburgers.education-services.ru/api/";

    public User user;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.filters(new AllureRestAssured());
    }

    @After
    public void teardown() {
        if (user != null) {
            deleteUserByToken(user);
        }
    }
}
