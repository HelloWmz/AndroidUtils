package com.example.admin.androidutils.weigdt;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.admin.androidutils.R;

/**
 * @ProjectName AndroidUtils
 * @CreateDate: 2020/7/24
 * @Author: wmz
 * @Description: 计步自定view
 */
public class StepView extends View {
    private int mOutside_color = Color.RED;
    private int mInside_color = Color.YELLOW;
    private int mStep_text_color = Color.YELLOW;
    private float mRing_width = 10;
    private Paint mOutsidePaint;
    private Paint mInsidePaint;
    private Paint mTextPaint;
    private float maxStep = 10000;
    private float mCurrentStep = 6000;
    private Rect mTexBounds;

    public StepView(Context context) {
        this(context, null);
    }

    public StepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StepView);
        mOutside_color = typedArray.getColor(R.styleable.StepView_outside_color, mOutside_color);
        mInside_color = typedArray.getColor(R.styleable.StepView_inside_color, mInside_color);
        mStep_text_color = typedArray.getColor(R.styleable.StepView_step_text_color, mStep_text_color);
        mRing_width = typedArray.getDimension(R.styleable.StepView_ring_width, mRing_width);
        //初始外画笔
        mOutsidePaint = new Paint();
        mOutsidePaint.setAntiAlias(true);//抗锯齿
        mOutsidePaint.setColor(mOutside_color);
        mOutsidePaint.setStrokeWidth(mRing_width);
        mOutsidePaint.setStyle(Paint.Style.STROKE);//设置镂空
        mOutsidePaint.setStrokeCap(Paint.Cap.ROUND); //设置边角圆形

        //初始内画笔
        mInsidePaint = new Paint();
        mInsidePaint.setAntiAlias(true);//抗锯齿
        mInsidePaint.setColor(mInside_color);
        mInsidePaint.setStrokeWidth(mRing_width);
        mInsidePaint.setStyle(Paint.Style.STROKE);//设置镂空
        mInsidePaint.setStrokeCap(Paint.Cap.ROUND); //设置边角圆形

        //初始画笔
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);//抗锯齿
        mTextPaint.setColor(mStep_text_color);
        mTexBounds = new Rect();
        mTextPaint.getTextBounds(mCurrentStep + "", 0, (mCurrentStep + "").length(), mTexBounds);
        mTextPaint.setTextSize(50);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth();
        setMeasuredDimension(measuredWidth > measuredHeight ? measuredHeight : measuredWidth, measuredWidth > measuredHeight ? measuredHeight : measuredWidth);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        float radius = getWidth() / 2 - mRing_width / 2;
        RectF rectF = new RectF(mRing_width / 2, mRing_width / 2, width / 2 + radius, width / 2 + radius);
        canvas.drawArc(rectF, 135, 270, false, mOutsidePaint);
        canvas.drawArc(rectF, 135, 270 * mCurrentStep / maxStep, false, mInsidePaint);
        canvas.drawText(String.valueOf((int) mCurrentStep), getWidth() / 2 - mTexBounds.width() / 2, getWidth() / 2 + mTexBounds.height() / 2, mTextPaint);

    }

    public void setCurrentStep(float currentStep) {
        mCurrentStep = currentStep;
        invalidate();
    }
}
