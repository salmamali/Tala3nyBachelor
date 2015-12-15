package eg.edu.guc.tala3nybachelor.adapter;

import android.content.Context;
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
import eg.edu.guc.tala3nybachelor.model.Comment;

/**
 * Created by TarekElBeih on 02/12/15.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {
    private ArrayList<Comment> comments;
    Context context;

    public CommentsAdapter(Context context, ArrayList<Comment> comments) {
        this.comments = comments;
        this.context = context;
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
            holder.txtSender.setText(singleComment.getSender()+": ");
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
