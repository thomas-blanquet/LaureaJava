package Recipe;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.io.*;
import java.util.ArrayList;


/*
 *  Web Scraper for marmitton.org
 */
public class Scraper {
    private Document doc = null;

    /*
     *  Constructor
     */
    public Scraper() {
    }

    /*
     *  Get a random recipe on the web site and convert it in a recipe object.
     *  Return a recipe object.
     */
    public Recipe generate() {
        try {
            doc = Jsoup.connect("https://www.marmiton.org/recettes/recette-hasard.aspx").get();

            Recipe recipe = new Recipe();
            recipe.setTitle(this.getInfo("main-title"));
            recipe.setTime(this.getInfo("recipe-infos__total-time__value"));
            recipe.setQuantity(this.getInfo("recipe-infos__quantity__value"));
            recipe.setQuantity_unit(this.getInfoDetails("recipe-infos__quantity", "recipe-infos__item-title"));
            recipe.setIngredients(this.getIngredients());
            recipe.setSteps(this.getSteps());
            recipe.setImage(this.getImage());

            return recipe;

        } catch (IOException ioe) {
            ioe.printStackTrace();

            this.doc = null;

            return null;
        }
    }

    /*
     *  Get steps from the web page.
     *  Return an array of all the steps.
     */
    private ArrayList<String> getSteps() {
        ArrayList<String> steps = new ArrayList<>();

        Elements step_list = this.doc.getElementsByClass("recipe-preparation__list__item");
        for (Element step : step_list) {
            String stp = "";
            stp += step.text();

            steps.add(stp);
        }
        return steps;
    }

    private String getImage() {
        Element img = this.doc.getElementById("af-diapo-desktop-0_img");
        return img.absUrl("src");
    }

    /*
     *  Get ingredients from the web page.
     *  Return an array of all the ingredients.
     */
    private ArrayList<String> getIngredients() {
        ArrayList<String> ingredients = new ArrayList<>();

        Elements ingredient_list = this.doc.getElementsByClass("recipe-ingredients__list__item");
        for (Element ingredient : ingredient_list) {
            String ingred = "";

            Elements qtty = ingredient.getElementsByClass("recipe-ingredient-qt");
            for (int i = 0; i < qtty.size(); i++) {
                if (i == 0) ingred += qtty.get(i).text();
            }
            if (ingred.length() > 0) ingred += " ";
            Elements elem = ingredient.getElementsByClass("ingredient");
            for (int i = 0; i < elem.size(); i++) {
                if (i == 0) ingred += elem.get(i).text();
            }

            ingredients.add(ingred);
        }
        return ingredients;
    }

    /*
     * Get an info in a html element with a specific class.
     * Return the text of this element.
     */
    private String getInfo(String info_class) {
        if (this.doc == null)
            return "";

        Elements elem = this.doc.getElementsByClass(info_class);
        if (elem.size() > 0) {
            return elem.get(0).text();
        }
        return "";
    }
    /*
    * Get an info in a html sub-element with a specific class.
    * Return the text of this element.
    */
    private String getInfoDetails(String info_class, String span_class) {
        if (this.doc == null)
            return "";

        Elements elems = this.doc.getElementsByClass(info_class);
        for (Element elem : elems) {
            Elements data = elem.getElementsByClass(span_class);
            if(data.size() > 0) {
                return data.get(0).text();
            }
        }
        return "";
    }
}