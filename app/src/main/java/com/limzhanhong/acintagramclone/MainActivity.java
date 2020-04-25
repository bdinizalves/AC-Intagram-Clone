package com.limzhanhong.acintagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener  {

    private EditText edtTextName, edtTextYearOfExperience, edtTextProgrammingLanguage;
    private Button btnSubmit, btnGetAllData;
    private TextView txtGetData;
    private String allProgrammer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtTextName = findViewById(R.id.editTextName);
        edtTextYearOfExperience = findViewById(R.id.editTextYearOfExperience);
        edtTextProgrammingLanguage = findViewById(R.id.editTextProgrammingLanguage);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        btnGetAllData = findViewById(R.id.btnGetAllData);


        txtGetData = findViewById(R.id.textViewGetData);
        txtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Programmer");
                parseQuery.getInBackground("137QJleV0K", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if(object != null && e == null){
                            txtGetData.setText(object.get("Name") + " " + object.get("YearOfExperience"));
                        }
                    }
                });
            }
        });

        btnGetAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                allProgrammer = "";
                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("Programmer");
                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if(e == null){
                            if(objects.size() > 0){
                                for(ParseObject parseObject: objects){
                                    allProgrammer += parseObject.get("Name") + " " +  parseObject.get("YearOfExperience") + " " + parseObject.get("ProgrammingLanguage") + "\n";
                                }
                                FancyToast.makeText(MainActivity.this, allProgrammer, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                            } else {
                                FancyToast.makeText(MainActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                            }
                        }
                    }
                });
            }
        });

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
