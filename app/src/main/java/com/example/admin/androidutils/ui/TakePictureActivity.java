package com.example.admin.androidutils.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.example.admin.androidutils.R;

import java.nio.ByteBuffer;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 2018/5/14.
 */

public class TakePictureActivity extends AppCompatActivity implements SurfaceHolder.Callback {
  @BindView(R.id.surface_view)
  SurfaceView mSurfaceView;
  private HandlerThread mThreadHandler;
  private Handler mHandler;
  public SurfaceHolder mSurfaceHolder;
  CameraManager mCameraManager;
  private int REQUEST_CAMERA_CODE = 0x01;
  private CameraDevice mCameraDevice;
  private CameraCaptureSession mCameraCaptureSession;
  private ImageReader mImageReader;
  private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

  ///为了使照片竖直显示
  static {
    ORIENTATIONS.append(Surface.ROTATION_0, 90);
    ORIENTATIONS.append(Surface.ROTATION_90, 0);
    ORIENTATIONS.append(Surface.ROTATION_180, 270);
    ORIENTATIONS.append(Surface.ROTATION_270, 180);
  }

  private Handler mainHandler;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_take_picture);
    ButterKnife.bind(this);
    mSurfaceHolder = mSurfaceView.getHolder();
    mSurfaceHolder.addCallback(this);
    initHadler();
  }

  private void initHadler() {
    mThreadHandler = new HandlerThread("CAMERA2");
    mThreadHandler.start();
    mHandler = new Handler(mThreadHandler.getLooper());
    mainHandler = new Handler(getMainLooper());
  }

  @Override
  public void surfaceCreated(SurfaceHolder holder) {
    initCamera();
  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {
    // 释放Camera资源
    if (null != mCameraDevice) {
      mCameraDevice.close();
      mCameraDevice = null;
    }
  }

  //初始化相机

  private void initCamera() {
    try {
      mImageReader = ImageReader.newInstance(1080, 1920, ImageFormat.JPEG, 1);
      mImageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() { //可以在这里处理拍照得到的临时照片 例如，写入本地
        @Override
        public void onImageAvailable(ImageReader reader) {
          // mCameraDevice.close();
          // 拿到拍照照片数据
          Image image = reader.acquireNextImage();
          ByteBuffer buffer = image.getPlanes()[0].getBuffer();
          byte[] bytes = new byte[buffer.remaining()];
          buffer.get(bytes);//由缓冲区存入字节数组
          final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
          if (bitmap != null) {
            // iv_show.setImageBitmap(bitmap);
          }
          image.close();
        }
      }, mainHandler);
      mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
        //申请WRITE_EXTERNAL_STORAGE权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          requestPermissions(new String[]{Manifest.permission.CAMERA},
               REQUEST_CAMERA_CODE);
        }
      } else {
        //打开摄像头
        mCameraManager.openCamera("0", stateCallback, mainHandler);
      }
    } catch (CameraAccessException e) {
    }
  }

  private CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
    @Override
    public void onOpened(@NonNull CameraDevice camera) {
      mCameraDevice = camera;
      //开启预览
      takePreview();
    }

    @Override
    public void onDisconnected(@NonNull CameraDevice camera) {
      if (null != mCameraDevice) {
        mCameraDevice.close();
        mCameraDevice = null;
      }
    }

    @Override
    public void onError(@NonNull CameraDevice camera, int error) {

    }
  };


  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == REQUEST_CAMERA_CODE) {
      if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        // Permission Granted
        try {
          mCameraManager.openCamera("0", stateCallback, mainHandler);
        } catch (CameraAccessException e) {
          e.printStackTrace();
        } catch (SecurityException e) {
          e.printStackTrace();
        }
      } else {
        // Permission Denied
      }
    }
  }

  /**
   * 开始预览
   */
  private void takePreview() {
    Log.e("test", "预览");
    try {
      // 创建预览需要的CaptureRequest.Builder
      final CaptureRequest.Builder previewRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
      // 将SurfaceView的surface作为CaptureRequest.Builder的目标
      previewRequestBuilder.addTarget(mSurfaceHolder.getSurface());
      // 创建CameraCaptureSession，该对象负责管理处理预览请求和拍照请求
      mCameraDevice.createCaptureSession(Arrays.asList(mSurfaceHolder.getSurface(), mImageReader.getSurface()), new CameraCaptureSession.StateCallback() // ③
      {
        @Override
        public void onConfigured(CameraCaptureSession cameraCaptureSession) {
          if (null == mCameraDevice) {
            Log.e("test", "预览null");
            return;
          }
          // 当摄像头已经准备好时，开始显示预览
          mCameraCaptureSession = cameraCaptureSession;
          try {
            // 自动对焦
            previewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            // 打开闪光灯
            previewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
            // 显示预览
            CaptureRequest previewRequest = previewRequestBuilder.build();
            mCameraCaptureSession.setRepeatingRequest(previewRequest, null, mHandler);
          } catch (CameraAccessException e) {
            e.printStackTrace();
          }
        }

        @Override
        public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
          Toast.makeText(TakePictureActivity.this, "配置失败", Toast.LENGTH_SHORT).show();
        }
      }, mHandler);
    } catch (CameraAccessException e) {
      e.printStackTrace();
    }
  }

  private void takePicture() {
    if (mCameraDevice == null) return;
    // 创建拍照需要的CaptureRequest.Builder
    final CaptureRequest.Builder captureRequestBuilder;
    try {
      captureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
      // 将imageReader的surface作为CaptureRequest.Builder的目标
      captureRequestBuilder.addTarget(mImageReader.getSurface());
      // 自动对焦
      captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
      // 自动曝光
      captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
      // 获取手机方向
      int rotation = getWindowManager().getDefaultDisplay().getRotation();
      // 根据设备方向计算设置照片的方向
      captureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));
      //拍照
      CaptureRequest mCaptureRequest = captureRequestBuilder.build();
      mCameraCaptureSession.capture(mCaptureRequest, null, mHandler);
    } catch (CameraAccessException e) {
      e.printStackTrace();
    }
  }

  public void click(View view) {
    takePicture();
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (null != mCameraDevice) {
      mCameraDevice.close();
      mCameraDevice = null;
    }
  }
}
