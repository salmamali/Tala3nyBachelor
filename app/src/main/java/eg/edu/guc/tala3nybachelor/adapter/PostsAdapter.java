package eg.edu.guc.tala3nybachelor.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.widget.IconTextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import eg.edu.guc.tala3nybachelor.Profile;
import eg.edu.guc.tala3nybachelor.R;
import eg.edu.guc.tala3nybachelor.controller.Controller;
import eg.edu.guc.tala3nybachelor.model.Post;

import eg.edu.guc.tala3nybachelor.model.User;
import eg.edu.guc.tala3nybachelor.singleton.RetrofitSingleton;
import eg.edu.guc.tala3nybachelor.singleton.RetrofitSingleton;
import retrofit.Callback;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by TarekElBeih on 28/11/15.
 */

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private ArrayList<Post> posts;
    Context context;
    String accessToken;
    String userId;


    public PostsAdapter(Context context, ArrayList<Post> posts) {
        this.posts = posts;
        this.context = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences("eg.edu.guc.tala3nybachelor", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "");
        accessToken = sharedPreferences.getString("accessToken", "");


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_cell, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Post singlePost = posts.get(position);

        if(singlePost.getBody() != null)
            holder.txtBody.setText(singlePost.getBody());
        if(singlePost.getComments() != -1)
            holder.txtCommentsCount.setText(singlePost.getComments() + " comments");
        if(singlePost.getLikes() != -1)
            holder.txtLikesCount.setText(singlePost.getLikes() + " likes");

        int time = singlePost.getTimestamp();
        if(time != -1) {
            if(time == 0) {
                holder.txtTimeStamp.setText("just now");
            } else {
                holder.txtTimeStamp.setText(time + "mins");
            }
        }

        Typeface light=Typeface.createFromAsset(context.getAssets(), "fonts/montserrat-light.otf");
        holder.icnLike.setTypeface(light);
        holder.icnComment.setTypeface(light);
      //  holder.icnFollow.setTypeface(light);
        holder.txtBody.setTypeface(light);
        holder.txtCommentsCount.setTypeface(light);
        holder.txtLikesCount.setTypeface(light);
       // holder.txtFollowersCount.setTypeface(light);

        holder.postLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.wtf("salma", "click");
                if(context instanceof Profile)
                    ((Profile)context).getSinglePost(singlePost.getId());
            }
        });

        holder.icnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singlePost.setLikes(singlePost.getLikes() + 1);
                LikePost(singlePost.getId(), singlePost.getLikes(), holder, singlePost);

            }
        });

    }

    public void LikePost(int id, final int likes, final ViewHolder holder, final Post singlePost) {
        Controller.LikePost retr = RetrofitSingleton.getInstance().create(Controller.LikePost.class);
        String accessToken = context.getSharedPreferences("eg.edu.guc.tala3nybachelor", context.MODE_PRIVATE).getString("accessToken", "");
        retr.post_like(accessToken, id, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(context, "You have liked this post", Toast.LENGTH_SHORT).show();
                holder.txtLikesCount.setText(likes+" likes");

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("RetrofitError", error.getMessage());
            }
        });
    }

    public void getUser(String token, int id) {
        Controller.getUser retr = RetrofitSingleton.getInstance().create(Controller.getUser.class);

        retr.get_user(token, id, new retrofit.Callback<User>() {
            @Override
            public void success(User user, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.post_cell_body) TextView txtBody;
        @Bind(R.id.post_cell_timestamp) TextView txtTimeStamp;
        @Bind(R.id.post_cell_likes_count) TextView txtLikesCount;
        @Bind(R.id.post_cell_comments_count) TextView txtCommentsCount;
        @Bind(R.id.drawer_like_icon) IconTextView icnLike;
        @Bind(R.id.drawer_comment_icon) IconTextView icnComment;
        @Bind(R.id.post_layout)
        RelativeLayout postLayout;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
