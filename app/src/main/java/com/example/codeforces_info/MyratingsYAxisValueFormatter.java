package com.example.codeforces_info;

import com.github.mikephil.charting.formatter.ValueFormatter;

public class MyratingsYAxisValueFormatter extends ValueFormatter {
    private Integer[] mValues;

    public MyratingsYAxisValueFormatter(Integer[] values) {
        this.mValues = values;
    }

    @Override
    public String getFormattedValue(float value) {
        System.out.println(value);
        return mValues[((int) value)-1].toString();

    }
}
