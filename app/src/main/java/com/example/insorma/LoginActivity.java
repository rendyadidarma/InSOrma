package com.example.insorma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public DBHelper database;
    SharedPreferences sPref;

    EditText email, password;
    Button sign_in, register;
    // temporary data
    public static int counter = 1;
    public static ArrayList<User> tempDataUser = new ArrayList<>();

    Cursor userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        database = new DBHelper(this);
        userData = database.getUserData();
        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
        sign_in = findViewById(R.id.login);
        register = findViewById(R.id.register);

        sPref = getSharedPreferences("LOGGED_IN", MODE_PRIVATE);

        boolean flag = checkPreferences();

        if(flag) {
            Toast.makeText(this, sPref.getString("Email", ""), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }
        sign_in.setOnClickListener(this);
        register.setOnClickListener(this);

    }

    public boolean checkPreferences() {
        String email = sPref.getString("Email", "");
        String pass = sPref.getString("Password", "");

        if(!email.equalsIgnoreCase("") && !pass.equalsIgnoreCase("")){
            boolean flag = getValidateUser(email, pass);
            return flag;
        }

        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        email.getText().clear();
        password.getText().clear();

        userData = database.getUserData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.wtf("Destroy", "onDestroyMan");
        userData.close();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.login) {
            userData = database.getUserData();
            if(email.getText().toString().trim().equalsIgnoreCase("") || password.getText().toString().trim().equalsIgnoreCase("")) {
                if(email.getText().toString().trim().equalsIgnoreCase("")){
                    email.setError("Email field must be filled in");
                    email.requestFocus();
                }
                if(password.getText().toString().trim().equalsIgnoreCase("")) {
                    password.setError("Password field must be filled in");
                    password.requestFocus();
                }
            } else {
                boolean flag = getValidateUser(email.getText().toString(), password.getText().toString());
                Log.wtf("flag", Boolean.toString(flag));
                if(flag) { // berhasil login
                    Log.wtf("tes", Integer.toString(userData.getInt(0)));
                    SharedPreferences.Editor editor = sPref.edit();
                    editor.putInt("ID", userData.getInt(0));
                    editor.putString("Email", userData.getString(1));
                    editor.putString("Password", userData.getString(4));
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else { //gagal login
                    Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                }
            }
        } else if(v.getId() == R.id.register) {
            Intent abc = new Intent(this, RegisterActivity.class);
            startActivity(abc);
        }
    }

    private boolean getValidateUser(String email, String password) {
        if(userData.getCount() != 0) {
            Log.wtf("ERROR", "MAMAMU ERROR");
            userData.moveToFirst();
            do {
                Log.wtf("userData", userData.getString(1));
                Log.wtf("userPassword", userData.getString(4));
                if(userData.getString(1).equals(email) && userData.getString(4).equals(password)) {
                    Log.wtf("userData", email);
                    Log.wtf("userPassword", password);
                    return true;
                }
            } while(userData.moveToNext());
        } else {
            Log.wtf("ERROR2", "MAMAMU ERROR");
        }
        return false;
    }

}