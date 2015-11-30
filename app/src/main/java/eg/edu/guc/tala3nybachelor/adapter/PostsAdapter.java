package eg.edu.guc.tala3nybachelor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    public PostsAdapter(ArrayList<Post> posts) {
        this.posts = posts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post singlePost = posts.get(position);

        holder.txtBody.setText(singlePost.getBody());
        holder.txtCommentsCount.setText(singlePost.getComments() + " comments");
        holder.txtLikesCount.setText(singlePost.getLikes() + " likes");
        holder.txtTimeStamp.setText(singlePost.getTimestamp() + "mins");
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


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}