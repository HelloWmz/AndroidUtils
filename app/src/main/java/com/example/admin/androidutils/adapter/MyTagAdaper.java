package com.example.admin.androidutils.adapter;

import android.view.View;
import android.widget.TextView;

import com.example.admin.androidutils.MainActivity;
import com.example.admin.androidutils.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * @ProjectName AndroidUtils
 * @CreateDate: 2020/8/13
 * @Author: wmz
 * @Description:
 */
class MyTagAdaper extends TagAdapter<String> {

    MyTagAdaper(List<String> datas) {
        super(datas);
    }

    @Override
    public View getView(FlowLayout parent, int position, String s) {
        TextView tv= (TextView) View.inflate(parent.getContext(), R.layout.tv,null);
        tv.setText(s);
        return tv;
    }
}

