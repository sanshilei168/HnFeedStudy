package com.nxt.hnfeedstudy.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DoubleUtils;
import com.nxt.hnfeedstudy.MyApplication;
import com.nxt.hnfeedstudy.R;
import com.nxt.hnfeedstudy.adapter.GvSceneAdapter;
import com.nxt.hnfeedstudy.bean.SceneImgInfo;
import com.nxt.hnfeedstudy.bean.SceneTestInfo;
import com.nxt.hnfeedstudy.db.DBSceneManager;
import com.nxt.hnfeedstudy.fragment.SceneFragment;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import nxt.com.ssllibrary.mytools.JSONTool;
import nxt.com.ssllibrary.mytools.ScreenUtils;

/**
 * Created by 三石磊 on 2017/6/13.
 * 现场考察
 */

public class MainSceneActivity extends BaseActivity {
    @BindView(R.id.vp_scene)
    ViewPager mVp_scene;
    @BindView(R.id.tv_scene_nu)
    TextView mTv_sceneNu;
    @BindView(R.id.tv_scene_title)
    TextView mTv_sceneTitle;
    @BindView(R.id.iv_scene_right1)
    ImageView mIv_sceneRight1;
    @BindView(R.id.iv_scene_right2)
    ImageView mIv_sceneRight2;
    @BindView(R.id.img_move)
    ImageView mImg_move;

    private List<Fragment> mFragments;
    public List<SceneImgInfo> listData;
    private FragmentPagerAdapter mPageAdpter;
    private PopupWindow popupWindow;
    private int currentNu = 0;//当前题
    private int allNu = 0;//总题数据
    private WindowManager.LayoutParams params;
    private static MainSceneActivity instance;
    private int sceneType;// 评审事项类型
    private String sceneTypeName;// 评审类型名称
    public ArrayList<SceneTestInfo> listInfo;
    public int from_stype;

    private float coorchiceX = 0f, coorchiceY = 0f;
    private boolean isCoorChiceClick = false;
    private int mScreenWidth, mScreenHeight;

    private int INTENT_REQUEST_CODE = 201;


    @Override
    public int getContentLayout() {
        return R.layout.activity_main_scene;
    }


    public static MainSceneActivity getInstance() {
        return instance;
    }

    @Override
    public void myBindView(Bundle savedInstanceState) {
        instance = this;
        mScreenWidth = ScreenUtils.getScreenWidth(this);
        mScreenHeight = ScreenUtils.getScreenHeight(this);
        initData();
//        if (MyApplication.getInstance().getIs_zhuzhang() == 1) {
//            mIv_sceneRight1.setVisibility(View.VISIBLE);
//        } else {
//            mIv_sceneRight1.setVisibility(View.INVISIBLE);
//        }
        initViewPages();//初始化viewpager
        initEvent();
        listenCoorChiceTouchEvent();
    }

    private void initData() {
        from_stype = getIntent().getIntExtra("from_stype", 0);
        sceneType = getIntent().getIntExtra("companyType", 0);
        sceneTypeName = getIntent().getStringExtra("sceneTypeName");

        if (from_stype == 1) {
            mImg_move.setVisibility(View.GONE);
        } else {
            mImg_move.setVisibility(View.VISIBLE);
        }

        mTv_sceneTitle.setText(sceneTypeName);
            listInfo = DBSceneManager.getInstance(this).getListSceneInfo(sceneType);

    }

    /*初始化viewpager*/
    private void initViewPages() {
        //初始化四个布局
        mFragments = new ArrayList<>();
        allNu = listInfo.size();
        mTv_sceneNu.setText((currentNu + 1) + "/" + allNu);

            listData = new ArrayList<>();
            for (int i = 0; i < listInfo.size(); i++) {
                SceneFragment tab01 = SceneFragment.newInstance(i);
                mFragments.add(tab01);
                SceneImgInfo mdata = new SceneImgInfo();
                mdata.setBusiness_id(sceneType+"");
                mdata.setTest_id(listInfo.get(i).getId());
                mdata.setImg_content(listInfo.get(i).getContent());
                listData.add(mdata);
            }


        //初始化Adapter这里使用FragmentPagerAdapter
        mPageAdpter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
        mVp_scene.setAdapter(mPageAdpter);
    }

