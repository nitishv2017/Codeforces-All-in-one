package com.example.codeforces_info;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;
import static com.google.common.primitives.Ints.max;

public class Sneak_submissions extends AppCompatActivity {
    String surl = "https://codeforces.com/api/";
    String CF_handle="";
    RecyclerView recyclerView;
    TextView heading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sneak_submissions);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ImageView back=findViewById(R.id.back_submissions);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sneak_submissions.this.onBackPressed();
            }
        });

        Intent intent = getIntent();
        CF_handle = intent.getExtras().getString("cf_handle");
        heading=findViewById(R.id.heading_submissions);
        heading.setText(CF_handle);
        recyclerView=findViewById(R.id.submissions_list);
        startProcess();
    }

    private void startProcess() {
        Retrofit retrofit_submissions = new Retrofit.Builder()
                .baseUrl(surl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        submissions_retro api_submissions = retrofit_submissions.create(submissions_retro.class);
        String extraUrl_submissions = "user.status?handle=" + CF_handle  +"&from=1&count=50";
        Call<submissions> call_submissions = api_submissions.getModels(extraUrl_submissions);


        call_submissions.enqueue(new Callback<submissions>() {
            @Override
            public void onResponse(Call<submissions> call, Response<submissions> response) {
                try {

                    List<Result> result = response.body().getResult() ;
                    ArrayList<ArrayList<String>>finalList = new ArrayList<ArrayList<String>>();

                    for(int i=0;i<result.size();i++)
                    {   ArrayList<String>ss=new ArrayList<String>();
                        Problem pp=result.get(i).getProblem();
                    String id=(pp.getContestId().toString()+pp.getIndex().toString());
                                 ss.add(id);
                        Author author=result.get(i).getAuthor();
                        Date date = new Date((long)author.getStartTimeSeconds()*1000);
                        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy hh:mm a");
                        String strDate= formatter.format(date);
                                ss.add(strDate);
                        String name=pp.getName();
                                 ss.add(name);
                                 ss.add(result.get(i).getVerdict().toLowerCase());
                                 ss.add(result.get(i).getId().toString());
                                 ss.add(result.get(i).getContestId().toString());
                        finalList.add(ss);
                    }
                    recyclerView.setAdapter(new SubmissionsAdapter(Sneak_submissions.this,finalList));
                    recyclerView.setLayoutManager(new LinearLayoutManager(Sneak_submissions.this));


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<submissions> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}