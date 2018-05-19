package com.nxt.hnfeedstudy.fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.decoration.RecycleViewDivider;
import com.luck.picture.lib.entity.LocalMedia;
import com.nxt.hnfeedstudy.MyApplication;
import com.nxt.hnfeedstudy.R;
import com.nxt.hnfeedstudy.activity.MainSceneActivity;
import com.nxt.hnfeedstudy.adapter.GridImageAdapter;
import com.nxt.hnfeedstudy.bean.SceneImgInfo;
import com.nxt.hnfeedstudy.bean.SceneTestInfo;
import com.nxt.hnfeedstudy.widght.FullyGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 三石磊 on 2017/6/14.
 * 现场考察内容页面
 */

public class SceneFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.noScrollview)
    NestedScrollView mnoScrollview;

    @BindView(R.id.tv_scene_zt)
    TextView mTv_sceneZt;

    @BindView(R.id.rv_scene_add_img)
    RecyclerView mRv_sceneAddImg;
    @BindView(R.id.ll_scene_pic)
    LinearLayout mLl_scenePic;
    @BindView(R.id.tv_scene_content)
    TextView mTv_sceneContent;
    @BindView(R.id.tv_scene_fs)
    TextView mTv_sceneFs;
    @BindView(R.id.tv_scene_yq)
    TextView mTv_sceneYq;
    @BindView(R.id.tv_scene_ts)
    TextView mTv_sceneTs;
//    @BindView(R.id.et_scene_pl)
//    EditText mEt_scenePl;
    @BindView(R.id.et_scene_bz)
EditText mEt_sceneBz;
    @BindView(R.id.ll_scene_ts)
    LinearLayout mLl_sceneTs;

    @BindView(R.id.rg_select)
    RadioGroup mRg_select;
    @BindView(R.id.rb_select_1)
    RadioButton mRb_select1;
    @BindView(R.id.rb_select_2)
    RadioButton mRb_select2;
    @BindView(R.id.rb_select_3)
    RadioButton mRb_select3;
    @BindView(R.id.rb_select_4)
    RadioButton mRb_select4;

    @BindView(R.id.iv_must_full)
    ImageView mIv_mustFull;
    @BindView(R.id.iv_must_full_img)
    ImageView mIv_mustFullImg;


    @BindView(R.id.ll_select_ok)
    LinearLayout mLl_selectOk;

    @BindView(R.id.ll_scene_pic2)
    LinearLayout mLl_scenePic2;
    @BindView(R.id.rv_scene_add_img2)
    RecyclerView mRv_sceneAddImg2;

    @BindView(R.id.tv_scene_chack)
    TextView mTv_sceneChack;//



    private TextView mTv_takePic, mTv_getPic, mTv_picCancle;

    private static final String KEY_POSITION = "SceneFragment:POSITION";
    private int maxSelectNum = 20;
    private PopupWindow popupWindow;
    private GridImageAdapter gvAdapter,gvAdapter2;
    private List<LocalMedia> selectList = new ArrayList<>();
    private List<LocalMedia> selectList2 = new ArrayList<>();
    private int mPosition;
    private SceneImgInfo mInfo;
    private String mBz = "";
    private SceneTestInfo mSceneTestInfo;
    private int mSelectIndex = -1;
    private int picIndex = 0;


    @Override
    public int getContentLayout() {
        return R.layout.fragment_scene;
    }

