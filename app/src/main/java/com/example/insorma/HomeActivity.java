package com.example.insorma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.insorma.fragment.AboutUsFragment;
import com.example.insorma.fragment.HistoryFragment;
import com.example.insorma.fragment.HomeFragment;
import com.example.insorma.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;

public class HomeActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private BottomNavigationView bottomNav;
    private int user_id_logged;

    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
        bottomNav.setOnItemSelectedListener(this);
    }

    private void generateData() {

        Cursor cursor = dbHelper.getProductData();
        cursor.moveToFirst();

        if(cursor.getCount() == 0) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://bit.ly/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RetrofitJSON retrofit1 = retrofit.create(RetrofitJSON.class);
            Call<JsonObject> getProduct = retrofit1.getProduct();

            getProduct.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(!response.isSuccessful()) {
                        Log.wtf("Error Response Unsuccessful", "Code Error : " + response.code());
                    } else {

                        if (response.body() != null) {
                            JsonArray products = response.body().getAsJsonArray("furnitures");

                            for(JsonElement item: products) {
                                JsonObject objectItem = item.getAsJsonObject();

                                Product product = new Product(-1,
                                        objectItem.get("price").getAsInt(),
                                        objectItem.get("rating").getAsString(),
                                        objectItem.get("product_name").getAsString(),
                                        objectItem.get("image").getAsString(),
                                        objectItem.get("description").getAsString()
                                );

                                dbHelper.setProductData(product);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.wtf("OnFailure", "Error" + t);
                }
            });
//            StringBuilder data = new StringBuilder();
////
////            try {
////                URL urlTarget = new URL("https://bit.ly/InSOrmaJSON");
////
////                HttpURLConnection httpURLConnection = (HttpURLConnection) urlTarget.openConnection();
////                InputStream inputStream = httpURLConnection.getInputStream();
////                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
////
////                String temp;
////                while((temp = bufferedReader.readLine()) != null) {
////                    data.append(temp);
////                }
////                if(data.length() > 0) {
////                    JSONObject jsonObject = new JSONObject(data.toString());
////                    JSONArray jsonArray = jsonObject.getJSONArray("furnitures");
////                    for(int i = 0; i < jsonArray.length() ; i++) {
////                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
////                        Log.v("tes", jsonObject1.getString("product_name"));
////                        Product product = new Product(i+1, jsonObject1.getInt("price"),
////                                jsonObject1.getString("rating"),
////                                jsonObject1.getString("product_name"),
////                                jsonObject1.getString("image"),
////                                jsonObject1.getString("description"));
////                        Log.wtf("Product" + i, product.toString());
////                        dbHelper.setProductData(product);
////                    }
////                }
////            } catch (IOException | JSONException e) {
////                e.printStackTrace();
////            } finally {
////                cursor.close();
////            }
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

    @Override
    protected void onStart() {
        super.onStart();
//        generateData();
    }

    public void init() {
        SharedPreferences sPref = getSharedPreferences("LOGGED_IN", MODE_PRIVATE);
        user_id_logged = sPref.getInt("ID", -1);
        switchFragment(new HomeFragment(user_id_logged));
        generateData();
//        switchFragment(new HomeFragment(user_id_logged));
        bottomNav = findViewById(R.id.bottomNav);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_home) {
            switchFragment(new HomeFragment(user_id_logged));
        } else if(item.getItemId() == R.id.action_history) {
            switchFragment(new HistoryFragment());
        } else if(item.getItemId() == R.id.action_aboutUs){
            switchFragment(new AboutUsFragment());
        } else if(item.getItemId() == R.id.action_profile) {
            switchFragment(new ProfileFragment(user_id_logged));
        }
        return true;
    }



}