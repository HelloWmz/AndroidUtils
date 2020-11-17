package com.example.admin.androidutils.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.admin.androidutils.R;
import com.example.admin.androidutils.RecyclerViewAdapter;
import com.example.admin.androidutils.utils.CommonUtils;
import com.example.admin.androidutils.utils.DividerItemDecoration;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 2018/5/15.
 */

public class BluetoothActivity extends AppCompatActivity implements BaseQuickAdapter.OnItemClickListener {

  private static final String TAG = "BluetoothActivity";


  public BluetoothAdapter mBluetoothAdapter;
  public static final String[] perms = {
       Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
  @BindView(R.id.recyclerView_bluetooth)
  RecyclerView mRecyclerViewBluetooth;
  private RecyclerViewAdapter mRecyclerViewAdapter;
  private ArrayList<BluetoothDevice> searchDevicesList = new ArrayList<>();
  private ArrayList<String> devicesList = new ArrayList<>();
  // 固定的UUID
  final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bluetooth);
    ButterKnife.bind(this);
    if (!AndPermission.hasPermission(this, perms)) {
      AndPermission.with(this)
           .requestCode(200)
           .permission(perms)
           .callback(this)
           .start();
    } else {
      initBluetooth();
    }
    initRecylerView();
  }

  private void initRecylerView() {
    mRecyclerViewBluetooth.setLayoutManager(new LinearLayoutManager(this));
    mRecyclerViewBluetooth.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    mRecyclerViewBluetooth.addItemDecoration(new DividerItemDecoration(RecyclerView.HORIZONTAL, 1, CommonUtils.getColor(this,R.color.colorAccent) ));
    mRecyclerViewAdapter = new RecyclerViewAdapter();
    mRecyclerViewBluetooth.setAdapter(mRecyclerViewAdapter);
    mRecyclerViewAdapter.setNewData(devicesList);
    mRecyclerViewAdapter.setOnItemClickListener(this);
  }

  @PermissionYes(200)
  private void getPermissionYes(List<String> grantedPermissions) {
    // TODO 申请权限成功
    Toast.makeText(this, "申请成功", Toast.LENGTH_LONG).show();
    initBluetooth();
  }

  @PermissionNo(200)
  private void getPermissionNo(List<String> deniedPermissions) {
    Toast.makeText(this, "申请失败", Toast.LENGTH_LONG).show();

  }

  private void initBluetooth() {
    //获取蓝牙设配
    mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    //判断蓝牙是否可用
    if (!mBluetoothAdapter.isEnabled()) {
      Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
      startActivity(intent);
    } else {
      if (mBluetoothAdapter.getScanMode() !=
           BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
        Intent discoverableIntent = new Intent(
             BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(
             BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 200);
        startActivity(discoverableIntent);
      }
    }
    IntentFilter filter = new IntentFilter();
    //发现设备
    filter.addAction(BluetoothDevice.ACTION_FOUND);
    //设备连接状态改变
    filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
    //蓝牙设备状态改变
    filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
    //搜索完成
    filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
    registerReceiver(mBluetoothReceiver, filter);
    mBluetoothAdapter.startDiscovery();
  }


  private BroadcastReceiver mBluetoothReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      // 获得已经搜索到的蓝牙设备,每扫描到一个设备，系统都会发送此广播。
      if (BluetoothDevice.ACTION_FOUND.equals(action)) {
        //获取蓝牙设备
        BluetoothDevice scanDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        if (scanDevice == null || scanDevice.getName() == null) return;
        Log.d(TAG, "name=" + scanDevice.getName() + "address=" + scanDevice.getAddress());
        if (scanDevice.getBondState() != BluetoothDevice.BOND_BONDED) {
          // 防止重复添加
          if (searchDevicesList.indexOf(scanDevice) == -1) {
            searchDevicesList.add(scanDevice);
            devicesList.add("未配对 | " + scanDevice.getName());
            mRecyclerViewAdapter.notifyDataSetChanged();
          }
        }
        //搜索完成
      } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
        Toast.makeText(BluetoothActivity.this, "搜索完成", Toast.LENGTH_LONG).show();
        //蓝牙状态改变的广播
      } else if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
        // 获取查找到的蓝牙设备
        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        String name = device.getName();
        // 如果查找到的设备符合要连接的设备，处理
        if (device.getName().equalsIgnoreCase(name)) {
          int connectState = device.getBondState();
          switch (connectState) {
            case BluetoothDevice.BOND_NONE:  //10
              Toast.makeText(BluetoothActivity.this, "取消配对：" + device.getName(), Toast.LENGTH_SHORT).show();
              break;
            case BluetoothDevice.BOND_BONDING:  //11
              Toast.makeText(BluetoothActivity.this, "正在配对：" + device.getName(), Toast.LENGTH_SHORT).show();
              break;
            //配对成功
            case BluetoothDevice.BOND_BONDED:   //12
              Toast.makeText(BluetoothActivity.this, "完成配对：" + device.getName(), Toast.LENGTH_SHORT).show();
              try {
                // 连接 并传输数据
                new ClientThread(device).start();
              } catch (Exception e) {
                Log.e(TAG, e.toString());
              }
              break;
          }
        }
      }
    }
  };


  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mBluetoothReceiver != null) {
      unregisterReceiver(mBluetoothReceiver);
    }
  }

  @SuppressLint("HandlerLeak")
  private Handler handler = new Handler() {
    public void handleMessage(Message msg) {
      Toast.makeText(BluetoothActivity.this, String.valueOf(msg.obj),
           Toast.LENGTH_LONG).show();
      super.handleMessage(msg);
    }
  };

  @Override
  public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    BluetoothDevice device = searchDevicesList.get(position);
    int connectState = device.getBondState();
    switch (connectState) {
      // 未配对
      case BluetoothDevice.BOND_NONE:
        // 配对蓝牙
        try {
          Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
          createBondMethod.invoke(device);
        } catch (Exception e) {
          e.printStackTrace();
        }
        break;
      // 已配对
      case BluetoothDevice.BOND_BONDED:
        // 连接
        new ClientThread(device).start();
        break;
    }
  }

  //蓝牙连接线程
  private class ClientThread extends Thread {
    private BluetoothDevice device;

    public ClientThread(BluetoothDevice device) {
      this.device = device;
    }

    @Override
    public void run() {
      BluetoothSocket socket;
      try {
        socket = device.createRfcommSocketToServiceRecord(UUID.fromString(SPP_UUID));
        Log.d(TAG, "连接服务端...");
        socket.connect();
        //客户端传输数据
        OutputStream outputStream = socket.getOutputStream();
        if (outputStream != null) {
          outputStream.write("StartOnNet\n".getBytes());
          outputStream.flush();
          outputStream.close();
        }

        Log.d(TAG, "连接建立.");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  //接受消息
  public class AcceptThread extends Thread {
    private BluetoothServerSocket serverSocket;
    private BluetoothSocket socket;
    private InputStream is;
    private final String NAME = "Bluetooth_Socket";


    public AcceptThread() {
      try {
        UUID uuid = UUID.fromString(SPP_UUID);
        serverSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, uuid);
      } catch (Exception e) {
        // TODO: handle exception
      }
    }

    @Override
    public void run() {
      super.run();
      try {
        socket = serverSocket.accept();
        is = socket.getInputStream();
        while (true) {
          byte[] buffer = new byte[128];
          int count = is.read(buffer);
          Message msg = new Message();
          msg.obj = new String(buffer, 0, count, "utf-8");
          handler.sendMessage(msg);
        }
      } catch (Exception e) {
        // TODO: handle exception
      }
    }
  }
}
