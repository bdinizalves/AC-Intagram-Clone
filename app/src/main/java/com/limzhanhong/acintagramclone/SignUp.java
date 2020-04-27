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

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements  View.OnClickListener  {

    private EditText edtTextUsername, edtTextEmail, edtTextPassword;
    private Button btnLogIn, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Sign Up");

        edtTextUsername = findViewById(R.id.editTextUserNameSignUp);
        edtTextEmail = findViewById(R.id.editTextEmailSignUp);
        edtTextPassword = findViewById(R.id.editTextPasswordSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogIn = findViewById(R.id.btnLogInActivity);

        btnSignUp.setOnClickListener(this);
        btnLogIn.setOnClickListener(this);

        edtTextPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event){
                if(keyCode == KeyEvent.KEYCODE_ENTER &&
                        event.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(btnSignUp);
                    //Button class is a subclass of view, therefore button is also considered as a view
                }
                return false;
            }
        });

//        if(ParseUser.getCurrentUser() != null){
//            transitionToSocialMediaActivity();
//        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSignUp:

                if(edtTextEmail.getText().toString().equals("") ||
                        edtTextUsername.getText().toString().equals("") ||
                        edtTextPassword.getText().toString().equals("")) {

                    FancyToast.makeText(SignUp.this, "Email, Username, Password is required",
                            FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();

                } else {
                    final ParseUser appUser = new ParseUser();
                    appUser.setEmail(edtTextEmail.getText().toString());
                    appUser.setUsername(edtTextUsername.getText().toString());
                    appUser.setPassword(edtTextPassword.getText().toString());

                    final ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
                    progressDialog.setMessage("Signing up " + edtTextUsername.getText().toString());
                    progressDialog.show();

                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null){
                                FancyToast.makeText(SignUp.this, appUser.getUsername() + " is signed up successfully",
                                        FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                                transitionToSocialMediaActivity();

                            } else{
                                FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }
                break;
            case R.id.btnLogInActivity:

                Intent intent = new Intent(SignUp.this, LogInActivity.class);
                startActivity(intent);

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
        Intent intent = new Intent(SignUp.this, SocialMediaActivity.class);
        startActivity(intent);
    }

//    @Override
//    public void onClick(View v) {
//
//            final ParseObject programmer = new ParseObject("Programmer");
//            programmer.put("Name", edtTextName.getText().toString());
//            programmer.put("YearOfExperience", Integer.parseInt(edtTextYearOfExperience.getText().toString()));
//            programmer.put("ProgrammingLanguage", edtTextProgrammingLanguage.getText().toString());
//        try {
//            programmer.saveInBackground(new SaveCallback() {
//                @Override
//                public void done(ParseException e) {
//                    if (e == null) {
//                        FancyToast.makeText(MainActivity.this, "Your data is saved successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
//                        //Toast.makeText(MainActivity.this, "Your data is saved successfully", Toast.LENGTH_SHORT).show();
//                    } else {
//                        FancyToast.makeText(MainActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
//                        //Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                }
//            });
//        } catch (Exception e){
//            FancyToast.makeText(MainActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
//        }
//    }
}
