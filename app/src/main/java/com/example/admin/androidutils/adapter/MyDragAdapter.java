package com.example.admin.androidutils.adapter;


import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.androidutils.R;

import java.util.List;

/**
 * @ProjectName Food_knowledge
 * @CreateDate: 2020/8/22
 * @Author: wmz
 * @Description:
 */
public class MyDragAdapter extends BaseItemDraggableAdapter<String, BaseViewHolder> {


    public MyDragAdapter(List<String> data) {
        super(R.layout.drag_rv,data);
    }


    @Override
    protected void convert(final BaseViewHolder helper, String item) {
       helper.setText(R.id.tv,item);

        //
    }


}
