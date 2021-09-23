package com.example.codeforces_info;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;
import static android.view.View.Y;
import static com.google.common.primitives.Ints.max;

public class CompareUsersResult extends AppCompatActivity {
String user1,user2;
TextView heading;
final String qurl="https://codeforces.com/api/";
final String surl="https://codeforces.com/api/";
final String purl="https://codeforces.com/api/";
View loading,showHiddenProfile;
TextView empty_view;

LineChart lineChart_c;
BarChart barChart_c;


//u1
int accepted_c1=0, wrong_c1 =0, tle_c1=0,cpe_c1=0,rte_c1=0;
    HashMap<String,Integer> mpp_tags_c1=new HashMap<String, Integer>();
    HashMap<Integer,Integer>mpp_ratings_c1=new HashMap<Integer, Integer>();
    HashMap<String,Integer>mpp_level_c1=new HashMap<String, Integer>();
    HashMap<String,Integer>vis_c1=new HashMap<String, Integer>();
    List<Pair<Integer,Integer>> ll_c1= new ArrayList<Pair<Integer, Integer>>();

    int max_tag_c1=0;
    //ratings change
    Integer maxRank_c1=1000000,minRank_c1=0,maxpos_c1=0,maxneg_c1=1000000,total_contests_c1=0;
    TextView t1_c1,t2_c1,t3_c1,t4_c1,t5_c1;


//u2

