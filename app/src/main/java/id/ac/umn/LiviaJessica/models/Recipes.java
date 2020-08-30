package id.ac.umn.LiviaJessica.models;

import id.ac.umn.LiviaJessica.models.Recipe;

import java.util.List;

public class Recipes {
    List<Recipe> recipes;
    Integer count;

    public Recipes(List<Recipe> recipes, Integer count) {
        this.recipes = recipes;
        this.count = count;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
