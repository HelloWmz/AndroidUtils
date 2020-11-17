package com.example.admin.androidutils.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.example.admin.androidutils.R;
import com.example.admin.androidutils.adapter.MyDragAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName AndroidUtils
 * @CreateDate: 2020/8/26
 * @Author: wmz
 * @Description:
 */
public class DragActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    List<String> datas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);
        mRecyclerView = findViewById(R.id.rv);
        initDatas();
        initRv();
    }

    private void initDatas() {
        for (int i = 0; i < 10; i++) {
            datas.add("111111");
        }
    }

    private void initRv() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyDragAdapter dragAdapter = new MyDragAdapter(datas);
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(dragAdapter);
        itemDragAndSwipeCallback.setDragMoveFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);

// open drag
        dragAdapter.enableDragItem(itemTouchHelper);
        dragAdapter.setOnItemDragListener(onItemDragListener);
        mRecyclerView.setAdapter(dragAdapter);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    OnItemDragListener onItemDragListener = new OnItemDragListener() {
        @Override
        public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
            Log.d("test", "onItemDragStart" + viewHolder.getAdapterPosition());
        }

        @Override
        public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
            Log.d("test", "move from: " + source.getAdapterPosition() + " to: " + target.getAdapterPosition());
        }

        @Override
        public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
            Log.d("test", "onItemDragEnd" + viewHolder.getAdapterPosition());
        }
    };
}
