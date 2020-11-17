package com.example.admin.androidutils.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.example.admin.androidutils.R;
import com.example.admin.androidutils.adapter.MyRvAdapter;
import com.example.admin.androidutils.bean.CommomBena;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName AndroidUtils
 * @CreateDate: 2020/8/28
 * @Author: wmz
 * @Description:
 */
public class DragRecylerViewActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyRvAdapter mFlowAdapter;
    List<CommomBena> datas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drag_flow_layout_activity);
        mRecyclerView = findViewById(R.id.rv);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFlowAdapter = new MyRvAdapter();
        mRecyclerView.setAdapter(mFlowAdapter);
        initData();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void initData() {
        datas.add(new CommomBena("已添加", 2));
        datas.add(new CommomBena("张三", 2));
        datas.add(new CommomBena("好多斯卡电话克里斯", 2));
        datas.add(new CommomBena("誓师大会", 2));
        mFlowAdapter.setNewData(datas);

    }

    private ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback
            (ItemTouchHelper.LEFT
                    | ItemTouchHelper.DOWN
                    | ItemTouchHelper.RIGHT
                    | ItemTouchHelper.UP
                    , ItemTouchHelper.ACTION_STATE_IDLE) {


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
            }
        }

        //结束拖拽
        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            Log.d("test", "onItemDragEnd" + viewHolder.getAdapterPosition());
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
}
