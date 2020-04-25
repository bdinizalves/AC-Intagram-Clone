package com.limzhanhong.acintagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener  {

    private EditText edtTextName, edtTextYearOfExperience, edtTextProgrammingLanguage;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtTextName = findViewById(R.id.editTextName);
        edtTextYearOfExperience = findViewById(R.id.editTextYearOfExperience);
        edtTextProgrammingLanguage = findViewById(R.id.editTextProgrammingLanguage);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

            final ParseObject programmer = new ParseObject("Programmer");
            programmer.put("Name", edtTextName.getText().toString());
            programmer.put("YearOfExperience", Integer.parseInt(edtTextYearOfExperience.getText().toString()));
            programmer.put("ProgrammingLanguage", edtTextProgrammingLanguage.getText().toString());
        try {
            programmer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        FancyToast.makeText(MainActivity.this, "Your data is saved successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                        //Toast.makeText(MainActivity.this, "Your data is saved successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        FancyToast.makeText(MainActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                        //Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (Exception e){
            FancyToast.makeText(MainActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
        }

        edtTextName.setText("");
        edtTextYearOfExperience.setText("");
        edtTextProgrammingLanguage.setText("");
    }
}
