package com.example.admin.androidutils.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.admin.androidutils.R;
import com.example.admin.androidutils.utils.PicUtils;

/**
 * Created by wmz on 2019/3/26.
 */

public class PicActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);
        ImageView iv = findViewById(R.id.iv);
        iv.setImageResource(R.mipmap.wd_img_tx);
        ImageView iv1 = findViewById(R.id.iv1);
        Bitmap bmp = BitmapFactory.decodeResource(this.getResources(), R.mipmap.wd_img_tx);
        Bitmap drawable = PicUtils.scale(bmp, bmp.getWidth(), bmp.getHeight());
        iv1.setImageBitmap(drawable);
    }
}
