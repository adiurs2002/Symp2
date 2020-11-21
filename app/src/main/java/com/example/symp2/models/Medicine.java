package com.example.symp2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Medicine {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("medicine_name")
    @Expose
    private String medicineName;
    @SerializedName("no_of_pills")
    @Expose
    private Integer noOfPills;
    @SerializedName("times")
    @Expose
    private List<String> times = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public Integer getNoOfPills() {
        return noOfPills;
    }

    public void setNoOfPills(Integer noOfPills) {
        this.noOfPills = noOfPills;
    }

    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }
}
