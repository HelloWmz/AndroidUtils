package com.example.admin.androidutils.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.androidutils.R;
import com.soundcloud.android.crop.Crop;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.yanzhenjie.album.Album;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 获取相册中的图片
 */

public class AlbumActivity extends AppCompatActivity implements View.OnClickListener {
  @BindView(R.id.bt)
  Button mBt;
  @BindView(R.id.bt_crop)
  Button mBtCrop;
  @BindView(R.id.iv)
  ImageView mIv;
  public String mPath;
  @BindView(R.id.bt_crop2)
  Button mBtCrop2;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_album);
    ButterKnife.bind(this);
    mBt.setOnClickListener(this);
    mBtCrop.setOnClickListener(this);
    mBtCrop2.setOnClickListener(this);

  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.bt_crop:
        Crop.pickImage(this);
        break;
      case R.id.bt_crop2:
        CropImage.activity()
             .setAllowFlipping(false)
             .setGuidelines(CropImageView.Guidelines.ON)
             .start(this);
        break;
      case R.id.bt:
        Album.startAlbum(this, 100
             , 9                                                         // 指定选择数量。
             , ContextCompat.getColor(this, R.color.colorPrimary)        // 指定Toolbar的颜色。
             , ContextCompat.getColor(this, R.color.colorPrimaryDark));
        break;
    }

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == 100 && resultCode == RESULT_OK) { // 判断是否成功。
      List<String> pathList = Album.parseResult(data);
      mPath = pathList.get(0);
      Toast.makeText(this, pathList.size() + "", Toast.LENGTH_LONG).show();
      //将图片的长和宽缩小味原来的1/2
      Bitmap bm = BitmapFactory.decodeFile(mPath);
      mIv.setImageBitmap(bm);
      // 拿到用户选择的图片路径List：
    } else if (resultCode == RESULT_CANCELED) { // 用户取消选择。
      // 根据需要提示用户取消了选择。
    } else if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
      /**
       * 当调用相册选择一张图片后，将图片信息通过data传过来
       * data.getData()获取到图片URI
       */
      beginCrop(data.getData());
    } else if (requestCode == Crop.REQUEST_CROP) {
      //调用Crop.start后来到这
      handleCrop(resultCode, data);
    } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
      CropImage.ActivityResult result = CropImage.getActivityResult(data);
      if (resultCode == RESULT_OK) {
        Uri resultUri = result.getUri();
        Bitmap bm = BitmapFactory.decodeFile(resultUri.getPath());
        mIv.setImageBitmap(bm);
      } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
        Exception error = result.getError();
      }
    }
  }

  /**
   * 调用Crop.pickImage后选择的图片调用Crop来裁剪
   *
   * @param source 通过相册选择的图片URI
   */
  private void beginCrop(Uri source) {
    Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
    Crop.of(source, destination).asSquare().start(this);
  }

  private void handleCrop(int resultCode, Intent result) {
    if (resultCode == RESULT_OK) {
      //如果裁剪正常，resultCode == RESULT_OK则到这里裁剪完成
      mIv.setImageURI(Crop.getOutput(result));
    } else if (resultCode == Crop.RESULT_ERROR) {
      Toast.makeText(AlbumActivity.this, Crop.getError(result).getMessage(), Toast.LENGTH_LONG).show();
    }
  }

}
