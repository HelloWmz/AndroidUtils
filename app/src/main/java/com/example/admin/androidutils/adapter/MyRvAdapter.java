package com.example.admin.androidutils.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.androidutils.R;
import com.example.admin.androidutils.bean.CommomBena;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ProjectName AndroidUtils
 * @CreateDate: 2020/8/28
 * @Author: wmz
 * @Description:
 */
public class MyRvAdapter extends BaseQuickAdapter<CommomBena, BaseViewHolder> {




    //
    public MyRvAdapter() {
        super(R.layout.my_rv_item);

    }

    @Override
    protected void convert(BaseViewHolder helper, CommomBena item) {
        RecyclerView mRecyclerView = helper.getView(R.id.my_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        MyRvInAdapter mMyRvInAdapter = new MyRvInAdapter();
        mRecyclerView.setAdapter(mMyRvInAdapter);
        List<String> datas = new ArrayList<>();
        datas.add("已添加1");
        datas.add("已添加2");
        datas.add("已添加3");
        datas.add("已添加4");
        datas.add("已添加5");
        datas.add("已添加6");
        mMyRvInAdapter.setNewData(datas);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT
                        | ItemTouchHelper.DOWN
                        | ItemTouchHelper.RIGHT
                        | ItemTouchHelper.UP
                , ItemTouchHelper.ACTION_STATE_IDLE
        ) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

            //长按选中动画
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    Log.d("test", "onItemINDragStart " + viewHolder.getAdapterPosition());
                }
            }

            //结束拖拽
            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                Log.d("test", "onItemINDragEnd" + viewHolder.getAdapterPosition());
            }

            @Override
            public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
                super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
                //获得两个Item的位置
                int originPosition = viewHolder.getAdapterPosition();
                int targetPistion = target.getAdapterPosition();
                //交换Adapter中对应的位置
                mMyRvInAdapter.move(originPosition, targetPistion);
                Log.d("test", "originINPosition=" + originPosition);
                Log.d("test", "targetINPistion=" + targetPistion);
            }
        });
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

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
