package DBManager;

import Recipe.Recipe;
import com.mongodb.*;

import java.util.ArrayList;

public class DBHandler {
    private DBCollection coll;

    public DBHandler() {
        try {
            MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://javalaurea:javalaurea2018@ds119374.mlab.com:19374/recipe"));
            DB recipeDB = mongoClient.getDB("recipe");
            this.coll = recipeDB.getCollection("favorite");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Recipe> getFavorites () {
        ArrayList<Recipe> favorites = new ArrayList<Recipe>();

        if (coll == null) {
            return favorites;
        }

        DBCursor cursor = this.coll.find();

        while (cursor.hasNext()) {
            Recipe recipe = new Recipe();
            DBObject obj = cursor.next();
            recipe.setTitle((String) obj.get("title"));
            recipe.setTime((String) obj.get("time"));
            recipe.setQuantity((String) obj.get("quantity"));
            recipe.setQuantity_unit((String) obj.get("quantity-unit"));
            recipe.setImage((String) obj.get("image"));

            BasicDBList list = (BasicDBList) obj.get("ingredients");
            ArrayList<String> res = new ArrayList<>();
            for(Object el: list) {
                res.add((String) el);
            }
            recipe.setIngredients(res);

            list = (BasicDBList) obj.get("steps");
            res = new ArrayList<>();
            for(Object el: list) {
                res.add((String) el);
            }
            recipe.setSteps(res);
            favorites.add(recipe);
        }

        return favorites;
    }

    public ArrayList<Recipe> addFavorite(Recipe recipe) {
        if (coll == null) {
            return new ArrayList<Recipe>();
        }
        BasicDBObject newRecipe = new BasicDBObject("title", recipe.getTitle())
                .append("time", recipe.getTime())
                .append("quantity", recipe.getQuantity())
                .append("quantity-unit", recipe.getQuantity_unit())
                .append("ingredients", recipe.getIngredients())
                .append("steps", recipe.getSteps())
                .append("image", recipe.getImage());

        coll.insert(newRecipe);
        return this.getFavorites();
    }

    public ArrayList<Recipe> removeFavorite(Recipe recipe) {
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("title", recipe.getTitle());		// Find documents with Joe Smith in name field

        coll.remove(searchQuery);
        return this.getFavorites();
    }
}
