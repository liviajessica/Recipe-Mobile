package id.ac.umn.LiviaJessica;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import id.ac.umn.LiviaJessica.models.Recipe;
import id.ac.umn.LiviaJessica.remote.F2FClient;
import id.ac.umn.LiviaJessica.R;
import id.ac.umn.LiviaJessica.models.Recipes;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecipeRecycler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecipeRecycler = findViewById(R.id.recipe_recycler);
        mRecipeRecycler.setLayoutManager(new LinearLayoutManager(this));
        SearchView searchView = findViewById(R.id.recipe_searchview);
//        Log.d("a","a");
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://food2fork.com/api/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
//        Log.d("b","b");
        final F2FClient client = retrofit.create(F2FClient.class);
//        Log.d("bb","bb");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_menu);
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                longclick();
                return true;
            }
        });
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "data sudah teregistrasi", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<String> ingredients = new ArrayList<>();
//                Log.d("c","c");
                ingredients.add(query);
                searchView.clearFocus();
//                Log.d("d","d");
                client.ingredientSearch(ingredients, getString(R.string.api_key)).enqueue(new Callback<Recipes>() {
                    @Override
                    public void onResponse(Call<Recipes> call, Response<Recipes> response) {
//                        Log.d("e","e");
                        if(response.body() != null && response.body().getRecipes() != null) {
                            if(response.body().getRecipes().isEmpty()) {
//                                Log.d("f","f");
                                mRecipeRecycler.setAdapter(new RecipeResultsAdapter(MainActivity.this, new ArrayList<Recipe>()));
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                                builder.setTitle(R.string.no_results_title)
                                        .setMessage(R.string.no_results_message)
                                        .setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss())
                                        .show();
                            } else {
                                mRecipeRecycler.setAdapter(new RecipeResultsAdapter(MainActivity.this, response.body().getRecipes()));
                            }
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                            builder.setTitle(R.string.general_error_title)
                                    .setMessage(R.string.general_error_message)
                                    .setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss())
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Recipes> call, Throwable t) {
                        mRecipeRecycler.setAdapter(new RecipeResultsAdapter(MainActivity.this, new ArrayList<Recipe>()));
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                        builder.setTitle(R.string.network_error_title)
                                .setMessage(R.string.network_error_message)
                                .setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss())
                                .show();
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void longclick() {
            Toast.makeText(this, "data sudah teregistrasi", Toast.LENGTH_LONG).show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this);
                    mBuilder.setSmallIcon(R.drawable.ic_notifications_black_24dp);
                    mBuilder.setContentTitle("Livia Jessica - 00000013091");
                    mBuilder.setContentText("data sudah teregistrasi");
                    Notification notifcation = mBuilder.build();
                    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(2, notifcation);
                }
            }, 5000); // 5 detik muncul notif

    }
}
