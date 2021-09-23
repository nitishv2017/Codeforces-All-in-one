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

public class SubmissionsAdapter extends RecyclerView.Adapter<SubmissionsAdapter.ViewHolder> {
    private ArrayList<ArrayList<String>> dlists;
    private Context context;
    private String qurl="";
    public SubmissionsAdapter(@NonNull Context context, ArrayList<ArrayList<String>>q) {
        this.context = context;
        this.dlists=q;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater
                        .from(context)
                        .inflate(R.layout.submissions_item, parent, false)
        );
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ArrayList<String> current = dlists.get(position);

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, webview_submissions.class);
                String url="https://codeforces.com/contest/"+current.get(5)+"/submission/"+current.get(4);
                i.putExtra("link", url);

                context.startActivity(i);
            }
        });

        holder.id.setText(current.get(0));
        holder.time.setText(current.get(1));

        holder.name.setText(current.get(2));
        if(current.get(3).equals("ok"))
        {
            holder.verdict.setText("accepted");
            int c=R.color.pupil;
            holder.verdict.setTextColor(context.getResources().getColor(c));
        }
        else if(current.get(3).equals("wrong_answer"))
        {
            holder.verdict.setText("wrong_answer");
            int c=R.color.gmaster;
            holder.verdict.setTextColor(context.getResources().getColor(c));
        }
        else if(current.get(3).equals("runtime_error"))
        {
            holder.verdict.setText("runtime_error");
            int c=R.color.master;
            holder.verdict.setTextColor(context.getResources().getColor(c));
        }
        else
        {
            holder.verdict.setText(current.get(3));
            int c=R.color.black;
            holder.verdict.setTextColor(context.getResources().getColor(c));
        }



    }



    @Override
    public int getItemCount() {
        return this.dlists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView id,time,name,verdict;
       private View v;
        public ViewHolder(@NonNull View view) {
            super(view);
            this.v=(View)view.findViewById(R.id.submissions_view);
            this.id=(TextView) view.findViewById(R.id.id_submissions);
            this.time=(TextView) view.findViewById(R.id.time_submissions);
            this.name=(TextView) view.findViewById(R.id.problem_name_submissions);
            this.verdict=(TextView)view.findViewById(R.id.verdict_submissions);
        }
    }




}


