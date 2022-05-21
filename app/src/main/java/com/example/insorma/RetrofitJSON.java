package com.example.insorma;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitJSON {
    @GET("InSOrmaJSON")

    Call<JsonObject> getProduct();
}
