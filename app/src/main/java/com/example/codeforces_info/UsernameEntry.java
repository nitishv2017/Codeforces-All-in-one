package com.example.codeforces_info;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class UsernameEntry extends AppCompatActivity {
    private static final String TAG = "das----";
    Button logoutBtn, submitbtn;
    EditText cf_handle;
    private String qurl="https://codeforces.com/api/user.info?handles=";
     String status_response;
    ProgressBar progressBar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_username_entry);
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        progressBar = (ProgressBar)findViewById(R.id.progres_status_check);
        Sprite foldingCube = new FoldingCube();
        progressBar.setIndeterminateDrawable(foldingCube);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        Toast.makeText(this, account.getDisplayName(), Toast.LENGTH_SHORT).show();

        logoutBtn=(Button)findViewById(R.id.logout);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Authentication.class));
            }
        });

        cf_handle=(EditText)findViewById(R.id.cf_handle);

        submitbtn=(Button)findViewById(R.id.submit);

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            progressBar.setVisibility(View.VISIBLE);
                String CF_id=cf_handle.getText().toString().trim();
                if(CF_id.contains(";") || CF_id.contains(" ") ) {

                    Toast.makeText(UsernameEntry.this, "Enter valid Codeforces handle", Toast.LENGTH_SHORT).show();
                return;
                }
                qurl="https://codeforces.com/api/user.info?handles=";
                qurl=qurl+CF_id;
                status_response="";
                new fetchData().execute();
                Log.i(TAG, "onsafddse: "+status_response);

            }
        });

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
                progressBar.setVisibility(View.GONE);
                Toast.makeText(UsernameEntry.this, "Enter valid Codeforces handle", Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(getApplicationContext(),tabs.class));
                finish();
            }
            else
            {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(UsernameEntry.this, "Enter valid Codeforces handle", Toast.LENGTH_SHORT).show();
            }



        }
    }
}

