package com.example.admin.androidutils.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.admin.androidutils.R;
import com.github.chengang.library.TickView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * UI控件
 */

public class UIActivity extends AppCompatActivity {

  @BindView(R.id.tick_view_accent)
  TickView mTickViewAccent;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ui);
    ButterKnife.bind(this);

    mTickViewAccent.setOnCheckedChangeListener(new TickView.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(TickView tickView, boolean isCheck) {
        //do something here
        Log.e("test", "isCheck=" + isCheck);
      }
    });
  }
}
