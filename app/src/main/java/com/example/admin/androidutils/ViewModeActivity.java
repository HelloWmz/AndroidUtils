package com.example.admin.androidutils;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.admin.androidutils.bean.Contact;
import com.example.admin.androidutils.bean.MyViewMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ProjectName AndroidUtils
 * @CreateDate: 2020/8/14
 * @Author: wmz
 * @Description:
 */
public class ViewModeActivity extends AppCompatActivity {
    @BindView(R.id.tv)
    TextView mTv;
    private MyViewMode mModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mode);
        ButterKnife.bind(this);
        mModel = ViewModelProviders.of(this).get(MyViewMode.class);
        mModel.getUsers().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(@Nullable List<Contact> contacts) {
                mTv.setText(contacts.toString());

            }
        });

    }

    @OnClick(R.id.bt1)
    public void onViewClicked() {
        mModel.setUsers("小米--小红");
    }
}
