package com.example.symp2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SymptomsResponse {
    @SerializedName("tree")
    @Expose
    private String tree;
    @SerializedName("random")
    @Expose
    private String random;
    @SerializedName("naives")
    @Expose
    private String naives;
    @SerializedName("knn")
    @Expose
    private String knn;

    public String getTree() {
        return tree;
    }

    public void setTree(String tree) {
        this.tree = tree;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public String getNaives() {
        return naives;
    }

    public void setNaives(String naives) {
        this.naives = naives;
    }

    public String getKnn() {
        return knn;
    }

    public void setKnn(String knn) {
        this.knn = knn;
    }
}
