package cgg.com.threeapp.view.fragment;

import android.content.Intent;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import cgg.com.threeapp.R;
import cgg.com.threeapp.model.bean.CartBean;
import cgg.com.threeapp.model.bean.HomeBean;
import cgg.com.threeapp.presenter.presenterImpl.CartPresenterImpl;
import cgg.com.threeapp.presenter.presenterImpl.HomePresenterImpl;
import cgg.com.threeapp.utils.API;
import cgg.com.threeapp.utils.ChenjinUtil;
import cgg.com.threeapp.utils.CommedUtil;
import cgg.com.threeapp.view.activity.LoginActivity;
import cgg.com.threeapp.view.adapter.HomeRecommendAdapter;
import cgg.com.threeapp.view.adapter.MyExpandableAdapter;
import cgg.com.threeapp.view.costomView.MyExpandableListView;
import cgg.com.threeapp.view.viewInterface.ICartView;
import cgg.com.threeapp.view.viewInterface.IHomeView;

/**
 * author: Wanderer
 * date:   2018/2/23 19:11
 * email:  none
 */

public class CartFragment extends Fragment implements IHomeView, ICartView {
    private RecyclerView cart_tuijian_recycler;
    private HomePresenterImpl homePresenter;
    private MyExpandableListView expandableLisetView;
    private static CartPresenterImpl cartPresenter;
    private static TextView totalPrice, totalCount;
    private CheckBox cart_allSelect;
    private MyExpandableAdapter myExpandableAdapter;
    private TextView cartNull, cart_login_button;
    private LinearLayout cart_login_view,cart_cart_view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View cartView = inflater.inflate(R.layout.cart_layout, container, false);
        cart_tuijian_recycler = cartView.findViewById(R.id.cart_tuijian_recycler);
        expandableLisetView = cartView.findViewById(R.id.cart_expandable);
        totalPrice = cartView.findViewById(R.id.toalPrice);
        totalCount = cartView.findViewById(R.id.cart_jiesuan);
        cart_allSelect = cartView.findViewById(R.id.cart_allSelect);
        cartNull = cartView.findViewById(R.id.cartNull);
        cart_login_view = cartView.findViewById(R.id.cart_login_view);
        cart_login_button = cartView.findViewById(R.id.cart_login_button);
        cart_cart_view = cartView.findViewById(R.id.cart_cart_view);

        homePresenter = new HomePresenterImpl(this);
        cartPresenter = new CartPresenterImpl(this);


        return cartView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        cart_tuijian_recycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        homePresenter.getHomeBean(API.INDEX_URL, new HashMap<String, String>());
        init();
        cart_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        initChenJin();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if(!hidden){
            initChenJin();
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void setHomeBean(HomeBean homeBean) {
        HomeBean.TuijianBean tuijian = homeBean.getTuijian();
        cart_tuijian_recycler.setAdapter(new HomeRecommendAdapter(tuijian, getActivity()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        homePresenter.destroy();
    }

    @Override
    public void setCartBean(final CartBean cartBean) {
        if (null == cartBean) {
            cartNull.setVisibility(View.VISIBLE);
            cart_cart_view.setVisibility(View.GONE);
            expandableLisetView.setVisibility(View.GONE);
        } else {
            if (cartBean.getData().size() > 0) {
                cartNull.setVisibility(View.GONE);
                cart_cart_view.setVisibility(View.VISIBLE);
                expandableLisetView.setVisibility(View.VISIBLE);
                myExpandableAdapter = new MyExpandableAdapter(cartBean, getActivity());
                myExpandableAdapter.jiesuanPrice();
                List<CartBean.DataBean> data = cartBean.getData();
                for (int i = 0; i < data.size(); i++) {
                    boolean b = myExpandableAdapter.setParentChecked(data.get(i));
                    data.get(i).setCheck(b);
                }
                expandableLisetView.setAdapter(myExpandableAdapter);
                // 默认展开所有的条目
                for (int i = 0; i < myExpandableAdapter.getGroupCount(); i++) {
                    expandableLisetView.expandGroup(i);
                }

                // 设置子条目不可以收缩
                expandableLisetView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                        // 返回true 设置不可以收缩
                        return true;
                    }
                });

                cart_allSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Log.e("AAA", isChecked + "");
                        myExpandableAdapter.setAllChildChecked(isChecked);
                    }
                });
            }else{
                cartNull.setVisibility(View.VISIBLE);
                expandableLisetView.setVisibility(View.GONE);
            }
        }

    }

    // 请求数据的方法
    public static void getData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", CommedUtil.getIntValue("uid") + "");
        cartPresenter.getCartBean(API.SELECT_CART, map);

    }

    public static void setTotalPrice(double totalPric) {
        totalPrice.setText("总价:￥ " + totalPric);
    }

    public static void setCount(int count) {
        totalCount.setText("去结算(" + count + ")");
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        if (CommedUtil.getBooleanValue("isLogin")) {
            getData();
            cart_login_view.setVisibility(View.GONE);
            expandableLisetView.setVisibility(View.VISIBLE);
            cart_cart_view.setVisibility(View.VISIBLE);

        } else {
            cartNull.setVisibility(View.VISIBLE);
            cart_login_view.setVisibility(View.VISIBLE);
            cart_cart_view.setVisibility(View.GONE);
            expandableLisetView.setVisibility(View.GONE);
        }
    }

    private void initChenJin() {
        ChenjinUtil.setStatusBarColor(getActivity(), getResources().getColor(R.color.colorGray));
    }
}
