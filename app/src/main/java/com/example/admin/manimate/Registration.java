package com.example.admin.manimate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.manimate.AsyncTasks.AsyncResponse;
import com.example.admin.manimate.AsyncTasks.WebserviceCall;
import com.example.admin.manimate.Helper.Utils;
import com.example.admin.manimate.Model.SignUp;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.admin.manimate.R.id.fullName;
import static com.example.admin.manimate.R.layout.activity_registration;

public class Registration extends AppCompatActivity   {
    Spinner spinner;

    int selectedRole=1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_registration);
        final EditText ed1=(EditText)findViewById(fullName);
        final EditText ed2=(EditText)findViewById(R.id.userEmailId);
        final EditText ed3=(EditText)findViewById(R.id.mobileNumber);
        final EditText ed4=(EditText)findViewById(R.id.addrs);
        final EditText ed5=(EditText)findViewById(R.id.password);
        Button button=(Button)findViewById(R.id.signUpBtn) ;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strname = ed1.getText().toString();
                String strmailid = ed2.getText().toString();
                String strmobile = ed3.getText().toString();
                String stradrs = ed4.getText().toString();
                String strpass = ed5.getText().toString();
                if (strname.isEmpty()) {
                    Toast.makeText(Registration.this, "UserName can't be empty", Toast.LENGTH_SHORT).show();
                } else if (strmailid.isEmpty()) {
                    Toast.makeText(Registration.this, "Emailid can't be empty", Toast.LENGTH_SHORT).show();

                } else if (strmobile.isEmpty()) {
                    Toast.makeText(Registration.this, "mobile can't be empty", Toast.LENGTH_SHORT).show();

                } else if (stradrs.isEmpty()) {
                    Toast.makeText(Registration.this, "Address can't be empty", Toast.LENGTH_SHORT).show();

                } else if (strpass.isEmpty()) {
                    Toast.makeText(Registration.this, "password can't be empty", Toast.LENGTH_SHORT).show();

                } else {


                    String[] keys = new String[]{"mode", "userName", "emailId", "contactNumber", "address", "password"};
                    String[] values = new String[]{"Registration", strname, strmailid, strmobile, stradrs, strpass};
                    String jsonRequest = Utils.createJsonRequest(keys, values);

                    String URL = "http://vnurture.in/logistic/webservice.php";

                    new WebserviceCall(Registration.this, URL, jsonRequest, "Logging in", true, new AsyncResponse() {
                        @Override
                        public void onCallback(String response) {
                            Log.d("myapp", response);
                            SignUp model = new Gson().fromJson(response, SignUp.class);
                            Toast.makeText(Registration.this, model.getMessage(), Toast.LENGTH_SHORT).show();
                            if (model.getStatus() == 1) {
                                Intent intent = new Intent(Registration.this, Home.class);

                                startActivity(intent);
                            }

                        }
                    }).execute();

                }
//
            }
//
        });
    }






            public static boolean isValidEmail(CharSequence target) {
                Pattern pattern;
                Matcher matcher;
                final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                pattern = Pattern.compile(EMAIL_PATTERN);
                matcher = pattern.matcher(target);
                return matcher.matches();
            }

}


