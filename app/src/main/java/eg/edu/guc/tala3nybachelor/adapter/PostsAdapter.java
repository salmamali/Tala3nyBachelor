package eg.edu.guc.tala3nybachelor.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import eg.edu.guc.tala3nybachelor.R;
import eg.edu.guc.tala3nybachelor.model.Post;

/**
 * Created by TarekElBeih on 28/11/15.
 */

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private ArrayList<Post> posts;
    Context context;


    public PostsAdapter(Context context, ArrayList<Post> posts) {
        this.posts = posts;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_cell, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post singlePost = posts.get(position);

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
        holder.icnFollow.setTypeface(light);
        holder.txtBody.setTypeface(light);
        holder.txtCommentsCount.setTypeface(light);
        holder.txtLikesCount.setTypeface(light);
        holder.txtFollowersCount.setTypeface(light);
        holder.txtTimeStamp.setTypeface(light);

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
        @Bind(R.id.drawer_follow_icon) IconTextView icnFollow;
        @Bind(R.id.post_cell_followers_count) TextView txtFollowersCount;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
