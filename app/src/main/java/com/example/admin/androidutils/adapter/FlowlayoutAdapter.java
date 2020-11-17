package com.example.admin.androidutils.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.androidutils.R;
import com.example.admin.androidutils.bean.Contact;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.HashMap;
import java.util.Set;

/**
 * @ProjectName AndroidUtils
 * @CreateDate: 2020/8/13
 * @Author: wmz
 * @Description:
 */
public class FlowlayoutAdapter extends BaseQuickAdapter<Contact, BaseViewHolder> {
    private OnTagSelectListener mOnTagSelectListener;

    private HashMap<Integer, Set<Integer>> maps = new HashMap<>();


    public FlowlayoutAdapter() {
        super(R.layout.rv_item);
    }

    //设置是否有以前有选中
    public void setSelectData(HashMap<Integer, Set<Integer>> map) {
        this.maps = map;
    }

    public interface OnTagSelectListener {
        void onSelected(HashMap<Integer, Set<Integer>> map);
    }

    public void setOnSelectListener(OnTagSelectListener onSelectListener) {
        mOnTagSelectListener = onSelectListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, Contact item) {
        int layoutPosition = helper.getLayoutPosition();
        TagFlowLayout tagFlowLayout = helper.getView(R.id.id_flowlayout);
        MyTagAdaper adapter = new MyTagAdaper(item.datas);
        tagFlowLayout.setAdapter(adapter);
        if (maps != null) {
            for (Integer integer : maps.keySet()) {
                if (layoutPosition == integer) {
                    adapter.setSelectedList(maps.get(integer));
                }
            }
        }
        //点击标签时回调
        tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                maps.put(layoutPosition,selectPosSet);
                if (mOnTagSelectListener != null) {
                    mOnTagSelectListener.onSelected(maps);
                }
            }
        });

    }
}
