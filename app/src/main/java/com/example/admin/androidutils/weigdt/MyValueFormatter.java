package com.example.admin.androidutils.weigdt;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class MyValueFormatter extends ValueFormatter {


    private String suffix;

    public MyValueFormatter(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public String getFormattedValue(float value) {
        return value+ suffix;
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        int num = (int) value;
       return num + suffix;
    }
}
