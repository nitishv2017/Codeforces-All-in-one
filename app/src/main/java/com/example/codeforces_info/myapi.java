package com.example.codeforces_info;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface myapi {

    @GET("contest.list")
    Call<model> getModels();
}
