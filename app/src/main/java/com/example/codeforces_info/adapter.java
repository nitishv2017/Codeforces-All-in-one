package com.example.codeforces_info;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.ViewHolder> {
    private List<outline>dlists;
    private Context context;

    public adapter(@NonNull Context context, List<outline> lists) {
        this.context = context;
        this.dlists=lists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater
                        .from(context)
                        .inflate(R.layout.list_item, parent, false)
        );
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        outline current = dlists.get(position);

        Date date = new Date((long)current.getStartTimeSeconds()*1000);
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy hh:mm a");
        String strDate= formatter.format(date);

        int hours = current.getDurationSeconds() / 3600;
        int minutes = (current.getDurationSeconds() % 3600) / 60;


        holder.tit.setText(current.getName());
        holder.det.setText(strDate);
        holder.dur.setText(hours+" hrs "+minutes+" mins");

        holder.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://codeforces.com/contests";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });

        holder.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setData(CalendarContract.Events.CONTENT_URI);
                intent.putExtra(CalendarContract.Events.TITLE,current.getName());
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, (long)current.getStartTimeSeconds()*1000);
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,(long)current.getStartTimeSeconds()*1000 + (long)current.getDurationSeconds()*1000 );
                context.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return this.dlists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tit;
        private TextView det;
        private TextView dur;
        private View btn1,btn2;
        public ViewHolder(@NonNull View view) {
            super(view);
            this.tit = (TextView)view
                    .findViewById(R.id.contestName);
            this.det = (TextView)view
                    .findViewById(R.id.details);
            this.dur = (TextView)view
                    .findViewById(R.id.duration);
            this.btn1=(View) view.findViewById(R.id.bt1);
            this.btn2=(View) view.findViewById(R.id.bt2);
        }
    }
}
