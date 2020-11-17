package com.example.admin.androidutils.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.admin.androidutils.R;
import com.example.admin.androidutils.utils.LeftPopupWindows;

/**
 * Created by Admin on 2018/7/18.
 */

public class PopActivity extends AppCompatActivity implements View.OnClickListener {

  public View mBt;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pop);
    mBt = findViewById(R.id.bt);
    mBt.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    LeftPopupWindows leftPopupWindows = new LeftPopupWindows(this,v);
    leftPopupWindows.showAsDropDown(mBt);
  }
}
