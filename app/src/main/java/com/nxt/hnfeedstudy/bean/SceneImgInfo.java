package com.nxt.hnfeedstudy.bean;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.luck.picture.lib.entity.LocalMedia;


import java.util.ArrayList;
import java.util.List;

import nxt.com.ssllibrary.mytools.JSONTool;

/**
 * Created by 三石磊 on 2017/6/19.
 */

public class SceneImgInfo {

    private String id;
    private String business_id;//企业id
    private String expert_id;//专家id
    private String test_id;//试题的id

    private String img_yt,//图片的原图
    img_ys,//压缩图片
    img_jq;//剪切的图片
    private String img_conclusion;//审核结论 1 符合 2 不符合 3 不涉及
    private String img_mark;//备注

    private List<LocalMedia> imgList;//图片
    private List<LocalMedia> imgListNo;//部分符合的图片
    private int is_ok = 0;//是否已经作答
    private String img_content;//审核内容


    public String getImg_mark() {
        return img_mark;
    }

    public void setImg_mark(String img_mark) {
        this.img_mark = img_mark;
    }

    public String getImg_conclusion() {
        return img_conclusion;
    }

    public void setImg_conclusion(String img_conclusion) {
        this.img_conclusion = img_conclusion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public String getExpert_id() {
        return expert_id;
    }

    public void setExpert_id(String expert_id) {
        this.expert_id = expert_id;
    }

    public String getTest_id() {
        return test_id;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }

    public String getImg_yt() {
        return img_yt;
    }

    public void setImg_yt(String img_yt) {
        this.img_yt = img_yt;
    }

    public String getImg_ys() {
        return img_ys;
    }

    public void setImg_ys(String img_ys) {
        this.img_ys = img_ys;
    }

    public String getImg_jq() {
        return img_jq;
    }

    public void setImg_jq(String img_jq) {
        this.img_jq = img_jq;
    }

    public List<LocalMedia> getImgList() {
        return imgList;
    }

    public void setImgList(List<LocalMedia> imgList) {
        this.imgList = imgList;
    }

    public List<LocalMedia> getImgListNo() {
        return imgListNo;
    }

    public void setImgListNo(List<LocalMedia> imgListNo) {
        this.imgListNo = imgListNo;
    }

    public int getIs_ok() {
        return is_ok;
    }

    public void setIs_ok(int is_ok) {
        this.is_ok = is_ok;
    }

    public String getImg_content() {
        return img_content;
    }

    public void setImg_content(String img_content) {
        this.img_content = img_content;
    }

    public static List<SceneImgInfo> parse(JSONObject peopleObject){
        List<SceneImgInfo> peopleList = new ArrayList<SceneImgInfo>();
        try {
            JSONArray peopleArray = JSONTool.getJsonArry(peopleObject, "mSceneImgInfoList");
            for(int y=0;y<peopleArray.size();y++) {
                JSONObject obj = peopleArray.getJSONObject(y);
                SceneImgInfo peopleInfo = new SceneImgInfo();
                peopleInfo.setBusiness_id(JSONTool.getJsonString(obj, "business_id"));

                peopleInfo.setExpert_id(JSONTool.getJsonString(obj, "expert_id"));
                peopleInfo.setTest_id(JSONTool.getJsonString(obj, "test_id"));

                peopleInfo.setImg_conclusion(JSONTool.getJsonString(obj, "img_conclusion"));
                peopleInfo.setImg_mark(JSONTool.getJsonString(obj, "img_mark"));
                peopleInfo.setImg_content(JSONTool.getJsonString(obj, "img_content"));

                List<LocalMedia> imgList = parseImg(obj);
                peopleInfo.setImgList(imgList);

                List<LocalMedia> imgListNo = parseImgNO(obj);
                peopleInfo.setImgListNo(imgListNo);

                peopleInfo.setIs_ok(JSONTool.getJsonInt(obj, "is_ok"));
                peopleList.add(peopleInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return peopleList;
    }





    private  static List<LocalMedia> parseImg(JSONObject peopleObject){
        List<LocalMedia> peopleList = new ArrayList<LocalMedia>();
        try {
            JSONArray peopleArray = JSONTool.getJsonArry(peopleObject, "imgList");
            for(int y=0;y<peopleArray.size();y++) {
                JSONObject obj = peopleArray.getJSONObject(y);
                LocalMedia peopleInfo = new LocalMedia();
                peopleInfo.setCompressPath(JSONTool.getJsonString(obj, "compressPath"));

                peopleInfo.setCompressed(JSONTool.getJsonBoolean(obj, "compressed"));
                peopleInfo.setDuration(JSONTool.getJsonInt(obj, "duration"));

                peopleInfo.setHeight(JSONTool.getJsonInt(obj, "height"));
                peopleInfo.setChecked(JSONTool.getJsonBoolean(obj, "isChecked"));

                peopleInfo.setCut(JSONTool.getJsonBoolean(obj, "isCut"));
                peopleInfo.setCutPath(JSONTool.getJsonString(obj, "cutPath"));

                peopleInfo.setMimeType(JSONTool.getJsonInt(obj, "mimeType"));
                peopleInfo.setNum(JSONTool.getJsonInt(obj, "num"));
                peopleInfo.setPath(JSONTool.getJsonString(obj, "path"));
                peopleInfo.setPictureType(JSONTool.getJsonString(obj, "pictureType"));
                peopleInfo.setPosition(JSONTool.getJsonInt(obj, "position"));
                peopleInfo.setWidth(JSONTool.getJsonInt(obj, "width"));
                peopleList.add(peopleInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return peopleList;
    }

    private  static List<LocalMedia> parseImgNO(JSONObject peopleObject){
        List<LocalMedia> peopleList = new ArrayList<LocalMedia>();
        try {
            JSONArray peopleArray = JSONTool.getJsonArry(peopleObject, "imgListNo");
            for(int y=0;y<peopleArray.size();y++) {
                JSONObject obj = peopleArray.getJSONObject(y);
                LocalMedia peopleInfo = new LocalMedia();
                peopleInfo.setCompressPath(JSONTool.getJsonString(obj, "compressPath"));

                peopleInfo.setCompressed(JSONTool.getJsonBoolean(obj, "compressed"));
                peopleInfo.setDuration(JSONTool.getJsonInt(obj, "duration"));

                peopleInfo.setHeight(JSONTool.getJsonInt(obj, "height"));
                peopleInfo.setChecked(JSONTool.getJsonBoolean(obj, "isChecked"));

                peopleInfo.setCut(JSONTool.getJsonBoolean(obj, "isCut"));
                peopleInfo.setCutPath(JSONTool.getJsonString(obj, "cutPath"));

                peopleInfo.setMimeType(JSONTool.getJsonInt(obj, "mimeType"));
                peopleInfo.setNum(JSONTool.getJsonInt(obj, "num"));
                peopleInfo.setPath(JSONTool.getJsonString(obj, "path"));
                peopleInfo.setPictureType(JSONTool.getJsonString(obj, "pictureType"));
                peopleInfo.setPosition(JSONTool.getJsonInt(obj, "position"));
                peopleInfo.setWidth(JSONTool.getJsonInt(obj, "width"));
                peopleList.add(peopleInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return peopleList;
    }



}
