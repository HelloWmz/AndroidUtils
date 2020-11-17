package com.example.admin.androidutils.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.admin.androidutils.DiffCallBack;
import com.example.admin.androidutils.R;
import com.example.admin.androidutils.bean.DiffUtilBean;
import com.example.admin.androidutils.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Admin on 2018/9/19.
 */

public class DiffUtilActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.bt)
    Button mBt;
    @BindView(R.id.rv)
    RecyclerView mRv;
    private List<DiffUtilBean> newDiffUtilBeans = new ArrayList();
    private List<DiffUtilBean> oldDiffUtilBeans = new ArrayList();
    public RecylerAdapter mAdapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diffutil);
        ButterKnife.bind(this);
        mBt.setOnClickListener(this);
        initData();
        initRecylerView();

    }


    boolean isClick = false;

    private void initData() {
        for (int i = 4; i > 0; i--) {
            oldDiffUtilBeans.add(new DiffUtilBean("小红" + i, i, "男"));
        }
        for (int i = 6; i > 0; i--) {
            newDiffUtilBeans.add(new DiffUtilBean("小红" + i, i, "男"));
        }
    }

    private void initRecylerView() {
        CommonUtils.setBaseRecylerView(null, this, mRv);
        mAdapter = new RecylerAdapter();
        mRv.setAdapter(mAdapter);
        mAdapter.setNewData(oldDiffUtilBeans);


    }


    @Override
    public void onClick(View v) {
        DiffUtil.DiffResult diffResult;
        if (isClick) {
            diffResult = DiffUtil.calculateDiff(new DiffCallBack(oldDiffUtilBeans, newDiffUtilBeans), true);
        } else {
            diffResult = DiffUtil.calculateDiff(new DiffCallBack(newDiffUtilBeans, oldDiffUtilBeans), true);
        }
        isClick = !isClick;
        diffResult.dispatchUpdatesTo(mAdapter);
        mAdapter.setNewData(isClick ? newDiffUtilBeans : oldDiffUtilBeans);

    }




}

