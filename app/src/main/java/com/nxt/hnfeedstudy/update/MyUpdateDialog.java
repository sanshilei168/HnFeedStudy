package com.nxt.hnfeedstudy.update;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nxt.hnfeedstudy.activity.MyUpdateApkActivity;
import com.nxt.hnfeedstudy.db.DBSceneManager;

import java.util.List;

import nxt.com.ssllibrary.update.MVersionInfo;

public class MyUpdateDialog implements OnClickListener {

	private Context context;
	private String url;
	private TextView umeng_update_content;
	private Button umeng_update_id_ok;
	private Button umeng_update_id_cancel;
	private AlertDialog dialog;

	public MyUpdateDialog(Context context){
		this.context = context;
	}

	private MVersionInfo mInfo;
	public void showDialog(MVersionInfo info){
		this.mInfo = info;
		this.url = info.getVersionUrl();
		LayoutInflater inflaterDl = LayoutInflater.from(context);
		RelativeLayout layout = (RelativeLayout)inflaterDl.inflate(nxt.com.ssllibrary.R.layout.umeng_update_dialog, null );

		umeng_update_content = (TextView)layout.findViewById(nxt.com.ssllibrary.R.id.umeng_update_content);
		
		umeng_update_id_ok = (Button)layout.findViewById(nxt.com.ssllibrary.R.id.umeng_update_id_ok);
		umeng_update_id_cancel = (Button)layout.findViewById(nxt.com.ssllibrary.R.id.umeng_update_id_cancel);
		umeng_update_id_ok.setOnClickListener(this);
		umeng_update_id_cancel.setOnClickListener(this);
		String content = info.getNewVName()+" 替换 "+info.getLocalVName()+"\n"+info.getVersionContent();
		
		umeng_update_content.setText(content);
		
		
		//对话框
	    dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		dialog.setCancelable(false);
		dialog.getWindow().setContentView(layout);
		dialog.setCanceledOnTouchOutside(false); 
	}

	@Override
	public void onClick(View v) {
		int i = v.getId();
		if (i == nxt.com.ssllibrary.R.id.umeng_update_id_ok) {
			if (!canDownloadState()) {
				Toast.makeText(context, "下载服务没有开启,请您启用", Toast.LENGTH_SHORT).show();
				showDownloadSetting();
				return;
			}
			DBSceneManager.getInstance(context).deleteFile();
			context.startActivity(new Intent(context, MyUpdateApkActivity.class).putExtra("mInfo",mInfo));
			if (dialog != null) {
				dialog.dismiss();
				dialog = null;
			}
//			ApkUpdateUtils.download(context.getApplicationContext(), url, context.getResources().getString(R.string.app_name));

		} else if (i == nxt.com.ssllibrary.R.id.umeng_update_id_cancel) {
			if (dialog != null) {
				dialog.dismiss();
				dialog = null;
			}

		} else {
		}
	}

	 private boolean canDownloadState() {
	        try {
	            int state = context.getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");

	            if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
	                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
	                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
	                return false;
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	        return true;
	    }
	 
	 private void showDownloadSetting() {
	        String packageName = "com.android.providers.downloads";
	        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
	        intent.setData(Uri.parse("package:" + packageName));
	        if (intentAvailable(intent)) {
	            context.startActivity(intent);
	        }
	    }
	 
	 private boolean intentAvailable(Intent intent) {
	        PackageManager packageManager = context.getPackageManager();
	        List list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
	        return list.size() > 0;
	    }

}
