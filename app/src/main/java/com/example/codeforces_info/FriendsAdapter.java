package com.example.codeforces_info;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    private ArrayList<ArrayList<String>> dlists;
    private Context context;
    private String qurl="";
    public FriendsAdapter(@NonNull Context context, ArrayList<ArrayList<String>>q) {
        this.context = context;
        this.dlists=q;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater
                        .from(context)
                        .inflate(R.layout.custom_friend_item, parent, false)
        );
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ArrayList<String> current = dlists.get(position);

        Picasso.get().load(current.get(0)).into(holder.img);
        holder.txt.setText(current.get(1));
        holder.txt.setTextColor(context.getResources().getColor( getRankColor(current.get(2)) ));
        //Log.i(TAG, "onBindViewHolder:0000------>>>> "+current.get(2));
        holder.friend_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(),Friend_profile_view.class);
                i.putExtra("handle",current.get(1));
                v.getContext().startActivity(i);
            }
        });
    }



    @Override
    public int getItemCount() {
        return this.dlists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView txt;
        private View friend_view;
        public ViewHolder(@NonNull View view) {
            super(view);

            this.friend_view=(View) view.findViewById(R.id.friend_item_view);
           this.img=(ImageView)view.findViewById(R.id.item_profile_picture_friend);
           this.txt=(TextView)view.findViewById(R.id.handle_txt);
        }
    }

    int getRankColor(String s)
    {
        int c = 0;
        switch (s) {
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
        return c;
    }


}


