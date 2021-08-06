package com.example.codeforces_info;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import org.jetbrains.annotations.NotNull;



public class tabs extends AppCompatActivity {
    private static final String TAG = "das";
    ChipNavigationBar chipNavigationBar;
    FragmentManager fragmentManager;
    DrawerLayout drawerLayout;
    TextView heading;
    ImageView heading_icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        Log.i(TAG, "onCreate: fuck");
        heading=findViewById(R.id.heading);

        if(savedInstanceState==null)
        {


            chipNavigationBar = findViewById(R.id.bottom_navigation_bar);
            chipNavigationBar.setItemSelected(R.id.profile,true);
            bottomMenu();
            heading.setText("Profile");
            fragmentManager = getSupportFragmentManager();
            Fragment2 homeFragment = new Fragment2();
            fragmentManager.beginTransaction().replace(R.id.frameContainer, homeFragment)
                    .commit();

          //  getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
          //  getSupportActionBar().setCustomView(R.layout.custom_action_bar);


        }
        drawerLayout=findViewById(R.id.drawer_layout);


    }

    public void  ClickedDrawer(View v)
    {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void CloseDrawer(View v)
    {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View v)
    {
        recreate();
    }


    private void bottomMenu() {

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i) {
                    case R.id.schedules: {
                        fragment = new Fragment1();
                        heading.setText("Schedule");

                    }
                        break;
                    case R.id.profile: {
                        fragment = new Fragment2();
                        heading.setText("Profile");

                    }
                    break;
                    case R.id.sneakin: {
                        fragment = new Fragment3();
                        heading.setText("Sneak Friends");

                    }
                }

                if (fragment != null) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frameContainer, fragment).commit();
                } else {
                    Log.e(TAG, "Error in creating Fragment");
                }
            }
        });
    }


    //DOUBLE BACK EXIT
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}