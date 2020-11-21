package com.example.symp2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MedicineRequest {
    @SerializedName("medicine_name")
    @Expose
    private String medicineName;
    @SerializedName("times")
    @Expose
    private List<String> times = null;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("no_pills")
    @Expose
    private Integer noPills;

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getNoPills() {
        return noPills;
    }

    public void setNoPills(Integer noPills) {
        this.noPills = noPills;
    }
}
