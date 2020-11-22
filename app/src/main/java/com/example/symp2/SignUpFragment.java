package com.example.symp2;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import com.example.symp2.models.SignUpResponse;
import com.example.symp2.models.User;
import com.example.symp2.utils.UserSession;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpFragment extends Fragment {

    EditText username,password;
    Button signupBtnFrag;
    private APIService mAPIService;
    UserSession userSession;

    public SignUpFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    private void sendPost(String user_name, String password_user) {
        SignUpResponse signUpResponse = new SignUpResponse();
        signUpResponse.setName("XYZ");
        signUpResponse.setUsername(user_name);
        signUpResponse.setPassword(password_user);
        mAPIService.signUp(signUpResponse).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){
                    JsonObject object = response.body();
                    Log.i("Response",response.body().toString());
                    userSession.createLoginSession(object.getAsJsonObject("user").get("username").toString().replaceAll("\"",""),object.getAsJsonObject("user").get("_id").toString().replaceAll("\"",""),object.getAsJsonObject("user").get("name").toString().replaceAll("\"",""));
                    Log.d("TAG",object.getAsJsonObject("user").get("_id").toString().replaceAll("\"",""));
                    FragmentManager fm = getActivity().getFragmentManager();
                    FragmentTransaction tm = fm.beginTransaction();
                    tm.replace(R.id.fragment, new UserInfoFragment());
                    tm.commit();
                }
                else{
                    Log.d("err","errorororororor"+response.message()+response.body());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("failed si ",t.toString());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        username = view.findViewById(R.id.Username);
        password = view.findViewById(R.id.password_toggle);
        signupBtnFrag = view.findViewById(R.id.btnSignUpFrag);
        mAPIService = APIUtils.getAPIService();
        userSession = new UserSession(getActivity());

        Log.d("TAG","");

        signupBtnFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_name = username.getText().toString();
                String password_user = password.getText().toString();
                sendPost(user_name,password_user);
            }
        });

        return view;
    }
}