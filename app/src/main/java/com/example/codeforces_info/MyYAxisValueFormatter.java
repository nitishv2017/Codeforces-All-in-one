package com.example.codeforces_info;

import com.github.mikephil.charting.formatter.ValueFormatter;

public class MyYAxisValueFormatter extends ValueFormatter {
    private String[] mValues;

    public MyYAxisValueFormatter(String[] values) {
        this.mValues = values;
    }

    @Override
    public String getFormattedValue(float value) {
        System.out.println(value);
        return mValues[((int) value)-1];

    }

}
