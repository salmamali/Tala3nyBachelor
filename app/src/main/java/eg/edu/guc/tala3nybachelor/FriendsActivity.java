package eg.edu.guc.tala3nybachelor;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import eg.edu.guc.tala3nybachelor.adapter.FriendsAdapter;
import eg.edu.guc.tala3nybachelor.model.Friend;

public class FriendsActivity extends FullScreenActivity implements Animation.AnimationListener{

    @Bind(R.id.friends_list)
    ListView friendsList;
    @Bind(R.id.search_icon)
    TextView searchIcon;
    @Bind(R.id.search_field)
    EditText searchField;
    @Bind(R.id.friends_layout)
    RelativeLayout friendsLayout;
    @Bind(R.id.search_layout)
    LinearLayout searchLayout;
    @Bind(R.id.exit_search) TextView exitSearch;
    private FriendsAdapter friendsAdapter;
    private Animation slideLeft;
    private Animation slideRight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        ButterKnife.bind(this);

        ArrayList<Friend> friends = new ArrayList<>();
        Friend f1 = new Friend(true, "Salma ElTarzi");
        Friend f2 = new Friend(true, "Menna Darwish");
        Friend f3 = new Friend(true, "Salma Ali");
        friends.add(f2);
        friends.add(f1);
        friends.add(f3);
        friendsAdapter = new FriendsAdapter(this, friends);
        friendsList.setAdapter(friendsAdapter);
        Iconify.addIcons(searchIcon, exitSearch);
        slideLeft = AnimationUtils.loadAnimation(this, R.anim.slide_left_right);
        slideLeft.setAnimationListener(this);

        slideRight = AnimationUtils.loadAnimation(this, R.anim.slide_right_left);
        slideRight.setAnimationListener(this);

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                friendsLayout.startAnimation(slideLeft);
                searchLayout.startAnimation(slideLeft);


            }
        });

        exitSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //searchLayout.setVisibility(View.GONE);
                searchLayout.startAnimation(slideRight);
                friendsLayout.startAnimation(slideRight);
                friendsLayout.setVisibility(View.VISIBLE);
            }
        });

        searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });

        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                friendsAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });





    }

    @Override
    public void onAnimationStart(Animation animation) {
        if (animation.equals(slideLeft)) {
            friendsLayout.setVisibility(View.GONE);
            searchLayout.setVisibility(View.VISIBLE);
        } else {
            searchLayout.setVisibility(View.GONE);
            friendsLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
