package com.example.admin.androidutils.ui;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.KeyListener;
import android.text.method.NumberKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.androidutils.R;
import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 2018/5/11.
 */

public class PermissionsActivity extends AppCompatActivity implements View.OnClickListener {
   String s="...";
  /**
   * 定义所需要的权限
   */
  public static final String[] perms = {
       Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
  @BindView(R.id.et_main)
  EditText mEtMain;
  @BindView(R.id.bt)
  Button mBt;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_permissions);
    ButterKnife.bind(this);
    Log.e("test",s.length()+"");
    if (!AndPermission.hasPermission(this, perms)) {
      onViewClicked();
    }
    mBt.setOnClickListener(this);
    mEtMain.setKeyListener(listener);
  }

  /**
   * 输入框第一次弹出数字键盘
   */
  KeyListener listener = new NumberKeyListener() {

    /**
     * @return ：返回哪些希望可以被输入的字符,默认不允许输入
     */
    @Override
    protected char[] getAcceptedChars() {
      char[] chars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'X'};
      return chars;
//            return new char[0];
    }

    /**
     * 0：无键盘,键盘弹不出来
     * 1：英文键盘
     * 2：模拟键盘
     * 3：数字键盘
     *
     * @return
     */
    @Override
    public int getInputType() {
      return 3;
    }
  };

  public void onViewClicked() {
    AndPermission.with(this)
         .requestCode(200)
         .permission(perms)
         .callback(this)
         .start();
  }

  private RationaleListener rationaleListener = (requestCode, rationale) -> {
    AlertDialog.newBuilder(this)
         .setTitle("友好提醒")
         .setMessage("你已拒绝过定位权限，沒有定位定位权限无法为你推荐附近的妹子，你看着办！")
         .setPositiveButton("好，给你", (dialog, which) -> {
           rationale.resume();
         })
         .setNegativeButton("我拒绝", (dialog, which) -> {
           rationale.cancel();
         }).show();
  };


//  private PermissionListener listener = new PermissionListener() {
//    @Override
//    public void onSucceed(int requestCode, List<String> grantedPermissions) {
//      // 权限申请成功回调。
//
//      // 这里的requestCode就是申请时设置的requestCode。
//      // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
//      if (requestCode == 200) {
//        // TODO ...
//      }
//    }
//
//    @Override
//    public void onFailed(int requestCode, List<String> deniedPermissions) {
//      // 权限申请失败回调。
//      if (requestCode == 200) {
//        // TODO ...
//      }
//    }
//  };

  @PermissionYes(200)
  private void getPermissionYes(List<String> grantedPermissions) {
    // TODO 申请权限成功
    Toast.makeText(this, "申请成功", Toast.LENGTH_LONG).show();
  }

  @PermissionNo(200)
  private void getPermissionNo(List<String> deniedPermissions) {
    Toast.makeText(this, "申请失败", Toast.LENGTH_LONG).show();

  }

  @Override
  public void onClick(View v) {
    onViewClicked();
  }
}