    /*事件处理*/
    private void initEvent() {
        mVp_scene.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                System.out.println("position1++"+position);
                currentNu = mVp_scene.getCurrentItem();
                mTv_sceneNu.setText((currentNu + 1) + "/" + allNu);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                hideKeyboard();
            }
        });

    }

    @OnClick({R.id.ib_scene_pre, R.id.tv_scene_nu, R.id.ib_scene_next
            , R.id.iv_back, R.id.iv_scene_right1, R.id.iv_scene_right2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_scene_right1:
//                Intent intent1 = new Intent(this, ShenHeTableActivity.class);
//                intent1.putExtra("mIndex", mCompanyIndex);
//                intent1.putExtra("from_stype", from_stype);
//                startActivity(intent1);
                break;
            case R.id.iv_scene_right2:
//                if (!DoubleUtils.isFastDoubleClick()) {
//                    SceneResultInfo minfo = new SceneResultInfo();
//                    minfo.setSceneImgInfoList(listData);
//                    MyApplication.getInstance().saveSharedPreferences(mCompanyInfo.getCompanyId() + mApplyInfo.getId(), JSONTool.toBeanString(minfo));
//
//                    Intent intent2 = new Intent(this, UnqualifiedTableActivity.class);
//                    intent2.putExtra("mIndex", mCompanyIndex);
//                    intent2.putExtra("from_stype", from_stype);
//                    intent2.putParcelableArrayListExtra("infoList", listInfo);
//                    startActivityForResult(intent2,INTENT_REQUEST_CODE);
//                }
                break;

            case R.id.ib_scene_pre://上一题
                currentNu = mVp_scene.getCurrentItem();
                if (currentNu == 0) {
                    showShortToast(getResources().getString(R.string.topic_first_question));
                } else {
                    mVp_scene.setCurrentItem(currentNu - 1);
                }
                break;
            case R.id.tv_scene_nu://当前题
                hideKeyboard();
                getPopupWindow();
                break;
            case R.id.ib_scene_next://下一题
                currentNu = mVp_scene.getCurrentItem();
                if (currentNu == allNu - 1) {
                    showShortToast(getResources().getString(R.string.topic_last_question));
                } else {
                    mVp_scene.setCurrentItem(currentNu + 1);
                }
                break;
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
            popupWindow.showAtLocation(mTv_sceneNu, Gravity.BOTTOM, 0, 0);
        }
    }

    /**
     * 创建PopupWindow
     */
    protected void initPopuptWindow() {
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = getLayoutInflater().inflate(
                R.layout.pop_exam, null, false);
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(MainSceneActivity.this,R.color.transparent_black_50));
//        // 设置弹出窗体的背景
//        popupWindow.setBackgroundDrawable(dw);
        // 设置外部可点击