//    public SceneFragment(int position) {
//        super();
//        mPosition = position;
//    }

    public static final SceneFragment newInstance(int position)
    {
        SceneFragment fragment = new SceneFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        fragment.setArguments(bundle);
        return fragment ;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_POSITION, mPosition);
    }

    @Override
    public void myBindView(Bundle savedInstanceState) {

        if ((savedInstanceState != null)
                && savedInstanceState.containsKey(KEY_POSITION)) {
            mPosition = savedInstanceState.getInt(KEY_POSITION);
        }else{
        mPosition=getArguments().getInt("position");
        }

         mSceneTestInfo = MainSceneActivity.getInstance().listInfo.get(mPosition);
        mInfo = MainSceneActivity.getInstance().listData.get(mPosition);

        if (mInfo.getImgList() != null && mInfo.getImgList().size() > 0) {
            selectList = mInfo.getImgList();
        }

        if (mInfo.getImgListNo() != null && mInfo.getImgListNo().size() > 0) {
            selectList2 = mInfo.getImgListNo();
        }
        if(mSceneTestInfo.getAuditrole() != null &&  "kz".equals(mSceneTestInfo.getAuditrole())){
            mTv_sceneZt.setText(Html.fromHtml(mSceneTestInfo.getItem()+"<font><small>(地科)</small></font>"));
        }else if(mSceneTestInfo.getAuditrole() != null &&  "gyzj".equals(mSceneTestInfo.getAuditrole())){
            mTv_sceneZt.setText(Html.fromHtml(mSceneTestInfo.getItem()+"<font><small>(工艺)</small></font>"));
        }else if(mSceneTestInfo.getAuditrole() != null &&  "jhyzj".equals(mSceneTestInfo.getAuditrole())){
            mTv_sceneZt.setText(Html.fromHtml(mSceneTestInfo.getItem()+"<font><small>(检化验)</small></font>"));
        }else {
        mTv_sceneZt.setText(mSceneTestInfo.getItem());
        }

        mTv_sceneContent.setText(mSceneTestInfo.getContent());
        mTv_sceneFs.setText(mSceneTestInfo.getProcess());
        mTv_sceneYq.setText(mSceneTestInfo.getSeriaNumber()+" "+ mSceneTestInfo.getRequire());


        if(mSceneTestInfo.getHint() == null || "".equals(mSceneTestInfo.getHint())){
            mLl_sceneTs.setVisibility(View.GONE);
        }else{
            mLl_sceneTs.setVisibility(View.VISIBLE);
            mTv_sceneTs.setText(mSceneTestInfo.getHint());
        }
        //拍照
//        if(mSceneTestInfo.getPhotograph() != null && "0".equals(mSceneTestInfo.getHint())){
            FullyGridLayoutManager manager = new FullyGridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
            mRv_sceneAddImg.setLayoutManager(manager);
            gvAdapter = new GridImageAdapter(getActivity(), onAddPicClickListener);
            gvAdapter.setList(selectList);
            gvAdapter.setSelectMax(maxSelectNum);
            mRv_sceneAddImg.setAdapter(gvAdapter);

            mRv_sceneAddImg.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 20, ContextCompat.getColor(getActivity(), R.color.white)));

        FullyGridLayoutManager manager2 = new FullyGridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        mRv_sceneAddImg2.setLayoutManager(manager2);
        gvAdapter2 = new GridImageAdapter(getActivity(), onAddPicClickListener2);
        gvAdapter2.setList(selectList2);
        gvAdapter2.setSelectMax(maxSelectNum);
        mRv_sceneAddImg2.setAdapter(gvAdapter2);

        mRv_sceneAddImg2.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 20, ContextCompat.getColor(getActivity(), R.color.white)));


        mRv_sceneAddImg.setNestedScrollingEnabled(false);
        mRv_sceneAddImg2.setNestedScrollingEnabled(false);

