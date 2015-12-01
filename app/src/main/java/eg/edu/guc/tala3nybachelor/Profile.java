package eg.edu.guc.tala3nybachelor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
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

    @Bind(R.id.profile_name_holder) TextView txtName;
    @Bind(R.id.profile_image_progress) ProgressBar prgImage;

    @Bind(R.id.profile_image_holder) ImageView imgHolder;

    //icons
    @Bind(R.id.profile_options_post_icon) IconTextView icnPost;
    @Bind(R.id.profile_options_messages_icon) IconTextView icnMessage;
    @Bind(R.id.profile_options_friends_icon) IconTextView icnFriends;
    @Bind(R.id.profile_options_menu_icon) IconTextView icnMenu;
    @Bind(R.id.profile_image_refresh) IconTextView imgReload;
    @Bind(R.id.drawer_logout_icon) IconTextView imgLogout;
    @Bind(R.id.drawer_info_icon) IconTextView imgInfo;

    @Bind(R.id.profile_posts_list_view) RecyclerView postsList;
    @Bind(R.id.settings_drawer) DrawerLayout settingsDrawer;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);
        Picasso.with(this).setLoggingEnabled(true);
        sharedPreferences = getSharedPreferences("eg.edu.guc.tala3nybachelor", MODE_PRIVATE);

        Iconify.addIcons(icnPost, icnMessage, icnFriends, icnMenu, imgReload, imgLogout, imgInfo);

        String name = sharedPreferences.getString("username", "Tarek ElBeih");
        txtName.setText(name);
        txtName.setTextColor(Color.argb(200, 255, 255, 255));

        DisplayMetrics dm = getResources().getDisplayMetrics();
        updateProfileImage(dm);

        ArrayList<Post> posts = new ArrayList<>();
        posts.add(new Post("I found this great topic.", 3, 4, 27));
        posts.add(new Post("I need help finding a place to stay in Stuttgart!", 23, 0, 3));
        posts.add(new Post("For those interested in topics about machine learning and AI please comment or contact me", 41, 19, 34));

        PostsAdapter adapter = new PostsAdapter(posts);
        postsList.setAdapter(adapter);
        postsList.setOverScrollMode(View.OVER_SCROLL_NEVER);
        postsList.setVerticalScrollBarEnabled(false);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        postsList.setLayoutManager(llm);
    }

    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.profile_options_post_icon:
                Toast.makeText(this, "you pressed post icon", Toast.LENGTH_SHORT).show();
                //TODO: Launch post activity!
                break;

            case R.id.profile_options_messages_icon:
                Intent messages = new Intent(Profile.this, Messages.class);
                startActivity(messages);
                break;

            case R.id.profile_options_friends_icon:
                Toast.makeText(this, "you pressed friends icon", Toast.LENGTH_SHORT).show();
                //TODO: Launch friends activity!
                break;

            case R.id.profile_options_menu_icon:
                settingsDrawer.openDrawer(GravityCompat.END);
                break;

            case R.id.drawer_logout_icon:
                Intent logout = new Intent(this, Login.class);
                startActivity(logout);

            case R.id.drawer_info_icon:
                //TODO: Launch info activity!

            default:
        }
    }

    private void updateProfileImage(final DisplayMetrics dm){
        int width = dm.widthPixels;
        int density = (int) dm.density;
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
                    }
                });

        imgReload.setVisibility(View.VISIBLE);
        imgReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgReload.setVisibility(View.GONE);
                prgImage.setVisibility(View.VISIBLE);
                updateProfileImage(dm);
            }
        });

    }
}
