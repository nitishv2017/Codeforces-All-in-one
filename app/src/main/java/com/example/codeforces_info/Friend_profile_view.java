package com.example.codeforces_info;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;

public class Friend_profile_view extends AppCompatActivity {
    String CF_handle="";
    String qurl = "https://codeforces.com/api/";
    ProgressBar pb;
    View showHiddenProfile;
    TextView emptyView;
    View fragment_show_when_ready;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_2);

        Intent i=getIntent();
        CF_handle=i.getStringExtra("handle");

        fragment_show_when_ready=findViewById(R.id.fragment2_view);
        pb = findViewById(R.id.progress_profile);
        Sprite foldingCube = new FoldingCube();
        pb.setIndeterminateDrawable(foldingCube);


        emptyView = findViewById(R.id.f2empty_view);
        showHiddenProfile=findViewById(R.id.LLtohide);



        startProcess();

    }

    private void startProcess() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {

            pb.setVisibility(View.GONE);

            /*-------------------------------*/


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(qurl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            user_info_retro api = retrofit.create(user_info_retro.class);
            String extraUrl = "user.info?handles=" + CF_handle;
            Log.i(TAG, "onSuccess:----><"+qurl+extraUrl);
            Call<user_info_model> call = api.getModels(extraUrl);


            call.enqueue(new Callback<user_info_model>() {
                @Override
                public void onResponse(Call<user_info_model> call, Response<user_info_model> response) {
                    try {

                        List<result_user_info_model> data = response.body().getResult();

                        pb.setVisibility(GONE);
                        fragment_show_when_ready.setVisibility(View.VISIBLE);

                        result_user_info_model result = data.get(0);

                        TextView i1, i2;
                        i1 = findViewById(R.id.item_text_1);
                        i2 = findViewById(R.id.item_text_2);

                        if (result.getHandle() != null) {
                            i1.setText(result.getHandle());

                        }


                        if (result.getRank() != null) {
                            i2.setText(result.getRank());

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
                                    c = R.color.specialist;
                                    break;
                                case "master":
                                case "international master":
                                    c = R.color.specialist;
                                    break;
                                case "grandmaster":
                                case "international grandmaster":
                                case "legendary grandmaster":
                                    c = R.color.specialist;
                                    break;
                                default:
                                    c = R.color.unrated;

                            }
                            i2.setTextColor(getResources().getColor(c));
                        } else {
                            i2.setText(result.getRank());
                            i2.setTextColor(getResources().getColor(R.color.unrated));
                        }

                        TextView i1v, i2v, i3v;
                        i1v = findViewById(R.id.item_1_value);
                        if (result.getContribution() != null)
                            i1v.setText(result.getContribution().toString());
                        else i1v.setText("0");

                        i2v = findViewById(R.id.item_2_value);
                        if (result.getRating() != null)
                            i2v.setText(result.getRating().toString());
                        else i2v.setText("0");

                        i3v = findViewById(R.id.item_3_value);
                        if (result.getMaxRank() != null)
                            i3v.setText(result.getMaxRank().toString());
                        else i3v.setText("unrated");

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
                                c = R.color.specialist;
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
                        i3v.setTextColor(getResources().getColor(c));

                        ImageView image_profile = findViewById(R.id.item_profile_picture);

                        Picasso.get().load(result.getTitlePhoto()).into(image_profile);

                        showHiddenProfile.setVisibility(View.VISIBLE);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    /*-------------------------------*/


                }

                @Override
                public void onFailure(Call<user_info_model> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            pb.setVisibility(View.GONE);
            emptyView.setText("No internet connectivity");
            emptyView.setVisibility(View.VISIBLE);
        }


    }
}