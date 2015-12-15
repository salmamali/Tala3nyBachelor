package eg.edu.guc.tala3nybachelor;

import android.animation.LayoutTransition;
import android.content.Context;
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
import android.util.Log;
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
import com.joanzapata.iconify.widget.IconTextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import eg.edu.guc.tala3nybachelor.adapter.PostsAdapter;
import eg.edu.guc.tala3nybachelor.controller.Controller;
import eg.edu.guc.tala3nybachelor.model.Comment;
import eg.edu.guc.tala3nybachelor.model.Post;
import eg.edu.guc.tala3nybachelor.model.SetData;
import eg.edu.guc.tala3nybachelor.singleton.RetrofitSingleton;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Profile extends FullScreenActivity implements Animation.AnimationListener {

    private static final String PROFILE_IMAGE = "http://s-media-cache-ak0.pinimg.com/736x/8b/1e/f5/8b1ef57d12bb0fa2518b9111dab97809.jpg";

    @Bind(R.id.profile_name_holder)
    TextView txtName;
    @Bind(R.id.profile_image_progress)
    ProgressBar prgImage;

    @Bind(R.id.profile_image_holder)
    ImageView imgHolder;

    @Bind(R.id.profile_options_layout)
    LinearLayout profileOptionsLayout;
    @Bind(R.id.profile_post_edit_layout)
    LinearLayout postEditLayout;

    @Bind(R.id.profile_post_edit_text)
    EditText postEditText;

    @Bind(R.id.profile_info_age) TextView txtAge;
    @Bind(R.id.profile_info_gender) TextView txtGender;
    @Bind(R.id.profile_info_nationality) TextView txtNationality;

    //icons
    @Bind(R.id.profile_options_post_icon)
    IconTextView icnPost;
    @Bind(R.id.profile_options_messages_icon)
    IconTextView icnMessage;
    @Bind(R.id.profile_options_friends_icon)
    IconTextView icnFriends;
    @Bind(R.id.profile_options_menu_icon)
    IconTextView icnMenu;
    @Bind(R.id.profile_image_refresh)
    IconTextView imgReload;
    @Bind(R.id.drawer_logout_icon)
    IconTextView imgLogout;
    @Bind(R.id.drawer_info_icon)
    IconTextView imgInfo;
    @Bind(R.id.drawer_feed_icon)
    IconTextView imgFeed;

    @Bind(R.id.profile_posts_list_view)
    RecyclerView postsList;
    @Bind(R.id.settings_drawer)
    DrawerLayout settingsDrawer;

    @Bind(R.id.drawerPane)
    RelativeLayout drawerPane;
    @Bind(R.id.profile_info_layout)
    RelativeLayout infoLayout;
    @Bind(R.id.personal_info_text) TextView personalInfoText;
    @Bind(R.id.firstname) TextView firstName;
    @Bind(R.id.lastname) TextView lastName;

    private String name;
    private Animation slideRight, slideLeft;
    private int measuredWidth, measuredHeight;
    private PostsAdapter adapter;
    private ArrayList<Post> posts;
    private int userId;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);
        Picasso.with(this).setLoggingEnabled(true);
        sharedPreferences = getSharedPreferences("eg.edu.guc.tala3nybachelor", MODE_PRIVATE);

        slideRight = AnimationUtils.loadAnimation(this, R.anim.slide_left_right);
        slideRight.setAnimationListener(this);
        slideLeft = AnimationUtils.loadAnimation(this, R.anim.slide_right_left);
        slideLeft.setAnimationListener(this);
        drawerPane.measure(0, 0);
        measuredHeight = drawerPane.getMeasuredHeight();
        measuredWidth = drawerPane.getMeasuredWidth();

        Typeface light = Typeface.createFromAsset(getAssets(), "fonts/montserrat-light.otf");


        Typeface bold = Typeface.createFromAsset(getAssets(), "fonts/montserrat-bold.otf");
        icnPost.setTypeface(light);
        icnMessage.setTypeface(light);
        icnFriends.setTypeface(light);
        imgLogout.setTypeface(light);
        imgInfo.setTypeface(light);
        personalInfoText.setTypeface(bold);
        imgFeed.setTypeface(light);
        txtAge.setTypeface(light);
        txtGender.setTypeface(light);
        txtNationality.setTypeface(light);
        firstName.setTypeface(light);
        lastName.setTypeface(light);



        userId = sharedPreferences.getInt("userId", 1);
        name = sharedPreferences.getString("userName", "");
        txtName.setText(name);
        txtName.setTextColor(Color.argb(200, 255, 255, 255));

        DisplayMetrics dm = getResources().getDisplayMetrics();
        updateProfileImage(dm);



        getProfile(1, this);
        postsList.setOverScrollMode(View.OVER_SCROLL_NEVER);
        postsList.setVerticalScrollBarEnabled(false);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        postsList.setLayoutManager(llm);
    }

    @Override
    protected void onResume() {
        hideInfo();
        settingsDrawer.closeDrawers();
        super.onResume();

        postEditText.setText("");
        postEditLayout.setVisibility(View.GONE);
        icnMenu.setVisibility(View.VISIBLE);
        icnMessage.setVisibility(View.VISIBLE);
        icnFriends.setVisibility(View.VISIBLE);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_options_post_icon:
                hideInfo();
                if (postEditLayout.getVisibility() == View.GONE) {
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
                startActivity(new Intent(this, FriendsActivity.class));
                //TODO: Launch friends activity!
                break;

            case R.id.profile_options_menu_icon:
                settingsDrawer.openDrawer(GravityCompat.END);
                break;

            case R.id.drawer_logout_icon:
                Intent logout = new Intent(this, Login.class);
                startActivity(logout);
                break;

            case R.id.drawer_feed_icon:
                Intent feed = new Intent(this, Feed.class);
                startActivity(feed);
                break;

            case R.id.drawer_info_icon:
                settingsDrawer.closeDrawers();
                if(infoLayout.getVisibility() == View.GONE) {
                    showInfo();
                } else {
                    hideInfo();
                }
                break;

            case R.id.post_cell_likes_count:
            case R.id.post_cell_comments_count:
                //launchPostActivity();
                break;


            default:
        }
    }

    private void showInfo() {
        postsList.setVisibility(View.GONE);
        infoLayout.setVisibility(View.VISIBLE);
    }

    private void hideInfo() {
        postsList.setVisibility(View.VISIBLE);
        infoLayout.setVisibility(View.GONE);
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
    }

    private void sendPost() {
        String postBody = postEditText.getText().toString();
        if (!postBody.isEmpty()) {
            postEditText.setText("");
            SetData data = new SetData(null, null, null, null, postBody, userId);
            addPost("33ff8cff9c46b099e34020ababb378b8", data);

        }
    }

    public void addPost(String token, SetData data) {

        Controller.addPost retr = RetrofitSingleton.getInstance().create(Controller.addPost.class);
        retr.add_post(token, data, new retrofit.Callback<Post>() {
            @Override
            public void success(Post post, Response response) {
                posts.add(post);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

    public void getProfile(Integer dest, final Context context) {
        Controller.getProfilePosts retr = RetrofitSingleton.getInstance().create(Controller.getProfilePosts.class);

        retr.get_profile(dest, new retrofit.Callback<ArrayList<Post>>() {
            @Override
            public void success(ArrayList<Post> postsRet, Response response) {
                Log.wtf("salma", "posts in profile: " + postsRet.size());
                posts = postsRet;
                adapter = new PostsAdapter(context,posts);
                postsList.setAdapter(adapter);

            }

            @Override
            public void failure(RetrofitError error) {
                Log.wtf("salma", error);
            }
        });
    }

    public void getSinglePost(final Integer id) {

        Controller.getPost retr = RetrofitSingleton.getInstance().create(Controller.getPost.class);
        retr.get_post(id, new retrofit.Callback<Post>() {
            @Override
            public void success(Post post, Response response) {
                Log.wtf("salma", "succes");
                Intent postIntent = new Intent(Profile.this, PostActivity.class);
                postIntent.putExtra("post-id", id);
                postIntent.putExtra("post-owner", name);
                postIntent.putExtra("post-body", post.getBody());
                postIntent.putExtra("post-time", "27mins");


                Gson gson = new Gson();
                String jsonComments = gson.toJson(post.getCommentsArray());
                postIntent.putExtra("comments", jsonComments);

                startActivity(postIntent);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.wtf("salma", error);
            }
        });

    }

    private void updateProfileImage(final DisplayMetrics dm) {
        int width = dm.widthPixels;
        int density = (int) dm.density;
        Picasso.with(this)
                .load(PROFILE_IMAGE)
                .resize(width, 150 * density)
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
        if (animation == slideRight) {
            postEditLayout.setVisibility(View.VISIBLE);
            postEditLayout.setOverScrollMode(View.OVER_SCROLL_NEVER);
            postEditLayout.setVerticalScrollBarEnabled(false);
            postEditText.requestFocus();
        } else if (animation == slideLeft) {
            icnMenu.setVisibility(View.VISIBLE);
            icnMessage.setVisibility(View.VISIBLE);
            icnFriends.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

}
