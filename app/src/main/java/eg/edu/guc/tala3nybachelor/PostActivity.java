package eg.edu.guc.tala3nybachelor;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import eg.edu.guc.tala3nybachelor.model.Comment;

public class PostActivity extends FullScreenActivity {

    private String sender;

    @Bind(R.id.post_sender_holder) TextView txtSender;
    @Bind(R.id.post_timestamp_holder) TextView txtTimeStamp;
    @Bind(R.id.post_body_holder) TextView txtBody;

    @Bind(R.id.post_comments_list) RecyclerView commentsList;

    @Bind(R.id.post_comment_edit_text) EditText editComment;

    @Bind(R.id.post_comment_button) Button btnPostComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);

        sender = getIntent().getExtras().getString("post-owner");
        txtSender.setText(sender);
        String body = getIntent().getExtras().getString("post-body");
        txtBody.setText(body);
        String timeStamp = getIntent().getExtras().getString("post-time");
        txtTimeStamp.setText(timeStamp);

        String commentsJson = getIntent().getExtras().getString("comments");
        Gson gson = new Gson();
        final ArrayList<Comment> comments = gson.fromJson(commentsJson, new TypeToken<ArrayList<Comment>>(){}.getType());

        final CommentsAdapter adapter = new CommentsAdapter(comments);
        commentsList.setAdapter(adapter);
        commentsList.setOverScrollMode(View.OVER_SCROLL_NEVER);
        commentsList.setVerticalScrollBarEnabled(false);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        commentsList.setLayoutManager(llm);

        btnPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comment c = new Comment(sender, editComment.getText().toString(), 0);
                comments.add(c);
                adapter.notifyDataSetChanged();
                editComment.setText("");
            }
        });
    }
}
