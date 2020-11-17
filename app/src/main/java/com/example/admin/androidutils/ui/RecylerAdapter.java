package com.example.admin.androidutils.ui;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.androidutils.R;
import com.example.admin.androidutils.bean.DiffUtilBean;

/**
 * Created by Admin on 2018/9/7.
 */

class RecylerAdapter extends BaseQuickAdapter<DiffUtilBean,BaseViewHolder>{
  public RecylerAdapter() {
    super(R.layout.item);
  }

  @Override
  protected void convert(BaseViewHolder helper, DiffUtilBean item) {
    helper.setText(R.id.tv_textview,item.name+","+item.sex+","+item.age);
  }
}
