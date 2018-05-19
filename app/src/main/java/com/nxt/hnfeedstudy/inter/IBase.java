package com.nxt.hnfeedstudy.inter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
*created by 三石磊 on 2017/6/6
*/
public interface IBase {
    //主布局
    int getContentLayout();
    //初始化布局 和 ButterKnife
    View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    //得到布局主控件
    View getView();
    //数据处理
    void myBindView(Bundle savedInstanceState);

}
