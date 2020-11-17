package com.example.admin.androidutils.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.example.admin.androidutils.R;
import com.example.admin.androidutils.adapter.FlowAdapter;
import com.example.admin.androidutils.bean.CommomBena;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName AndroidUtils
 * @CreateDate: 2020/8/28
 * @Author: wmz
 * @Description:
 */
public class DragFlowLayoutActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FlowAdapter mFlowAdapter;
    List<CommomBena> datas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drag_flow_layout_activity);
        mRecyclerView = findViewById(R.id.rv);
     /*   ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(this)
                //set vertical gravity for all items in a row. Default = Gravity.CENTER_VERTICAL
                .setChildGravity(Gravity.TOP)
                //whether RecyclerView can scroll. TRUE by default
                .setScrollingEnabled(true)
                //set maximum views count in a particular row
                .setMaxViewsInRow(2)

                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                // row strategy for views in completed row, could be STRATEGY_DEFAULT, STRATEGY_FILL_VIEW,
                //STRATEGY_FILL_SPACE or STRATEGY_CENTER
                .setRowStrategy(ChipsLayoutManager.STRATEGY_FILL_SPACE)
                // whether strategy is applied to last row. FALSE by default
                .withLastRow(true)
                .build();*/
        ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(this)
                .build();
        mRecyclerView.setLayoutManager(chipsLayoutManager);
        mFlowAdapter = new FlowAdapter(datas);
        mRecyclerView.setAdapter(mFlowAdapter);
        initData();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);


     /*  // MyDragAdapter dragAdapter = new MyDragAdapter(datas);
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mFlowAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);

// open drag
        mFlowAdapter.enableDragItem(itemTouchHelper);
        mFlowAdapter.setOnItemDragListener(onItemDragListener);
        mRecyclerView.setAdapter(mFlowAdapter);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);*/
    }

    private void initData() {
        datas.add(new CommomBena("已添加", 1));
        datas.add(new CommomBena("张三", 2));
        datas.add(new CommomBena("好多斯卡电话克里斯", 2));
        datas.add(new CommomBena("誓师大会", 2));
        datas.add(new CommomBena("张三的十大", 2));
        datas.add(new CommomBena("你大大三到四", 2));
        datas.add(new CommomBena("dsd", 2));
        datas.add(new CommomBena("ffsdfsf", 2));

        mFlowAdapter.setNewData(datas);

    }

    private ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback
            (ItemTouchHelper.LEFT
                    | ItemTouchHelper.DOWN
                    | ItemTouchHelper.RIGHT
                    | ItemTouchHelper.UP
                    , ItemTouchHelper.ACTION_STATE_IDLE) {
        View view;

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
            return source.getItemViewType() == target.getItemViewType();
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }

        //长按选中动画
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            super.onSelectedChanged(viewHolder, actionState);
            if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                Log.d("test", "onItemDragStart " + viewHolder.getAdapterPosition());
                ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(viewHolder.itemView, "scaleX", 1.0f, 1.1f);
                ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(viewHolder.itemView, "scaleY", 1.0f, 1.1f);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(300);
                animatorSet.playTogether(objectAnimatorX, objectAnimatorY);
                animatorSet.start();
                view = viewHolder.itemView;
            }
           /* if (actionState == ItemTouchHelper.ACTION_STATE_IDLE) {
                ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(view, "scaleX", 1.2f, 1.0f);
                ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(view, "scaleY", 1.2f, 1.0f);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(300);
                animatorSet.playTogether(objectAnimatorX, objectAnimatorY);
                animatorSet.start();
                mFlowAdapter.notifyDataSetChanged();
            }*/
        }

        //结束拖拽
        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            Log.d("test", "onItemDragEnd" + viewHolder.getAdapterPosition());
            ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(view, "scaleX", 1.1f, 1.0f);
            ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(view, "scaleY", 1.1f, 1.0f);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(300);
            animatorSet.playTogether(objectAnimatorX, objectAnimatorY);
            animatorSet.start();
        }

        @Override
        public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
            super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
            //获得两个Item的位置
            int originPosition = viewHolder.getAdapterPosition();
            int targetPistion = target.getAdapterPosition();
            //交换Adapter中对应的位置
            mFlowAdapter.move(originPosition, targetPistion);
            Log.d("test", "originPosition=" + originPosition);
            Log.d("test", "targetPistion=" + targetPistion);
        }
    };


    OnItemDragListener onItemDragListener = new OnItemDragListener() {
        @Override
        public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {

            Log.d("test", "onItemDragStart " + viewHolder.getAdapterPosition());
        }

        @Override
        public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
            //  Log.d("test", "move from: " + source.getAdapterPosition() + " to: " + target.getAdapterPosition());
        }

        @Override
        public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
            Log.d("test", "onItemDragEnd" + viewHolder.getAdapterPosition());

        }

    };


}
