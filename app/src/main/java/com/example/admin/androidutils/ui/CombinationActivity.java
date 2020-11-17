package com.example.admin.androidutils.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.admin.androidutils.R;

import io.reactivex.annotations.NonNull;


public class CombinationActivity extends AppCompatActivity {
    ImageView iv_2;
    ImageView iv_3;
    ImageView iv_4;
    ImageView iv_6;
    ImageView iv_7;
    ImageView iv_3_3;
    ImageView iv_4_4;
    ImageView iv_6_6;
    int type = 0;
    boolean isSend=true;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void dispatchMessage(@NonNull final Message msg) {
            super.dispatchMessage(msg);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    switch (msg.what) {
                        case 0:
                            initview(iv_2.getTranslationX(), iv_2.getTranslationY(), iv_2, iv_7);
                            break;
                        case 1:
                            initview(iv_3.getTranslationX(), iv_3.getTranslationY(), iv_3, iv_3_3);
                            break;
                        case 2:
                            initview(iv_4.getTranslationX(), iv_4.getTranslationY(), iv_4, iv_4_4);
                            break;
                        case 3:
                            isSend=false;
                            initview(iv_6.getTranslationX(), iv_6.getTranslationY(), iv_6, iv_6_6);
                            break;
                    }
                }
            });

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_2 = findViewById(R.id.iv_2);
        iv_7 = findViewById(R.id.iv_7);
        iv_3 = findViewById(R.id.iv_3);
        iv_4 = findViewById(R.id.iv_4);
        iv_6 = findViewById(R.id.iv_6);
        iv_3_3 = findViewById(R.id.iv_3_3);
        iv_4_4 = findViewById(R.id.iv_4_4);
        iv_6_6 = findViewById(R.id.iv_6_6);
        new Thread(new MyThread()).start();


    }

    private void initview(float mCurTranslationX, float mCurTranslationY, final ImageView iv_2, final ImageView iv_7) {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(iv_2, "translationY", mCurTranslationY, mCurTranslationY - 30);
        final ObjectAnimator animator3 = ObjectAnimator.ofFloat(iv_7, "translationY", mCurTranslationY, mCurTranslationY - 30);
        final AnimatorSet animSet = new AnimatorSet();
        animSet.play(animator1).with(animator3);
        animSet.setDuration(1000);
        animSet.start();
        final float finalMCurTranslationX = mCurTranslationX;
        final float finalMCurTranslationY = mCurTranslationY;
        animator1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(iv_2, "translationX", finalMCurTranslationX, getScreenWidth(CombinationActivity.this));
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(iv_7, "translationX", -getScreenWidth(CombinationActivity.this), finalMCurTranslationX);
                ObjectAnimator animator3 = ObjectAnimator.ofFloat(iv_7, "translationY", finalMCurTranslationY - 30, finalMCurTranslationY);
                AnimatorSet animSet = new AnimatorSet();
                animSet.play(animator1).with(animator2).with(animator3);
                animSet.setDuration(5000);
                animator3.setDuration(5);
                animSet.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
    public class MyThread implements Runnable {
        @Override
        public void run() {
            while (isSend) {
                try {
                    Thread.sleep(type*500);// 线程暂停10秒，单位毫秒
                    Message msg = new Message();
                    msg.what = type;
                    mHandler.dispatchMessage(msg);
                    type++;
                    Log.e("type==",type+"");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
