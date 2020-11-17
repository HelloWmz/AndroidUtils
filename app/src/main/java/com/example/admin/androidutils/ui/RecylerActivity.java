package com.example.admin.androidutils.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.admin.androidutils.R;
import com.example.admin.androidutils.utils.CommonUtils;
import com.github.jdsjlzx.recyclerview.LRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 2018/9/7.
 */

public class RecylerActivity extends AppCompatActivity {
  @BindView(R.id.list)
  LRecyclerView mList;
  public RecylerAdapter mAdapter;
  ArrayList<String > datas =new ArrayList();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recyler);
    ButterKnife.bind(this);
    CommonUtils.setBaseRecylerView(null,this,mList);
    mAdapter = new RecylerAdapter();
    mList.setAdapter(mAdapter);
    initData();
    initListener();

  }

  private void initData() {
    for (int i = 30; i > 0; i--) {
      datas.add("数据"+i);
    }
   // mAdapter.addData(datas);
  }

  private void initListener() {
    mList.setPullRefreshEnabled(false);
   mList.setLScrollListener(new LRecyclerView.LScrollListener() {
     @Override
     public void onScrollUp() {

     }

     @Override
     public void onScrollDown() {

     }

     @Override
     public void onScrolled(int distanceX, int distanceY) {

     }

     @Override
     public void onScrollStateChanged(int state) {

     }
   });

  }
}
