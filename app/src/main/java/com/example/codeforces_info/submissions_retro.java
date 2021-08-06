package com.example.codeforces_info;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface submissions_retro {
    @GET
    Call<submissions> getModels(
            @Url String s
    );
}
