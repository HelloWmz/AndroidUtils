package com.example.admin.androidutils.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.admin.androidutils.R;
import com.example.admin.androidutils.adapter.BeautyAdapter;
import com.example.admin.androidutils.utils.RepeatLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName AndroidUtils
 * @CreateDate: 2021/1/19
 * @Author: wmz
 * @Description:
 */
public class LayoutManagerActivity  extends AppCompatActivity {
    private List<String> mData = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_manager);


        for (int i = 0; i < 20; i++) {
            mData.add("" + i);
        }
        RecyclerView recyclerView=findViewById(R.id.rv);
        recyclerView.setAdapter(new BeautyAdapter(mData));
        recyclerView.setLayoutManager(new RepeatLayoutManager(RecyclerView.HORIZONTAL));

    }
}
