package com.example.symp2;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TimeUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.symp2.Adapter.MedicinAdapter;
import com.example.symp2.Adapter.TimePickerFragment;
import com.example.symp2.RetrofitClient.APIService;
import com.example.symp2.RetrofitClient.APIUtils;
import com.example.symp2.models.Medicine;
import com.example.symp2.models.MedicineRequest;
import com.example.symp2.models.User;
import com.example.symp2.models.UserRequest;
import com.example.symp2.utils.UserSession;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements TimePickerDialog.OnTimeSetListener {


    private APIService apiService;
    UserSession userSession ;
    UserRequest userRequest;
    List<Medicine> medicines;
    RecyclerView medicineRecyclerView;
    private FloatingActionButton newMedicine;
    List<Calendar> times;
    MedicineRequest request;
    AlertDialog alertDialog;
    TextView taken;
    CircularProgressBar circularProgressBar;


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
        taken = view.findViewById(R.id.medicinesTaken);
        circularProgressBar = view.findViewById(R.id.circularProgressBar);
        times = ((MainActivity)getActivity()).getTimes();
        newMedicine = view.findViewById(R.id.newMedicine);
        medicineRecyclerView = view.findViewById(R.id.medicineRecyclerView);
       // thread.start();
        taken.setText("Medicines Taken : "+String.valueOf(userSession.getDoneAlerts())+"/"+String.valueOf(userSession.getAlerts()));
        if (userSession.getAlerts()==0)
            circularProgressBar.setProgress(0);
        else {
            circularProgressBar.setProgressMax(100);
            int done = userSession.getDoneAlerts();
            int all = userSession.getAlerts();
            float result = ((float)done/all)*100;
            Log.d("TAG", "run:hurray "+result);
            circularProgressBar.setProgress(result);
        }
        userRequest.setUserId(userSession.getUserDetails().get("_id"));
        apiService.getUser(userRequest).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("TAG", "onResponse: "+response.body().toString());
                for (int i = 0 ; i<response.body().getAsJsonArray("medicines").size();i++){
                    Log.d("TAG", "onResponse: "+ response.body().getAsJsonArray("medicines").get(i));
                    Gson g = new Gson();
                    Medicine med = g.fromJson(response.body().getAsJsonArray("medicines").get(i),Medicine.class);
                    Log.d("TAG", "onResponse: "+med.getId()+med.getNoOfPills());
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
        newMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                ViewGroup viewGroup = view.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.add_medicine,viewGroup,false);
                builder.setView(dialogView);
                alertDialog = builder.create();
                alertDialog.show();

                dialogView.findViewById(R.id.addTime).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogFragment timePicker = new TimePickerFragment();
                        timePicker.show(getActivity().getSupportFragmentManager(), "time picker");
                        times = ((MainActivity)getActivity()).getTimes();

                        Log.d("TAG", "onClick: From Dialog View"+((MainActivity)getActivity()).getBundle().getInt("size"));
                        Log.d("TAG","onClick: From Dialog View"+((MainActivity)getActivity()).getBundle().getLong(String.valueOf(((MainActivity)getActivity()).getBundle().getInt("size"))));
                        Log.d("TAG", "onClick: "+times.size());
                    }
                });
                dialogView.findViewById(R.id.addMedicine).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        times = ((MainActivity)getActivity()).getTimes();
                        List<String> timeStr = new ArrayList<>();
                        Log.d("TAG", "onClick: "+times.size());
                        for (Calendar c : times){
                            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                            Intent intent = new Intent(getContext(), AlertReceiver.class);
                            userSession.increment();
                            Log.d("TAG", "onClick: After"+userSession.getAlerts());
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), userSession.getAlerts(), intent, 0);
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
                            timeStr.add(String.valueOf(c.getTimeInMillis()));
                        }
                        request = new MedicineRequest();
                        request.setUserId(userSession.getUserDetails().get("_id"));
                        EditText name= dialogView.findViewById(R.id.medicine_name);
                        request.setMedicineName(name.getText().toString());
                        request.setTimes(timeStr);
                        EditText no_of_pills = dialogView.findViewById(R.id.no_of_pills);
                        request.setNoPills(Integer.parseInt(no_of_pills.getText().toString()));
                        //sendMedicine.start();
                        apiService.addMedicine(request).enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                if(response.isSuccessful()){
                                    Log.d("TAG", "onResponse: "+response.body());
                                    alertDialog.dismiss();
                                    ((MainActivity)getActivity()).onRestart();
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                            }
                        });
                    }
                });
            }
        });


        return view;
    }

    Thread sendMedicine = new Thread(){
        @Override
        public void run() {
            super.run();


        }
    };


    Thread thread = new Thread(){
        @Override
        public void run() {
            super.run();


        }
    };

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,i);
        c.set(Calendar.MINUTE,i1);
        c.set(Calendar.SECOND,0);
        times.add(c);
        Log.d("TAG", "onTimeSet:herhehr ");
        Log.d("TAG", "onTimeSet: "+times.size());

    }

    @Override
    public void onStop() {
        super.onStop();
        thread.interrupt();
    }
}