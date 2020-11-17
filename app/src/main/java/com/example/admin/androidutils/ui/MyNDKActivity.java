package com.example.admin.androidutils.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.androidutils.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 2018/5/16.
 */

public class MyNDKActivity extends AppCompatActivity {
  @BindView(R.id.bt)
  Button mBt;
  @BindView(R.id.tv_ndk)
  TextView mTvNdk;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dnk);
    ButterKnife.bind(this);
    mBt.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
       // mTvNdk.setText(TestJni.testJni());
        shareUtil("原生分享", "你是谁啊?", "哈哈哈哈", "");
      }
    });
  }

  public void shareUtil(String activityTitle, String msgTitle, String msgText,
                        String imgPath) {
    Intent intent = new Intent(Intent.ACTION_SEND);
    if (imgPath == null || imgPath.equals("")) {
      intent.setType("text/plain"); // 纯文本
    } else {
      File f = new File(imgPath);
      if (f != null && f.exists() && f.isFile()) {
        intent.setType("image/jpg");
        Uri u = Uri.fromFile(f);
        intent.putExtra(Intent.EXTRA_STREAM, u);
      }
    }
    intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
    intent.putExtra(Intent.EXTRA_TEXT, msgText);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(Intent.createChooser(intent, activityTitle));
  }

}
