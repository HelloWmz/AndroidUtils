package com.example.admin.androidutils.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.androidutils.R;
import com.example.admin.androidutils.bean.DiffUtilBean;

import java.util.List;

/**
 * @ProjectName AndroidUtils
 * @CreateDate: 2020/5/21
 * @Author: wmz
 * @Description:
 */
public class StickyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private final List<DiffUtilBean> datas;

    public StickyAdapter(Context context, List<DiffUtilBean> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new ContentVH(LayoutInflater.from(mContext).inflate(R.layout.item_news_letter, parent, false));
        }
        return new TitleVH(LayoutInflater.from(mContext).inflate(R.layout.adapter_title, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            DiffUtilBean newsContentModel = datas.get(position);

            if (holder instanceof ContentVH) {
                ((ContentVH) holder).bindData(newsContentModel);
            }
            if (holder instanceof TitleVH) {
                ((TitleVH) holder).bindData(newsContentModel);
            }
        }catch (IndexOutOfBoundsException e) {

        }
    }
    @Override
    public int getItemViewType(int position) {
        return datas.get(position).itemType;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    static class TitleVH extends RecyclerView.ViewHolder {

        TextView tittle;

        public TitleVH(View itemView) {
            super(itemView);
            tittle = (TextView) itemView.findViewById(R.id.tittle);
            itemView.setTag(true);
        }

        public void bindData(DiffUtilBean value) {
            tittle.setText(value.name);

        }

    }

    static class ContentVH extends RecyclerView.ViewHolder {


        TextView tv;


        public ContentVH(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
            itemView.setTag(false);
        }

        public void bindData(final DiffUtilBean value) {
            tv.setText(value.name);
        }

    }
}
