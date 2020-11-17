package com.example.admin.androidutils.weigdt;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.androidutils.R;


public class HeadlineView extends LinearLayout {
  private TextView headlineLabel;

    public HeadlineView(Context context) {
        super(context,null);
    }

    public HeadlineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public HeadlineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = View.inflate(context, R.layout.more_view_head_item, this);
        headlineLabel=view.findViewById(R.id.headlineLabel);
    }

    public void setText(String text) {
        headlineLabel.setText(text);
    }
}
