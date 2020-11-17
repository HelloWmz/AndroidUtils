package com.example.admin.androidutils.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.admin.androidutils.R;
import com.example.admin.androidutils.weigdt.GooglePlaceRatingOpenView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 2018/7/22.
 */

public class StarActivity extends AppCompatActivity {
  @BindView(R.id.star)
  GooglePlaceRatingOpenView mStar;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_star);
    ButterKnife.bind(this);
    mStar.addGoogleStarRating(3);
  }
}