    int accepted_c2=0, wrong_c2 =0, tle_c2=0,cpe_c2=0,rte_c2=0;
    HashMap<String,Integer> mpp_tags_c2=new HashMap<String, Integer>();
    HashMap<Integer,Integer>mpp_ratings_c2=new HashMap<Integer, Integer>();
    HashMap<String,Integer>mpp_level_c2=new HashMap<String, Integer>();
    HashMap<String,Integer>vis_c2=new HashMap<String, Integer>();
    List<Pair<Integer,Integer>> ll_c2= new ArrayList<Pair<Integer, Integer>>();
    int max_tag_c2=0;
    Integer maxRank_c2=1000000,minRank_c2=0,maxpos_c2=0,maxneg_c2=1000000,total_contests_c2=0;
    TextView t1_c2,t2_c2,t3_c2,t4_c2,t5_c2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_compare_users_result);


        user1=getIntent().getStringExtra("user1");
        user2=getIntent().getStringExtra("user2");


    lineChart_c=findViewById(R.id.line_chart_c);
    barChart_c=findViewById(R.id.barChart_c);


    heading=findViewById(R.id.heading_compare);
        heading.setText("Compare Users");


        loading=findViewById(R.id.loading_c);
        empty_view=findViewById(R.id.empty_view_c);
        showHiddenProfile=findViewById(R.id.show_all);

        startProcess();


    }

    private void startProcess() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {

        /*-------------------------------*/
         Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(qurl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            user_info_retro api = retrofit.create(user_info_retro.class);
            String extraUrl = "user.info?handles=" + user1.trim()+";"+user2.trim();
            Log.i(TAG, "onSuccess:----><"+qurl+extraUrl);
            Call<user_info_model> call = api.getModels(extraUrl);


            call.enqueue(new Callback<user_info_model>() {
                @Override
                public void onResponse(Call<user_info_model> call, Response<user_info_model> response) {
                    try {

                        List<result_user_info_model> data = response.body().getResult();
                        int kk=0;
                        if(kk==0) {
                            result_user_info_model result = data.get(0);

                            TextView i1_c1, i2_c1;
                            i1_c1 = findViewById(R.id.item_text_1_c1);
                            i2_c1 = findViewById(R.id.item_text_2_c1);

                            if (result.getHandle() != null) {
                                i1_c1.setText(result.getHandle());

                            }


                            if (result.getRank() != null) {
                                i2_c1.setText(result.getRank());

                                int c = 0;
                                switch (result.getRank().toLowerCase()) {
                                    case "newbie":
                                        c = R.color.newbie;
                                        break;
                                    case "pupil":
                                        c = R.color.pupil;
                                        break;
                                    case "specialist":
                                        c = R.color.specialist;
                                        break;
                                    case "expert":
                                        c = R.color.expert;
                                        break;
                                    case "candidate master":
                                        c = R.color.cm;
                                        break;
                                    case "master":
                                    case "international master":
                                        c = R.color.master;
                                        break;
                                    case "grandmaster":
                                    case "international grandmaster":
                                    case "legendary grandmaster":
                                        c = R.color.gmaster;
                                        break;
                                    default:
                                        c = R.color.unrated;

                                }
                                i2_c1.setTextColor(getResources().getColor(c));
                            } else {
                                i2_c1.setText(result.getRank());
                                i2_c1.setTextColor(getResources().getColor(R.color.unrated));
                            }

                            TextView i1v_c1, i2v_c1, i3v_c1;
                            i1v_c1 = findViewById(R.id.item_1_value_c1);
                            if (result.getContribution() != null)
                                i1v_c1.setText(result.getContribution().toString());
                            else i1v_c1.setText("0");

                            i2v_c1 = findViewById(R.id.item_2_value_c1);
                            if (result.getRating() != null)
                                i2v_c1.setText(result.getRating().toString());
                            else i2v_c1.setText("0");

                            i3v_c1 = findViewById(R.id.item_3_value_c1);
                            if (result.getMaxRank() != null)
                                i3v_c1.setText(result.getMaxRank().toString());
                            else i3v_c1.setText("unrated");

                            int c = 0;
                            switch (result.getMaxRank().toLowerCase()) {
                                case "newbie":
                                    c = R.color.newbie;
                                    break;
                                case "pupil":
                                    c = R.color.pupil;
                                    break;
                                case "specialist":
                                    c = R.color.specialist;
                                    break;
                                case "expert":
                                    c = R.color.expert;
                                    break;
                                case "candidate master":
                                    c = R.color.cm;
                                    break;
                                case "master":
                                case "international master":
                                    c = R.color.imaster;
                                    break;
                                case "grandmaster":
                                case "international grandmaster":
                                case "legendary grandmaster":
                                    c = R.color.gmaster;
                                    break;
                                default:
                                    c = R.color.unrated;

                            }
                            i3v_c1.setTextColor(getResources().getColor(c));

                            ImageView image_profile = findViewById(R.id.item_profile_picture_c1);

                            Picasso.get().load(result.getTitlePhoto()).into(image_profile);
                            kk=1;
                        }

                        if(kk==1)
                        {
                            result_user_info_model result = data.get(1);

                            TextView i1_c2, i2_c2;
                            i1_c2 = findViewById(R.id.item_text_1_c2);
                            i2_c2 = findViewById(R.id.item_text_2_c2);

                            if (result.getHandle() != null) {
                                i1_c2.setText(result.getHandle());

                            }


                            if (result.getRank() != null) {
                                i2_c2.setText(result.getRank());

                                int c = 0;
                                switch (result.getRank().toLowerCase()) {
                                    case "newbie":
                                        c = R.color.newbie;
                                        break;
                                    case "pupil":
                                        c = R.color.pupil;
                                        break;
                                    case "specialist":
                                        c = R.color.specialist;
                                        break;
                                    case "expert":
                                        c = R.color.expert;
                                        break;
                                    case "candidate master":
                                        c = R.color.cm;
                                        break;
                                    case "master":
                                    case "international master":
                                        c = R.color.master;
                                        break;
                                    case "grandmaster":
                                    case "international grandmaster":
                                    case "legendary grandmaster":
                                        c = R.color.gmaster;
                                        break;
                                    default:
                                        c = R.color.unrated;

                                }
                                i2_c2.setTextColor(getResources().getColor(c));
                            } else {
                                i2_c2.setText(result.getRank());
                                i2_c2.setTextColor(getResources().getColor(R.color.unrated));
                            }

                            TextView i1v_c2, i2v_c2, i3v_c2;
                            i1v_c2 = findViewById(R.id.item_1_value_c2);
                            if (result.getContribution() != null)
                                i1v_c2.setText(result.getContribution().toString());
                            else i1v_c2.setText("0");

                            i2v_c2 = findViewById(R.id.item_2_value_c2);
                            if (result.getRating() != null)
                                i2v_c2.setText(result.getRating().toString());
                            else i2v_c2.setText("0");

                            i3v_c2 = findViewById(R.id.item_3_value_c2);
                            if (result.getMaxRank() != null)
                                i3v_c2.setText(result.getMaxRank().toString());
                            else i3v_c2.setText("unrated");

                            int c = 0;
                            switch (result.getMaxRank().toLowerCase()) {
                                case "newbie":
                                    c = R.color.newbie;
                                    break;
                                case "pupil":
                                    c = R.color.pupil;
                                    break;
                                case "specialist":
                                    c = R.color.specialist;
                                    break;
                                case "expert":
                                    c = R.color.expert;
                                    break;
                                case "candidate master":
                                    c = R.color.cm;
                                    break;
                                case "master":
                                case "international master":
                                    c = R.color.imaster;
                                    break;
                                case "grandmaster":
                                case "international grandmaster":
                                case "legendary grandmaster":
                                    c = R.color.gmaster;
                                    break;
                                default:
                                    c = R.color.unrated;

                            }
                            i3v_c2.setTextColor(getResources().getColor(c));

                            ImageView image_profile = findViewById(R.id.item_profile_picture_c2);

                            Picasso.get().load(result.getTitlePhoto()).into(image_profile);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    loading.setVisibility(GONE);
                    showHiddenProfile.setVisibility(View.VISIBLE);
                    /*-------------------------------*/
                    collectData(user1);




                    /*-------------------------------*/

                }

                @Override
                public void onFailure(Call<user_info_model> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            loading.setVisibility(View.GONE);
            empty_view.setText("No internet connectivity");
            empty_view.setVisibility(View.VISIBLE);
        }


    }

    private void collectData(String user) {
        if (user.equals(user1)) {
            //box 2nd
            Retrofit retrofit_submissions = new Retrofit.Builder()
                    .baseUrl(surl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            submissions_retro api_submissions = retrofit_submissions.create(submissions_retro.class);
            String extraUrl_submissions = "user.status?handle=" + user;
            Call<submissions> call_submissions = api_submissions.getModels(extraUrl_submissions);


            call_submissions.enqueue(new Callback<submissions>() {
                @Override
                public void onResponse(Call<submissions> call, Response<submissions> response) {
                    try {

                        List<Result> result = response.body().getResult();

                        Log.i(TAG, "onResponse: 😍😍😍😍😍______OOOOOOO>" + result.size());
                        for (int i = 0; i < result.size(); i++) {
                            String s = result.get(i).getVerdict().trim();
                            switch (s) {
                                case "OK":
                                    accepted_c1++;
                                    break;
                                case "COMPILATION_ERROR":
                                    cpe_c1++;
                                    break;
                                case "RUNTIME_ERROR":
                                    rte_c1++;
                                    break;
                                case "WRONG_ANSWER":
                                    wrong_c1++;
                                    break;
                            }
                            Log.i(TAG, "onResponse: 😍😍😍😍😍______OOOOOOO>" + s);
                            if (!s.equals("OK")) continue;

                            Problem problem = result.get(i).getProblem();

                            String problem_id = problem.getContestId() + problem.getIndex();
                            Log.i(TAG, "onResponse:--> " + problem_id);
                            if (vis_c1.containsKey(problem_id))
                                continue;

                            vis_c1.put(problem_id, 1);

                            List<String> tags = problem.getTags();

                            for (int j = 0; j < tags.size(); j++) {
                                if (mpp_tags_c1.containsKey(tags.get(j).trim()))
                                    mpp_tags_c1.put(tags.get(j).trim(), mpp_tags_c1.get(tags.get(j).trim()) + 1);
                                else {
                                    mpp_tags_c1.put(tags.get(j).trim(), 1);
                                }

                                max_tag_c1 = max(mpp_tags_c1.get(tags.get(j).trim()), max_tag_c1);
                            }

                            String level = problem.getIndex();

                            if (level != null && mpp_level_c1.containsKey(level))
                                mpp_level_c1.put(level, mpp_level_c1.get(level) + 1);
                            else if (level != null) {
                                mpp_level_c1.put(level, 1);
                            }
                            int rating = 0;
                            if (problem.getRating() != null) rating = problem.getRating();

                            if (mpp_ratings_c1.containsKey(rating)) {
                                mpp_ratings_c1.put(rating, mpp_ratings_c1.get(rating) + 1);

                            } else mpp_ratings_c1.put(rating, 1);


                        }

//                        create_verdicts();
//                        create_tags();
//                        create_levels();
//                        create_ratings();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(Call<submissions> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_SHORT).show();
                }
            });
            /*-------------------------------*/
            /*-------------------------------*/
            //max +ve wala

            Retrofit retrofit_b = new Retrofit.Builder()
                    .baseUrl(purl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            ratings_api api_b = retrofit_b.create(ratings_api.class);
            String extraUrl_b = "user.rating?handle=" + user;
            Call<ratings_change> call_b = api_b.getModels(extraUrl_b);


            call_b.enqueue(new Callback<ratings_change>() {
                @Override
                public void onResponse(Call<ratings_change> call, Response<ratings_change> response) {
                    try {

                        List<results_rating_change> result = response.body().getResult();


                        total_contests_c1 = result.size();

                        for (int i = 0; i < result.size(); i++) {
                            ll_c1.add(new Pair(result.get(i).getNewRating(),result.get(i).getRatingUpdateTimeSeconds()));
                            maxRank_c1 = Math.min(result.get(i).getRank(), maxRank_c1);
                            minRank_c1 = Math.max(result.get(i).getRank(), minRank_c1);
                            maxpos_c1 = Math.max(result.get(i).getNewRating() - result.get(i).getOldRating(), maxpos_c1);
                            maxneg_c1 = Math.min(result.get(i).getNewRating() - result.get(i).getOldRating(), maxneg_c1);

                        }
                        Log.i(TAG, "onResponse: ratings change" + total_contests_c1);

//                        t1_c1.setText(total_contests_c1.toString());
//                        t2_c1.setText(maxRank_c1.toString());
//                        t3_c1.setText(minRank_c1.toString());
//                        t4_c1.setText(maxpos_c1.toString());
//                        t5_c1.setText(maxneg_c1.toString());
//
                        collectData(user2);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(Call<ratings_change> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_SHORT).show();
                }
            });

        }
        else
        {
            //box 2nd
            Retrofit retrofit_submissions = new Retrofit.Builder()
                    .baseUrl(surl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            submissions_retro api_submissions = retrofit_submissions.create(submissions_retro.class);
            String extraUrl_submissions = "user.status?handle=" + user;
            Call<submissions> call_submissions = api_submissions.getModels(extraUrl_submissions);


            call_submissions.enqueue(new Callback<submissions>() {
                @Override
                public void onResponse(Call<submissions> call, Response<submissions> response) {
                    try {

                        List<Result> result = response.body().getResult();

                        Log.i(TAG, "onResponse: 😍😍😍😍😍______OOOOOOO>" + result.size());
                        for (int i = 0; i < result.size(); i++) {
                            String s = result.get(i).getVerdict().trim();
                            switch (s) {
                                case "OK":
                                    accepted_c2++;
                                    break;
                                case "COMPILATION_ERROR":
                                    cpe_c2++;
                                    break;
                                case "RUNTIME_ERROR":
                                    rte_c2++;
                                    break;
                                case "WRONG_ANSWER":
                                    wrong_c2++;
                                    break;
                            }
                            Log.i(TAG, "onResponse: 😍😍😍😍😍______OOOOOOO>" + s);
                            if (!s.equals("OK")) continue;

                            Problem problem = result.get(i).getProblem();

                            String problem_id = problem.getContestId() + problem.getIndex();
                            Log.i(TAG, "onResponse:--> " + problem_id);
                            if (vis_c2.containsKey(problem_id))
                                continue;

                            vis_c2.put(problem_id, 1);

                            List<String> tags = problem.getTags();

                            for (int j = 0; j < tags.size(); j++) {
                                if (mpp_tags_c2.containsKey(tags.get(j).trim()))
                                    mpp_tags_c2.put(tags.get(j).trim(), mpp_tags_c2.get(tags.get(j).trim()) + 1);
                                else {
                                    mpp_tags_c2.put(tags.get(j).trim(), 1);
                                }

                                max_tag_c2 = max(mpp_tags_c2.get(tags.get(j).trim()), max_tag_c2);
                            }

                            String level = problem.getIndex();

                            if (level != null && mpp_level_c2.containsKey(level))
                                mpp_level_c2.put(level, mpp_level_c2.get(level) + 1);
                            else if (level != null) {
                                mpp_level_c2.put(level, 1);
                            }
                            int rating = 0;
                            if (problem.getRating() != null) rating = problem.getRating();

                            if (mpp_ratings_c2.containsKey(rating)) {
                                mpp_ratings_c2.put(rating, mpp_ratings_c2.get(rating) + 1);

                            } else mpp_ratings_c2.put(rating, 1);


                        }

//                        create_verdicts();
//                        create_tags();
//                        create_levels();
//                        create_ratings();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(Call<submissions> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_SHORT).show();
                }
            });
            /*-------------------------------*/
            /*-------------------------------*/
            //max +ve wala

            Retrofit retrofit_b = new Retrofit.Builder()
                    .baseUrl(purl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            ratings_api api_b = retrofit_b.create(ratings_api.class);
            String extraUrl_b = "user.rating?handle=" + user;
            Call<ratings_change> call_b = api_b.getModels(extraUrl_b);


            call_b.enqueue(new Callback<ratings_change>() {
                @Override
                public void onResponse(Call<ratings_change> call, Response<ratings_change> response) {
                    try {

                        List<results_rating_change> result = response.body().getResult();


                        total_contests_c2 = result.size();
                        for (int i = 0; i < result.size(); i++) {
                            ll_c2.add(new Pair(result.get(i).getNewRating(),result.get(i).getRatingUpdateTimeSeconds()));
                            maxRank_c2 = Math.min(result.get(i).getRank(), maxRank_c2);
                            minRank_c2 = Math.max(result.get(i).getRank(), minRank_c2);
                            maxpos_c2 = Math.max(result.get(i).getNewRating() - result.get(i).getOldRating(), maxpos_c2);
                            maxneg_c2 = Math.min(result.get(i).getNewRating() - result.get(i).getOldRating(), maxneg_c2);

                        }
                        Log.i(TAG, "onResponse: ratings change" + total_contests_c2);

//                        t1_c2.setText(total_contests_c2.toString());
//                        t2_c2.setText(maxRank_c2.toString());
//                        t3_c2.setText(minRank_c2.toString());
//                        t4_c2.setText(maxpos_c2.toString());
//                        t5_c2.setText(maxneg_c2.toString());

                        create_linechart();
                        create_barChart_totalContests();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(Call<ratings_change> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void create_barChart_totalContests() {
        ArrayList<BarEntry> data_1=new ArrayList<>();
        ArrayList<BarEntry> data_2=new ArrayList<>();

        data_1.add(new BarEntry(1,total_contests_c1));
        data_2.add(new BarEntry(2,total_contests_c2));


        BarDataSet l1=new BarDataSet(data_1,user1);
        BarDataSet l2=new BarDataSet(data_2,user2);

        l1.setColor(ContextCompat.getColor(CompareUsersResult.this,R.color.orange_800 ));
        l2.setColor(ContextCompat.getColor(CompareUsersResult.this,R.color.blue_800 ));

        BarData barData=new BarData();
        barData.addDataSet(l1);
        barData.addDataSet(l2);

        YAxis yAxis=barChart_c.getAxisLeft();
        barChart_c.getAxisRight().setGranularityEnabled(true);
        barChart_c.getAxisRight().setGranularity(1f);
        barChart_c.getAxisRight().setAxisMinimum(0f);
        barChart_c.getAxisRight().setAxisMaximum(Math.max(total_contests_c1,total_contests_c2)+5);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(Math.max(total_contests_c1,total_contests_c2)+5);

        yAxis.setGranularityEnabled(true);
        yAxis.setGranularity(1f);
        barChart_c.getXAxis().setGranularityEnabled(true);
        barChart_c.getXAxis().setGranularity(1f);
        barChart_c.getXAxis().setDrawGridLines(false);

        String[] labels={user1,user2};

        barChart_c.setData(barData);
        barData.setBarWidth(0.2f);
        barChart_c.invalidate();

    }

    private void create_linechart() {
        ArrayList<Entry> data_1=new ArrayList<>();
        ArrayList<Entry> data_2=new ArrayList<>();
        ArrayList<Integer>sd=new ArrayList<>();
        for(int i=0;i<ll_c1.size();i++)
        {
            Date date = new Date((long)ll_c1.get(i).second*1000);
            SimpleDateFormat formatter = new SimpleDateFormat("MMM yyyy");
            String strDate= formatter.format(date);
            data_1.add(new Entry(ll_c1.get(i).second,ll_c1.get(i).first));
            sd.add(ll_c1.get(i).second);
        }
        for(int i=0;i<ll_c2.size();i++)
        {

            data_2.add(new Entry(ll_c2.get(i).second,ll_c2.get(i).first));
            sd.add(ll_c2.get(i).second);
        }
        Collections.sort(sd);
        LineDataSet l1=new LineDataSet(data_1,user1);
        l1.setCircleColor(ContextCompat.getColor(CompareUsersResult.this,R.color.orange_800 ));
        l1.setColor(ContextCompat.getColor(CompareUsersResult.this,R.color.orange_800 ));
        l1.setLineWidth(1.5f);
        LineDataSet l2=new LineDataSet(data_2,user2);
        l2.setCircleColor(ContextCompat.getColor(CompareUsersResult.this,R.color.blue_800 ));
        l2.setColor(ContextCompat.getColor(CompareUsersResult.this,R.color.blue_800 ));
        l2.setLineWidth(1.5f);
        LineData lineData=new LineData(l1,l2);




        lineChart_c.getXAxis().setGranularity(2678400f);
        lineChart_c.getXAxis().setValueFormatter(new linechartXaxisValueFormatter(sd));
        lineChart_c.getXAxis().setGranularityEnabled(true);
        lineChart_c.getXAxis().setDrawGridLines(false);
        lineChart_c.setData(lineData);
        lineChart_c.invalidate();

    }

}