//        }
        chackInfo();
        initEvent();
        if(mInfo.getImg_conclusion() != null && !"".equals(mInfo.getImg_conclusion())){
            mSelectIndex = Integer.parseInt(mInfo.getImg_conclusion());
//            selectRb(mSelectIndex);
            if(mSelectIndex == 1){
                mRb_select1.setChecked(true);
                mRb_select1.setClickable(true);
                mRb_select1.setEnabled(true);
            }else if(mSelectIndex == 2){
                mRb_select2.setChecked(true);
                mRb_select2.setClickable(true);
                mRb_select2.setEnabled(true);
            }else if(mSelectIndex == 3){
                mRb_select3.setChecked(true);
                mRb_select3.setClickable(true);
                mRb_select3.setEnabled(true);
            }else if(mSelectIndex == 4){
                mRb_select4.setChecked(true);
                mRb_select4.setClickable(true);
                mRb_select4.setEnabled(true);
            }
        }else{
            mLl_selectOk.setVisibility(View.GONE);
        }

        if(mInfo.getImg_mark() != null){
            mEt_sceneBz.setText(mInfo.getImg_mark());
        }
    }

    private void initEvent() {

        gvAdapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                LocalMedia media = selectList.get(position);
                String pictureType = media.getPictureType();
                int mediaType = PictureMimeType.pictureToVideo(pictureType);
                switch (mediaType) {
                    case 1:
                        // 预览图片
                        PictureSelector.create(SceneFragment.this).externalPicturePreview(position, selectList);
                        break;
                    case 2:
                        // 预览视频
                        PictureSelector.create(SceneFragment.this).externalPictureVideo(media.getPath());
                        break;
                }
            }
        });

        gvAdapter2.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                LocalMedia media = selectList2.get(position);
                String pictureType = media.getPictureType();
                int mediaType = PictureMimeType.pictureToVideo(pictureType);
                switch (mediaType) {
                    case 1:
                        // 预览图片
                        PictureSelector.create(SceneFragment.this).externalPicturePreview(position, selectList2);
                        break;
                    case 2:
                        // 预览视频
                        PictureSelector.create(SceneFragment.this).externalPictureVideo(media.getPath());
                        break;
                }
            }
        });

        if(MainSceneActivity.getInstance().from_stype == 1){//查看
            mRg_select.setClickable(false);
            mRg_select.setEnabled(false);
            disableRadioGroup(mRg_select);
            mRg_select.setFocusable(false);
            mRg_select.setFocusableInTouchMode(false);
            mEt_sceneBz.setFocusable(false);
            mEt_sceneBz.setFocusableInTouchMode(false);
        }else{//编辑

//            //地市级科长
//            if(MyApplication.getInstance().getExpert_stype() == 1 &&
//                    mSceneTestInfo.getAuditrole() != null &&  "kz".equals(mSceneTestInfo.getAuditrole())
//                    ){
//                mRg_select.setClickable(true);
//                mRg_select.setEnabled(true);
//                enableRadioGroup(mRg_select);
//                mRg_select.setFocusable(true);
//                mRg_select.setFocusableInTouchMode(true);
//
//                //检化验
//            }else if(MyApplication.getInstance().getExpert_stype() == 2 &&
//                    mSceneTestInfo.getAuditrole() != null &&  "jhyzj".equals(mSceneTestInfo.getAuditrole())){//检化验
//                mRg_select.setClickable(true);
//                mRg_select.setEnabled(true);
//                enableRadioGroup(mRg_select);
//                mRg_select.setFocusable(true);
//                mRg_select.setFocusableInTouchMode(true);
//                // 工艺
//            }else if(MyApplication.getInstance().getExpert_stype() == 3 &&
//            mSceneTestInfo.getAuditrole() != null &&  "gyzj".equals(mSceneTestInfo.getAuditrole())){//工艺
//                mRg_select.setClickable(true);
//                mRg_select.setEnabled(true);
//                enableRadioGroup(mRg_select);
//                mRg_select.setFocusable(true);
//                mRg_select.setFocusableInTouchMode(true);
//            }else {
//                mRg_select.setClickable(false);
//                mRg_select.setEnabled(false);
//                disableRadioGroup(mRg_select);
//                mRg_select.setFocusable(false);
//                mRg_select.setFocusableInTouchMode(false);
//            }
//
//            if(MyApplication.getInstance().getIs_zhuzhang() == 1){
                mRg_select.setClickable(true);
                mRg_select.setEnabled(true);
                enableRadioGroup(mRg_select);
                mRg_select.setFocusable(true);
                mRg_select.setFocusableInTouchMode(true);
//            }

            mRg_select.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                    switch (checkedId){
                        case R.id.rb_select_1:
                            mSelectIndex = 1;
                            selectRb(mSelectIndex);
                            break;
                        case  R.id.rb_select_2:
                            mSelectIndex = 2;
                            selectRb(mSelectIndex);

                            break;
                        case  R.id.rb_select_3:
                            mSelectIndex = 3;
                            selectRb(mSelectIndex);
                            break;
                        case  R.id.rb_select_4:
                            mSelectIndex = 4;
                            selectRb(mSelectIndex);
                            break;
                    }
                }
            });


            mEt_sceneBz.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }
                @Override
                public void afterTextChanged(Editable s) {
                    mBz = s.toString().trim();
                    if(mBz.length() >0 ){
//                    MainSceneActivity.getInstance().listData.get(mPosition).setIs_ok(1);
                        MainSceneActivity.getInstance().listData.get(mPosition).setImg_mark(mBz);
                    }else{
//                    MainSceneActivity.getInstance().listData.get(mPosition).setIs_ok(0);
                        MainSceneActivity.getInstance().listData.get(mPosition).setImg_mark("");
                    }
                }
            });
        }

    }


    public void disableRadioGroup(RadioGroup testRadioGroup) {
        for (int i = 0; i < testRadioGroup.getChildCount(); i++) {
            testRadioGroup.getChildAt(i).setClickable(false);
            testRadioGroup.getChildAt(i).setEnabled(false);
        }
    }

    public void enableRadioGroup(RadioGroup testRadioGroup) {
        for (int i = 0; i < testRadioGroup.getChildCount(); i++) {
            testRadioGroup.getChildAt(i).setClickable(true);
            testRadioGroup.getChildAt(i).setEnabled(true);
        }
    }


    /***
     * 获取PopupWindow实例
     */
    private void getPopupWindow() {
        if (null != popupWindow && popupWindow.isShowing()) {
            popupWindow.dismiss();
            return;
        } else {
            initPopuptWindow();
            // 设置弹出窗体显示时的动画，从底部向上弹出
            popupWindow.setAnimationStyle(R.style.pop_anim);
            popupWindow.showAtLocation(mLl_scenePic, Gravity.BOTTOM, 0, 0);
        }
    }

    /**
     * 创建PopupWindow
     */
    protected void initPopuptWindow() {
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = getActivity().getLayoutInflater().inflate(
                R.layout.pop_add_img, null, false);
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(MainSceneActivity.this,R.color.transparent_black_50));
//        // 设置弹出窗体的背景
//        popupWindow.setBackgroundDrawable(dw);
        // 设置外部可点击
