package com.nxt.hnfeedstudy.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.nxt.hnfeedstudy.MyConstant;
import com.nxt.hnfeedstudy.R;
import com.nxt.hnfeedstudy.http.HttpManager;
import com.nxt.hnfeedstudy.update.MyUpdateDialog;
import com.nxt.hnfeedstudy.utils.DialogTools;


import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import nxt.com.ssllibrary.mytools.AppVersion;
import nxt.com.ssllibrary.mytools.DoubleUtils;
import nxt.com.ssllibrary.mytools.JSONTool;
import nxt.com.ssllibrary.mytools.NetworkTool;
import nxt.com.ssllibrary.update.MVersionInfo;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{


    private static final int RC_EXTERNAL_STORAGE = 0x04;//存储权限
    private int locationVC;
    private String locationVName;
    private boolean permissionOk = false;

    @Override
    public int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        ll_left.setVisibility(View.GONE);
        tv_title.setText("饲料现场审核学习版");
        iv_right.setImageResource(R.drawable.setting);
        iv_right.setVisibility(View.VISIBLE);
        ll_right.setVisibility(View.VISIBLE);
    }

    @Override
    public void myBindView(Bundle savedInstanceState) {
        initTitle();
        locationVC = AppVersion.getVersionCode(this);
        locationVName = AppVersion.getVersionName(this);

        requestExternalStorage();
        getNetData();

    }

    private void getNetData() {

        if(!NetworkTool.isNetWorkConnected(this)) return;

        mMap.clear();
        mMap.put("id", MyConstant.UPDATE_ID);
        Call<ResponseBody> call = HttpManager.getRetrofit().HttpVesionUpdate(mMap);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response == null || response.body() == null ) {
                    return;
                }
                String myString = null;
                try {
                    myString = response.body().string().toString();
                    System.out.println("mstring++"+ myString);
                    JSONObject object = JSONTool.parseFromJson(myString);
                    String stat = JSONTool.getJsonString(object, "upstat");
                    if(stat != null &&  "1".equals(stat)){
                        int versionCode = JSONTool.getJsonInt(object, "bbh");
                        if(versionCode > locationVC){
                            MVersionInfo info = new MVersionInfo();
                            info.setNewVCode(versionCode);
                            info.setLocalVCode(locationVC);
                            info.setNewVName(JSONTool.getJsonString(object, "bbmc"));
                            info.setVersionContent(JSONTool.getJsonString(object, "gxnr"));
                            info.setVersionUrl(JSONTool.getJsonString(object, "xzdz"));
                            info.setLocalVName(locationVName);
                            new MyUpdateDialog(MainActivity.this).showDialog(info);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                return;
            }
        });

    }

//    1：浓缩饲料、配合饲料、精料补充料   2：添加剂预混合饲料   3：饲料添加剂  4：混合型饲料添加剂  5：单一饲料

    @OnClick({R.id.cv_home_1, R.id.cv_home_2, R.id.cv_home_3, R.id.cv_home_4, R.id.cv_home_5})
    public void onViewClicked(View view) {

        if(DoubleUtils.isFastDoubleClick()) return;

        if(!permissionOk){
            requestExternalStorage();
            return;
        }

        switch (view.getId()) {
            case R.id.ll_right:
                startActivity(new Intent(this,AboutUsActivity.class));
                break;
            case R.id.cv_home_1:
                startActivity(new Intent(this,MainSceneActivity.class)
                .putExtra("sceneTypeName","浓缩饲料、配合饲料、精料补充料")
                .putExtra("companyType",1)
                .putExtra("from_stype",0));
                break;
            case R.id.cv_home_2:
                startActivity(new Intent(this,MainSceneActivity.class)
                        .putExtra("sceneTypeName","添加剂预混合饲料")
                        .putExtra("companyType",2)
                        .putExtra("from_stype",0));
                break;
            case R.id.cv_home_3:
                startActivity(new Intent(this,MainSceneActivity.class)
                        .putExtra("sceneTypeName","饲料添加剂")
                        .putExtra("companyType",3)
                        .putExtra("from_stype",0));
                break;
            case R.id.cv_home_4:
                startActivity(new Intent(this,MainSceneActivity.class)
                        .putExtra("sceneTypeName","混合型饲料添加剂")
                        .putExtra("companyType",4)
                        .putExtra("from_stype",0));
                break;
            case R.id.cv_home_5:
                startActivity(new Intent(this,MainSceneActivity.class)
                        .putExtra("sceneTypeName","单一饲料")
                        .putExtra("companyType",5)
                        .putExtra("from_stype",0));
                break;
        }
    }


    @AfterPermissionGranted(RC_EXTERNAL_STORAGE)
    public void requestExternalStorage() {
        String[] perms = {Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            permissionOk = true;
        } else {
            EasyPermissions.requestPermissions(this, "请授予本APP相应权限", RC_EXTERNAL_STORAGE, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        DialogTools.INSTANCE.createSimpleDialog(MainActivity.this, "需要开启您手机的存储权限才能下载安装，是否现在开启", "去开启", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
//                DialogTools.INSTANCE.cancelAlertDialog();
            }
        });


        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

}
