package com.example.admin.androidutils.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.admin.androidutils.R;
import com.example.admin.androidutils.bean.EventSignBean;
import com.example.admin.androidutils.bean.WeekDay;
import com.example.admin.androidutils.utils.Utils;
import com.example.admin.androidutils.utils.sign.SignActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ProjectName AndroidUtils
 * @CreateDate: 2020/8/10
 * @Author: wmz
 * @Description:
 */
public class SignDemoActivity extends AppCompatActivity {
    private ImageView mIvSign;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mIvSign = findViewById(R.id.iv);


    }

    @OnClick(R.id.bt)
    public void onViewClicked() {
        Intent intent = new Intent();
        intent.setClass(this, SignActivity.class);
        startActivity(intent);
        List<WeekDay> weekDay = Utils.getWeekDay();
        Log.e("test",weekDay.toString());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(EventSignBean signBean) {
        String signPath = signBean.getSignFilePath();
        Log.e("test",signPath);
        Glide.with(this).load(signPath).into(mIvSign);
    }
}
