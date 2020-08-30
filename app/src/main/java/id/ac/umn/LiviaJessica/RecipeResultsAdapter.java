package id.ac.umn.LiviaJessica;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import id.ac.umn.LiviaJessica.R;
import id.ac.umn.LiviaJessica.models.Recipe;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;

public class RecipeResultsAdapter extends RecyclerView.Adapter<RecipeResultsAdapter.RecipeViewHolder> {
    private Context mContext;
    private List<Recipe> mRecipeList;

    public RecipeResultsAdapter(Context context, List<Recipe> recipeList) {
        mContext = context;
        mRecipeList = recipeList;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, final int position) {
        holder.recipeTitle.setText(mRecipeList.get(position).getTitle());
        Picasso.get().load(mRecipeList.get(position).getImage_url()).fit().centerInside().into(holder.recipeImage);
        holder.recipeRating.setText(mContext.getString(R.string.rating, new DecimalFormat("#0.0").format(mRecipeList.get(position).getSocial_rank())));
        holder.recipeSource.setText(mContext.getString(R.string.source, mRecipeList.get(position).getPublisher()));
        holder.recipeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mRecipeList.get(position).getSource_url() != null && !mRecipeList.get(position).getSource_url().isEmpty()) {
                    mContext.startActivity(new Intent(mContext, RecipeView.class).putExtra("url", mRecipeList.get(position).getSource_url()));
                }
            }
        });
        holder.recipeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                longclick1();
                return true;
            }

            private void longclick1() {
                Toast.makeText(mContext, "data sudah teregistrasi", Toast.LENGTH_LONG).show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext);
                        mBuilder.setSmallIcon(R.drawable.ic_notifications_black_24dp);
                        mBuilder.setContentTitle(mRecipeList.get(position).getTitle());
                        mBuilder.setContentText(mRecipeList.get(position).getPublisher());
                        Notification notifcation = mBuilder.build();
                        NotificationManager nm = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
                        nm.notify(2, notifcation);
                    }
                }, 5000); // 5 detik muncul notif
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder{
        private TextView recipeTitle;
        private ImageView recipeImage;
        private TextView recipeRating;
        private TextView recipeSource;
        private LinearLayout recipeLayout;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeTitle = itemView.findViewById(R.id.title_textview);
            recipeImage = itemView.findViewById(R.id.recipe_imageview);
            recipeRating = itemView.findViewById(R.id.rating_textview);
            recipeSource = itemView.findViewById(R.id.source_textview);
            recipeLayout = itemView.findViewById(R.id.recipe_card_layout);
        }
    }
}
