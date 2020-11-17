package com.example.admin.androidutils.weigdt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.admin.androidutils.R;
import com.example.admin.androidutils.utils.CommonUtils;


/**
 * Created by wmz on 2019/4/23.
 */

/**
 * 自定义可以点击的textview
 */
@SuppressLint("AppCompatCustomView")
public class MyTextView extends TextView {

    private final Context mContext;
    /**
     * fasle 是否编辑
     */
    public boolean isClick;
    private boolean isShowArrow;

    public MyTextView(Context context) {
        this(context, null);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.my_TextView);
        isClick = ta.getBoolean(R.styleable.my_TextView_isClick, false);
        isShowArrow = ta.getBoolean(R.styleable.my_TextView_isShow_Arrow, false);
        setIsClick(isClick);
        setIsShowArrow(isShowArrow);

    }

    /**
     * 代码中隐藏DrawableRight 图片
     *
     * @param isShowArrow
     */
    public void setIsShowArrow(boolean isShowArrow) {
        if (!isShowArrow) {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        } else {
            Drawable drawable = getResources().getDrawable(R.mipmap.jt);

            setCompoundDrawablesWithIntrinsicBounds(null,
                    null, drawable, null);
             setCompoundDrawablePadding(CommonUtils.dip2px(mContext,10));

        }
        this.isShowArrow = isShowArrow;
    }


    public void setIsClick(boolean isClick) {
        setEnabled(isClick);
        setClickable(isClick);
        this.isClick = isClick;
    }
}
