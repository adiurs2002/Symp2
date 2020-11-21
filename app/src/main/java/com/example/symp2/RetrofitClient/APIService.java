package com.example.symp2.RetrofitClient;

import com.example.symp2.models.SignInRequest;
import com.example.symp2.models.SignUpResponse;
import com.example.symp2.models.User;
import com.example.symp2.models.UserInfoRequest;
import com.example.symp2.models.UserRequest;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @POST("/signup")
    @Headers({"Content-Type : application/json"})
    Call<JsonObject> signUp(@Body SignUpResponse body);

    @POST("/BMI")
    @Headers({"Content-Type : application/json"})
    Call<JsonObject> userInfo(@Body UserInfoRequest body);

    @POST("/login")
    @Headers({"Content-Type : application/json"})
    Call<JsonObject> login(@Body SignInRequest signInRequest);

    @POST("/alluserinfo")
    @Headers({"Content-Type : application/json"})
    Call<JsonObject> getUser(@Body UserRequest userRequest);

}
