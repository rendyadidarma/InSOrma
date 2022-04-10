package com.example.insorma;


import static com.example.insorma.LoginActivity.tempDataUser;
import static com.example.insorma.LoginActivity.counter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    public DBHelper database = new DBHelper(this);
    EditText username, email, phone_number, password;
    Button login_btn, regis_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = findViewById(R.id.editTextUsername);
        email = findViewById(R.id.editTextTextEmailAddress);
        phone_number = findViewById(R.id.editTextPhone);
        password = findViewById(R.id.editTextPassword);

        login_btn = findViewById(R.id.buttonLogin);
        regis_btn = findViewById(R.id.buttonRegister);

        login_btn.setOnClickListener(this);
        regis_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.buttonLogin) {
            Intent a = new Intent(this, LoginActivity.class);
            startActivity(a);
        } else if(v.getId() == R.id.buttonRegister) {
            boolean flag = true;

            if(username.getText().toString().trim().equalsIgnoreCase("")) {
                username.setError("Username field must be filled in");
                username.requestFocus();
                flag = false;
            }

            if(email.getText().toString().trim().equalsIgnoreCase("")){
                email.setError("Email field must be filled in");
                email.requestFocus();
                flag = false;
            }

            if(phone_number.getText().toString().trim().equalsIgnoreCase("")){
                phone_number.setError("Phone Number field must be filled in");
                phone_number.requestFocus();
                flag = false;
            }

            if(password.getText().toString().trim().equalsIgnoreCase("")) {
                password.setError("Password field must be filled in");
                password.requestFocus();
                flag = false;
            }

            if(!email.getText().toString().trim().equalsIgnoreCase("") && !email.getText().toString().endsWith(".com")) {
                email.setError("Your email type is invalid");
                email.requestFocus();
                flag = false;
            }

            if(!username.getText().toString().trim().equalsIgnoreCase("") && (username.getText().toString().length() < 3 || username.getText().toString().length() > 20)) {
                username.setError("Username must be between 3 - 20");
                username.requestFocus();
                flag = false;
            }

            boolean numFlag = false;
            boolean letFlag = false;

            for(int i = 0; i  < password.getText().toString().length(); i++) {
                if(Character.isDigit(password.getText().toString().charAt(i)))
                    numFlag = true;
                else if(Character.isLetter(password.getText().toString().charAt(i)))
                    letFlag = true;
            }

            if(!password.getText().toString().trim().equalsIgnoreCase("") && (!numFlag || !letFlag)) {
                password.setError("Password must contain one digit number and one letter");
                flag = false;
            }

            if(flag) {
                try {
                    User data = new User();
                    data.setUserEmailAddress(email.getText().toString());
                    data.setUserUsername(username.getText().toString());
                    data.setUserPhoneNumber(phone_number.getText().toString());
                    data.setUserPassword(password.getText().toString());

                    boolean isSet = database.setUserData(data);

                    if(isSet) {
                        Toast.makeText(RegisterActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (SQLiteConstraintException e) {
                    Toast.makeText(RegisterActivity.this, "Error Biasa", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

}