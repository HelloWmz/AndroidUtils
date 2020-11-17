package com.example.admin.androidutils.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.admin.androidutils.R;
import com.example.admin.androidutils.utils.LogUtils;

/**
 * Created by wmz on 2019/3/15.
 */

public class AnimatorActivity extends AppCompatActivity implements View.OnClickListener {
    TextView mTv;
    TextView mTv1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setWindow();
        setContentView(R.layout.animator_activity);
        mTv =findViewById(R.id.tv);
        mTv1 =findViewById(R.id.tv1);
        mTv.setOnClickListener(this);
        String quantityString = getResources().getQuantityString(R.plurals.contunt, 1,33);
        mTv.setText(quantityString);
        mTv1.setText(String.format(getResources().getString(R.string.frommat),"1","是"));
        int a=100;
        int b=200;
        mTv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swithAb(100,200);
                LogUtils.e("a=="+a+",=="+"b=="+b);
            }
        });
    }
    public void  swithAb(int a ,int b){
        int tem=b;
        b=a;
        a=tem;
    }


    private void setWindow() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION //这里删除的话  可以解决华为虚拟按键的覆盖
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.RED);
            window.setNavigationBarColor(Color.RED);//这里删除的话
        }
    }

    @Override
    public void onClick(View v) {
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mTv, "scaleY", 1f, 1.2f, 1f);
        ObjectAnimator scaleX= ObjectAnimator.ofFloat(mTv, "scaleX", 1f, 1.2f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleX).with(scaleY);
        animatorSet.setDuration(500);
        animatorSet.start();
        mTv.animate().scaleX(1.2f).scaleX(1.2f).setDuration(500);
    }
}
