package com.example.admin.androidutils.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 1、MeasureSpec.AT_MOST : 在布局中指定了wrap_content
 * 2、MeasureSpec.EXACTLY : 在布居中指定了确切的值 100dp match_parent fill_parent
 * 3、MeasureSpec.UNSPECIFIED : 尽可能的大,很少能用到，ListView , ScrollView 在测量子布局的时候会用UNSPECIFIE
 */

public class PercentView extends View {
  private final static String TAG = PercentView.class.getSimpleName();
  private Paint mPaint;
  private RectF oval;
  private int mWidth;
  private int mHeight;
  private float mTitleTextSize = 30;
  private String mTitleText = "你是谁啊";
  //保存字体测量的宽高
  private Rect mBound = new Rect();

  public PercentView(Context context) {
    super(context);
    init();
  }

  public PercentView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public PercentView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    mPaint = new Paint();
    mPaint.setAntiAlias(true);
    oval = new RectF();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int widthMode = MeasureSpec.getMode(widthMeasureSpec);
    int widthSize = MeasureSpec.getSize(widthMeasureSpec);
    int heightMode = MeasureSpec.getMode(heightMeasureSpec);
    int heightSize = MeasureSpec.getSize(heightMeasureSpec);
    Log.e(TAG, "onMeasure--widthMode-->" + widthMode);
    mWidth = getWidthMeasureResult(widthMode, widthSize);
    mHeight = getHeightMeasureResult(heightMode, heightSize);
    setMeasuredDimension(mWidth, mHeight);
    Log.e(TAG, "onMeasure--widthSize-->" + widthSize);
    Log.e(TAG, "onMeasure--heightMode-->" + heightMode);
    Log.e(TAG, "onMeasure--heightSize-->" + heightSize);
  }

  //获取真实的高度
  private int getHeightMeasureResult(int heightMode, int heightSize) {
    mPaint.setTextSize(mTitleTextSize);
    mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
    int textheight = mBound.height();
    // match_parent , 精确的值 accurate
    if (heightMode == MeasureSpec.EXACTLY) {
      return Math.max(heightSize, textheight);
      // 在布局中指定了 wrap_content
    } else {
      int desired = getPaddingTop() + textheight + getPaddingBottom();
      return desired;
    }

  }

  //获取真实的宽度
  private int getWidthMeasureResult(int widthMode, int widthSize) {
    mPaint.setTextSize(mTitleTextSize);
    mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
    int textWidth = mBound.width();
    if (widthMode == MeasureSpec.EXACTLY) {
      //返回最大的宽度
      return Math.max(widthSize, textWidth);
    } else {
      int desired = getPaddingLeft() + textWidth + getPaddingRight();
      return desired;
    }
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
  }

  /**
   * startAngle 起始角度的位置
   * sweepAngle  扫过的角度
   * useCenter   是否与圆心相连
   */
//  @Override
//  protected void onDraw(Canvas canvas) {
//    super.onDraw(canvas);
//    mPaint.setColor(Color.GRAY);
//    // FILL填充, STROKE描边,FILL_AND_STROKE填充和描边
//    mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//    int with = getWidth();
//    int height = getHeight();
//    Log.e(TAG, "onDraw---->" + with + "*" + height);
//    float radius = with / 4;
//    canvas.drawCircle(with / 2, with / 2, radius, mPaint);
//    mPaint.setColor(Color.BLUE);
//    oval.set(with / 2 - radius, with / 2 - radius, with / 2
//         + radius, with / 2 + radius);//用于定义的圆弧的形状和大小的界限
//    canvas.drawArc(oval, -90, 90, true, mPaint);  //根据进度画圆弧
//  }
  //绘制文字
  @Override
  protected void onDraw(Canvas canvas) {
    mPaint.setTextSize(mTitleTextSize);
    mPaint.setColor(Color.RED);
    //如果文字的宽度大于测量的宽度
    if (mBound.width() == mWidth || mBound.height() == mHeight) {
      canvas.drawText(mTitleText, getPaddingLeft(), mHeight - getPaddingRight(), mPaint);
    } else {
      canvas.drawText(mTitleText, mWidth / 2 - mBound.width() * 1.0f / 2, mHeight / 2 - mBound.height() * 1.0f / 2, mPaint);
    }
  }
}
