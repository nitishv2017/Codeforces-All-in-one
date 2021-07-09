package com.example.codeforces_info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class user_info_model {


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("result")
    @Expose
    private List<result_user_info_model> result = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<result_user_info_model> getResult() {
        return result;
    }

    public void setResult(List<result_user_info_model> result) {
        this.result = result;
    }
}
