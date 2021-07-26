package com.example.codeforces_info;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment3 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment3.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment3 newInstance(String param1, String param2) {
        Fragment3 fragment = new Fragment3();
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
    private String qurl="https://codeforces.com/api/user.info?handles=";
    private String url_multi_friends="https://codeforces.com/api/user.info?handles=";
    String status_response;
    Context cc;
    ProgressBar progressBar ;
    String SearchedFriend="";
    TextView emptyView;
    FirebaseFirestore db;
    FirebaseUser user;
    AlertDialog dialog;
    private FirebaseAuth mAuth;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_3, container, false);
        cc=getContext();
        progressBar = (ProgressBar)v.findViewById(R.id.friend_progress);
        Sprite foldingCube = new FoldingCube();
        progressBar.setIndeterminateDrawable(foldingCube);
        //ADDING FRIEND---->
        FloatingActionButton fab = v.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.Theme_AppCompat_Light_Dialog_Alert);

                final View add_friend_layout=getLayoutInflater().inflate(R.layout.add_friend_dialog,null);

                builder.setTitle("Add Friends");

                builder.setView(add_friend_layout);

                EditText friend_field=add_friend_layout.findViewById(R.id.friend_handle);

                Button add_friend=add_friend_layout.findViewById(R.id.add_friend_btn);

                add_friend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getContext(),"GG did it",Toast.LENGTH_SHORT).show();
                        EditText et= add_friend_layout.findViewById(R.id.friend_handle);
                        SearchedFriend=et.getText().toString().trim();
                        go(v);
                    }
                });


                dialog= builder.create();
                dialog.show();
            }
        });

        //RecyclerView and acquiring result from firstore database----->
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


                    List<String> Friends_array =(List<String>) documentSnapshot.get("friends");
                    if(Friends_array==null || Friends_array.size()==0)
                    {   progressBar.setVisibility(View.GONE);
                        emptyView.setText("No Friends 😥");
                        emptyView.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        for(int i=0;i<Friends_array.size();i++)
                        {
                            url_multi_friends+=Friends_array.get(i).trim();
                            if(i<Friends_array.size()-1)
                            url_multi_friends+=";";
                        }


                        recyclerView = (RecyclerView) v.findViewById(R.id.friend_list);
                        new fetchFriends().execute();
                    }

                    /*-------------------------------*/





                    /*-------------------------------*/


                }
            });
        } else {
            progressBar.setVisibility(View.GONE);
            emptyView.setText("No internet connectivity");
            emptyView.setVisibility(View.VISIBLE);
        }



        return v;
    }

    private void go(View v) {

        if(SearchedFriend.contains(";") || SearchedFriend.contains(" ") ) {

            Toast.makeText(getContext(), "Enter valid Codeforces handle", Toast.LENGTH_SHORT).show();
            return;
        }
        qurl="https://codeforces.com/api/user.info?handles=";
        qurl=qurl+SearchedFriend;
        status_response="";
        new fetchData().execute();

    }

    public class fetchData extends AsyncTask<String, String, String> {

        @Override
        public void onPreExecute() {
            super .onPreExecute();
            //swipeRefresh.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                URL url = new URL(qurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String temp;

                    while ((temp = reader.readLine()) != null) {
                        stringBuilder.append(temp);
                    }
                    result = stringBuilder.toString();
                }else  {
                    result = "error";
                }

            } catch (Exception  e) {
                // Toast.makeText(UsernameEntry.this, "Enter valid Codeforces handle", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return result;
        }

        @Override
        public void onPostExecute(String s) {
            super .onPostExecute(s);
            //swipeRefresh.setRefreshing(false);
            if(s.equals("error")) {

                Toast.makeText(getContext(), "Enter valid Codeforces handle", Toast.LENGTH_SHORT).show();
                return;

            }

            try {

                JSONObject object = new JSONObject(s);
                status_response = object.getString("status");
                Log.i(TAG, "onPostExecute: "+status_response);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(status_response.equals("OK"))
            {
                dialog.cancel();
                AddtoDatabase();
            }
            else
            {

                Toast.makeText(getContext(), "Enter valid Codeforces handle", Toast.LENGTH_SHORT).show();
            }



        }
    }

    private void AddtoDatabase()
    {   mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        // Write a message to the database
        db = FirebaseFirestore.getInstance();
        DocumentReference myRef = db.collection("Users").document(user.getUid());

        myRef.update("friends", FieldValue.arrayUnion(SearchedFriend)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                // Reload current fragment
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameContainer, new Fragment3()).commit();
            }
        });

    }

    ///FETCHING --->
    public class fetchFriends extends AsyncTask<String, String, String> {

        @Override
        public void onPreExecute() {
            super .onPreExecute();
            //swipeRefresh.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                URL url = new URL(url_multi_friends);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String temp;

                    while ((temp = reader.readLine()) != null) {
                        stringBuilder.append(temp);
                    }
                    result = stringBuilder.toString();
                }else  {
                    result = "error";
                }

            } catch (Exception  e) {
                // Toast.makeText(UsernameEntry.this, "Enter valid Codeforces handle", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return result;
        }

        @Override
        public void onPostExecute(String s) {
            super .onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            if(s.equals("error")) {


                Toast.makeText(getContext(), "Error try again later", Toast.LENGTH_SHORT).show();
                return;

            }

            try {
                ArrayList<ArrayList<String>>finalList = new ArrayList<ArrayList<String>>();
                JSONObject object = new JSONObject(s);
                JSONArray jarray = object.getJSONArray("result");
                for(int i=0;i<jarray.length();i++)
                {
                    JSONObject o= jarray.getJSONObject(i);
                    ArrayList<String>ss=new ArrayList<String>();
                    ss.add(o.getString("avatar"));
                    ss.add(o.getString("handle"));
                    if(o.has("rank"))
                    {ss.add(o.getString("rank"));
                        Log.i(TAG, "oyeee____>>>>>: "+o.getString("rank"));}
                    else
                    ss.add("unranked");
                    finalList.add(ss);
                }


                recyclerView.setAdapter(new FriendsAdapter(getContext(),finalList));
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


            } catch (JSONException e) {
                e.printStackTrace();
            }





        }
    }

}