//        popupWindow.setOutsideTouchable(true);

        mTv_takePic = (TextView) popupWindow_view.findViewById(R.id.tv_take_pic);
        mTv_getPic = (TextView) popupWindow_view.findViewById(R.id.tv_get_pic);
        mTv_picCancle = (TextView) popupWindow_view.findViewById(R.id.tv_pic_cancle);

        mTv_takePic.setOnClickListener(this);
        mTv_getPic.setOnClickListener(this);
        mTv_picCancle.setOnClickListener(this);

        // 点击其他地方消失
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindowDismiss();
                return false;
            }
        });

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_take_pic://拍照
                mTv_takePic.setSelected(false);

                takePic();

                popupWindowDismiss();
                break;
            case R.id.tv_get_pic://相册
                mTv_getPic.setSelected(false);
                    getPic();
                popupWindowDismiss();
                break;
            case R.id.tv_pic_cancle://取消
                mTv_picCancle.setSelected(false);
                popupWindowDismiss();
                break;
        }
    }

    /*popupWindow消失*/
    private void popupWindowDismiss() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }


    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            if(MainSceneActivity.getInstance().from_stype == 0){
                picIndex =1;
            getPopupWindow();//图片添加
            }
        }
    };

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener2 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            if(MainSceneActivity.getInstance().from_stype == 0){
                picIndex =2;
                getPopupWindow();//图片添加
            }
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:

                    if(picIndex == 2){//不符合的图片
                        // 图片选择
                        selectList2 = PictureSelector.obtainMultipleResult(data);
                        // 例如 LocalMedia 里面返回三种path
                        // 1.media.getPath(); 为原图path
                        // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                        // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                        // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                        gvAdapter2.setList(selectList2);
                        gvAdapter2.notifyDataSetChanged();
                        if(MainSceneActivity.getInstance().listData.get(mPosition).getImgListNo() == null){
                            MainSceneActivity.getInstance().listData.get(mPosition).setImgListNo(selectList2);
                        }else{
                            MainSceneActivity.getInstance().listData.get(mPosition).getImgListNo().clear();
                            MainSceneActivity.getInstance().listData.get(mPosition).getImgListNo().addAll(selectList2);
                        }
                    }else{// 符合的图片
                    // 图片选择
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    gvAdapter.setList(selectList);
                    gvAdapter.notifyDataSetChanged();
                    if(MainSceneActivity.getInstance().listData.get(mPosition).getImgList() == null){
                        MainSceneActivity.getInstance().listData.get(mPosition).setImgList(selectList);
                    }else{
                    MainSceneActivity.getInstance().listData.get(mPosition).getImgList().clear();
                    MainSceneActivity.getInstance().listData.get(mPosition).getImgList().addAll(selectList);
                    }
                    }

