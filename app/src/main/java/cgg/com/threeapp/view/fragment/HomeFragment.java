package cgg.com.threeapp.view.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dash.zxinglibrary.activity.CaptureActivity;
import com.dash.zxinglibrary.activity.CodeUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sunfusheng.marqueeview.MarqueeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cgg.com.threeapp.R;
import cgg.com.threeapp.model.bean.ClassifyBean;
import cgg.com.threeapp.model.bean.HomeBean;
import cgg.com.threeapp.presenter.presenterImpl.ClassifyPresenterImpl;
import cgg.com.threeapp.presenter.presenterImpl.HomePresenterImpl;
import cgg.com.threeapp.utils.API;
import cgg.com.threeapp.utils.BannerImageLoader;
import cgg.com.threeapp.utils.ChenjinUtil;
import cgg.com.threeapp.view.activity.WebViewActivity;
import cgg.com.threeapp.view.adapter.HomeClassifyAdapter;
import cgg.com.threeapp.view.adapter.HomeMiaoShaAdapter;
import cgg.com.threeapp.view.adapter.HomeRecommendAdapter;
import cgg.com.threeapp.view.costomView.ObservableScrollView;
import cgg.com.threeapp.view.viewInterface.IClassifyView;
import cgg.com.threeapp.view.viewInterface.IHomeView;

/**
 * author: Wanderer
 * date:   2018/2/23 19:11
 * email:  none
 */

public class HomeFragment extends Fragment implements IHomeView, IClassifyView, View.OnClickListener {
    private ImageView home_android, home_message, home_saoASao, home_souSuo;
    private Banner home_banner;
    private MarqueeView home_marqueeView;
    private RecyclerView home_menu_recycler, home_sale, home_recommend;
    private SmartRefreshLayout home_sMart;
    private HomePresenterImpl homePresenter;
    private ClassifyPresenterImpl classifyPresenter;
    private LinearLayout home_title_liner;
    private ObservableScrollView home_scro;
    private final String TAG_MARGIN_ADDED = "marginAdded";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View homeView = inflater.inflate(R.layout.home_fragment_layout, container, false);
        // 关联控件
        home_menu_recycler = homeView.findViewById(R.id.home_menu_recycler);
        home_recommend = homeView.findViewById(R.id.home_recommend);
        home_sale = homeView.findViewById(R.id.home_sale);

        home_banner = homeView.findViewById(R.id.home_banner);
        home_marqueeView = homeView.findViewById(R.id.home_marqueeView);
        home_sMart = homeView.findViewById(R.id.home_sMart);

        home_android = homeView.findViewById(R.id.home_android);
        home_message = homeView.findViewById(R.id.home_message);
        home_saoASao = homeView.findViewById(R.id.home_saoASao);
        home_souSuo = homeView.findViewById(R.id.home_souSuo);

        home_android.setOnClickListener(this);
        home_message.setOnClickListener(this);
        home_saoASao.setOnClickListener(this);
        home_souSuo.setOnClickListener(this);

