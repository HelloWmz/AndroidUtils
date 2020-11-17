package com.example.admin.androidutils.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.example.admin.androidutils.R;
import com.example.admin.androidutils.utils.BaseDialog;
import com.example.admin.androidutils.utils.LoadingDialog;
import com.example.admin.androidutils.utils.ScreenUtils;

/**
 * Created by Admin on 2018/6/21.
 */

public class BaseDialogActivity extends AppCompatActivity implements View.OnClickListener {
  private BaseDialog dialog;
  public LoadingDialog mDialog;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_base_dialog);
    findViewById(R.id.btn_center).setOnClickListener(this);
    findViewById(R.id.btn_left).setOnClickListener(this);
    findViewById(R.id.btn_top).setOnClickListener(this);
    findViewById(R.id.btn_right).setOnClickListener(this);
    findViewById(R.id.btn_bottom).setOnClickListener(this);
    findViewById(R.id.btn_progress).setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {

    switch (v.getId()) {
      case R.id.btn_center:
        dialog = new BaseDialog(this);
        dialog.config(R.layout.dialog_center, true).show();
        dialog.findViewById(R.id.tv_confirm).setOnClickListener(this);
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(this);
        break;
      case R.id.btn_left:
        BaseDialog dialog_left = new BaseDialog(this);

        dialog_left.config(R.layout.dialog_left, 0.5f, Gravity.LEFT | Gravity.CENTER, BaseDialog.AnimInType.LEFT,
             WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.MATCH_PARENT, true).show();

        break;
      case R.id.btn_top:
        BaseDialog dialog_top = new BaseDialog(this);
        dialog_top.setOffset(0, ScreenUtils.dip2px(this, 48));
        dialog_top.config(R.layout.dialog_photo, 0.5f, Gravity.TOP, BaseDialog.AnimInType.TOP,
             WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true).show();
        break;
      case R.id.btn_right:
        BaseDialog dialog_right = new BaseDialog(this);

        dialog_right.setOffset(20, 0);

        dialog_right.config(R.layout.dialog_right, Gravity.RIGHT | Gravity.CENTER, BaseDialog.AnimInType.RIGHT,
             WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.MATCH_PARENT, true).show();
        break;
      case R.id.btn_bottom:
        BaseDialog dialog_bottom = new BaseDialog(this);
        dialog_bottom.config(R.layout.dialog_photo, Gravity.BOTTOM, BaseDialog.AnimInType.BOTTOM, true).show();
        break;
      case R.id.tv_confirm:
        dialog.dismiss();
        break;
      case R.id.tv_cancel:
        dialog.dismiss();
        break;
      case R.id.btn_progress:
        mDialog = LoadingDialog.createDialog(this);
        mDialog.Show();
    }

  }
}
