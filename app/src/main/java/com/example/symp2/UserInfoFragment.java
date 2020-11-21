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
import com.example.symp2.models.UserInfoRequest;
import com.example.symp2.utils.UserSession;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserInfoFragment extends Fragment {


    public UserInfoFragment() {
        // Required empty public constructor
    }

    private Button signInbtn,proceed;
    private EditText weight,height,age;
    UserSession userSession;
    private APIService mAPIService;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        signInbtn = getActivity().findViewById(R.id.btnSignIn);
        proceed = getActivity().findViewById(R.id.btnProceed);
        weight = view.findViewById(R.id.weight);
        height = view.findViewById(R.id.height);
        age = view.findViewById(R.id.age);
        signInbtn.setVisibility(View.GONE);
        proceed.setVisibility(View.VISIBLE);
        mAPIService = APIUtils.getAPIService();
        userSession = new UserSession(getActivity());
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfoRequest userInfoRequest = new UserInfoRequest();
                userInfoRequest.setAge(Integer.parseInt(age.getText().toString()));
                userInfoRequest.setHeight(Integer.parseInt(height.getText().toString()));
                userInfoRequest.setWeight(Integer.parseInt(weight.getText().toString()));
                userInfoRequest.setUserId(userSession.getUserDetails().get("_id"));
                mAPIService.userInfo(userInfoRequest).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            Log.d("TAG", "onResponse: succ");
                            Intent intent = new Intent(getActivity(),MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Log.d("TAG", "onResponse: errrrr");
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("TAG",call.toString()+"errrrrrr");
                    }
                });
            }
        });

        return view;
    }
}