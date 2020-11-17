package com.example.admin.androidutils.ui;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.admin.androidutils.R;
import com.example.admin.androidutils.utils.DBHelper;
import com.example.admin.androidutils.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Admin on 2018/9/5.
 */

public class DataBaseActivity extends AppCompatActivity {

  public DBHelper mDbHelper;
  public SQLiteDatabase mWritableDatabase;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_data_base);
    ButterKnife.bind(this);
    mDbHelper = new DBHelper(this);
    mWritableDatabase = mDbHelper.getWritableDatabase();


  }

  @OnClick({R.id.creat_data_base, R.id.creat_data_up_data_base, R.id.creat_add, R.id.creat_data_delete, R.id.creat_data_updata, R.id.creat_data_check})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.creat_data_base:


        break;
      case R.id.creat_data_up_data_base:
        break;
      case R.id.creat_add:
        ContentValues contentValues = new ContentValues();
        contentValues.put("name","zhangsan");
        contentValues.put("age","20");
        long student = mWritableDatabase.insert("student", null, contentValues);
        contentValues.clear();
        if (student>0){
          ToastUtils.showUiToast(this,"添加成功"+student);
        }
        break;
      case R.id.creat_data_delete:
        break;
      case R.id.creat_data_updata:
        break;
      case R.id.creat_data_check:
        break;
    }
  }
}
