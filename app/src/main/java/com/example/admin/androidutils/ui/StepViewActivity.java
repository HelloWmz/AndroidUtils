package com.example.admin.androidutils.ui;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.LinearInterpolator;

import com.example.admin.androidutils.R;
import com.example.admin.androidutils.weigdt.StepView;

/**
 * @ProjectName AndroidUtils
 * @CreateDate: 2020/7/24
 * @Author: wmz
 * @Description:
 */

public class StepViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_view);
        StepView mStepView =findViewById(R.id.step_view);
        //创建数字改变动画
        ValueAnimator valueAnimator  = ObjectAnimator.ofFloat(0,8000);
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float animatedValue = (Float) animation.getAnimatedValue();
                mStepView.setCurrentStep(animatedValue);
            }
        });
        valueAnimator.start();
    }
}
