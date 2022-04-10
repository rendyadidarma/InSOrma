package com.example.insorma;

import static com.example.insorma.LoginActivity.tempDataUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.insorma.databinding.ActivityHomeBinding;
import com.example.insorma.fragment.HistoryFragment;
import com.example.insorma.fragment.HomeFragment;
import com.example.insorma.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    BottomNavigationView bottomNav;
    private int user_id_logged;

    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        init();
        bottomNav.setOnItemSelectedListener(this);
    }

    private void generateData() {

        Cursor cursor = dbHelper.getProductData();
        cursor.moveToFirst();

        if(cursor.getCount() == 0) {
            StringBuilder data = new StringBuilder();

            try {
                URL urlTarget = new URL("https://bit.ly/InSOrmaJSON");

                HttpURLConnection httpURLConnection = (HttpURLConnection) urlTarget.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String temp;
                while((temp = bufferedReader.readLine()) != null) {
                    data.append(temp);
                }
                if(data.length() > 0) {
                    JSONObject jsonObject = new JSONObject(data.toString());
                    JSONArray jsonArray = jsonObject.getJSONArray("furnitures");
                    for(int i = 0; i < jsonArray.length() ; i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        Log.v("tes", jsonObject1.getString("product_name"));
                        Product product = new Product(i+1, jsonObject1.getInt("price"),
                                jsonObject1.getString("rating"),
                                jsonObject1.getString("product_name"),
                                jsonObject1.getString("image"),
                                jsonObject1.getString("description"));
                        Log.wtf("Product" + i, product.toString());
                        dbHelper.setProductData(product);
                    }
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        generateData();
    }

    private void switchFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment);
        ft.commit();
    }

    public void init() {
        SharedPreferences sPref = getSharedPreferences("LOGGED_IN", MODE_PRIVATE);
        user_id_logged = sPref.getInt("ID", -1);
        generateData();
        switchFragment(new HomeFragment(user_id_logged));
        bottomNav = findViewById(R.id.bottomNav);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_home) {
            switchFragment(new HomeFragment(user_id_logged));
        } else if(item.getItemId() == R.id.action_history) {
            switchFragment(new HistoryFragment());
        } else if(item.getItemId() == R.id.action_profile) {
            switchFragment(new ProfileFragment(user_id_logged));
        }
        return true;
    }



}