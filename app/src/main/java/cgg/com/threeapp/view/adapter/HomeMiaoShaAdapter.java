package cgg.com.threeapp.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import cgg.com.threeapp.R;
import cgg.com.threeapp.model.bean.HomeBean;
import cgg.com.threeapp.view.activity.WebViewActivity;

/**
 * author: Wanderer
 * date:   2018/2/23 22:05
 * email:  none
 */

public class HomeMiaoShaAdapter extends RecyclerView.Adapter<HomeMiaoShaAdapter.MSViewHodler> {
    private HomeBean.MiaoshaBean miaoshaBean;
    private Context context;

    public HomeMiaoShaAdapter(HomeBean.MiaoshaBean miaoshaBean, Context context) {
        this.miaoshaBean = miaoshaBean;
        this.context = context;
    }

    @Override
    public MSViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.home_miao_sha_layout, null);
        return new MSViewHodler(view);
    }

    @Override
    public void onBindViewHolder(MSViewHodler holder, int position) {
        ImageView icon = holder.getIcon();

        HomeBean.MiaoshaBean.ListBeanX listBeanX = miaoshaBean.getList().get(position);
        String msUrl = listBeanX.getImages().split("\\|")[0];
        final String detailUrl = listBeanX.getDetailUrl();

        Glide.with(context).load(msUrl).into(icon);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("bannerUrl",detailUrl);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return miaoshaBean.getList().size();
    }

    class MSViewHodler extends RecyclerView.ViewHolder {
        private ImageView icon;

        public MSViewHodler(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.home_miaoSha_item_icon);
        }


        public ImageView getIcon() {
            return icon;
        }
    }
}
