package com.example.codeforces_info;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface user_info_retro {
    @GET
    Call<user_info_model> getModels(
            @Url String s
            );
}
