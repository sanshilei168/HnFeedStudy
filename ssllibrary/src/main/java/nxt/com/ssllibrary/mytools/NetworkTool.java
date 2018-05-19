package nxt.com.ssllibrary.mytools;

import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author Administrator
 *         <p>
 *         网络公共类
 */
public class NetworkTool {

    /*
     * 网络是否可用
     */
    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = manager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }

        return false;
    }

    /*
     * 是否为数据链接
     */
    public static boolean isNetWorkMobile(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mobileInfo = manager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mobileInfo != null && mobileInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }

    /*
     * 是否为WiFi
     */
    public static boolean isNetWorkWifi(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiInfo = manager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifiInfo != null && wifiInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }

    /*
     * 打开网络设置界面
     */
    public static void openSetting(final Context context){
//		 Intent intent=null;
//         //判断手机系统的版本  即API大于10 就是3.0或以上版本
//         if(android.os.Build.VERSION.SDK_INT>10){
//             intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
//         }else{
//             intent = new Intent();
//             ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
//             intent.setComponent(component);
//             intent.setAction("android.intent.action.VIEW");
//         }
//         activity.startActivity(intent);

        //提示对话框
        Builder builder = new Builder(context);
        builder.setTitle("网络设置提示").setMessage("网络连接不可用,是否进行设置?").setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = null;
                //判断手机系统的版本  即API大于10 就是3.0或以上版本
                if (android.os.Build.VERSION.SDK_INT > 10) {
                    intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                } else {
                    intent = new Intent();
                    ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
                    intent.setComponent(component);
                    intent.setAction("android.intent.action.VIEW");
                }
                context.startActivity(intent);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();

    }
}
