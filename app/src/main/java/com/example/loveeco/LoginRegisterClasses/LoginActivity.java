package com.example.loveeco.LoginRegisterClasses;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.loveeco.R;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button login;
    String pass,role;
    public static final String MyPREFERENCES = "com.example.myrentalapp.MyPrefs";

    private DBConnectivity receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.etUserName);
        password = findViewById(R.id.etPassword);
        login=findViewById(R.id.btnSubmit);

        receiver = new DBConnectivity(pass,role,this);

        ClickLogin();

    }

    //This is method for doing operation of check login
    private void ClickLogin() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (username.getText().toString().trim().isEmpty()) {

                    Snackbar snackbar = Snackbar.make(view, "Please fill out these fields",
                            Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbar.show();
                  //  txtInLayoutUsername.setError("Username should not be empty");
                }
                if (password.getText().toString().trim().isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, "Please fill out these fields",
                            Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbar.show();
                  //  txtInLayoutPassword.setError("Password should not be empty");
                }
                Intent i = new Intent(LoginActivity.this,DownloadService.class);
                SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
                editor.putString("pass", password.getText().toString());
//                editor.putString("role", spinnerRole.getSelectedItem().toString());
//                editor.putString("loginOrRegister", "login");

                editor.apply();
                i.putExtra("UserName",username.getText().toString());
//                i.putExtra("loginOrRegister","login");
                startService(i);

            }


        });

    }


    @Override
    protected void onPause() {
        // Unregister since the activity is paused.
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // An IntentFilter can match against actions, categories, and data
        IntentFilter filter = new IntentFilter(DBConnectivity.STATUS_DONE);
          /*
        Intent registerReceiver (BroadcastReceiver receiver, IntentFilter filter)
        Register a BroadcastReceiver to be run in the main activity thread.
        The receiver will be called with any broadcast Intent that matches filter,
        in the main application thread.
         */

        registerReceiver(receiver,filter);
    }
}
