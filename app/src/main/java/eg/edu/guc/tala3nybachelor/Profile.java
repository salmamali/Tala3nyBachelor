package eg.edu.guc.tala3nybachelor;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.widget.IconTextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import eg.edu.guc.tala3nybachelor.adapter.PostsAdapter;
import eg.edu.guc.tala3nybachelor.model.Comment;
import eg.edu.guc.tala3nybachelor.model.Post;

public class Profile extends FullScreenActivity implements Animation.AnimationListener{

    private static final String PROFILE_IMAGE = "http://s-media-cache-ak0.pinimg.com/736x/8b/1e/f5/8b1ef57d12bb0fa2518b9111dab97809.jpg";

    @Bind(R.id.profile_name_holder) TextView txtName;
    @Bind(R.id.profile_image_progress) ProgressBar prgImage;

    @Bind(R.id.profile_image_holder) ImageView imgHolder;

    @Bind(R.id.profile_options_layout) LinearLayout profileOptionsLayout;
    @Bind(R.id.profile_post_edit_layout) LinearLayout postEditLayout;
    @Bind(R.id.profile_post_edit_text) EditText postEditText;

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
    @Bind(R.id.account_info_layout) LinearLayout accountInfoLayout;
    @Bind(R.id.drawerPane)
    RelativeLayout drawerPane;

 //   private SharedPreferences sharedPreferences;
    private String name;
    private Animation slideRight, slideLeft;
    private PostsAdapter adapter;
    private ArrayList<Post> posts;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //sharedPreferences = getSharedPreferences("eg.edu.guc.tala3nybachelor", MODE_PRIVATE);


        ButterKnife.bind(this);
        Picasso.with(this).setLoggingEnabled(true);
        sharedPreferences = getSharedPreferences("eg.edu.guc.tala3nybachelor", MODE_PRIVATE);

        Iconify.addIcons(icnPost, icnMessage, icnFriends, icnMenu, imgReload, imgLogout, imgInfo);
        slideRight = AnimationUtils.loadAnimation(this, R.anim.slide_left_right);
        slideRight.setAnimationListener(this);
        slideLeft = AnimationUtils.loadAnimation(this, R.anim.slide_right_left);
        slideLeft.setAnimationListener(this);

        Typeface light=Typeface.createFromAsset(getAssets(),"fonts/montserrat-light.otf");


        Typeface bold=Typeface.createFromAsset(getAssets(),"fonts/montserrat-bold.otf");
        icnPost.setTypeface(light);
        icnMessage.setTypeface(light);
        icnFriends.setTypeface(light);
        imgLogout.setTypeface(light);
        imgInfo.setTypeface(light);



        name = sharedPreferences.getString("username", "Tarek ElBeih");
        txtName.setText(name);
        txtName.setTextColor(Color.argb(200, 255, 255, 255));

        DisplayMetrics dm = getResources().getDisplayMetrics();
        updateProfileImage(dm);

        posts = new ArrayList<>();
        posts.add(new Post("I found this great topic.", 27, 3, 9));
        posts.add(new Post("I need help finding a place to stay in Stuttgart!", 23, 0, 3));
        posts.add(new Post("For those interested in topics about machine learning and AI please comment or contact me", 41, 19, 34));

        adapter = new PostsAdapter(posts);
        postsList.setAdapter(adapter);
        postsList.setOverScrollMode(View.OVER_SCROLL_NEVER);
        postsList.setVerticalScrollBarEnabled(false);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        postsList.setLayoutManager(llm);
    }

    @Override
    protected void onResume() {
        super.onResume();

        postEditText.setText("");
        postEditLayout.setVisibility(View.GONE);
        icnMenu.setVisibility(View.VISIBLE);
        icnMessage.setVisibility(View.VISIBLE);
        icnFriends.setVisibility(View.VISIBLE);
    }

    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.profile_options_post_icon:
                if(postEditLayout.getVisibility() == View.GONE) {
                    showAddPost();
                } else {
                    hideAddPost();
                }
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
                if(accountInfoLayout.getVisibility() == View.VISIBLE) {
                    accountInfoLayout.setVisibility(View.GONE);
                }
                break;

            case R.id.drawer_logout_icon:
                Intent logout = new Intent(this, Login.class);
                startActivity(logout);
                break;

            case R.id.drawer_info_icon:
                accountInfoLayout.setVisibility(View.VISIBLE);
                drawerPane.setVisibility(View.GONE);
                break;

            case R.id.post_cell_likes_count:
            case R.id.post_cell_comments_count:
                launchPostActivity();
                break;


            default:
        }
    }

    private void hideAddPost() {
        LayoutTransition transition = new LayoutTransition();
        postEditLayout.setVisibility(View.GONE);
        transition.enableTransitionType(LayoutTransition.DISAPPEARING);
        profileOptionsLayout.setLayoutTransition(transition);
        profileOptionsLayout.startAnimation(slideLeft);
        sendPost();
    }

    private void showAddPost() {
        LayoutTransition transition = new LayoutTransition();
        icnMenu.setVisibility(View.GONE);
        icnMessage.setVisibility(View.GONE);
        icnFriends.setVisibility(View.GONE);
        transition.enableTransitionType(LayoutTransition.APPEARING);
        profileOptionsLayout.setLayoutTransition(transition);
        profileOptionsLayout.startAnimation(slideRight);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(accountInfoLayout.getVisibility() == View.VISIBLE) {
            accountInfoLayout.setVisibility(View.GONE);
        }
    }

    private void sendPost() {
        String postBody = postEditText.getText().toString();
        if(!postBody.isEmpty()) {
            postEditText.setText("");
            Post p = new Post(postBody, 0, 0, 0);
            posts.add(p);
            adapter.notifyDataSetChanged();
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

    private void updateProfileImage(final DisplayMetrics dm){
        int width = dm.widthPixels;
        int density = (int) dm.density;
        Picasso.with(this)
                .load(PROFILE_IMAGE)
                .resize(width,150 * density)
                .centerCrop()
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(imgHolder, new Callback() {
                    @Override
                    public void onSuccess() {
                        prgImage.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        prgImage.setVisibility(View.GONE);
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
                });
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(animation == slideRight) {
            postEditLayout.setVisibility(View.VISIBLE);
            postEditLayout.setOverScrollMode(View.OVER_SCROLL_NEVER);
            postEditLayout.setVerticalScrollBarEnabled(false);
            postEditText.requestFocus();
        } else if(animation == slideLeft) {
            icnMenu.setVisibility(View.VISIBLE);
            icnMessage.setVisibility(View.VISIBLE);
            icnFriends.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
