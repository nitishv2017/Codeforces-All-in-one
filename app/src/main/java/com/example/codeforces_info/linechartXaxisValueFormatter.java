package com.example.codeforces_info;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class linechartXaxisValueFormatter extends ValueFormatter{

        private ArrayList<Integer> mValues;

        public linechartXaxisValueFormatter(ArrayList<Integer> values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value) {
            System.out.println(value);
            Date date = new Date((long)value*1000);
            SimpleDateFormat formatter = new SimpleDateFormat("MMM yyyy");
            String strDate= formatter.format(date);
            return strDate;

        }


}
