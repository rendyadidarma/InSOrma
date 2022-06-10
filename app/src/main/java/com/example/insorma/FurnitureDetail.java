package com.example.insorma;


import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class FurnitureDetail extends AppCompatActivity implements View.OnClickListener {
    ImageView imgView;
    TextView nameView, ratingView, priceView, descView;
    EditText qtyView;
    Button submitOrder;
    DBHelper dbHelper;
    SharedPreferences sharedPreferences;
    long product_id;

    String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furniture_detail);

        init();
        submitOrder.setOnClickListener(this);
    }

    public void init() {
        qtyView = findViewById(R.id.quantity);
        submitOrder = findViewById(R.id.buy_now);
        Intent intent = this.getIntent();

        dbHelper = new DBHelper(this);

        if(intent != null) {
            product_id = intent.getLongExtra("position", 0);
            phoneNumber = intent.getStringExtra("phone_number");
            Log.wtf("Phone in Detail 1", phoneNumber);
            nameView = findViewById(R.id.detail_name);
            ratingView = findViewById(R.id.detail_rating);
            priceView = findViewById(R.id.detail_price);
            descView = findViewById(R.id.detail_desc);
            imgView = findViewById(R.id.detail_img);

            Cursor cursor = dbHelper.getProductData(product_id);
            cursor.moveToFirst();

            nameView.setText(cursor.getString(1));
            ratingView.setText(cursor.getString(2));
            priceView.setText(cursor.getString(3));
            new ListAdapter.DownloadImageTask(imgView).execute(cursor.getString(4));
            descView.setText(cursor.getString(5));

            cursor.close();

            sharedPreferences = getSharedPreferences("LOGGED_IN", MODE_PRIVATE);

        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buy_now) {
            String temp = qtyView.getText().toString();

            if(temp.trim().equalsIgnoreCase("")) {
                Toast.makeText(this, "Quantity cannot be empty", Toast.LENGTH_SHORT).show();
                qtyView.requestFocus();
            } else {
                int quantity = Integer.parseInt(temp);
                if(quantity <= 0) {
                    Toast.makeText(this, "Quantity must be more than zero", Toast.LENGTH_SHORT).show();
                    qtyView.requestFocus();
                } else {

//                    int tempId = historyList.size()+1;
                    Date currDate = new Date();
                    dbHelper.setTransactionData(new Transaction(-1, sharedPreferences.getInt("ID", -1) , (int) product_id, quantity, currDate));

                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        Log.wtf("Phone in Detail 2", phoneNumber);
                        smsManager.sendTextMessage(phoneNumber, null, "Test123", null, null);
                        Log.wtf("Success", "Message Sent");
                    } catch (Exception e) {
                        Log.wtf("Error", "Empty Field");
                    }

                    Toast.makeText(this, "COMPLETE BUY!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }
}