//                    if(mRb_select2.isChecked()){
//                        if(selectList.size()>0  && !TextUtils.isEmpty(mBz)){
//                            MainSceneActivity.getInstance().listData.get(mPosition).setIs_ok(1);
//                        }else {
//                            MainSceneActivity.getInstance().listData.get(mPosition).setIs_ok(0);
//                        }
//                    }




                    break;
            }
        }
    }

    private void takePic() {
        // 单独拍照
        PictureSelectionModel mPictureSelectionModel = PictureSelector.create(SceneFragment.this)
                .openCamera(PictureMimeType.ofImage())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                .theme(R.style.picture_QQ_style)// 主题样式设置 具体参考 values/styles
                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片
                .previewVideo(false)// 是否可预览视频
                .enablePreviewAudio(false) // 是否可播放音频
                .isCamera(false)// 是否显示拍照按钮
                .enableCrop(false)// 是否裁剪
                .compress(true)// 是否压缩
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                .isGif(false)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .openClickSound(true)// 是否开启点击声音
//                .selectionMedia(selectList1)// 是否传入已选图片
                .previewEggs(false)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                .cropCompressQuality(80)// 裁剪压缩质量 默认为100
                .minimumCompressSize(100);// 小于100kb的图片不压缩
        //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
        //.rotateEnabled() // 裁剪是否可旋转图片
        //.scaleEnabled()// 裁剪是否可放大缩小图片
        //.videoQuality()// 视频录制质量 0 or 1
        //.videoSecond()////显示多少秒以内的视频or音频也可适用
//                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        if (picIndex == 1) {
            mPictureSelectionModel.selectionMedia(selectList)
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code;// 是否传入已选图片
        } else {
            mPictureSelectionModel.selectionMedia(selectList2)
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code;// 是否传入已选图片
        }

    }

    private void getPic() {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelectionModel mPictureSelectionModel = PictureSelector.create(SceneFragment.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_QQ_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .previewVideo(false)// 是否可预览视频
                .enablePreviewAudio(false) // 是否可播放音频
                .isCamera(false)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(false)// 是否裁剪
                .compress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                .isGif(false)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .openClickSound(true)// 是否开启点击声音
                .selectionMedia(selectList)// 是否传入已选图片
                //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                .cropCompressQuality(80)// 裁剪压缩质量 默认100
                .minimumCompressSize(100);// 小于100kb的图片不压缩
        //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
        //.rotateEnabled(true) // 裁剪是否可旋转图片
        //.scaleEnabled(true)// 裁剪是否可放大缩小图片
        //.videoQuality()// 视频录制质量 0 or 1
        //.videoSecond()//显示多少秒以内的视频or音频也可适用
        //.recordVideoSecond()//录制视频秒数 默认60s
        if (picIndex == 1) {
            mPictureSelectionModel.selectionMedia(selectList)
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code;// 是否传入已选图片
        } else {
            mPictureSelectionModel.selectionMedia(selectList2)
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code;// 是否传入已选图片
        }
    }

    private  void selectRb(int selectIndex){
        switch (selectIndex){
            case 1:
                mLl_selectOk.setVisibility(View.VISIBLE);
                mLl_scenePic2.setVisibility(View.GONE);
                mIv_mustFull.setImageResource(R.drawable.must_fill_tag_no);
                MainSceneActivity.getInstance().listData.get(mPosition).setIs_ok(1);
                MainSceneActivity.getInstance().listData.get(mPosition).setImg_conclusion("1");
                break;
            case 2:
                mLl_selectOk.setVisibility(View.VISIBLE);
                mLl_scenePic2.setVisibility(View.VISIBLE);
                mIv_mustFull.setImageResource(R.drawable.must_fill_tag_yes);
                MainSceneActivity.getInstance().listData.get(mPosition).setIs_ok(1);
                MainSceneActivity.getInstance().listData.get(mPosition).setImg_conclusion("2");
                break;
            case 3:
                mLl_selectOk.setVisibility(View.GONE);
                MainSceneActivity.getInstance().listData.get(mPosition).setIs_ok(1);
                MainSceneActivity.getInstance().listData.get(mPosition).setImg_conclusion("3");
                break;
            case 4:
                mLl_selectOk.setVisibility(View.GONE);
                MainSceneActivity.getInstance().listData.get(mPosition).setIs_ok(0);
                MainSceneActivity.getInstance().listData.get(mPosition).setImg_conclusion("4");
                selectList.clear();
                selectList2.clear();

                if(MainSceneActivity.getInstance().listData.get(mPosition).getImgList() != null){
                MainSceneActivity.getInstance().listData.get(mPosition).getImgList().clear();
                }
                if(MainSceneActivity.getInstance().listData.get(mPosition).getImgListNo() != null){
                MainSceneActivity.getInstance().listData.get(mPosition).getImgListNo().clear();
                }

                gvAdapter.notifyDataSetChanged();
                gvAdapter2.notifyDataSetChanged();
                mEt_sceneBz.setText("");
                MainSceneActivity.getInstance().listData.get(mPosition).setImg_mark("");

                break;
        }
    }


    private void  chackInfo(){
        //证件
        if(mSceneTestInfo.getContent().contains("技术负责人") && "3.1".equals(mSceneTestInfo.getSeriaNumber())){
            mTv_sceneChack.setVisibility(View.VISIBLE);
            mTv_sceneChack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   showShortToast("查看技术负责人相关信息");
                }
            });
        }else if(mSceneTestInfo.getContent().contains("生产负责人") && "4.1".equals(mSceneTestInfo.getSeriaNumber())){
            mTv_sceneChack.setVisibility(View.VISIBLE);
            mTv_sceneChack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showShortToast("查看生产负责人相关信息");
                }
            });
        }else if(mSceneTestInfo.getContent().contains("质量负责人") && "5.1".equals(mSceneTestInfo.getSeriaNumber())){
            mTv_sceneChack.setVisibility(View.VISIBLE);
            mTv_sceneChack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showShortToast("查看质量负责人相关信息");
                }
            });
        }else if(mSceneTestInfo.getContent().contains("采购负责人") && "7.1".equals(mSceneTestInfo.getSeriaNumber())){
            mTv_sceneChack.setVisibility(View.VISIBLE);
            mTv_sceneChack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showShortToast("查看采购负责人相关信息");
                }
            });
        }
        else if(mSceneTestInfo.getContent().contains("销售负责人") && "6.1".equals(mSceneTestInfo.getSeriaNumber())){
            mTv_sceneChack.setVisibility(View.VISIBLE);
            mTv_sceneChack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showShortToast("查看销售负责人相关信息");
                }
            });
        }else if(mSceneTestInfo.getContent().contains("检验化验员") && "8".equals(mSceneTestInfo.getSeriaNumber())){
            mTv_sceneChack.setVisibility(View.VISIBLE);
            mTv_sceneChack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showShortToast("查看检验化验员相关信息");
                }
            });
        }else if(mSceneTestInfo.getContent().contains("维修工") && "10".equals(mSceneTestInfo.getSeriaNumber())){
            mTv_sceneChack.setVisibility(View.VISIBLE);
            mTv_sceneChack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showShortToast("查看维修工相关信息");
                }
            });
        }
        //考试成绩的显示
