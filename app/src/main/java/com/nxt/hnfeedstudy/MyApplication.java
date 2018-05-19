package com.nxt.hnfeedstudy;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

import com.bumptech.glide.request.target.ViewTarget;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nxt.com.ssllibrary.mytools.AppManager;
import nxt.com.ssllibrary.mytools.JSONTool;

/**
 * Created by Administrator on 2016/9/5.
 */
public class MyApplication extends Application {

    public static MyApplication instance;
    private String  nickname,
            firstImg,area,
            roleName,realName;
    private String KSOK,//考试数据
                  XCOK;//现场数据是否已经上传

    private String BKPEOPLE;
    private String BKOK;

    private int is_zhuzhang,//  1是组长
            is_manager;  // 1  管理员  0 是普通专家  3 补考管理

    //专家类型
    private int expert_stype;// 1 地市级科长  2 检化验  3 工艺

    public String pc_code,pc_code_id;//批次号 和 id

//    public List<ExamResultInfo> examRecordList;
    public String strList;



    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //glide 加载图片时
        ViewTarget.setTagId(R.id.glide_tag);

    }
    public static MyApplication getInstance() {
        return instance;
    }



    public void setIs_zhuzhang(int is_zhuzhang) {
        this.is_zhuzhang = is_zhuzhang;
    }

    public int getIs_manager() {
        return is_manager;
    }

    public void setIs_manager(int is_manager) {
        this.is_manager = is_manager;
    }

    public int getExpert_stype() {
        return expert_stype;
    }

    public void setExpert_stype(int expert_stype) {
        this.expert_stype = expert_stype;
    }





    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }


//    public void setCompanyInfoList(List<CompanyInfo> companyInfoList){
//        mCompanyInfoList = companyInfoList;
//    }






    public void clearAllSp() {
        this.pc_code = "";
        this.pc_code_id = "";
        strList = "";
        this.KSOK = "";
        this.XCOK = "";
        SharedPreferences preferences = instance.getSharedPreferences(
                MyConstant.APP_SP, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    public void saveSharedPreferences(String key, String v){
        SharedPreferences preferences = instance.getSharedPreferences(
                MyConstant.APP_SP, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, v);
        editor.commit();
    }

    public String getSharedPreferences(String key){
        SharedPreferences preferences = instance.getSharedPreferences(
                MyConstant.APP_SP, MODE_PRIVATE);
        String v = preferences.getString(key, "");
        return v;
    }

}
