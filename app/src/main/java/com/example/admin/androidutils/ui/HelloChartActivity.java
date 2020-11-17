package com.example.admin.androidutils.ui;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.admin.androidutils.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.formatter.ColumnChartValueFormatter;
import lecho.lib.hellocharts.formatter.SimpleColumnChartValueFormatter;
import lecho.lib.hellocharts.model.*;
import lecho.lib.hellocharts.view.ColumnChartView;


/**
 * @ProjectName AndroidUtils
 * @CreateDate: 2019/12/31
 * @Author: wmz
 * @Description:
 */
public class HelloChartActivity extends AppCompatActivity {


    public static final int[] VORDIPLOM_COLORS = {
            Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED
    };
    //柱状图控件
    private ColumnChartView column_chart_view;
    //统计图数据
    private ColumnChartData data;
    //数据标志
    private List<String> week;
    //模拟数据
    private List<Float> testData;
    float [] num ={352,216,143,195,66,481,620,201,400,163,255,404};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_chart);
        column_chart_view = (ColumnChartView) findViewById(R.id.column_chart_view);
        testData = new ArrayList<>();
        for (int i = 0; i <12 ; i++) {
            testData.add(num[i]);
        }
        setHistoryChart(testData, Color.parseColor("#F1C704"), column_chart_view);

    }

    /* *//**
     * @param columnDatas :每一条柱子上代表的数量，columnColor:柱子颜色，charView：柱形图控件
     * @description 历史记录柱形图数据填充
     * @author ldm
     * @time 2017/5/23 11:03
     *//*
    private void setHistoryChart(List<Float> columnDatas, int columnColor, ColumnChartView charView) {
        week = new ArrayList<>();
       *//* for (int i = 6; i >= 0; i--) {
            week.add(getWeekDays(i));
        }*//*
        // 使用的 7列，每列1个subcolumn。
        int numSubcolumns = 1;
        int numColumns = 7;
        //定义一个圆柱对象集合
        List<Column> columns = new ArrayList<Column>();
        //子列数据集合
        List<SubcolumnValue> values;

        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        //遍历列数numColumns
        for (int i = 0; i < numColumns; ++i) {
            values = new ArrayList<SubcolumnValue>();
            //遍历每一列的每一个子列
            //为每一柱图添加颜色和数值
            float f = columnDatas.get(i);
            values.add(new SubcolumnValue(f, columnColor));
            //创建Column对象
            Column column = new Column(values);
            ColumnChartValueFormatter chartValueFormatter = new SimpleColumnChartValueFormatter(0);
            column.setFormatter(chartValueFormatter);
            //是否有数据标注
            column.setHasLabels(true);
            //是否是点击圆柱才显示数据标注
            column.setHasLabelsOnlyForSelected(false);
            columns.add(column);
            //给x轴坐标设置描述
            axisValues.add(new AxisValue(i).setLabel(week.get(i)));
        }
        //创建一个带有之前圆柱对象column集合的ColumnChartData
        data = new ColumnChartData(columns);
        data.setValueLabelTextSize(8);
        data.setValueLabelBackgroundColor(Color.parseColor("#C9C9C9"));
        data.setValueLabelTypeface(Typeface.DEFAULT);// 设置数据文字样式
        data.setValueLabelBackgroundEnabled(true);
        data.setValueLabelBackgroundAuto(false);
        //定义x轴y轴相应参数
        Axis axisX = new Axis();
        Axis axisY = new Axis().setHasLines(true);
        axisY.hasLines();//是否显示网格线
        axisY.setTextColor(Color.parseColor("#C9C9C9"));//颜色
        axisX.hasLines();
        axisX.setTextColor(Color.parseColor("#C9C9C9"));
        axisX.setValues(axisValues);
        axisX.setTextSize(10);
        axisX.setHasSeparationLine(false);
        //把X轴Y轴数据设置到ColumnChartData 对象中
        data.setAxisXBottom(axisX);
        data.setAxisYRight(axisY);
        //给表填充数据，显示出来
        charView.setInteractive(false);
        charView.setColumnChartData(data);
    }*/

    /**
     * @param columnDatas :每一条柱子上代表的数量，columnColor:柱子颜色，charView：柱形图控件
     * @description 历史记录柱形图数据填充
     * @author ldm
     * @time 2017/5/23 11:03
     */
    private void setHistoryChart(List<Float> columnDatas, int columnColor, ColumnChartView charView) {
        week = new ArrayList<>();
        getWeekDays();
       /* for (int i = 6; i >= 0; i--) {
            week.add(getWeekDays(i));
        }*/

        int numColumns = 11;
        //定义一个圆柱对象集合
        List<Column> columns = new ArrayList<Column>();
        //子列数据集合
        List<SubcolumnValue> values;

        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        //遍历列数numColumns
        for (int i = 0; i < numColumns; ++i) {
            values = new ArrayList<SubcolumnValue>();
            //遍历每一列的每一个子列
            //为每一柱图添加颜色和数值
            float f = columnDatas.get(i);
            values.add(new SubcolumnValue(f, columnColor));
            //创建Column对象
            Column column = new Column(values);
            ColumnChartValueFormatter chartValueFormatter = new SimpleColumnChartValueFormatter(0);
            column.setFormatter(chartValueFormatter);
            //是否有数据标注
            column.setHasLabels(true);
            //是否是点击圆柱才显示数据标注
            column.setHasLabelsOnlyForSelected(false);
            columns.add(column);
            //给x轴坐标设置描述
            axisValues.add(new AxisValue(i).setLabel(week.get(i)));
        }
        //创建一个带有之前圆柱对象column集合的ColumnChartData
        data = new ColumnChartData(columns);
        data.setValueLabelTextSize(8);
        data.setValueLabelBackgroundColor(Color.parseColor("#C9C9C9"));
        data.setValueLabelTypeface(Typeface.DEFAULT);// 设置数据文字样式
        data.setValueLabelBackgroundEnabled(true);
        data.setValueLabelBackgroundAuto(false);
        //定义x轴y轴相应参数
        Axis axisX = new Axis();
        Axis axisY = new Axis().setHasLines(true);
        axisY.hasLines();//是否显示网格线
        axisY.setTextColor(Color.parseColor("#C9C9C9"));//颜色
        axisY.setMaxLabelChars(4);
        axisX.hasLines();
        axisX.setTextColor(Color.parseColor("#C9C9C9"));
        axisX.setValues(axisValues);
        axisX.setTextSize(10);
        axisX.setHasSeparationLine(false);
        //把X轴Y轴数据设置到ColumnChartData 对象中
        data.setAxisXBottom(axisX);
        data.setAxisYRight(axisY);
        //给表填充数据，显示出来
        charView.setInteractive(false);
        charView.setColumnChartData(data);
    }

    /**
     * @description 获取最近七天的星期
     * @author ldm
     * @time 2017/5/23 10:30
     *//*
    private String getWeekDays(int days) {
        if (days == 0) {
            return "今天";
        }
        String[] weekDays = getResources().getStringArray(R.array.cms_week);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -days);
        Date monday = cal.getTime();
        cal.setTime(monday);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }*/

    /**
     * @description 获取最近七天的星期
     * @author ldm
     * @time 2017/5/23 10:30
     */
    private void getWeekDays() {
        week = new ArrayList<>();
        week.add("10%");
        week.add("8%");
        week.add("6%");
        week.add("4%");
        week.add("2%");
        week.add("0%");
        week.add("-2%");
        week.add("-4%");
        week.add("-6%");
        week.add("-8%");
        week.add("-10%");
    }


}