//        if(MainSceneActivity.getInstance().from_stype == 0
//                && MainSceneActivity.getInstance().listData.get(mPosition).getIs_ok() != 1){
//         if(mSceneTestInfo.getContent().contains("技术负责人") && "3.2".equals(mSceneTestInfo.getSeriaNumber())){
//             mIv_mustFullImg.setVisibility(View.INVISIBLE);
//             for(ExamResultInfo  mInfo : MainSceneActivity.getInstance().examRecoreList){
//                if(mInfo.getPeople_type() == 5 ){
//                    if(mInfo.getResultscore()>=80){
//                        mEt_sceneBz.setText(mInfo.getPeople_name()+" 成绩合格："+mInfo.getResultscore()+"分");
//                        mRb_select1.setChecked(true);
//                        mLl_selectOk.setVisibility(View.VISIBLE);
//                        mLl_scenePic2.setVisibility(View.GONE);
//                        mIv_mustFull.setImageResource(R.drawable.must_fill_tag_no);
//                        MainSceneActivity.getInstance().listData.get(mPosition).setIs_ok(1);
//                        MainSceneActivity.getInstance().listData.get(mPosition).setImg_conclusion("1");
//                    }else{
//                        mEt_sceneBz.setText(mInfo.getPeople_name()+" 成绩不合格："+mInfo.getResultscore()+"分");
//                        mRb_select2.setChecked(true);
//                        mLl_selectOk.setVisibility(View.VISIBLE);
//                        mLl_scenePic2.setVisibility(View.VISIBLE);
//                        mIv_mustFull.setImageResource(R.drawable.must_fill_tag_yes);
//                        MainSceneActivity.getInstance().listData.get(mPosition).setIs_ok(1);
//                        MainSceneActivity.getInstance().listData.get(mPosition).setImg_conclusion("2");
//                    }
//                    MainSceneActivity.getInstance().listData.get(mPosition).setImg_mark(mEt_sceneBz.getText().toString().trim());
//                    break;
//                }
//
//            }
//        }else if(mSceneTestInfo.getContent().contains("生产负责人") && "4.2".equals(mSceneTestInfo.getSeriaNumber())){
//             mIv_mustFullImg.setVisibility(View.INVISIBLE);
//             for(ExamResultInfo  mInfo : MainSceneActivity.getInstance().examRecoreList){
//                if(mInfo.getPeople_type() == 2 ){
//                    if(mInfo.getResultscore()>=80){
//                        mEt_sceneBz.setText(mInfo.getPeople_name()+" 成绩合格："+mInfo.getResultscore()+"分");
//                        mRb_select1.setChecked(true);
//                        mLl_selectOk.setVisibility(View.VISIBLE);
//                        mLl_scenePic2.setVisibility(View.GONE);
//                        mIv_mustFull.setImageResource(R.drawable.must_fill_tag_no);
//                        MainSceneActivity.getInstance().listData.get(mPosition).setIs_ok(1);
//                        MainSceneActivity.getInstance().listData.get(mPosition).setImg_conclusion("1");
//                    }else{
//                        mEt_sceneBz.setText(mInfo.getPeople_name()+" 成绩不合格："+mInfo.getResultscore()+"分");
//                        mRb_select2.setChecked(true);
//                        mLl_selectOk.setVisibility(View.VISIBLE);
//                        mLl_scenePic2.setVisibility(View.VISIBLE);
//                        mIv_mustFull.setImageResource(R.drawable.must_fill_tag_yes);
//                        MainSceneActivity.getInstance().listData.get(mPosition).setIs_ok(1);
//                        MainSceneActivity.getInstance().listData.get(mPosition).setImg_conclusion("2");
//                    }
//                    MainSceneActivity.getInstance().listData.get(mPosition).setImg_mark(mEt_sceneBz.getText().toString().trim());
//
//                    break;
//                }
//            }
//        }else if(mSceneTestInfo.getContent().contains("质量负责人") && "5.2".equals(mSceneTestInfo.getSeriaNumber())){
//             mIv_mustFullImg.setVisibility(View.INVISIBLE);
//            for(ExamResultInfo  mInfo : MainSceneActivity.getInstance().examRecoreList){
//                if(mInfo.getPeople_type() == 4 ){
//                    if(mInfo.getResultscore()>=80){
//                        mEt_sceneBz.setText(mInfo.getPeople_name()+" 成绩合格："+mInfo.getResultscore()+"分");
//                        mRb_select1.setChecked(true);
//                        mLl_selectOk.setVisibility(View.VISIBLE);
//                        mLl_scenePic2.setVisibility(View.GONE);
//                        mIv_mustFull.setImageResource(R.drawable.must_fill_tag_no);
//                        MainSceneActivity.getInstance().listData.get(mPosition).setIs_ok(1);
//                        MainSceneActivity.getInstance().listData.get(mPosition).setImg_conclusion("1");
//                    }else{
//                        mEt_sceneBz.setText(mInfo.getPeople_name()+" 成绩不合格："+mInfo.getResultscore()+"分");
//                        mRb_select2.setChecked(true);
//                        mLl_selectOk.setVisibility(View.VISIBLE);
//                        mLl_scenePic2.setVisibility(View.VISIBLE);
//                        mIv_mustFull.setImageResource(R.drawable.must_fill_tag_yes);
//                        MainSceneActivity.getInstance().listData.get(mPosition).setIs_ok(1);
//                        MainSceneActivity.getInstance().listData.get(mPosition).setImg_conclusion("2");
//                    }
//                    MainSceneActivity.getInstance().listData.get(mPosition).setImg_mark(mEt_sceneBz.getText().toString().trim());
//                    break;
//                }
//            }
//        }else if(mSceneTestInfo.getContent().contains("销售负责人") && "6".equals(mSceneTestInfo.getSeriaNumber())){
//             mIv_mustFullImg.setVisibility(View.INVISIBLE);
//            for(ExamResultInfo  mInfo : MainSceneActivity.getInstance().examRecoreList){
//                 if(mInfo.getPeople_type() == 3 ){
//                     if(mInfo.getResultscore()>=80){
//                         mEt_sceneBz.setText(mInfo.getPeople_name()+" 成绩合格："+mInfo.getResultscore()+"分");
//                         mRb_select1.setChecked(true);
//                         mLl_selectOk.setVisibility(View.VISIBLE);
//                         mLl_scenePic2.setVisibility(View.GONE);
//                         mIv_mustFull.setImageResource(R.drawable.must_fill_tag_no);
//                         MainSceneActivity.getInstance().listData.get(mPosition).setIs_ok(1);
//                         MainSceneActivity.getInstance().listData.get(mPosition).setImg_conclusion("1");
//                     }else{
//                         mEt_sceneBz.setText(mInfo.getPeople_name()+" 成绩不合格："+mInfo.getResultscore()+"分");
//                         mRb_select2.setChecked(true);
//                         mLl_selectOk.setVisibility(View.VISIBLE);
//                         mLl_scenePic2.setVisibility(View.VISIBLE);
//                         mIv_mustFull.setImageResource(R.drawable.must_fill_tag_yes);
//                         MainSceneActivity.getInstance().listData.get(mPosition).setIs_ok(1);
//                         MainSceneActivity.getInstance().listData.get(mPosition).setImg_conclusion("2");
//                     }
//                     MainSceneActivity.getInstance().listData.get(mPosition).setImg_mark(mEt_sceneBz.getText().toString().trim());
//                     break;
//                 }
//             }
//         }else if(mSceneTestInfo.getContent().contains("采购负责人") && "7".equals(mSceneTestInfo.getSeriaNumber())){
//             mIv_mustFullImg.setVisibility(View.INVISIBLE);
//            for(ExamResultInfo  mInfo : MainSceneActivity.getInstance().examRecoreList){
//                 if(mInfo.getPeople_type() == 1 ){
//                     if(mInfo.getResultscore()>=80){
//                         mEt_sceneBz.setText(mInfo.getPeople_name()+" 成绩合格："+mInfo.getResultscore()+"分");
//                         mRb_select1.setChecked(true);
//                         mLl_selectOk.setVisibility(View.VISIBLE);
//                         mLl_scenePic2.setVisibility(View.GONE);
//                         mIv_mustFull.setImageResource(R.drawable.must_fill_tag_no);
//                         MainSceneActivity.getInstance().listData.get(mPosition).setIs_ok(1);
//                         MainSceneActivity.getInstance().listData.get(mPosition).setImg_conclusion("1");
//                     }else{
//                         mEt_sceneBz.setText(mInfo.getPeople_name()+" 成绩不合格："+mInfo.getResultscore()+"分");
//                         mRb_select2.setChecked(true);
//                         mLl_selectOk.setVisibility(View.VISIBLE);
//                         mLl_scenePic2.setVisibility(View.VISIBLE);
//                         mIv_mustFull.setImageResource(R.drawable.must_fill_tag_yes);
//                         MainSceneActivity.getInstance().listData.get(mPosition).setIs_ok(1);
//                         MainSceneActivity.getInstance().listData.get(mPosition).setImg_conclusion("2");
//                     }
//                     MainSceneActivity.getInstance().listData.get(mPosition).setImg_mark(mEt_sceneBz.getText().toString().trim());
//                     break;
//                 }
//             }
//         }
//        }
    }

}
