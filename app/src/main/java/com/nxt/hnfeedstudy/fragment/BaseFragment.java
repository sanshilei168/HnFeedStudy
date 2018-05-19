package com.nxt.hnfeedstudy.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nxt.hnfeedstudy.R;
import com.nxt.hnfeedstudy.inter.IBase;

import butterknife.ButterKnife;

/**
 * Created by 三石磊 on 2017/6/6.
 */

public abstract class BaseFragment extends Fragment implements IBase {

    protected View mRootView;

    protected Context mContext;
    private InputMethodManager manager;//软键盘管理
    protected ImageView iv_left, iv_right;
    protected TextView tv_left, tv_title, tv_right, tv_show;
    protected LinearLayout ll_left, ll_right, ll_all;
    protected RelativeLayout rl_title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        } else {
            mRootView = createView(inflater, container, savedInstanceState);
        }
        mContext = mRootView.getContext();
        return mRootView;
}

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getContentLayout(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myBindView(savedInstanceState);
    }


    @Nullable
    @Override
    public View getView() {
        return mRootView;
    }


    protected void initTitle() {
        rl_title = (RelativeLayout) getView().findViewById(R.id.rl_title);
        ll_left = (LinearLayout) getView().findViewById(R.id.ll_left);
        ll_right = (LinearLayout) getView().findViewById(R.id.ll_right);
        iv_left = (ImageView) getView().findViewById(R.id.iv_left);
        tv_left = (TextView) getView().findViewById(R.id.tv_left);
        tv_title = (TextView) getView().findViewById(R.id.tv_title);
        tv_show = (TextView) getView().findViewById(R.id.tv_show);
        tv_right = (TextView) getView().findViewById(R.id.tv_right);
        iv_right = (ImageView) getView().findViewById(R.id.iv_right);

        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }


    /*
          * 短的toast
          */
    protected void showShortToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
    /*
     * 长的toast
     */
    protected void showLongToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    /*
    隐藏软件盘
    */
    protected void hideKeyboard() {
        if(manager == null){
            manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getActivity().getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


}
