package com.example.codeforces_info;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ratings_api {
    @GET
    Call<ratings_change> getModels(
            @Url String s
    );
}
