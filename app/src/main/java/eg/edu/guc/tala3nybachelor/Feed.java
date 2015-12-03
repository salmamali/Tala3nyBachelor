package eg.edu.guc.tala3nybachelor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import eg.edu.guc.tala3nybachelor.adapter.PostsAdapter;
import eg.edu.guc.tala3nybachelor.model.Comment;
import eg.edu.guc.tala3nybachelor.model.Post;

public class Feed extends FullScreenActivity {

    @Bind(R.id.feed_posts_list) RecyclerView feedList;

    private SharedPreferences sharedPreferences;
    private ArrayList<Post> posts;
    private PostsAdapter adapter;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        ButterKnife.bind(this);

        sharedPreferences = getSharedPreferences("eg.edu.guc.tala3nybachelor", MODE_PRIVATE);
        name = sharedPreferences.getString("username", "");

        posts = new ArrayList<>();
        posts.add(new Post("I found this great topic.", 27, 3, 9));
        posts.add(new Post("I need help finding a place to stay in Stuttgart!", 23, 0, 3));
        posts.add(new Post("For those interested in topics about machine learning and AI please comment or contact me", 41, 19, 34));

        adapter = new PostsAdapter(posts);
        feedList.setAdapter(adapter);
        feedList.setOverScrollMode(View.OVER_SCROLL_NEVER);
        feedList.setVerticalScrollBarEnabled(false);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        feedList.setLayoutManager(llm);
    }

    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.post_cell_likes_count:
            case R.id.post_cell_comments_count:
                launchPostActivity();
                break;

            default:
        }
    }

    private void launchPostActivity() {
        Intent post = new Intent(this, PostActivity.class);
        post.putExtra("post-owner", name);
        post.putExtra("post-body", "I found this great topic.");
        post.putExtra("post-time", "27mins");

        ArrayList<Comment> comments = new ArrayList<>();
        comments.add(new Comment("Salma", "how can I contact you?", 12));
        comments.add(new Comment(name, "send me an email", 10));
        comments.add(new Comment("Salma", "ok, thanks!", 6));

        Gson gson = new Gson();
        String jsonComments = gson.toJson(comments);
        post.putExtra("comments", jsonComments);

        startActivity(post);
    }
}
