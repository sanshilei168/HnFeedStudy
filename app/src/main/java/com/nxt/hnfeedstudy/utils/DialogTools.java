package com.nxt.hnfeedstudy.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.tools.ScreenUtils;
import com.nxt.hnfeedstudy.MyApplication;
import com.nxt.hnfeedstudy.R;


/**
 * Created By LiuChaoya
 * On 2017/12/21
 * For NXT
 */
public enum DialogTools {
    INSTANCE;

    private Dialog alertDialog;

    public Dialog createAlertDialog(Context context, String msg, String cancleBtnMsg, String sureBtnMsg, View.OnClickListener canclelistener, View.OnClickListener surelistener) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_common_dialog, null);// 得到加载view
        RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.alertdialog);
        TextView tv_title = (TextView) v.findViewById(R.id.tvPromptMsg);
        Button btnCancle = (Button) v.findViewById(R.id.btnCancle);
        Button btnSure = (Button) v.findViewById(R.id.btnSure);
        btnCancle.setVisibility(View.VISIBLE);
        btnCancle.setText(cancleBtnMsg);
        btnSure.setText(sureBtnMsg);
        tv_title.setText(msg);
        btnCancle.setOnClickListener(canclelistener);
        btnSure.setOnClickListener(surelistener);
        alertDialog = new Dialog(context, R.style.textDialogStyle);// 创建自定义样式dialog
        alertDialog.setCancelable(true);// 可以用“返回键”取消
        alertDialog.setContentView(layout);// 设置布局
        alertDialog.show();
        return alertDialog;

    }

    private Dialog simpleDialog;

    public Dialog createSimpleDialog(Context context, String msg, String sureBtnMsg, View.OnClickListener surelistener) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_common_dialog, null);// 得到加载view
        RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.alertdialog);
        TextView tv_title = (TextView) v.findViewById(R.id.tvPromptMsg);
        Button btnCancle = (Button) v.findViewById(R.id.btnCancle);
        Button btnSure = (Button) v.findViewById(R.id.btnSure);
        btnCancle.setVisibility(View.GONE);
        btnSure.setText(sureBtnMsg);
        tv_title.setText(msg);
        btnSure.setOnClickListener(surelistener);
        simpleDialog = new Dialog(context, R.style.textDialogStyle);// 创建自定义样式dialog
        simpleDialog.setCancelable(true);// 可以用“返回键”取消
        simpleDialog.setContentView(layout);// 设置布局
        simpleDialog.show();
        return simpleDialog;

    }

    public void cancelAlertDialog() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    public void cancelSimpleDialog() {
        if (simpleDialog != null) {
            simpleDialog.dismiss();
        }
    }

    private static Toast toast;

    /**
     * @param context  上下文
     * @param content  显示内容，可文本，可资源id
     * @param duration 时长
     * @param type
     */
    //由于实现沉浸式状态栏，导致系统自带的Toast出现问题，所以自定义Toast的View
    public void showToast(Context context, Object content, int duration, ContentType type) {
        if (context == null) {
            return;
        }
        if (toast == null) {
            Context applicationContext = MyApplication.instance; //防止内存泄漏
            toast = new Toast(applicationContext);
            toast.setView(View.inflate(context, R.layout.layout_toast, null));
        }
        toast.setGravity(Gravity.CENTER, 0, (int) ScreenUtils.dip2px(context, 0));
        toast.setDuration(duration);
        if (type == ContentType.RESID) {
            toast.setText((int) content);
        } else {
            toast.setText((String) content);
        }
        toast.show();
    }

    public enum ContentType {
        TEXT, RESID
    }
}
