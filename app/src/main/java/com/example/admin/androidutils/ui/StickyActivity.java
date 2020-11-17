package com.example.admin.androidutils.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.admin.androidutils.R;
import com.example.admin.androidutils.adapter.StickyAdapter;
import com.example.admin.androidutils.bean.DiffUtilBean;
import com.example.admin.androidutils.weigdt.sticky.StickyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ProjectName AndroidUtils
 * @CreateDate: 2020/5/21
 * @Author: wmz
 * @Description:
 */
public class StickyActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private List<DiffUtilBean> newDiffUtilBeans = new ArrayList();
    private List<DiffUtilBean> datas = new ArrayList();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky);
        ButterKnife.bind(this);
        for (int i = 0; i < 20; i++) {
            newDiffUtilBeans.add(new DiffUtilBean("小明"+"=="+"i",0));
        }
        for (int i = 0; i < newDiffUtilBeans.size(); i++) {
            if (i%5==0){
                datas.add( new DiffUtilBean("标题",1));
                datas.add(newDiffUtilBeans.get(i));
            }else {
                datas.add(newDiffUtilBeans.get(i));
            }
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new StickyItemDecoration());
        StickyAdapter adapter = new StickyAdapter(this, datas);
        mRecyclerView.setAdapter(adapter);
    }
}
