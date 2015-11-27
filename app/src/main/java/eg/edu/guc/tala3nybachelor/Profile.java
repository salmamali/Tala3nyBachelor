package eg.edu.guc.tala3nybachelor;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.widget.IconTextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

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
                    }
                });

    }
}
