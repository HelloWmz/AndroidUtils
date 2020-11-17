package com.example.admin.androidutils.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.androidutils.R;
import com.example.admin.androidutils.bean.CommomBena;

import java.util.Collections;
import java.util.List;

/**
 * @ProjectName AndroidUtils
 * @CreateDate: 2020/8/28
 * @Author: wmz
 * @Description:
 */
public class FlowAdapter extends BaseMultiItemQuickAdapter<CommomBena, BaseViewHolder> {
    public static final int ITEM_TYPE_1 = 1;
    public static final int ITEM_TYPE_2 = 2;

    //
    public FlowAdapter(List<CommomBena> data) {
        super(data);
        addItemType(ITEM_TYPE_1, R.layout.flow_item2);
        addItemType(ITEM_TYPE_2, R.layout.flow_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommomBena item) {
        switch (helper.getItemViewType()) {
            case ITEM_TYPE_1:
                helper.setText(R.id.tv, item.name);
                break;
            case ITEM_TYPE_2:
                helper.setText(R.id.tv, item.name);
                break;
        }

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
