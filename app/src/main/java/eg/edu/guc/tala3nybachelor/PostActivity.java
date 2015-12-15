package eg.edu.guc.tala3nybachelor;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import eg.edu.guc.tala3nybachelor.adapter.CommentsAdapter;
import eg.edu.guc.tala3nybachelor.controller.Controller;
import eg.edu.guc.tala3nybachelor.model.Comment;
import eg.edu.guc.tala3nybachelor.model.Post;
import eg.edu.guc.tala3nybachelor.model.SetData;
import eg.edu.guc.tala3nybachelor.singleton.RetrofitSingleton;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PostActivity extends FullScreenActivity {

    private String sender;

    @Bind(R.id.post_sender_holder) TextView txtSender;
    @Bind(R.id.post_timestamp_holder) TextView txtTimeStamp;
    @Bind(R.id.post_body_holder) TextView txtBody;

    @Bind(R.id.post_comments_list) RecyclerView commentsList;

    @Bind(R.id.post_comment_edit_text) EditText editComment;

    @Bind(R.id.post_comment_button) Button btnPostComment;

    private int postId;
    private ArrayList<Comment> comments;
    private CommentsAdapter adapter;
    private String accessToken;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("eg.edu.guc.tala3nybachelor", MODE_PRIVATE);
        accessToken = sharedPreferences.getString("accessToken", "");


        Typeface light=Typeface.createFromAsset(getAssets(),"fonts/montserrat-light.otf");

        txtSender.setTypeface(light);
        txtBody.setTypeface(light);
        editComment.setTypeface(light);
        btnPostComment.setTypeface(light);

        postId = getIntent().getExtras().getInt("post-id");
        sender = getIntent().getExtras().getString("post-owner");
        txtSender.setText(sender);
        String body = getIntent().getExtras().getString("post-body");
        txtBody.setText(body);
        String timeStamp = getIntent().getExtras().getString("post-time");
        txtTimeStamp.setText(timeStamp);

        String commentsJson = getIntent().getExtras().getString("comments");
        Gson gson = new Gson();
        comments = gson.fromJson(commentsJson, new TypeToken<ArrayList<Comment>>(){}.getType());

        adapter = new CommentsAdapter(this, comments);
        commentsList.setAdapter(adapter);
        commentsList.setOverScrollMode(View.OVER_SCROLL_NEVER);
        commentsList.setVerticalScrollBarEnabled(false);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        commentsList.setLayoutManager(llm);

        btnPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.wtf("salma", "post id: " + postId);
                SetData data = new SetData(editComment.getText().toString(), postId, 1, 1, null, null);
                Log.wtf("salma", "text: " + data.text+ " postId: " + data.postId + " post_id: "+ data.post_id + " user_id: " + data.user_id);
                addComment(accessToken, data);
            }
        });


    }

    public void addComment(String token, final SetData data) {

        Controller.addComment retr = RetrofitSingleton.getInstance().create(Controller.addComment.class);

        retr.add_comment(token, data, new Callback<Post>() {
            @Override
            public void success(Post post, Response response) {
                Log.wtf("salma", "in first success");
                comments = post.getCommentsArray();
                adapter = new CommentsAdapter(PostActivity.this, comments);
                commentsList.setAdapter(adapter);
                editComment.setText("");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.wtf("salma", error);
            }
        });

    }


}
