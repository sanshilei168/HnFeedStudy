package com.nxt.hnfeedstudy.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nxt.hnfeedstudy.MyApplication;
import com.nxt.hnfeedstudy.R;
import com.nxt.hnfeedstudy.bean.SceneImgInfo;
import com.nxt.hnfeedstudy.bean.SceneTestInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 三石磊 on 2017/6/13.
 */

public class GvSceneAdapter extends BaseAdapter {

    private Context mContext;
    private int currentNu;
    private List<SceneImgInfo> listData;
    private ArrayList<SceneTestInfo> listInfo;
    public GvSceneAdapter(Context context, int currentNu, List<SceneImgInfo> listData, ArrayList<SceneTestInfo> listInfo) {
        this.mContext = context;
        this.currentNu = currentNu;
        this.listData = listData;
        this.listInfo = listInfo;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView == null ){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gv_exam,parent,false);
            holder.mTv = (TextView)convertView.findViewById(R.id.tv_exam_select);
            holder.mLl_exam_item = (LinearLayout)convertView.findViewById(R.id.ll_exam_item);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        SceneImgInfo mInfo = listData.get(position);

//        if(position == currentNu){//当前试题
//            holder.mTv.setBackground(ContextCompat.getDrawable(mContext,R.drawable.select_now));
//            holder.mTv.setTextColor(ContextCompat.getColor(mContext,R.color.white));
//        }else{
//            if(mInfo.getIs_ok() == 1){
//                holder.mTv.setBackground(ContextCompat.getDrawable(mContext,R.drawable.select_ok));
//                holder.mTv.setTextColor(ContextCompat.getColor(mContext,R.color.white));
//            }else{
//                holder.mTv.setBackground(ContextCompat.getDrawable(mContext,R.drawable.select_no));
//                holder.mTv.setTextColor(ContextCompat.getColor(mContext,R.color.txt_black));
//            }
//        }

        if(listInfo.get(position).getAuditrole() != null &&  "kz".equals(listInfo.get(position).getAuditrole())){
            holder.mLl_exam_item.setBackgroundColor(ContextCompat.getColor(mContext,R.color.bg_orange));
        }else if(listInfo.get(position).getAuditrole() != null &&  "gyzj".equals(listInfo.get(position).getAuditrole())){
            holder.mLl_exam_item.setBackgroundColor(ContextCompat.getColor(mContext,R.color.bg_blue));
        } else if(listInfo.get(position).getAuditrole() != null &&  "jhyzj".equals(listInfo.get(position).getAuditrole())){
            holder.mLl_exam_item.setBackgroundColor(ContextCompat.getColor(mContext,R.color.bg_green));
        }

//        if(MyApplication.getInstance().getExpert_stype() == 1){
//            if(mInfo.getIs_ok() == 1){//组长
//                if(!TextUtils.isEmpty(mInfo.getImg_conclusion()) && "2".equals(mInfo.getImg_conclusion())){
//                holder.mTv.setBackground(ContextCompat.getDrawable(mContext,R.drawable.answer_error_small));
//                }else {
//                holder.mTv.setBackground(ContextCompat.getDrawable(mContext,R.drawable.answer_ok_small));
//                }
//                holder.mTv.setTextColor(ContextCompat.getColor(mContext,R.color.white));
//            }else if(listInfo.get(position).getAuditrole() != null &&  "kz".equals(listInfo.get(position).getAuditrole())){
//                holder.mTv.setBackground(ContextCompat.getDrawable(mContext,R.drawable.select_ok));
//                holder.mTv.setTextColor(ContextCompat.getColor(mContext,R.color.txt_black));
//            }else{
//                holder.mTv.setBackground(ContextCompat.getDrawable(mContext,R.drawable.select_now));
//                holder.mTv.setTextColor(ContextCompat.getColor(mContext,R.color.white));
//            }
//        }else if(MyApplication.getInstance().getExpert_stype() == 2){//检化验
//            if(mInfo.getIs_ok() == 1){//组长
//                if(!TextUtils.isEmpty(mInfo.getImg_conclusion()) && "2".equals(mInfo.getImg_conclusion())){
//                    holder.mTv.setBackground(ContextCompat.getDrawable(mContext,R.drawable.answer_error_small));
//                }else {
//                    holder.mTv.setBackground(ContextCompat.getDrawable(mContext,R.drawable.answer_ok_small));
//                }
//                holder.mTv.setTextColor(ContextCompat.getColor(mContext,R.color.white));
//            }else if(listInfo.get(position).getAuditrole() != null &&  "jhyzj".equals(listInfo.get(position).getAuditrole())){
//                holder.mTv.setBackground(ContextCompat.getDrawable(mContext,R.drawable.select_ok));
//                holder.mTv.setTextColor(ContextCompat.getColor(mContext,R.color.txt_black));
//            }else{
//                holder.mTv.setBackground(ContextCompat.getDrawable(mContext,R.drawable.select_now));
//                holder.mTv.setTextColor(ContextCompat.getColor(mContext,R.color.white));
//            }
//        }else if(MyApplication.getInstance().getExpert_stype() == 3){//工艺
//            if(mInfo.getIs_ok() == 1){//组长
//                if(!TextUtils.isEmpty(mInfo.getImg_conclusion()) && "2".equals(mInfo.getImg_conclusion())){
//                    holder.mTv.setBackground(ContextCompat.getDrawable(mContext,R.drawable.answer_error_small));
//                }else {
//                    holder.mTv.setBackground(ContextCompat.getDrawable(mContext,R.drawable.answer_ok_small));
//                }
//                holder.mTv.setTextColor(ContextCompat.getColor(mContext,R.color.white));
//            }else if(listInfo.get(position).getAuditrole() != null &&  "gyzj".equals(listInfo.get(position).getAuditrole())){
//                holder.mTv.setBackground(ContextCompat.getDrawable(mContext,R.drawable.select_ok));
//                holder.mTv.setTextColor(ContextCompat.getColor(mContext,R.color.txt_black));
//            }else{
//                holder.mTv.setBackground(ContextCompat.getDrawable(mContext,R.drawable.select_now));
//                holder.mTv.setTextColor(ContextCompat.getColor(mContext,R.color.white));
//            }
//        }else {//其他

            if(mInfo.getIs_ok() == 1){//已答
                if(!TextUtils.isEmpty(mInfo.getImg_conclusion()) && "2".equals(mInfo.getImg_conclusion())){
                    holder.mTv.setBackground(ContextCompat.getDrawable(mContext,R.drawable.answer_error_small));
                }else {
                    holder.mTv.setBackground(ContextCompat.getDrawable(mContext,R.drawable.answer_ok_small));
                }
                holder.mTv.setTextColor(ContextCompat.getColor(mContext,R.color.white));
            }else{
                holder.mTv.setBackground(ContextCompat.getDrawable(mContext,R.drawable.select_now));
                holder.mTv.setTextColor(ContextCompat.getColor(mContext,R.color.white));
            }

//        }

        holder.mTv.setText(position+1+"");

        return convertView;
    }


   private class ViewHolder{
       private TextView mTv;
       private LinearLayout mLl_exam_item;
   }

}
