package com.example.codeforces_info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ratings_change {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("result")
    @Expose
    private List<results_rating_change> result = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<results_rating_change> getResult() {
        return result;
    }

    public void setResult(List<results_rating_change> result) {
        this.result = result;
    }
}
