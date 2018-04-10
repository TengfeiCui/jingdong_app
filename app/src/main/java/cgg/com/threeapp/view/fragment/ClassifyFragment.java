package cgg.com.threeapp.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;

import cgg.com.threeapp.R;
import cgg.com.threeapp.model.bean.ClassifyBean;
import cgg.com.threeapp.model.bean.ClassifyRightBean;
import cgg.com.threeapp.presenter.presenterImpl.ClassifyPresenterImpl;
import cgg.com.threeapp.presenter.presenterImpl.ClassifyRightPresenterImpl;
import cgg.com.threeapp.utils.API;
import cgg.com.threeapp.utils.ChenjinUtil;
import cgg.com.threeapp.view.adapter.ClassifyLeftAdapter;
import cgg.com.threeapp.view.adapter.ClassifyRightViewAdapter;
import cgg.com.threeapp.view.adapter.HomeClassifyAdapter;
import cgg.com.threeapp.view.viewInterface.IClassifyRightView;
import cgg.com.threeapp.view.viewInterface.IClassifyView;

/**
 * author: Wanderer
 * date:   2018/2/23 19:11
 * email:  none
 */

public class ClassifyFragment extends Fragment implements IClassifyView, IClassifyRightView {

    private ListView leftView;
    private ClassifyPresenterImpl classifyPresenter;
    private RecyclerView rightView;
    private ClassifyRightPresenterImpl classifyRightPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View classifyView = inflater.inflate(R.layout.classify_layout, container, false);
        initChenJin();
        leftView = classifyView.findViewById(R.id.classify_left_view);
        rightView = classifyView.findViewById(R.id.classify_right_view);

        classifyPresenter = new ClassifyPresenterImpl(this);
        classifyRightPresenter = new ClassifyRightPresenterImpl(this);
        // 初始化View
        initView();
        return classifyView;
    }

    private void initView() {
        rightView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        classifyPresenter.getClassifyBean(API.INDEX_SORT_URL, new HashMap<String, String>());
        HashMap<String, String> map = new HashMap<>();
        map.put("cid", 1 + "");
        classifyRightPresenter.getClassRifhtBean(API.CLASSIFY_RIGHT_URL, map);
    }

    @Override
    public void setClassifyBean(final ClassifyBean classifyBean) {
        final ClassifyLeftAdapter classifyLeftAdapter = new ClassifyLeftAdapter(classifyBean, getActivity());
        leftView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 设置点击的条目
                classifyLeftAdapter.setI(position);
                // 刷新
                classifyLeftAdapter.notifyDataSetChanged();
                // 滚动屏幕的中心
                leftView.smoothScrollToPositionFromTop(position, (parent.getHeight() - view.getHeight()) / 2);
                HashMap<String, String> map = new HashMap<>();
                int cid = classifyBean.getData().get(position).getCid();
                map.put("cid", cid + "");
                classifyRightPresenter.getClassRifhtBean(API.CLASSIFY_RIGHT_URL, map);
            }
        });
        leftView.setAdapter(classifyLeftAdapter);
    }


    @Override
    public void setClassRightBean(ClassifyRightBean classRightBean) {
        rightView.setAdapter(new ClassifyRightViewAdapter(classRightBean, getActivity()));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (! hidden) {
            initChenJin();
        }
    }

    private void initChenJin() {
        ChenjinUtil.setStatusBarColor(getActivity(), getResources().getColor(R.color.colorGray));
    }
}
