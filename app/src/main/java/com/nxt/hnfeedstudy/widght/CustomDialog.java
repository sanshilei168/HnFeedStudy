package com.nxt.hnfeedstudy.widght;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.TextView;

import com.nxt.hnfeedstudy.R;


/**
 * 自定义透明的dialog
 */
public class CustomDialog extends Dialog {
    private String content;
    private TextView tv_content;

    public CustomDialog(Context context) {
        super(context, R.style.CustomDialog);
        initView();
    }


    public CustomDialog(Context context, String content) {
        super(context, R.style.CustomDialog);
        this.content=content;
        initView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                if(CustomDialog.this.isShowing())
                    CustomDialog.this.dismiss();
                break;
        }
        return true;
    }

    private void initView(){
        setContentView(R.layout.dialog_view);
        tv_content =(TextView)findViewById(R.id.tvcontent);
        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.alpha=0.9f;
        getWindow().setAttributes(attributes);
        setCancelable(false);
    }

    public void setMsg(String content){
        tv_content.setText(content);
    }


}