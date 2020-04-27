package com.limzhanhong.acintagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtEmailLogin, edtPasswordLogin;
    private Button btnLoginActivity, btnSignUpLoginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        setTitle("Log In");

        edtEmailLogin = findViewById(R.id.editTextEmailLogIn);
        edtPasswordLogin = findViewById(R.id.editTextPasswordLogIn);
        btnLoginActivity = findViewById(R.id.btnLogInActivity);
        btnSignUpLoginActivity = findViewById(R.id.btnSigninActivity);

        btnLoginActivity.setOnClickListener(this);
        btnSignUpLoginActivity.setOnClickListener(this);

        edtPasswordLogin.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event){
                if(keyCode == KeyEvent.KEYCODE_ENTER &&
                        event.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(btnLoginActivity);
                    //Button class is a subclass of view, therefore button is also considered as a view
                }
                return false;
            }
        });

        if(ParseUser.getCurrentUser() != null){
            ParseUser.getCurrentUser().logOut();
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){


            case R.id.btnLogInActivity:
                if(edtEmailLogin.getText().toString().equals("") || edtPasswordLogin.getText().toString().equals("")){
                    FancyToast.makeText(LogInActivity.this, "Email and Password is required",
                            FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();
                }
                else {
                    final ProgressDialog progressDialog = new ProgressDialog(LogInActivity.this);
                    progressDialog.setMessage("Logging in...");
                    progressDialog.show();

                    ParseUser.logInInBackground(edtEmailLogin.getText().toString(),
                            edtPasswordLogin.getText().toString(),

                            new LogInCallback() {
                                @Override
                                public void done(ParseUser user, ParseException e) {
                                    if(user != null && e == null){
                                        FancyToast.makeText(LogInActivity.this, user.getUsername() + " is logged in successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                                    } else {
                                        FancyToast.makeText(LogInActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                                    }
                                    progressDialog.dismiss();
                                    transitionToSocialMediaActivity();
                                }
                            });
                }

                break;

            case R.id.btnSigninActivity:

                break;
        }

    }

    public void layoutIsTapped(View view){

        try{
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch(Exception e){
            e.printStackTrace();
        }
    }


    private void transitionToSocialMediaActivity() {
        Intent intent = new Intent(LogInActivity.this, SocialMediaActivity.class);
        startActivity(intent);
    }
}
