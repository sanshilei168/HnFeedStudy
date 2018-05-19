package com.nxt.hnfeedstudy.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nxt.hnfeedstudy.R;

import butterknife.BindView;
import nxt.com.ssllibrary.mytools.AppVersion;


/**
 * 关于我们
 */

public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.tv_version)
    TextView mTv_version;

    @Override
    public int getContentLayout() {
        return R.layout.activity_about_us;
    }


    @Override
    protected void initTitle() {
        super.initTitle();
        tv_title.setText("关于我们");
        ll_left.setVisibility(View.VISIBLE);
    }

    @Override
    public void myBindView(Bundle savedInstanceState) {
        initTitle();
        mTv_version.setText("版本号："+ AppVersion.getVersionName(this));
    }
}
