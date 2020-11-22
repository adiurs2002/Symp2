package com.example.symp2.RetrofitClient;

public class APIUtils {
    private void ApiUtils() {}

    public static final String BASE_URL = "http://192.168.43.238:8000";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

}
