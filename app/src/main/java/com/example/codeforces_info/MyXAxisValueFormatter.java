package com.example.codeforces_info;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class MyXAxisValueFormatter extends ValueFormatter {
    private String[] mValues;

    public MyXAxisValueFormatter(String[] values) {
        this.mValues = values;
    }

    @Override
    public String getFormattedValue(float value) {
        System.out.println(value);
        return mValues[((int) value)-1];

    }


}