        home_title_liner = homeView.findViewById(R.id.home_title_liner);
        home_scro = homeView.findViewById(R.id.home_scro1);
        initTitleBar();
        initHomeView();
        return homeView;
    }

    // 初始控件
    private void initHomeView() {
        // 初始化banner
        home_banner.setImageLoader(new BannerImageLoader());
        home_banner.isAutoPlay(true);
        home_banner.setDelayTime(2000);
        home_banner.setIndicatorGravity(BannerConfig.RIGHT);

        // 初始化Recycler
        home_menu_recycler.setLayoutManager(new GridLayoutManager(getActivity(), 2, OrientationHelper.HORIZONTAL, false));
        home_recommend.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        home_sale.setLayoutManager(new LinearLayoutManager(getActivity(), OrientationHelper.HORIZONTAL, false));

        homePresenter = new HomePresenterImpl(this);
        classifyPresenter = new ClassifyPresenterImpl(this);

        home_sMart.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                home_sMart.finishLoadmore(2000);
            }
        });
        home_sMart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                home_sMart.finishRefresh(2000);
            }
        });


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initChenJin();
        //标题栏

        homePresenter.getHomeBean(API.INDEX_URL, new HashMap<String, String>());
        classifyPresenter.getClassifyBean(API.INDEX_SORT_URL, new HashMap<String, String>());
        final ArrayList<String> names = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            names.add("跑-马-灯→" + i);
        }

        home_marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                Toast.makeText(getActivity(), names.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        home_marqueeView.startWithList(names);

        home_sMart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                home_sMart.finishRefresh(1000);
                homePresenter.getHomeBean(API.INDEX_URL, new HashMap<String, String>());
                classifyPresenter.getClassifyBean(API.INDEX_SORT_URL, new HashMap<String, String>());
                Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
            }
        });

        home_sMart.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                home_sMart.finishLoadmore(1000);
                Toast.makeText(getActivity(), "待开发", Toast.LENGTH_SHORT).show();
            }
        });

        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void setHomeBean(HomeBean homeBean) {
        HomeBean.MiaoshaBean miaoshaData = homeBean.getMiaosha();
        HomeBean.TuijianBean tuijianData = homeBean.getTuijian();
        List<HomeBean.DataBean> bannerData = homeBean.getData();

        final ArrayList<String> bannerUrls = new ArrayList<>();
        ArrayList<String> bannerIcons = new ArrayList<>();
        ArrayList<String> bannerTitles = new ArrayList<>();


        for (int i = 0; i < bannerData.size(); i++) {
            HomeBean.DataBean dataBean = bannerData.get(i);
            String bannerIcon = dataBean.getIcon();
            String bannerUrl = dataBean.getUrl();
            String title = dataBean.getTitle();

            bannerUrls.add(bannerUrl);
            bannerIcons.add(bannerIcon);
            bannerTitles.add(title);
        }

        // 给banner赋值
        home_banner.setImages(bannerIcons);
        home_banner.setBannerTitles(bannerTitles);
        home_banner.start();
        // 设置点击事件
        home_banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("bannerUrl", bannerUrls.get(position));
                startActivity(intent);
            }
        });

        
        
        home_sale.setAdapter(new HomeMiaoShaAdapter(miaoshaData, getActivity()));
        home_recommend.setAdapter(new HomeRecommendAdapter(tuijianData, getActivity()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        homePresenter.destroy();
    }

    @Override
    public void setClassifyBean(ClassifyBean classifyBean) {
        home_menu_recycler.setAdapter(new HomeClassifyAdapter(classifyBean, getActivity()));
    }

    private void initChenJin() {
        ChenjinUtil.setStatusBarColor(getActivity(), Color.TRANSPARENT);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            initChenJin();
        }
        super.onHiddenChanged(hidden);
    }

    private void initTitleBar() {

        //linearLayout在view绘制的时候外面包裹一层relativeLayout
        //应该尽量减少使用linearLayout...view优化

        addMargin();


        ViewTreeObserver viewTreeObserver = home_title_liner.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                home_title_liner.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                final int height = home_title_liner.getHeight();

                home_scro.setScrollViewListener(new ObservableScrollView.IScrollViewListener() {
                    @Override
                    public void onScrollChanged(int x, int y, int oldx, int oldy) {
                        if (y <= 0) {
                            addMargin();
                            ChenjinUtil.setStatusBarColor(getActivity(), Color.TRANSPARENT);

                            home_title_liner.setBackgroundColor(Color.argb(0, 227, 29, 26));//AGB由相关工具获得，或
                        } else if (y > 0 && y < height) {

                            if (y > ChenjinUtil.getStatusBarHeight(getActivity())) {
                                //去掉margin
                                removeMargin();
                                //状态栏为灰色
                                ChenjinUtil.setStatusBarColor(getActivity(), getResources().getColor(R.color.colorGray));
                            }

                            float scale = (float) y / height;
                            float alpha = (255 * scale);
                            // 只是layout背景透明(仿知乎滑动效果)
                            home_title_liner.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                        } else {
                            home_title_liner.setBackgroundColor(Color.argb(255, 255, 255, 255));
                        }
                    }
                });
            }
        });

    }

    private void removeMargin() {
        if (TAG_MARGIN_ADDED.equals(home_title_liner.getTag())) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) home_title_liner.getLayoutParams();
            // 移除的间隔大小就是状态栏的高度
            params.topMargin -= ChenjinUtil.getStatusBarHeight(getActivity());
            home_title_liner.setLayoutParams(params);
            home_title_liner.setTag(null);
        }
    }

    private void addMargin() {
        //给标题调价margin
        if (!TAG_MARGIN_ADDED.equals(home_title_liner.getTag())) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) home_title_liner.getLayoutParams();
            // 添加的间隔大小就是状态栏的高度
            params.topMargin += ChenjinUtil.getStatusBarHeight(getActivity());
            home_title_liner.setLayoutParams(params);
            home_title_liner.setTag(TAG_MARGIN_ADDED);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_android:
                break;
            case R.id.home_message:
                break;
            case R.id.home_saoASao:
                Intent intent=  new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, 1001);
                break;
            case R.id.home_souSuo:
                break;
        }
       // Toast.makeText(getActivity(), "标题的惦记时间", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                //拿到传递回来的数据
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }

                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    //解析得到的结果
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    if (result.startsWith("http://")) {
                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
                        intent.putExtra("bannerUrl",result);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getActivity(), "暂不支持的二维码", Toast.LENGTH_LONG).show();
                    }
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
