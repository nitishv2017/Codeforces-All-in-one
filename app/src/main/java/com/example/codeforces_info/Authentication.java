package com.example.codeforces_info;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Authentication extends AppCompatActivity {
    private static final String TAG = "------------asdasdfsaf";
    SignInButton btn;
    GoogleSignInClient  mGoogleSignInClient;
    private FirebaseAuth mAuth;
    EditText cf_handle;
    private String qurl="https://codeforces.com/api/user.info?handles=";
    String status_response;
    ProgressBar progressBar ;
    String CF_id;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
       // updateUI(currentUser);
        if(currentUser!=null)
        {
            startActivity(new Intent(getApplicationContext(),tabs.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
//        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);



        btn=(SignInButton) findViewById(R.id.signinbtn);
        mAuth= FirebaseAuth.getInstance();

        processRequest();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                go();

            }
        });

    }



    private void processRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }


    private void processLogin() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 101) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                //Log.i(TAG, "onActivityResult:-------------- ");
                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(Authentication.this, "User cancelled login", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.i(TAG, "onComplete: ------;;;;;>>>>"+ user.getUid());
                            // Write a message to the database
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            DocumentReference myRef = db.collection("Users").document(user.getUid());

                            myRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                            myRef.update("handle",CF_id.trim());
                                        } else {
                                            Log.d(TAG, "No such document");

                                            Map<String,String> mp=new HashMap<>();

                                            mp.put("handle",CF_id.trim());
                                            myRef.set(mp);
                                        }
                                        startActivity(new Intent(getApplicationContext(),tabs.class));
                                        finish();
                                    } else {
                                        Log.d(TAG, "get failed with ", task.getException());
                                    }
                                }
                            });



                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Authentication.this, "Problem found in login", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void go() {
        progressBar = (ProgressBar)findViewById(R.id.progress_auth);
        Sprite foldingCube = new FoldingCube();
        progressBar.setIndeterminateDrawable(foldingCube);
        cf_handle=(EditText)findViewById(R.id.cf_handle);

        progressBar.setVisibility(View.VISIBLE);
        CF_id=cf_handle.getText().toString().trim();
        if(CF_id.contains(";") || CF_id.contains(" ") ) {

            Toast.makeText(this, "Enter valid Codeforces handle", Toast.LENGTH_SHORT).show();
            return;
        }
        qurl="https://codeforces.com/api/user.info?handles=";
        qurl=qurl+CF_id;
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
                progressBar.setVisibility(View.GONE);
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(), "Enter valid Codeforces handle", Toast.LENGTH_SHORT).show();
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
                processLogin();
            }
            else
            {
                progressBar.setVisibility(View.GONE);
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(), "Enter valid Codeforces handle", Toast.LENGTH_SHORT).show();
            }



        }
    }


}