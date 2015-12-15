package eg.edu.guc.tala3nybachelor.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import eg.edu.guc.tala3nybachelor.R;
import eg.edu.guc.tala3nybachelor.controller.Controller;
import eg.edu.guc.tala3nybachelor.model.Comment;
import eg.edu.guc.tala3nybachelor.model.User;
import eg.edu.guc.tala3nybachelor.singleton.RetrofitSingleton;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by TarekElBeih on 02/12/15.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {
    private ArrayList<Comment> comments;
    Context context;
    private String userId;
    private String accessToken;

    public CommentsAdapter(Context context, ArrayList<Comment> comments) {
        this.comments = comments;
        this.context = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences("eg.edu.guc.tala3nybachelor", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "");
        accessToken = sharedPreferences.getString("accessToken", "");

    }

    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_cell, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(CommentsAdapter.ViewHolder holder, int position) {
        Comment singleComment = comments.get(position);

        if(singleComment.getSender() !=null)
//            getUser(accessToken, Integer.parseInt(userId), holder.txtSender);
        holder.txtSender.setText(singleComment.getSender());
        if(singleComment.getText() != null)
            holder.txtBody.setText(singleComment.getText());


        //int time = singleComment.getTimeStamp();
//        if(time != -1) {
//            if(time == 0) {
//                holder.txtTimeStamp.setText("just now");
//            } else {
//                holder.txtTimeStamp.setText(time + "mins");
//            }
//        }

        /*int time = singleComment.getTimeStamp();
        if(time != -1) {
            if(time == 0) {
                holder.txtTimeStamp.setText("just now");
            } else {
                holder.txtTimeStamp.setText(time + "mins");
            }
        }*/

        Typeface light=Typeface.createFromAsset(context.getAssets(), "fonts/montserrat-light.otf");
        holder.txtSender.setTypeface(light);
        holder.txtBody.setTypeface(light);

    }

//    public void getUser (String token, Integer id, final TextView sender) {
//        Controller.getUser retr = RetrofitSingleton.getInstance().create(Controller.getUser.class);
//
//        retr.get_user(token, id, new Callback<User>() {
//            @Override
//            public void success(User user, Response response) {
//                sender.setText(user.getName());
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//
//            }
//        });
//
//    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.comment_sender_holder) TextView txtSender;
        @Bind(R.id.comment_timestamp_holder) TextView txtTimeStamp;
        @Bind(R.id.comment_body_holder) TextView txtBody;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
