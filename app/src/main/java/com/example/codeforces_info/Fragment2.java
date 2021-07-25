package com.example.codeforces_info;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment2 newInstance(String param1, String param2) {
        Fragment2 fragment = new Fragment2();
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

    String qurl = "https://codeforces.com/api/";
    String CF_handle;
    FirebaseFirestore db;
    FirebaseUser user;
    private FirebaseAuth mAuth;
    ProgressBar pb;
    View showHiddenProfile;
    TextView emptyView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_2, container, false);

        pb = view.findViewById(R.id.progress_profile);
        Sprite foldingCube = new FoldingCube();
        pb.setIndeterminateDrawable(foldingCube);
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        emptyView = view.findViewById(R.id.f2empty_view);
        showHiddenProfile=view.findViewById(R.id.LLtohide);

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
                    pb.setVisibility(View.GONE);
                    CF_handle = documentSnapshot.getString("handle");
                    Log.i(TAG, "onSuccess: ___" + CF_handle);

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


                                result_user_info_model result = data.get(0);

                                TextView i1, i2;
                                i1 = view.findViewById(R.id.item_text_1);
                                i2 = view.findViewById(R.id.item_text_2);

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
                                i1v = view.findViewById(R.id.item_1_value);
                                if (result.getContribution() != null)
                                    i1v.setText(result.getContribution().toString());
                                else i1v.setText("0");

                                i2v = view.findViewById(R.id.item_2_value);
                                if (result.getRating() != null)
                                    i2v.setText(result.getRating().toString());
                                else i2v.setText("0");

                                i3v = view.findViewById(R.id.item_3_value);
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

                                ImageView image_profile = view.findViewById(R.id.item_profile_picture);

                                Picasso.get().load(result.getTitlePhoto()).into(image_profile);

                                showHiddenProfile.setVisibility(View.VISIBLE);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onFailure(Call<user_info_model> call, Throwable t) {
                            Toast.makeText(getContext(), "Something went wrong!!!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    /*-------------------------------*/


                }
            });
        } else {
            pb.setVisibility(View.GONE);
            emptyView.setText("No internet connectivity");
            emptyView.setVisibility(View.VISIBLE);
        }


        return view;
    }

}