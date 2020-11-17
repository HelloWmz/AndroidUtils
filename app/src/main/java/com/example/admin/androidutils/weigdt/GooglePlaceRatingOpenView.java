package com.example.admin.androidutils.weigdt;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.admin.androidutils.R;
import com.example.admin.androidutils.utils.CommonUtils;


public class GooglePlaceRatingOpenView extends LinearLayout {

    LinearLayout starsContainer;
    public View mViewGroup;

    public GooglePlaceRatingOpenView(Context context) {
        this(context,null);
    }

    public GooglePlaceRatingOpenView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GooglePlaceRatingOpenView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mViewGroup = View.inflate(context, R.layout.old_cn_a_view_google_place_rating_open, this);
        starsContainer=mViewGroup.findViewById(R.id.starsContainer);
    }

    public void addGoogleStarRating(double rating) {
        starsContainer.removeAllViews();
        for (int i = 1; i <= 5; i++) {
            int size = (int) CommonUtils.convertDPToPixel(getContext(), 9);
            LayoutParams layoutParams = new LayoutParams(size, size);
            layoutParams.setMargins(0, 0, 2, 0);
            ImageView star = new ImageView(getContext());
            star.setLayoutParams(layoutParams);
            if (i <= rating) {
                star.setImageResource(R.mipmap.a_icn_rated_star);
            } else {
                star.setImageResource(R.mipmap.a_icn_nonrated_star);
            }
            starsContainer.addView(star);
        }
    }
}
