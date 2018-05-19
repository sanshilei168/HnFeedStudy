package com.nxt.hnfeedstudy.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nxt.hnfeedstudy.MyConstant;
import com.nxt.hnfeedstudy.R;
import com.nxt.hnfeedstudy.http.HttpService;
import com.nxt.hnfeedstudy.update.ProgressListener;
import com.nxt.hnfeedstudy.update.ProgressResponseBody;
import com.nxt.hnfeedstudy.utils.FileUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import nxt.com.ssllibrary.update.ApkUpdateUtils;
import nxt.com.ssllibrary.update.MVersionInfo;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by 三石磊 on 2017/9/13.
 * apk 下载进度监听
 */

public class MyUpdateApkActivity extends Activity {


//    @BindView(R.id.tv_nu)
    TextView mTvNu;
//    @BindView(R.id.pb)
    ProgressBar progressBar;

    private String apkPath ;
    private MVersionInfo mInfo;
    private String apkUrl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去除标题  必须在setContentView()方法之前调用
        setContentView(R.layout.activity_update_apk);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN); // 设置全屏

        mTvNu = (TextView)findViewById(R.id.tv_nu);
        progressBar = (ProgressBar)findViewById(R.id.pb);
        apkPath= FileUtils.getRootFilePath()+ MyConstant.FILE_NAME+".apk";
        mInfo = (MVersionInfo)getIntent().getSerializableExtra("mInfo");
        apkUrl = mInfo.getVersionUrl();
        File file = new File(apkPath);
        if (file.exists()) {
            Uri uri = Uri.fromFile(file);
            if (uri != null) {
                if (ApkUpdateUtils.compare(ApkUpdateUtils.getApkInfo(MyUpdateApkActivity.this, uri.getPath()), MyUpdateApkActivity.this)) {
                    ApkUpdateUtils.startInstall(this, uri);
                    finish();
                    return;
                } else {
                    FileUtils.deleteFile(apkPath);
                }
            }
        }
        if(apkUrl != null){
            getApk();
        }
    }

    private void getApk() {
        String baseurl = "http://appupdate.agridoor.com.cn/";
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(baseurl);
        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                return response.newBuilder().body(new ProgressResponseBody(response.body(),
                                new ProgressListener() {
                                    @Override
                                    public void onProgress(long progress, long total, boolean done) {
//                                        Log.i("LC", total + "----->" + progress);
                                        final int result = (int) (100 * progress / total);
//                                方法二：
                                DownloadTask task = new DownloadTask();
                                task.execute(result);

//                                方法三：
//                                Message message = mHandler.obtainMessage();
//                                message.arg1 = result;
//                                mHandler.sendMessage(message);
                                    }
                                })
                ).build();
            }
        }).build();
        HttpService api = builder.client(client).build().create(HttpService.class);
        Call<ResponseBody> call = api.HttpUpdateApk(apkUrl);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                    String mpath = "";
                try {
                    if(response == null){
                        Toast.makeText(MyUpdateApkActivity.this,"没有找到更新内容！", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }

                    InputStream is = response.body().byteStream();

                    File file = new File(apkPath);
                     mpath = file.getPath();
                    FileOutputStream fos = new FileOutputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[1024];
                    int i = 0;
                    while ((i = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, i);
                        fos.flush();
                    }
                    fos.close();
                    is.close();
                    bis.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Uri uri = Uri.fromFile(new File(apkPath));
                ApkUpdateUtils.startInstall(MyUpdateApkActivity.this,uri);
                finish();
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MyUpdateApkActivity.this,"下载失败,请重新进行更新！", Toast.LENGTH_SHORT).show();
            finish();
            }
        });
    }

    class DownloadTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            mTvNu.setText(integer + "%");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            publishProgress(params[0]);
            return params[0];
        }
    }

    @Override
    public void onBackPressed() {
    }

}
