package eg.edu.guc.tala3nybachelor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.joanzapata.iconify.Iconify;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import eg.edu.guc.tala3nybachelor.R;
import eg.edu.guc.tala3nybachelor.model.Friend;

/**
 * Created by salmaali on 12/3/15.
 */
public class FriendsAdapter extends ArrayAdapter<Friend> {

    private Context context;
    private ArrayList<Friend> friends;
    private ArrayList<Friend> originalFriend;
    private FriendsFilter filter;

    @Bind(R.id.friend_image)
    ImageView friendImage;
    @Bind(R.id.friend_name)
    TextView friendName;
    @Bind(R.id.add_friend) TextView addFriend;
    @Bind(R.id.remove_friend) TextView removeFriend;

    public FriendsAdapter(Context context, ArrayList<Friend> friends) {
        super(context, R.layout.friends_cell, friends);
        this.context = context;
        this.friends = friends;
        originalFriend = new ArrayList<>();
        originalFriend.addAll(friends);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View customView;
        LayoutInflater inflater = LayoutInflater.from(getContext());

        if (convertView == null) {
            customView = inflater.inflate(R.layout.friends_cell, parent, false);
        } else {
            customView = convertView;
        }

        ButterKnife.bind(this, customView);
        Iconify.addIcons(removeFriend, addFriend);

        if (friendImage!=null) {
            Picasso.with(context).load(friends.get(position).getImageUrl()).into(friendImage);
        } else {
            Picasso.with(context).load(R.drawable.avatar_placeholder).into(friendImage);
        }

        friendName.setText(friends.get(position).getFriend());
        if (friends.get(position).isAccepted()) {
            addFriend.setVisibility(View.GONE);
            removeFriend.setVisibility(View.VISIBLE);
        } else {
            addFriend.setVisibility(View.VISIBLE);
            removeFriend.setVisibility(View.GONE);
        }

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friends.get(position).setAccepted(true);
                notifyDataSetChanged();
            }
        });

        removeFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friends.get(position).setAccepted(false);
                notifyDataSetChanged();
            }
        });

        return customView;

    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new FriendsFilter();
        }
        return filter;
    }

    private class FriendsFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            ArrayList<Friend> tempList = new ArrayList<>();
            if (constraint != null && constraint.toString().length() > 0) {
                for (int i = 0; i < originalFriend.size(); i++) {
                    String fullName = (originalFriend.get(i).getFriend().toLowerCase());
                    if (fullName.contains(constraint.toString().toLowerCase())) {
                        if (fullName.length() > 0 && constraint.length() > 0) {
                            if (fullName.charAt(0) == constraint.toString().toLowerCase().charAt(0)) {
                                tempList.add(0, originalFriend.get(i));
                            } else {
                                tempList.add(originalFriend.get(i));
                            }
                        }
                        filterResults.values = tempList;
                        filterResults.count = tempList.size();
                    }
                }

            } else {
                filterResults.count = originalFriend.size();
                filterResults.values = originalFriend;
            }
            return filterResults;
        }

        @Override
        @SuppressWarnings("unchecked")
        protected void publishResults(CharSequence constraint, FilterResults results) {
            friends = (ArrayList<Friend>) results.values;
            notifyDataSetChanged();
            clear();
            if (friends != null)
                for (int i = 0, l = friends.size(); i < l; i++)
                    add(friends.get(i));
            notifyDataSetInvalidated();

        }
    }

    }
