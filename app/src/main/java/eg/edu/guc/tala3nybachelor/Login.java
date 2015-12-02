package eg.edu.guc.tala3nybachelor;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import eg.edu.guc.tala3nybachelor.adapter.LoginSpinnerAdapter;


public class Login extends FullScreenActivity implements Animation.AnimationListener{

    private CallbackManager callbackManager;

    @Bind(R.id.register_text) TextView registerText;
    @Bind(R.id.username_login) EditText username;
    @Bind(R.id.password_login) EditText password;
    @Bind(R.id.login_button) RelativeLayout loginButton;
    @Bind(R.id.country_spinner) Spinner countrySpinner;
    @Bind(R.id.day_spinner) Spinner daySpinner;
    @Bind(R.id.month_spinner) Spinner monthSpinner;
    @Bind(R.id.year_spinner) Spinner yearSpinner;
    @Bind(R.id.login_layout) LinearLayout loginLayout;
    @Bind(R.id.register_layout) ScrollView registerLayout;
    @Bind(R.id.register_button) RelativeLayout registerButton;
    @Bind(R.id.facebook_login) ImageView facebookLogin;
    @Bind(R.id.twitter_login) ImageView twitterLogin;
    @Bind(R.id.signin_picture) ImageView signinPicture;
    @Bind(R.id.login_text) TextView loginText;

    private Animation slideTop;
    private Animation slideBottom;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        sharedPreferences = getSharedPreferences("eg.edu.guc.tala3nybachelor", MODE_PRIVATE);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        registerText.setPaintFlags(registerText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        slideTop = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_top);
        slideTop.setAnimationListener(this);
        slideBottom = AnimationUtils.loadAnimation(this, R.anim.slide_top_bottom);
        slideBottom.setAnimationListener(this);

        Typeface light=Typeface.createFromAsset(getAssets(),"fonts/montserrat-light.otf");


        Typeface bold=Typeface.createFromAsset(getAssets(),"fonts/montserrat-bold.otf");
        registerText.setTypeface(light);
        username.setTypeface(light);
        password.setTypeface(light);
        loginText.setTypeface(bold);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sUsername = username.getText().toString();
                String sPassword = password.getText().toString();

                if (sUsername.equals("") || sPassword.equals("")) {
                    Toast.makeText(Login.this, "Username or Password cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    sharedPreferences.edit().putString("username", sUsername).apply();
                    Intent i = new Intent(Login.this, Profile.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                }
            }
        });

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //disappear animation
                LayoutTransition transition = new LayoutTransition();
                transition.enableTransitionType(LayoutTransition.DISAPPEARING);
                loginLayout.setLayoutTransition(transition);
                selectView(true);
                registerLayout.startAnimation(slideTop);
                registerLayout.setVisibility(View.VISIBLE);
                registerLayout.setOverScrollMode(View.OVER_SCROLL_NEVER);
                registerLayout.setVerticalScrollBarEnabled(false);
            }
        });

        //initializing country spinner
        Locale[] locale = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<>();
        String country;
        for( Locale loc : locale ){
            country = loc.getDisplayCountry();
            if( country.length() > 0 && !countries.contains(country) ){
                countries.add( country );
            }
        }
        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);
        LoginSpinnerAdapter countriesAdapter = new LoginSpinnerAdapter(this, countries);
        countrySpinner.setAdapter(countriesAdapter);
        countrySpinner.setOverScrollMode(View.OVER_SCROLL_NEVER);
        countrySpinner.setVerticalScrollBarEnabled(false);

        //initializing day spinner
        ArrayList<String> days = new ArrayList<>();
        for (int i = 1; i < 32; i++) {
            days.add(i+"");
        }
        LoginSpinnerAdapter daysAdapter = new LoginSpinnerAdapter(this, days);
        daySpinner.setAdapter(daysAdapter);
        daySpinner.setOverScrollMode(View.OVER_SCROLL_NEVER);
        daySpinner.setVerticalScrollBarEnabled(false);

        //initializing month spinner
        ArrayList<String> months = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            months.add(i+"");
        }
        LoginSpinnerAdapter monthAdapter = new LoginSpinnerAdapter(this, months);
        monthSpinner.setAdapter(monthAdapter);
        monthSpinner.setOverScrollMode(View.OVER_SCROLL_NEVER);
        monthSpinner.setVerticalScrollBarEnabled(false);

        //initializing year spinner
        ArrayList<String> years = new ArrayList<>();
        for (int i = 2015; i > 1940 ; i--) {
            years.add(i+"");
        }
        LoginSpinnerAdapter yearAdapter = new LoginSpinnerAdapter(this, years);
        yearSpinner.setAdapter(yearAdapter);
        yearSpinner.setOverScrollMode(View.OVER_SCROLL_NEVER);
        yearSpinner.setVerticalScrollBarEnabled(false);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Profile.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        Picasso.with(this)
                .load(R.drawable.twitter_logo_blue)
                .fit()
                .centerCrop()
                .into(twitterLogin, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.wtf("salma", "success");

                    }

                    @Override
                    public void onError() {
                        Log.wtf("salma", "error");
                    }
                });
/*
        Picasso.with(this)
                .load(R.drawable.uni2)
                .fit()
                .centerCrop()
                .into(signinPicture);*/

        Picasso.with(this)
                .load(R.drawable.facebook_logo)
                .fit()
                .centerCrop()
                .into(facebookLogin);

        facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("public_profile", "email", "user_friends"));
            }
        });

        LoginManager.getInstance().logOut();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject jsonObject, GraphResponse response) {
                        try {
                            String name = jsonObject.getString("first_name") + "." + jsonObject.getString("last_name");
                            sharedPreferences.edit().putString("username", name).apply();
                            Intent i = new Intent(Login.this, Profile.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "first_name,last_name");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();

        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        AppEventsLogger.deactivateApp(this);
    }

    @Override
    public void onBackPressed() {
        if (registerLayout.getVisibility() == View.VISIBLE) {
            registerLayout.startAnimation(slideBottom);
            registerLayout.setVisibility(View.GONE);
            selectView(false);
        } else {
            super.onBackPressed();
        }
    }

    public void selectView(boolean flag) {
        if (flag) {
            username.setVisibility(View.GONE);
            password.setVisibility(View.GONE);
            loginButton.setVisibility(View.GONE);
            registerText.setVisibility(View.GONE);
            twitterLogin.setVisibility(View.GONE);
            facebookLogin.setVisibility(View.GONE);
        } else {
            username.setVisibility(View.VISIBLE);
            password.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.VISIBLE);
            registerText.setVisibility(View.VISIBLE);
            twitterLogin.setVisibility(View.VISIBLE);
            facebookLogin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {
        if (animation.equals(slideTop)) {
            registerLayout.setVisibility(View.VISIBLE);
        } else {
            registerLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


}
