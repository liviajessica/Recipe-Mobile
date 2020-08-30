package id.ac.umn.LiviaJessica.remote;

import id.ac.umn.LiviaJessica.models.Recipes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface F2FClient {
    @GET("search")
    Call<Recipes> ingredientSearch(@Query("q")List<String> ingredients, @Query("key") String key);
}
