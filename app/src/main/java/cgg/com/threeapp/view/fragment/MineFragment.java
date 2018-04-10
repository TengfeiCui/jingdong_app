package cgg.com.threeapp.view.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.HashMap;

import cgg.com.threeapp.R;
import cgg.com.threeapp.model.bean.HomeBean;
import cgg.com.threeapp.presenter.presenterImpl.HomePresenterImpl;
import cgg.com.threeapp.utils.API;
import cgg.com.threeapp.utils.ChenjinUtil;
import cgg.com.threeapp.utils.CommedUtil;
import cgg.com.threeapp.utils.GlideImgManager;
import cgg.com.threeapp.view.activity.LoginActivity;
import cgg.com.threeapp.view.activity.SIgnInActivity;
import cgg.com.threeapp.view.activity.UserInfoActivity;
import cgg.com.threeapp.view.activity.UserSettingActivity;
import cgg.com.threeapp.view.adapter.HomeRecommendAdapter;
import cgg.com.threeapp.view.costomView.ObservableScrollView;
import cgg.com.threeapp.view.viewInterface.IHomeView;

/**
 * author: Wanderer
 * date:   2018/2/23 19:11
 * email:  none
 */

public class MineFragment extends Fragment implements IHomeView, View.OnClickListener {
    private RecyclerView tuijian_recycler;
    private LinearLayout mine_dpj, mine_dsh, mine_dzf, mine_myOrder, mine_sh_th, mine_login_background,mine_title_liner;
    private ImageView mine_message, mine_setting, mine_user_icon;
    private TextView mine_user_name;
    private final String TAG_MARGIN_ADDED = "marginAdded";
    private ObservableScrollView mine_scroll;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_layout, container, false);
        // 填充控件
        tuijian_recycler = view.findViewById(R.id.mine_tuijian);
        mine_dpj = view.findViewById(R.id.mine_dpj);
        mine_dsh = view.findViewById(R.id.mine_dsh);
        mine_dzf = view.findViewById(R.id.mine_dzf);
        mine_sh_th = view.findViewById(R.id.mine_sh_th);
        mine_title_liner = view.findViewById(R.id.mine_title_liner);
        mine_scroll = view.findViewById(R.id.mine_scroll);

        
        mine_login_background = view.findViewById(R.id.mine_login_background);
        mine_message = view.findViewById(R.id.mine_message);
        mine_setting = view.findViewById(R.id.mine_setting);
        mine_user_icon = view.findViewById(R.id.mine_user_icon);
        mine_user_name = view.findViewById(R.id.mine_user_name);

        // 设置监听
        tuijian_recycler.setOnClickListener(this);
        mine_dpj.setOnClickListener(this);
        mine_dsh.setOnClickListener(this);
        mine_dzf.setOnClickListener(this);
        mine_sh_th.setOnClickListener(this);
        mine_message.setOnClickListener(this);
        mine_setting.setOnClickListener(this);
        mine_user_icon.setOnClickListener(this);
        mine_user_name.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        initChenJin();
        initTitleBar();
        HomePresenterImpl homePresenter = new HomePresenterImpl(this);
        homePresenter.getHomeBean(API.INDEX_URL, new HashMap<String, String>());
    }

    @Override
    public void setHomeBean(HomeBean homeBean) {
        HomeBean.TuijianBean tuijian = homeBean.getTuijian();
        tuijian_recycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        tuijian_recycler.setAdapter(new HomeRecommendAdapter(tuijian, getActivity()));
    }

    @Override
    public void onClick(View v) {
        if (CommedUtil.getBooleanValue("isLogin")) {
            switch (v.getId()) {
                case R.id.mine_user_name:
                case R.id.mine_user_icon:
                    Intent intent = new Intent(getActivity(), UserSettingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.mine_dpj:
                    Toast.makeText(getActivity(), "待评价", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.mine_dsh:
                    Toast.makeText(getActivity(), "待收货", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.mine_dzf:
                    Toast.makeText(getActivity(), "待付款", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.mine_myOrder:
                    Toast.makeText(getActivity(), "订单", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.mine_message:
                    Toast.makeText(getActivity(), "消息", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.mine_setting:
                    Toast.makeText(getActivity(), "设置", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else {
            startLoginActivity();
        }

    }

    // 跳转到注册的activity
    public void startSignInActivity() {
        Intent intent = new Intent(getActivity(), SIgnInActivity.class);
        startActivity(intent);
    }

    // 跳转到登录的activity
    public void startLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if(!hidden){
            initChenJin();
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        if (CommedUtil.getBooleanValue("isLogin")) {
            mine_login_background.setBackgroundResource(R.drawable.reg_bg);
            String iconUrl = CommedUtil.getStringValue("icon");
            String userName = CommedUtil.getStringValue("userName");
            if (!"isNull".equals(iconUrl)) {
                GlideImgManager.glideLoader(getActivity(),iconUrl , R.drawable.user, R.drawable.user, mine_user_icon, 0);
            } else {
                mine_user_icon.setImageResource(R.drawable.user);
            }

            if (!"isNull".equals(userName)) {
                mine_user_name.setText(userName);
            }

        } else {
            mine_login_background.setBackgroundResource(R.drawable.b5a);
            mine_user_name.setText("登陆/注册 >");
        }
    }
    private void initChenJin() {
        ChenjinUtil.setStatusBarColor(getActivity(), Color.TRANSPARENT);
    }


    private void initTitleBar() {

        //linearLayout在view绘制的时候外面包裹一层relativeLayout
        //应该尽量减少使用linearLayout...view优化

        addMargin();


        ViewTreeObserver viewTreeObserver = mine_title_liner.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mine_title_liner.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                final int height = mine_title_liner.getHeight();

                mine_scroll.setScrollViewListener(new ObservableScrollView.IScrollViewListener() {
                    @Override
                    public void onScrollChanged(int x, int y, int oldx, int oldy) {
                        if (y <= 0) {
                            addMargin();
                            ChenjinUtil.setStatusBarColor(getActivity(),Color.TRANSPARENT);

                            mine_title_liner.setBackgroundColor(Color.argb( 0, 227, 29, 26));//AGB由相关工具获得，或
                        } else if (y > 0 && y < height) {

                            if (y > ChenjinUtil.getStatusBarHeight(getActivity())) {
                                //去掉margin
                                removeMargin();
                                //状态栏为灰色
                                ChenjinUtil.setStatusBarColor(getActivity(),getResources().getColor(R.color.colorGray));
                            }

                            float scale = (float) y / height;
                            float alpha = (255 * scale);
                            // 只是layout背景透明(仿知乎滑动效果)
                            mine_title_liner.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                        } else {
                            mine_title_liner.setBackgroundColor(Color.argb(255, 255, 255, 255));
                        }
                    }
                });
            }
        });

    }

    private void removeMargin() {
        if (TAG_MARGIN_ADDED.equals(mine_title_liner.getTag())) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mine_title_liner.getLayoutParams();
            // 移除的间隔大小就是状态栏的高度
            params.topMargin -= ChenjinUtil.getStatusBarHeight(getActivity());
            mine_title_liner.setLayoutParams(params);
            mine_title_liner.setTag(null);
        }
    }

    private void addMargin() {
        //给标题调价margin
        if (!TAG_MARGIN_ADDED.equals(mine_title_liner.getTag())) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mine_title_liner.getLayoutParams();
            // 添加的间隔大小就是状态栏的高度
            params.topMargin += ChenjinUtil.getStatusBarHeight(getActivity());
            mine_title_liner.setLayoutParams(params);
            mine_title_liner.setTag(TAG_MARGIN_ADDED);
        }
    }
}
