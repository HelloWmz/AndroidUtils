package com.example.admin.androidutils.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.admin.androidutils.R;
import com.example.admin.androidutils.net.NetApi;
import com.example.admin.androidutils.net.RetrofitManager;
import com.example.admin.androidutils.net.User;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;

/**
 * Created by Admin on 2018/6/25.
 */

public class RetrofitUtilsActivity extends AppCompatActivity implements View.OnClickListener {
  @BindView(R.id.bt_login)
  Button mBtLogin;
  public NetApi mNetApi;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_retrofit);
    ButterKnife.bind(this);
    mBtLogin.setOnClickListener(this);
    mNetApi = RetrofitManager.getInstance().createServiceFrom(NetApi.class);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.bt_login:
      /*  HashMap<String, String> params = new HashMap<>();
        mNetApi.Login(new User("maomao","123456"))
                .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe(new Subject<Void>() {
             });*/
        break;
    }
  }
}
