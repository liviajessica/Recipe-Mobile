package id.ac.umn.LiviaJessica;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import id.ac.umn.LiviaJessica.R;

public class RecipeView extends AppCompatActivity {

//    private static final String Channel_ID = "personal notification";
//    private static final int Notification_ID = 001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_view);
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                longclick();
                return true;
            }
        });

        WebView recipeWebView = findViewById(R.id.recipe_web_view);
        recipeWebView.getSettings().setJavaScriptEnabled(true);
        recipeWebView.setWebViewClient(new WebViewClient());
        recipeWebView.setWebChromeClient(new WebChromeClient());
        recipeWebView.loadUrl(getIntent().getStringExtra("url"));
    }

    private void longclick() {
        Toast.makeText(this, "data sudah teregistrasi", Toast.LENGTH_LONG).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(RecipeView.this);
                mBuilder.setSmallIcon(R.drawable.ic_notifications_black_24dp);
                mBuilder.setContentTitle("Livia Jessica - 00000013091");
                mBuilder.setContentText(getIntent().getStringExtra("url"));
                Notification notifcation = mBuilder.build();
                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                nm.notify(2, notifcation);
            }
        }, 5000); // 5 detik muncul notif
    }
}
