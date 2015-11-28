package eg.edu.guc.tala3nybachelor;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import eg.edu.guc.tala3nybachelor.adapter.LoginSpinnerAdapter;

public class Login extends FullScreenActivity implements Animation.AnimationListener{

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

    private Animation slideTop;
    private Animation slideBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        registerText.setPaintFlags(registerText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        slideTop = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_top);
        slideTop.setAnimationListener(this);
        slideBottom = AnimationUtils.loadAnimation(this, R.anim.slide_top_bottom);
        slideBottom.setAnimationListener(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sUsername = username.getText().toString();
                String sPassword = password.getText().toString();

                if (sUsername.equals("") || sPassword.equals("")) {
                    Toast.makeText(Login.this, "Username or Password cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
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
        } else {
            username.setVisibility(View.VISIBLE);
            password.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.VISIBLE);
            registerText.setVisibility(View.VISIBLE);
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
