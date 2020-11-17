package com.example.admin.androidutils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.admin.androidutils.adapter.FlowlayoutAdapter;
import com.example.admin.androidutils.bean.Contact;

import java.util.*;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ProjectName AndroidUtils
 * @CreateDate: 2020/8/13
 * @Author: wmz
 * @Description:
 */
public class FlowlayoutActivity extends AppCompatActivity implements FlowlayoutAdapter.OnTagSelectListener {

    @BindView(R.id.rv)
    RecyclerView mRv;
    private FlowlayoutAdapter mFlowlayoutAdapter;
    List<Contact> outs = new ArrayList<>();
    List<String> mSelectlist = new ArrayList<>();
    HashMap<Integer, Set<Integer>> map = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowlayout);
        ButterKnife.bind(this);
        initRv();
        initData();

    }

    private void initData() {
        for (int i = 0; i < 5; i++) {
            Contact contact = new Contact();
            List<String> list = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                list.add(i + "-" + j);
            }
            contact.datas = list;
            outs.add(contact);
        }
        mFlowlayoutAdapter.setNewData(outs);
        Set<Integer> item1 = new HashSet<>();
        item1.add(1);
        item1.add(3);
        Set<Integer> item2 = new HashSet<>();
        item2.add(0);
        item2.add(4);
        item2.add(2);
        item2.add(1);
        map.put(0, item1);
        map.put(3, item2);
    }

    private void initRv() {
        mRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mFlowlayoutAdapter = new FlowlayoutAdapter();
        mRv.setAdapter(mFlowlayoutAdapter);
        mFlowlayoutAdapter.setOnSelectListener(this);
        mFlowlayoutAdapter.setSelectData(map);
    }

    @Override
    public void onSelected(HashMap<Integer, Set<Integer>> map) {
        mSelectlist.clear();
        List<Integer> keyList = getSortSet(map.keySet());
        for (Integer outposition : keyList) {
            List<Integer> valueList = getSortSet(map.get(outposition));
            for (Integer item : valueList) {
                mSelectlist.add(outs.get(outposition).datas.get(item));
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : mSelectlist) {
            stringBuilder.append(s).append("---");
        }
        Log.e("test", stringBuilder.toString());
    }


    private List<Integer> getSortSet(Set<Integer> integers) {
        List<Integer> list = new ArrayList<>(integers);
        Collections.sort(list);
        return list;
    }

}
