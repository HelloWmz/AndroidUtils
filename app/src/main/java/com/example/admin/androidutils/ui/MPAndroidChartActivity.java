package com.example.admin.androidutils.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.admin.androidutils.R;
import com.example.admin.androidutils.weigdt.MyValueFormatter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;


/**
 * @ProjectName AndroidUtils
 * @CreateDate: 2019/12/31
 * @Author: wmz
 * @Description:
 */
public class MPAndroidChartActivity extends AppCompatActivity {

    public BarChart chart;
    public static final int[] VORDIPLOM_COLORS = {
            Color.RED,Color.RED,Color.RED,Color.RED,Color.RED,Color.RED,Color.RED,Color.RED,Color.RED,Color.RED,Color.RED
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpandroid_chart);

        chart = findViewById(R.id.chart1);


        chart.getAxisLeft();
        chart.setTouchEnabled(false);
        chart.setPinchZoom(false);
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setFitBars(true);
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(11);
        xAxis.setTextColor(Color.RED);
        ValueFormatter custom = new MyValueFormatter("%");
        xAxis.setValueFormatter(custom);
      //  chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setEnabled(false);
        YAxis rightAxis = chart.getAxisLeft();
        rightAxis.setDrawGridLines(true);
        rightAxis.setLabelCount(5,true);
        rightAxis.setAxisMinimum(0f);
        rightAxis.setGranularity(186f);
        rightAxis.setTextColor(Color.RED);
        rightAxis.setAxisMaximum(744f);
        chart.getLegend().setEnabled(false);
        ArrayList<BarEntry> values = new ArrayList<>();

        values.add(new BarEntry(10, 352));
        values.add(new BarEntry(8, 216));
        values.add(new BarEntry(6, 143));
        values.add(new BarEntry(4, 195));
        values.add(new BarEntry(2, 66));
        values.add(new BarEntry(0, 481));
        values.add(new BarEntry(-10, 404));
        values.add(new BarEntry(-8, 225));
        values.add(new BarEntry(-6, 400));
        values.add(new BarEntry(-4, 201));
        values.add(new BarEntry(-2, 620));
        BarDataSet set1 = new BarDataSet(values, "");
        set1.setColors(VORDIPLOM_COLORS);
        BarData data = new BarData(set1);
        data.setValueTextSize(10f);
        data.setBarWidth(0.9f);
        chart.setData(data);

        chart.setRotationY(180);

    }

}
