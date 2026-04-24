package stellar_burgers.steps;

import io.qameta.allure.Step;
import stellar_burgers.dataObjects.IngredientList;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class IngredientSteps {

    private static final String INGREDIENTS = "ingredients";

    @Step("Получение списка ингредиентов")
    public static IngredientList getIngredientsList() {
        return given()
                .get(INGREDIENTS)
                .then()
                .statusCode(200)
                .extract()
                .as(IngredientList.class);
    }

    @Step("Получение id ингредиентов")
    public static List<String> getIngredientsIds(int ingredientQuantity) {
        IngredientList ingredientList = getIngredientsList();

        return ingredientList.getData().stream()
                .limit(ingredientQuantity)
                .map(i -> i.get_id())
                .collect(Collectors.toList());
    }
}
