package com.example.symp2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SymptomsRequest {
    @SerializedName("symp1")
    @Expose
    private String symp1;
    @SerializedName("symp2")
    @Expose
    private String symp2;
    @SerializedName("symp3")
    @Expose
    private String symp3;
    @SerializedName("symp4")
    @Expose
    private String symp4;
    @SerializedName("symp5")
    @Expose
    private String symp5;

    public String getSymp1() {
        return symp1;
    }

    public void setSymp1(String symp1) {
        this.symp1 = symp1;
    }

    public String getSymp2() {
        return symp2;
    }

    public void setSymp2(String symp2) {
        this.symp2 = symp2;
    }

    public String getSymp3() {
        return symp3;
    }

    public void setSymp3(String symp3) {
        this.symp3 = symp3;
    }

    public String getSymp4() {
        return symp4;
    }

    public void setSymp4(String symp4) {
        this.symp4 = symp4;
    }

    public String getSymp5() {
        return symp5;
    }

    public void setSymp5(String symp5) {
        this.symp5 = symp5;
    }
}
