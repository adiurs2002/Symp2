package com.example.symp2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.symp2.Adapter.MedicinAdapter;
import com.example.symp2.RetrofitClient.APIService;
import com.example.symp2.RetrofitClient.APIUtils;
import com.example.symp2.models.Medicine;
import com.example.symp2.models.User;
import com.example.symp2.models.UserRequest;
import com.example.symp2.utils.UserSession;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {


    private APIService apiService;
    UserSession userSession ;
    UserRequest userRequest;
    List<Medicine> medicines;
    RecyclerView medicineRecyclerView;

    public HomeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        apiService = APIUtils.getAPIService();
        userSession = new UserSession(getActivity());
        userRequest = new UserRequest();
        medicines = new ArrayList<>();
        medicineRecyclerView = view.findViewById(R.id.medicineRecyclerView);
        thread.start();



        return view;
    }

    Thread thread = new Thread(){
        @Override
        public void run() {
            super.run();
            userRequest.setUserId(userSession.getUserDetails().get("_id"));
            apiService.getUser(userRequest).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Log.d("TAG", "onResponse: "+response.body().toString());
                    for (int i = 0 ; i<response.body().getAsJsonArray("medicines").size();i++){
                        Log.d("TAG", "onResponse: "+ response.body().getAsJsonArray("medicines").get(i));
                        Gson g = new Gson();
                        Medicine med = g.fromJson(response.body().getAsJsonArray("medicines").get(i),Medicine.class);
                        Log.d("TAG", "onResponse: "+med.getId());
                        medicines.add(med);
                        medicineRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
                        MedicinAdapter medicinAdapter = new MedicinAdapter(getContext(),medicines);
                        medicineRecyclerView.setAdapter(medicinAdapter);
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });
            Log.d("TAG", "onResponse: med"+medicines.size());

        }
    };
}