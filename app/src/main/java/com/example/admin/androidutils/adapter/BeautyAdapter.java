package com.example.admin.androidutils.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.androidutils.R;

import java.util.List;

/**
 * @ProjectName AndroidUtils
 * @CreateDate: 2021/1/19
 * @Author: wmz
 * @Description:
 */
public class BeautyAdapter extends BaseQuickAdapter<String , BaseViewHolder> {
    public BeautyAdapter( @Nullable List<String> data) {
        super(R.layout.item_demo, data);
    }



    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_text,item);

    }
}
