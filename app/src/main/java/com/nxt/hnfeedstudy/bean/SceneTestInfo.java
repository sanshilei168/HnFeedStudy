package com.nxt.hnfeedstudy.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 三石磊 on 2017/6/19.
 * 现场审核的试题实体类
 */

public class SceneTestInfo implements Parcelable {
    private String id; //id
    private String pid;// 父级id编号
    private String item;// 审核项目

    private String seriaNumber;// 序号

    private String content;// 审核内容

    private String process;// 审核方式

    private String require;// 审核要求

    private String photograph;// 是否拍照 0代表拍照  1代表不拍照

    private String hint;// 提示
    private Integer type; //审核饲料的类型  1：浓缩饲料、配合饲料、精料补充料   2：添加剂预混合饲料   3：饲料添加剂  4：混合型饲料添加剂  5：单一饲料
    private String indate;//录入时间
    private String modifyDate;//修改时间
    private String userName;//用户的账号
    private String auditrole;//那个级别进行答题



    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }


    public String getSeriaNumber() {
        return seriaNumber;
    }

    public void setSeriaNumber(String seriaNumber) {
        this.seriaNumber = seriaNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getRequire() {
        return require;
    }

    public void setRequire(String require) {
        this.require = require;
    }

    public String getPhotograph() {
        return photograph;
    }

    public void setPhotograph(String photograph) {
        this.photograph = photograph;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIndate() {
        return indate;
    }

    public void setIndate(String indate) {
        this.indate = indate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getAuditrole() {
        return auditrole;
    }

    public void setAuditrole(String auditrole) {
        this.auditrole = auditrole;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.pid);
        dest.writeString(this.item);
        dest.writeString(this.seriaNumber);
        dest.writeString(this.content);
        dest.writeString(this.process);
        dest.writeString(this.require);
        dest.writeString(this.photograph);
        dest.writeString(this.hint);
        dest.writeValue(this.type);
        dest.writeString(this.indate);
        dest.writeString(this.modifyDate);
        dest.writeString(this.userName);
        dest.writeString(this.auditrole);
    }

    public SceneTestInfo() {
    }

    protected SceneTestInfo(Parcel in) {
        this.id = in.readString();
        this.pid = in.readString();
        this.item = in.readString();
        this.seriaNumber = in.readString();
        this.content = in.readString();
        this.process = in.readString();
        this.require = in.readString();
        this.photograph = in.readString();
        this.hint = in.readString();
        this.type = (Integer) in.readValue(Integer.class.getClassLoader());
        this.indate = in.readString();
        this.modifyDate = in.readString();
        this.userName = in.readString();
        this.auditrole = in.readString();
    }

    public static final Creator<SceneTestInfo> CREATOR = new Creator<SceneTestInfo>() {
        @Override
        public SceneTestInfo createFromParcel(Parcel source) {
            return new SceneTestInfo(source);
        }

        @Override
        public SceneTestInfo[] newArray(int size) {
            return new SceneTestInfo[size];
        }
    };
}
