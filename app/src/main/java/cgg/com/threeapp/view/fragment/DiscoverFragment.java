package cgg.com.threeapp.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cgg.com.threeapp.R;
import cgg.com.threeapp.utils.ChenjinUtil;

/**
 * author: Wanderer
 * date:   2018/2/23 19:11
 * email:  none
 */

public class DiscoverFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText("DiscoverFragment");
        return textView;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initChenJin();
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
