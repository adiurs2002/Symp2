package com.example.symp2.RetrofitClient;

public class APIUtils {
    private void ApiUtils() {}

    public static final String BASE_URL = "https://disease-prediction-hack.herokuapp.com/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
