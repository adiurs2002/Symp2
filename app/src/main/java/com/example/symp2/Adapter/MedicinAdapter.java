package com.example.symp2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symp2.R;
import com.example.symp2.models.Medicine;
import com.example.symp2.utils.UserSession;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MedicinAdapter extends RecyclerView.Adapter<MedicinAdapter.ViewHolder> {


    private Context ctx ;
    List<Medicine> medicines;
    UserSession userSession ;

    public MedicinAdapter(Context ctx,List<Medicine> medicines) {
        this.ctx = ctx;
        this.medicines = medicines;
        userSession = new UserSession(ctx);
    }

    @NonNull
    @Override
    public MedicinAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_info,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicinAdapter.ViewHolder holder, int position) {
        holder.medName.setText(medicines.get(position).getMedicineName());
        userSession.setAlert(medicines.size());
        if(medicines.get(position).getNoOfPills()!=null)
            holder.medPills.setText("Number Of Pills : "+medicines.get(position).getNoOfPills());
        else
            holder.medPills.setText("Number of Pills : 1");
        if (medicines.get(position).getTimes().size()!=0){
            String str = "Time : ";
            for (String time :medicines.get(position).getTimes()) {
                Long milli = Long.parseLong(time);
                str = str + " " + convertSecondsToHMmSs(milli);
            }
            holder.medTime.setText(str);

        }
        else
            holder.medTime.setText("Time : 8:00 AM  9:30 PM");

    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder {
        private ImageView medPhoto;
        private TextView medName,medPills,medTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            medPhoto = itemView.findViewById(R.id.medphoto);
            medName = itemView.findViewById(R.id.medicine_name);
            medPills = itemView.findViewById(R.id.no_of_pills);
            medTime = itemView.findViewById(R.id.medTime);
        }
    }


    public static String convertSecondsToHMmSs(long seconds) {
        DateFormat formatter = new SimpleDateFormat("HH:mm", Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
        String text = formatter.format(new Date(seconds));
        return text;
    }
}