//        popupWindow.setOutsideTouchable(true);

        LinearLayout ll_1 = (LinearLayout) popupWindow_view.findViewById(R.id.ll_1);
        LinearLayout ll_2 = (LinearLayout) popupWindow_view.findViewById(R.id.ll_2);
        ll_1.setVisibility(View.GONE);
        ll_2.setVisibility(View.VISIBLE);
        TextView tv_current = (TextView) popupWindow_view.findViewById(R.id.tv_current);
        TextView tv_pop_stype0 = (TextView) popupWindow_view.findViewById(R.id.tv_pop_stype0);
        TextView tv_pop_stype4 = (TextView) popupWindow_view.findViewById(R.id.tv_pop_stype4);
        TextView tv_pop_stype5 = (TextView) popupWindow_view.findViewById(R.id.tv_pop_stype5);
        TextView tv_pop_stype6 = (TextView) popupWindow_view.findViewById(R.id.tv_pop_stype6);

        tv_pop_stype0.setVisibility(View.VISIBLE);
        tv_pop_stype6.setVisibility(View.VISIBLE);

        tv_current.setText("当前："+(currentNu + 1) + "/" + allNu);
        tv_pop_stype0.setText("必审");
        tv_pop_stype4.setText("符合");
        tv_pop_stype5.setText("不符合");
        tv_pop_stype6.setText("其他");

        GridView gv_exam = (GridView) popupWindow_view.findViewById(R.id.gv_exam);
        GvSceneAdapter mGvAdapter = new GvSceneAdapter(MainSceneActivity.this, currentNu, listData, listInfo);
        gv_exam.setAdapter(mGvAdapter);

        gv_exam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentNu != position) {
                    mVp_scene.setCurrentItem(position);
                }
                popWindowDismiss();
            }
        });
        // 点击其他地方消失
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popWindowDismiss();
                return false;
            }
        });
    }

    /*
    pop消失
     */
    private void popWindowDismiss() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    //返回键
    @Override
    public void onBackPressed() {
        if (from_stype == 0) {
            submitTest();
        } else {
            super.onBackPressed();
        }
    }

    /*
    保存数据
     */
    private void saveData() {
        showShortToast("学习数据不进行实际本地保存");
        return;
    }

    private void submitTest() {
        new AlertDialog.Builder(MainSceneActivity.this)
                .setTitle(getResources().getString(R.string.exit_scene))
                .setNegativeButton(getResources().getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
//                                saveData();
                                finish();
                            }
                        })
                .setPositiveButton(getResources().getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        }).show();
    }

    private void listenCoorChiceTouchEvent() {
        mImg_move.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        coorchiceX = event.getRawX();
                        coorchiceY = event.getRawY();
                        isCoorChiceClick = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float dx = event.getRawX() - coorchiceX;
                        float dy = event.getRawY() - coorchiceY;
                        AnimatorSet set = new AnimatorSet();
                        ObjectAnimator xAnim;
                        ObjectAnimator yAnim;
                        if (mImg_move.getY() < 0) {
                            xAnim = ObjectAnimator.ofFloat(mImg_move, "x", event.getRawX() - ScreenUtils.dip2px(MainSceneActivity.this, 30), event.getRawX() - ScreenUtils.dip2px(MainSceneActivity.this, 30));
                            yAnim = ObjectAnimator.ofFloat(mImg_move, "y", 0, 0);

                        } else if (mImg_move.getY() > mScreenHeight - 450) {
                            xAnim = ObjectAnimator.ofFloat(mImg_move, "x", event.getRawX() - ScreenUtils.dip2px(MainSceneActivity.this, 30), event.getRawX() - ScreenUtils.dip2px(MainSceneActivity.this, 30));
                            yAnim = ObjectAnimator.ofFloat(mImg_move, "y", mScreenHeight - 450, mScreenHeight - 450);

                        } else {
                            xAnim = ObjectAnimator.ofFloat(mImg_move, "x", event.getRawX() - ScreenUtils.dip2px(MainSceneActivity.this, 30), event.getRawX() - ScreenUtils.dip2px(MainSceneActivity.this, 30));
                            yAnim = ObjectAnimator.ofFloat(mImg_move, "y", coorchiceY - 240, event.getRawY() - 240);
                        }
                        set.playTogether(xAnim, yAnim);
                        set.setDuration(0);
                        set.start();

//                      coorchiceX = event.getRawX();
//                      coorchiceY = event.getRawY();
                        if (dx >= ViewConfiguration.getTouchSlop()) {
                            isCoorChiceClick = false;
                        } else if (dy >= ViewConfiguration.getTouchSlop()) {
                            isCoorChiceClick = false;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (isCoorChiceClick) {//保存数据
                            saveData();
                        }
                        ObjectAnimator yAnim1;
                        if (mImg_move.getY() <= 0) {
                            yAnim1 = ObjectAnimator.ofFloat(mImg_move, "y", 0f, 0f);
                        } else if (mImg_move.getY() > mScreenHeight - 450) {
                            yAnim1 = ObjectAnimator.ofFloat(mImg_move, "y", mScreenHeight - 450, mScreenHeight - 450);
                        } else {
                            yAnim1 = ObjectAnimator.ofFloat(mImg_move, "y", mImg_move.getY(), mImg_move.getY());
                        }
                        if (mImg_move.getX() <= mScreenWidth / 2) {
                            AnimatorSet set1 = new AnimatorSet();
                            ObjectAnimator xAnim1 = ObjectAnimator.ofFloat(mImg_move, "x", mImg_move.getX(), 0f);
//                              ObjectAnimator yAnim1 = ObjectAnimator.ofFloat(mImg_move, "y", mImg_move.getY(), mImg_move.getY());
                            set1.playTogether(xAnim1, yAnim1);
                            set1.setDuration(1000L);
                            set1.start();
                        } else {
                            AnimatorSet set1 = new AnimatorSet();
                            ObjectAnimator xAnim1 = ObjectAnimator.ofFloat(mImg_move, "x", mImg_move.getX(), mScreenWidth - mImg_move.getWidth());
//                              ObjectAnimator yAnim1 = ObjectAnimator.ofFloat(mImg_move, "y", mImg_move.getY(), mImg_move.getY());
                            set1.playTogether(xAnim1, yAnim1);
                            set1.setDuration(1000L);
                            set1.start();
                        }
                        break;
                }
                return true;
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == INTENT_REQUEST_CODE && resultCode == RESULT_OK){

            int getNu = data.getIntExtra("select_index", 0);

            if(getNu  > 0 && currentNu != getNu-1){
                currentNu = getNu-1;
                mVp_scene.setCurrentItem(currentNu);
            }
        }
    }
}
