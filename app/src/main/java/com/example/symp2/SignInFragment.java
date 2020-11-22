package com.example.symp2;

import android.content.Intent;
import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.symp2.RetrofitClient.APIService;
import com.example.symp2.RetrofitClient.APIUtils;
import com.example.symp2.models.SignInRequest;
import com.example.symp2.utils.UserSession;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInFragment extends Fragment {

    private Button signInBtn;
    private EditText username,password;
    private APIService apiService;
    private UserSession userSession;

    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
       signInBtn = view.findViewById(R.id.btnSignInfrag);
       username = view.findViewById(R.id.SigninUsername);
       password = view.findViewById(R.id.SigninPassword);
       userSession = new UserSession(getActivity());
       apiService = APIUtils.getAPIService();
       signInBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               thread.start();
           }
       });
        return view;
    }

    Thread thread = new Thread(){
        @Override
        public void run() {
            super.run();
            SignInRequest signInRequest = new SignInRequest();
            signInRequest.setUsername(username.getText().toString());
            signInRequest.setPassword(password.getText().toString());
            apiService.login(signInRequest).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Log.d("TAG", "onResponse: "+response.body());
                    Intent intent = new Intent(getActivity(),MainActivity.class);
                    userSession.createLoginSession(response.body().get("username").toString().replaceAll("\"",""),response.body().get("_id").toString().replaceAll("\"",""),response.body().get("name").toString().replaceAll("\"",""));
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });

        }
    };
}