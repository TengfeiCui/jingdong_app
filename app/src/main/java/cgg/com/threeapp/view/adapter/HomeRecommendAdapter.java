package cgg.com.threeapp.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import cgg.com.threeapp.R;
import cgg.com.threeapp.model.bean.HomeBean;
import cgg.com.threeapp.view.activity.DetailActivity;

/**
 * author: Wanderer
 * date:   2018/2/23 22:29
 * email:  none
 */

public class HomeRecommendAdapter extends RecyclerView.Adapter<HomeRecommendAdapter.RecommendViewHolder> {
    private HomeBean.TuijianBean tuijianBean;
    private Context context;

    public HomeRecommendAdapter(HomeBean.TuijianBean tuijianBean, Context context) {
        this.tuijianBean = tuijianBean;
        this.context = context;
    }

    @Override
    public RecommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View tjView = View.inflate(context, R.layout.home_tui_jian_layout, null);
        return new RecommendViewHolder(tjView);
    }

    @Override
    public void onBindViewHolder(RecommendViewHolder holder, int position) {
        ImageView icon = holder.getIcon();
        TextView price = holder.getPrice();
        TextView title = holder.getTitle();

        HomeBean.TuijianBean.ListBean listBean = tuijianBean.getList().get(position);
        double bargainPrice = listBean.getBargainPrice();
        String iconUrl = listBean.getImages().split("\\|")[0];
        String title1 = listBean.getTitle();

        Glide.with(context).load(iconUrl).into(icon);
        price.setText(bargainPrice+" 元");
        title.setText(title1);

        final int pid = listBean.getPid();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("pid",pid+"");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tuijianBean.getList().size();
    }

    class RecommendViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView title, price;

        public RecommendViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.home_tuiJian_item_icon);
            title = itemView.findViewById(R.id.home_tuiJian_item_title);
            price = itemView.findViewById(R.id.home_tuiJian_item_price);
        }

        public ImageView getIcon() {
            return icon;
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getPrice() {
            return price;
        }
    }
}
