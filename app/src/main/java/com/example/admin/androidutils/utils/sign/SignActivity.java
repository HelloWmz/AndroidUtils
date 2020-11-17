package com.example.admin.androidutils.utils.sign;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.admin.androidutils.R;
import com.example.admin.androidutils.bean.EventSignBean;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignActivity extends AppCompatActivity {
    @BindView(R.id.signature_pad)
    SignatureView mSignaturePad;
    @BindView(R.id.clear_button)
    Button mClearButton;
    @BindView(R.id.save_button)
    Button mSaveButton;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_utils);
        ButterKnife.bind(this);

//        mSignaturePad.setBackgroundResource(R.drawable.a);
        mSignaturePad.setOnSignedListener(new SignatureView.OnSignedListener() {
            @Override
            public void onSigned() {
                mSaveButton.setEnabled(true);
                mClearButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                mSaveButton.setEnabled(false);
                mClearButton.setEnabled(false);
            }
        });
    }


    @OnClick({R.id.clear_button, R.id.save_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clear_button:
                mSignaturePad.clear();
                break;
            case R.id.save_button:
                CreatSignTask creatSignTask = new CreatSignTask();
                creatSignTask.execute();
                break;
        }
    }


    public class CreatSignTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();
            String signPath = addSignatureToGallery(signatureBitmap);
            return signPath;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!TextUtils.isEmpty(s)) {
                Log.e("test1",s);
                EventBus.getDefault().post(new EventSignBean(s));
                finish();
            } else {
                Toast.makeText(SignActivity.this, "保存签名失败", Toast.LENGTH_SHORT).show();
                ;
            }
        }

    }


    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);

        if (!file.mkdirs()) {
            Log.e("SignatureFile", "Directory not created");
        }
        return file;
    }

    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.PNG, 80, stream);
        stream.close();
    }


    //把白色转换成透明
    public static Bitmap getImageToChange(Bitmap mBitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        if (mBitmap != null) {
            int mWidth = mBitmap.getWidth();
            int mHeight = mBitmap.getHeight();
            for (int i = 0; i < mHeight; i++) {
                for (int j = 0; j < mWidth; j++) {
                    int color = mBitmap.getPixel(j, i);
                    int g = Color.green(color);
                    int r = Color.red(color);
                    int b = Color.blue(color);
                    int a = Color.alpha(color);
                    if (g >= 250 && r >= 250 && b >= 250) {
                        a = 0;
                    }
                    color = Color.argb(a, r, g, b);
                    createBitmap.setPixel(j, i, color);
                }
            }
        }
        return createBitmap;
    }


    public String addSignatureToGallery(Bitmap signature) {
        String filePath = "";
        try {
            File photo = new File(getAlbumStorageDir("SignatureFile"), String.format("SignatureFile_%d.png", System.currentTimeMillis()));
            signature = getImageToChange(signature);
            saveBitmapToJPG(signature, photo);
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(photo);
            mediaScanIntent.setData(contentUri);
            SignActivity.this.sendBroadcast(mediaScanIntent);
            filePath = photo.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }


}
