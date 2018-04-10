package cgg.com.threeapp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import cgg.com.threeapp.model.bean.HomeBean;

/**
 * author: Wanderer
 * date:   2018/2/23 22:27
 * email:  none
 */

public class HomeMenuAdapter extends RecyclerView.Adapter<HomeMenuAdapter.MenuViewHolder>{
    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MenuViewHolder extends RecyclerView.ViewHolder{
        public MenuViewHolder(View itemView) {
            super(itemView);
        }
    }
}
