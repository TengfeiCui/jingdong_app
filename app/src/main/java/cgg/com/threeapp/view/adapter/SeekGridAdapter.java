package cgg.com.threeapp.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cgg.com.threeapp.R;
import cgg.com.threeapp.model.bean.SeekBean;
import cgg.com.threeapp.view.activity.DetailActivity;

/**
 * author: Wanderer
 * date:   2018/2/24 20:52
 * email:  none
 */

public class SeekGridAdapter extends RecyclerView.Adapter<SeekGridAdapter.SeekGridViewholder> {
    private Context context;
    private SeekBean seekBean;

    public SeekGridAdapter(SeekBean seekBean,Context context) {
        this.context = context;
        this.seekBean = seekBean;
    }

    @Override
    public SeekGridViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.seek_grid_layout, null);
        return new SeekGridViewholder(view);
    }

    @Override
    public void onBindViewHolder(SeekGridViewholder holder, int position) {
        ImageView icon = holder.getIcon();
        TextView seekPrice = holder.getSeekPrice();
        TextView seekTitle = holder.getSeekTitle();

        SeekBean.DataBean dataBean = seekBean.getData().get(position);
        double bargainPrice = dataBean.getBargainPrice();
        String iconUrl = dataBean.getImages().split("\\|")[0];
        String title = dataBean.getTitle();
        final int pid = dataBean.getPid();

        Glide.with(context).load(iconUrl).into(icon);
        seekPrice.setText("折扣价: ￥"+bargainPrice);
        seekTitle.setText(title);

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
        return seekBean.getData().size();
    }

    class SeekGridViewholder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView seekTitle, seekPrice;

        public SeekGridViewholder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.seek_line_icon);
            seekTitle = itemView.findViewById(R.id.seek_title);
            seekPrice = itemView.findViewById(R.id.seek_price);
        }

        public ImageView getIcon() {
            return icon;
        }

        public TextView getSeekTitle() {
            return seekTitle;
        }

        public TextView getSeekPrice() {
            return seekPrice;
        }
    }
}
