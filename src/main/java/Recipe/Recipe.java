package Recipe;

import java.util.ArrayList;

/*
 * Recipe object
 */
public class Recipe {
    private String title;
    private String time;
    private String quantity;
    private String quantity_unit;
    private ArrayList<String> ingredients;
    private ArrayList<String> steps;
    private String image;

    public Recipe() {}

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setQuantity_unit(String quantity_unit) {
        this.quantity_unit = quantity_unit;
    }

    public void setSteps(ArrayList<String> steps) {
        this.steps = steps;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getSteps() {
        return steps;
    }

    public String getQuantity() {
        return quantity;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public String getQuantity_unit() {
        return quantity_unit;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return this.title;
    }
}