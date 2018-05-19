package nxt.com.ssllibrary.update;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;

/**
 * Created by chiclaim on 2016/05/18
 */
public class ApkUpdateUtils {
    public static final String TAG = ApkUpdateUtils.class.getSimpleName();

    private static final String KEY_DOWNLOAD_ID = "downloadId";

    public static void download(Context context, String url, String title) {
        long downloadId = SpUtils.getInstance(context).getLong(KEY_DOWNLOAD_ID, -1L);
        if (downloadId != -1L) {
            FileDownloadManager fdm = FileDownloadManager.getInstance(context);
            int status = fdm.getDownloadStatus(downloadId);
            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                //启动更新界面
//                Uri uri = fdm.getDownloadUri(downloadId);
                Uri uri = Uri.parse(fdm.getDownloadPath(downloadId));
                if (uri != null) {
                    if (compare(getApkInfo(context, uri.getPath()), context)) {
                        startInstall(context, uri);
                        return;
                    } else {
                        fdm.getDm().remove(downloadId);
                    }
                }
                start(context, url, title);
            } else if (status == DownloadManager.STATUS_FAILED) {
                start(context, url, title);
            } else {

                Log.d(TAG, "apk is already downloading");
            }
        } else {
            start(context, url, title);
        }
    }

    private static void start(Context context, String url, String title) {
        long id = FileDownloadManager.getInstance(context).startDownload(url,
                title, "下载完成后点击打开");
        SpUtils.getInstance(context).putLong(KEY_DOWNLOAD_ID, id);
//        Log.d(TAG, "apk start download " + id);
        myListener(context,id);
    }

    private static void myListener(Context context,  final long Id) {  
   	 final FileDownloadManager fdm = FileDownloadManager.getInstance(context);
       // 注册广播监听系统的下载完成事件。  
       IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);  
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {  
           @Override  
           public void onReceive(Context context, Intent intent) {  
               long ID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);  
               if (ID == Id) {  
//                   Toast.makeText(context.getApplicationContext(), "任务:" + Id + " 下载完成!", Toast.LENGTH_LONG).show();
//               	   Uri uri = fdm.getDownloadUri(Id);
//                   startInstall(context, uri);

//                   String fileName = "/storage/emulated/0/Download/" + SslConstant.FILE_NAME+"_update.apk";
                   Uri uri = Uri.parse(fdm.getDownloadPath(Id));
                   startInstall(context, uri);
               }
           }  
       }; 
       context.registerReceiver(broadcastReceiver, intentFilter);  
   }  
    
    
    public static void startInstall(Context context, Uri uri) {
        Intent install = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File file = new File(uri.getPath());//更新包文件
            // Android7.0及以上版本
            Log.d("-->最新apk下载完毕","Android N及以上版本");
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            install.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            String authority=  context.getPackageName() + ".provider";
            Uri contentUri = FileProvider.getUriForFile(context, authority, file);//参数二:应用包名+".fileProvider"(和步骤二中的Manifest文件中的provider节点下的authorities对应)
            install.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            // Android7.0以下版本
            Log.d("-->最新apk下载完毕","Android N以下版本");
            install.setDataAndType(uri, "application/vnd.android.package-archive");
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(install);
    }


    /**
     * 获取apk程序信息[packageName,versionName...]
     *
     * @param context Context
     * @param path    apk path
     */
    public static PackageInfo getApkInfo(Context context, String path) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            //String packageName = info.packageName;
            //String version = info.versionName;
            //Log.d(TAG, "packageName:" + packageName + ";version:" + version);
            //String appName = pm.getApplicationLabel(appInfo).toString();
            //Drawable icon = pm.getApplicationIcon(appInfo);//得到图标信息
            return info;
        }
        return null;
    }


    /**
     * 下载的apk和当前程序版本比较
     *
     * @param apkInfo apk file's packageInfo
     * @param context Context
     * @return 如果当前应用版本小于apk的版本则返回true
     */
    public static boolean compare(PackageInfo apkInfo, Context context) {
        if (apkInfo == null) {
            return false;
        }
        String localPackage = context.getPackageName();
        if (apkInfo.packageName.equals(localPackage)) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(localPackage, 0);
                if (apkInfo.versionCode > packageInfo.versionCode) {
                    return true;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


}


