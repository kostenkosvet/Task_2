package stellar_burgers.dataObjects;

import java.util.List;

public class IngredientList {
    private boolean success;
    private List<Ingredient> data;

    public List<Ingredient> getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }
}
