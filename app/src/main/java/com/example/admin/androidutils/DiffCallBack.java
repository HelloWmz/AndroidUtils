package com.example.admin.androidutils;

import android.support.v7.util.DiffUtil;

import com.example.admin.androidutils.bean.DiffUtilBean;

import java.util.List;

/**
 * Created by Admin on 2018/9/19.
 */

public class DiffCallBack extends DiffUtil.Callback {
  private final List<DiffUtilBean> moldDiffUtilBeans;
  private final List<DiffUtilBean> mnewDiffUtilBeans;

  public DiffCallBack(List<DiffUtilBean> oldDiffUtilBeans, List<DiffUtilBean> newDiffUtilBeans) {
    this.moldDiffUtilBeans = oldDiffUtilBeans;
    this.mnewDiffUtilBeans = newDiffUtilBeans;
  }

  @Override
  public int getOldListSize() {
    return moldDiffUtilBeans.size();
  }

  @Override
  public int getNewListSize() {
    return mnewDiffUtilBeans.size();
  }

  @Override
  public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
    return moldDiffUtilBeans.get(oldItemPosition).getClass().equals(mnewDiffUtilBeans.get(newItemPosition).getClass());
  }

  @Override
  public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
    if (moldDiffUtilBeans.get(oldItemPosition).name.equals(mnewDiffUtilBeans.get(newItemPosition).name)) {
      if (moldDiffUtilBeans.get(oldItemPosition).age == mnewDiffUtilBeans.get(oldItemPosition).age) {
        if (moldDiffUtilBeans.get(oldItemPosition).sex.equals(mnewDiffUtilBeans.get(newItemPosition).sex)) {
          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    } else {
      return false;
    }
  }
}
