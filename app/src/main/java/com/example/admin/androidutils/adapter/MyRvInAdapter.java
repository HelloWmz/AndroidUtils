package com.example.admin.androidutils.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.androidutils.R;

import java.util.Collections;

/**
 * @ProjectName AndroidUtils
 * @CreateDate: 2020/8/28
 * @Author: wmz
 * @Description:
 */
public class MyRvInAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    //
    public MyRvInAdapter() {
        super(R.layout.my_rv_in_item);

    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv, item);

    }

    public void move(int origin, int target) {
        if (origin < target) {
            for (int i = origin; i < target; i++) {
                Collections.swap(getData(), i, i + 1);
            }
        }
        if (origin > target) {
            for (int i = origin; i > target; i--) {
                Collections.swap(getData(), i, i - 1);
            }
        }
        notifyItemMoved(origin, target);
    }


}
