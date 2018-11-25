import Handlers.GUIHandler;
import Items.Scraper;
import Items.Recipe;

public class Main {
    public static void main (String[] args) {
        GUIHandler test = new GUIHandler(800 , 600, "What to eat today ?");

        Scraper t = new Scraper();

        Recipe recipe = t.generate();

        System.out.println(recipe.title);
        System.out.println(recipe.time);
        System.out.print(recipe.quantity + " ");
        System.out.println(recipe.quantity_unit);
        for (String ingredient : recipe.ingredients) {
            System.out.println(ingredient);
        }
        for (String step : recipe.steps) {
            System.out.println(step);
        }
    }
}
