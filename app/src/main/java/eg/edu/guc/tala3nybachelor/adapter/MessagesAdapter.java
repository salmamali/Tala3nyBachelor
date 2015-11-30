package eg.edu.guc.tala3nybachelor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import eg.edu.guc.tala3nybachelor.R;
import eg.edu.guc.tala3nybachelor.model.Message;

/**
 * Created by salmaali on 11/28/15.
 */
public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder>{


    ArrayList<Message> items;

    public MessagesAdapter(Context context, ArrayList<Message> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.messageReceiver.setText(items.get(position).getUser().getFirstName() + " " + items.get(position).getUser().getLastName());

        holder.lastMessage.setText(items.get(position).getText());

        holder.messageTimestamp.setText(items.get(position).getTimestamp() + " min");

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.message_receiver) TextView messageReceiver;
        @Bind(R.id.last_message) TextView lastMessage;
        @Bind(R.id.message_timestamp) TextView messageTimestamp;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
