package com.example.admin.androidutils;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.androidutils.utils.IItemTouchHelperAdapter;
import com.example.admin.androidutils.utils.OnStartDragListener;

import java.util.Collections;

/**
 * Created by Admin on 2018/5/11.
 */

public class RecyclerViewAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements IItemTouchHelperAdapter {


    public RecyclerViewAdapter() {
        super(R.layout.item);
    }

    private OnStartDragListener mDragListener;

    public void setDragListener(OnStartDragListener mDragListener) {
        this.mDragListener = mDragListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_textview, item);
        helper.getView(R.id.item).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mDragListener.onStartDrag(helper);
                return false;
            }
        });

    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(getData(), fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }
}
