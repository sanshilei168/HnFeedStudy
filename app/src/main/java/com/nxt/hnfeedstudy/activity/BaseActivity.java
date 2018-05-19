package com.nxt.hnfeedstudy.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.nxt.hnfeedstudy.R;
import com.nxt.hnfeedstudy.inter.IBase;
import com.nxt.hnfeedstudy.widght.CustomDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import butterknife.ButterKnife;
import nxt.com.ssllibrary.mytools.AppManager;
import nxt.com.ssllibrary.mytools.BarUtils;
import nxt.com.ssllibrary.mytools.ContextUtils;
import nxt.com.ssllibrary.mytools.PermissionUtils;

/**
 * Created by 三石磊 on 2017/6/6.
 */

public abstract class BaseActivity extends AppCompatActivity implements IBase {

    private View mRootView;
    protected ImageView iv_left, iv_right;
    protected TextView tv_left, tv_title, tv_right, tv_show;
    protected LinearLayout ll_left, ll_right, ll_all;
    protected RelativeLayout rl_title;


    private InputMethodManager manager;//软键盘管理
    private CustomDialog progressDialog;//请求进度条
    protected HashMap<String,String> mMap;

    private static final String TAG = "BaseActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        int uiFlags =
//                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
//                 View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN | //hide statusBar
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION; //hide navigationBar
//        getWindow().getDecorView().setSystemUiVisibility(uiFlags);

        AppManager.getAppManager().addActivity(this);

        mRootView = createView(null, null, savedInstanceState);//初始化布局 和 ButterKnife
        setContentView(mRootView);
        BarUtils.setColor(this, Color.parseColor("#008cd5"), 0);
        mMap = new HashMap<>();
        myBindView(savedInstanceState);//
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = ContextUtils.inflate(this, getContentLayout());
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public View getView() {
        return mRootView;
    }


    protected void initTitle() {
        rl_title = (RelativeLayout) findViewById(R.id.rl_title);
        ll_left = (LinearLayout) findViewById(R.id.ll_left);
        ll_right = (LinearLayout) findViewById(R.id.ll_right);
        iv_left = (ImageView) findViewById(R.id.iv_left);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_show = (TextView) findViewById(R.id.tv_show);
        tv_right = (TextView) findViewById(R.id.tv_right);
        iv_right = (ImageView) findViewById(R.id.iv_right);

        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBackPressed();
            }
        });
    }

    /*
   * 短的toast
   */
    protected void showShortToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /*
     * 长的toast
     */
    protected void showLongToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /*
   隐藏软件盘
    */
    protected void hideKeyboard() {
        if (manager == null) {
            manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 权限回调Handler
     */
    private PermissionHandler mHandler;

    /**
     * 请求权限
     *
     * @param permissions 权限列表
     * @param handler     回调
     */
    protected void requestPermission(String[] permissions, PermissionHandler handler) {
        if (PermissionUtils.hasSelfPermissions(this, permissions)) {
            handler.onGranted();
        } else {
            mHandler = handler;
            ActivityCompat.requestPermissions(this, permissions, 001);
        }
    }


    /**
     * 权限请求结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (mHandler == null) return;
        if (PermissionUtils.verifyPermissions(grantResults)) {
            mHandler.onGranted();
        } else {
            if (!PermissionUtils.shouldShowRequestPermissionRationale(this, permissions)) {
                if (!mHandler.onNeverAsk()) {
                    Toast.makeText(this, "权限已被拒绝,请在设置-应用-权限中打开", Toast.LENGTH_SHORT).show();
                }
            } else {
                mHandler.onDenied();
            }
        }
    }


    /**
     * 权限回调接口
     */
    public abstract class PermissionHandler {
        /**
         * 权限通过
         */
        public abstract void onGranted();

        /**
         * 权限拒绝
         */
        public void onDenied() {
        }

        /**
         * 不再询问
         *
         * @return 如果要覆盖原有提示则返回true
         */
        public boolean onNeverAsk() {
            return false;
        }
    }

    /*
    * 展示提交或者加载数据的进度框
    */
    public void showProgressDialog(String method) {

        if (progressDialog == null) {
            progressDialog = new CustomDialog(this);
        }
        String progressDialogMessage = "";
        if (!progressDialog.isShowing()) {
            if (method.equals("get"))
                progressDialogMessage = getResources().getString(R.string.get);
            else if (method.equals("upload"))
                progressDialogMessage = getResources().getString(R.string.upload);
            else
                progressDialogMessage = method;
            progressDialog.setMsg(progressDialogMessage);
            progressDialog.show();
        }
    }

    /*
     * 隐藏对话框
	 */
    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog.cancel();
        }
    }

    public void full(boolean enable) {//导航栏占用空间 导航栏不占用空间
        if (enable) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @Override
    public void onBackPressed() {
        hideBottomUIMenu();
        super.onBackPressed();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }


    /**
     * 处理EditText 之外隐藏软件盘
     *
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }


    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


}
