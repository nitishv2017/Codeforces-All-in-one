package com.example.codeforces_info;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;
import static com.google.common.primitives.Ints.max;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment5#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment5 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment5() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment5.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment5 newInstance(String param1, String param2) {
        Fragment5 fragment = new Fragment5();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private String qurl="https://codeforces.com/api/";
    HashMap<String,Boolean>questionsMap;
    ProgressBar progressBar ;
    TextView emptyView;
    FirebaseFirestore db;
    FirebaseUser user;
    private FirebaseAuth mAuth;
    String CF_handle;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_5, container, false);

        progressBar = (ProgressBar)v.findViewById(R.id.friend_progress);
        Sprite foldingCube = new FoldingCube();
        progressBar.setIndeterminateDrawable(foldingCube);
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        emptyView = v.findViewById(R.id.empty_view_friends);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {

            mAuth = FirebaseAuth.getInstance();
            user = mAuth.getCurrentUser();

            db = FirebaseFirestore.getInstance();
            DocumentReference myRef = db.collection("Users").document(user.getUid());

            myRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    CF_handle = documentSnapshot.getString("handle");

                    Retrofit retrofit_submissions = new Retrofit.Builder()
                            .baseUrl(qurl)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();


                    submissions_retro api_submissions = retrofit_submissions.create(submissions_retro.class);
                    String extraUrl_submissions = "user.status?handle=" + CF_handle;
                    Call<submissions> call_submissions = api_submissions.getModels(extraUrl_submissions);


                    call_submissions.enqueue(new Callback<submissions>() {
                        @Override
                        public void onResponse(Call<submissions> call, Response<submissions> response) {
                            try {
                                progressBar.setVisibility(View.GONE);

                                List<Result> result = response.body().getResult();

                                for(int i=0;i<result.size();i++)
                                {
                                    String tp=result.get(i).getProblem().getContestId()+result.get(i).getProblem().getIndex();
                                    tp=tp.trim();
                                    if(questionsMap.containsKey(tp)==true && questionsMap.get(tp).booleanValue()==true)
                                    {
                                        continue;
                                    }
                                    if(result.get(i).getVerdict().equals("OK"))
                                    questionsMap.put(tp,true);
                                    else questionsMap.put(tp,false);
                                }





                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onFailure(Call<submissions> call, Throwable t) {
                            Toast.makeText(getContext(), "Something went wrong!!!", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });





        } else {
            progressBar.setVisibility(View.GONE);
            emptyView.setText("No internet connectivity");
            emptyView.setVisibility(View.VISIBLE);
        }



        return v;
    }
}