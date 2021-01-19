package com.example.admin.androidutils;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.admin.androidutils.ui.*;
import com.example.admin.androidutils.utils.*;
import com.example.admin.androidutils.weigdt.MyTextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements BaseQuickAdapter.OnItemClickListener, OnStartDragListener {


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    public RecyclerViewAdapter mRecyclerViewAdapter;
    @BindView(R.id.bt)
    Button bt;
    @BindView(R.id.et_address)
    MyTextView etAddress;
    private String[] data = {"权限申请", "自定义拍照", "蓝牙讲解", "android分享", "选取照片",
            "slidinguppanel布局", "UI控件", "ExoPlayer使用", "通用的dialog", "联网Retrofit", "pop"
            , "五角星", "RecyclerView 配合 DiffUtil", "属性动画", "图片缩放", "指纹识别"
            , "语音识别", "MPAndroidChart柱形图", "HelloChart柱形图", "组合动画", "粘性recylerView"
            , "计步器View", "签名", "flowlayout使用", "ViewMode使用",
            "DragActivity", "dragflowlayout", "Rv嵌套拖动","自定义LayoutManager"};
    private ArrayList<Class> mClasses = new ArrayList<>();
    private int REQUEST_CODE_TAKE_PHOTO = 200;
    //最后照片保存的本地地址
    private String mCurrentPhotoPath;
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        initRecyclerView();
        initClass();


        //安装 apk


    }

    /**
     * @param apkFile apk 文件
     */
    public void installApk(File apkFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (Build.VERSION.SDK_INT >= 24) {
            FileProvider7.setIntentDataAndType(this,
                    intent, "application/vnd.android.package-archive", apkFile, true);
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile),
                    "application/vnd.android.package-archive");
        }

        startActivity(intent);
    }

    //拍照
    public void takePhotoNoCompress(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            String filename = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.CHINA)
                    .format(new Date()) + ".png";
            //file 最后照片会保存在这个文件中
            File file = new File(Environment.getExternalStorageDirectory(), filename);
            mCurrentPhotoPath = file.getAbsolutePath();
            Uri fileUri = null;
            if (Build.VERSION.SDK_INT >= 24) {
                fileUri = FileProvider7.getUriForFile(this, file);
            } else {
                fileUri = Uri.fromFile(file);
            }
            List<ResolveInfo> resInfoList = getPackageManager()
                    .queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                grantUriPermission(packageName, fileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                        | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO);
        }
    }


    private void initClass() {
        mClasses.add(PermissionsActivity.class);
        mClasses.add(TakePictureActivity.class);
        mClasses.add(BluetoothActivity.class);
        mClasses.add(MyNDKActivity.class);
        mClasses.add(AlbumActivity.class);
        mClasses.add(SlidinguppanelActivity.class);
        mClasses.add(UIActivity.class);
        mClasses.add(ExoPlayerActivity.class);
        mClasses.add(BaseDialogActivity.class);
        mClasses.add(RetrofitUtilsActivity.class);
        mClasses.add(PopActivity.class);
        mClasses.add(StarActivity.class);
        // mClasses.add(RecylerActivity.class);
        // mClasses.add(DataBaseActivity.class);
        mClasses.add(DiffUtilActivity.class);
        mClasses.add(AnimatorActivity.class);

        mClasses.add(PicActivity.class);
        mClasses.add(FingerprintActivity.class);
        mClasses.add(SpeechActivity.class);
        mClasses.add(MPAndroidChartActivity.class);
        mClasses.add(HelloChartActivity.class);
        mClasses.add(CombinationActivity.class);
        mClasses.add(StickyActivity.class);
        mClasses.add(StepViewActivity.class);
        mClasses.add(SignDemoActivity.class);
        mClasses.add(FlowlayoutActivity.class);
        mClasses.add(ViewModeActivity.class);

        mClasses.add(DragActivity.class);
        mClasses.add(DragFlowLayoutActivity.class);
        mClasses.add(DragRecylerViewActivity.class);
        mClasses.add(LayoutManagerActivity.class);
        LogUtils.e("wmz", "nishi1");
        //使用线程池
        ExecutorService executorService = ThreadUtil.newDynamicSingleThreadedExecutor((r) -> {
            Thread thread = new Thread(r);
            thread.setName("AlivcEdit Thread");
            return thread;
        });

        executorService.execute(() -> {
        });

    }


    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerViewAdapter = new RecyclerViewAdapter();
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(RecyclerView.HORIZONTAL, 1, CommonUtils.getColor(this, R.color.red)));
        List<String> list = Arrays.asList(data);
        mRecyclerViewAdapter.setNewData(list);
        mItemTouchHelper = new ItemTouchHelper(new MyItemTouchHelperCallback(mRecyclerViewAdapter));
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerViewAdapter.setDragListener(this);
        mRecyclerViewAdapter.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        CommonUtils.startActivity(this, mClasses.get(position));
        //ToastUtils.showSafeToast(this,"新toast");

    }

    boolean isClick = true;

    @OnClick({R.id.bt, R.id.et_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt:
                isClick = !isClick;
                etAddress.setIsClick(isClick);
                etAddress.setIsShowArrow(isClick);
                break;
            case R.id.et_address:
                ToastUtils.showUiToast(this, "点击了");
                break;
        }
    }


    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        //通知ItemTouchHelper开始拖拽
        mItemTouchHelper.startDrag(viewHolder);
    }
}
