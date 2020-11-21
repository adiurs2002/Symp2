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

import java.util.List;

public class MedicinAdapter extends RecyclerView.Adapter<MedicinAdapter.ViewHolder> {


    private Context ctx ;
    List<Medicine> medicines;

    public MedicinAdapter(Context ctx,List<Medicine> medicines) {
        this.ctx = ctx;
        this.medicines = medicines;
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
        if(medicines.get(position).getNoOfPills()!=null)
            holder.medPills.setText("Number Of Pills : "+medicines.get(position).getNoOfPills());
        else
            holder.medPills.setText("Number of Pills : 1");
        if (medicines.get(position).getTimes().size()!=0){
            String str = "Time : ";
            for (String time :medicines.get(position).getTimes()) {
                Long milli = Long.parseLong(time);
                str = str + convertSecondsToHMmSs(milli);
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
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;
        String str;
        if (h>12)
            str = "PM";
        else
            str = "AM";
        return String.format("%d:%02d:%02d "+str, h,m,s);
    }
}
