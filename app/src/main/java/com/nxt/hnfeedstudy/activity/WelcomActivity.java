package com.nxt.hnfeedstudy.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.nxt.hnfeedstudy.R;


public class WelcomActivity extends AppCompatActivity {
	private static final String TAG = "WelcomActivity";
	private static final int sleepTime = 3000;
	private boolean isFirst;
	private SharedPreferences sp;
	private Intent intent;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
					// 进入主页面
						intent = new Intent(WelcomActivity.this,
								MainActivity.class);

				new Handler().postDelayed(new Runnable() {
					public void run() {
						startActivity(intent);
						finish();
					}
				}, sleepTime);
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		handler.sendEmptyMessage(1);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

}
