package eg.edu.guc.tala3nybachelor;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.widget.IconTextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import eg.edu.guc.tala3nybachelor.adapter.PostsAdapter;
import eg.edu.guc.tala3nybachelor.model.Post;

public class Profile extends FullScreenActivity {

    private static final String PROFILE_IMAGE = "http://s-media-cache-ak0.pinimg.com/736x/8b/1e/f5/8b1ef57d12bb0fa2518b9111dab97809.jpg";

    @Bind(R.id.profile_name_holder)
    TextView txtName;
    @Bind(R.id.profile_image_holder)
    ImageView imgHolder;
    @Bind(R.id.profile_image_progress)
    ProgressBar prgImage;

    //icons
    @Bind(R.id.profile_options_post_icon)
    IconTextView icnPost;
    @Bind(R.id.profile_options_message_icon)
    IconTextView icnMessage;
    @Bind(R.id.profile_options_friends_icon)
    IconTextView icnFriends;
    @Bind(R.id.profile_options_menu_icon)
    IconTextView icnMenu;

    @Bind(R.id.profile_posts_list_view)
    RecyclerView postsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        Iconify.addIcons(icnPost, icnMessage, icnFriends, icnMenu);

        txtName.setText("Tarek ElBeih");
        txtName.setTextColor(Color.argb(200, 255, 255, 255));

        Resources r = getResources();
        int width = r.getDisplayMetrics().widthPixels;
        int density = (int)r.getDisplayMetrics().density;

        Picasso.with(this)
                .load(PROFILE_IMAGE)
                .resize(width,150 * density)
                .centerCrop()
                .into(imgHolder, new Callback() {
                    @Override
                    public void onSuccess() {
                        prgImage.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        prgImage.setVisibility(View.GONE);
                        Log.wtf("etf", "error");
                    }
                });

        ArrayList<Post> posts = new ArrayList<>();
        posts.add(new Post("I found this great topic!", 3, 4, 27));
        posts.add(new Post("I need help finding a place to stay in Stuttgart", 23, 0, 3));
        posts.add(new Post("For those interested in topics about machine learning and AI please comment or contact me", 41, 19, 34));


        PostsAdapter adapter = new PostsAdapter(posts);
        postsList.setAdapter(adapter);
        postsList.setOverScrollMode(View.OVER_SCROLL_NEVER);
        postsList.setHorizontalScrollBarEnabled(false);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        postsList.setLayoutManager(llm);

        icnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profile.this, Messages.class);
                startActivity(i);
            }
        });
    }
}
