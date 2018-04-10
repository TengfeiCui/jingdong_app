package cgg.com.threeapp.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cgg.com.threeapp.R;
import cgg.com.threeapp.model.bean.ClassifyRightBean;
import cgg.com.threeapp.view.activity.SeekActivity;

/**
 * author: Wanderer
 * date:   2018/2/24 19:18
 * email:  none
 */

public class ClassifyRightGridAdapter extends RecyclerView.Adapter<ClassifyRightGridAdapter.GridViewHolder> {
    private List<ClassifyRightBean.DataBean.ListBean> list;
    private Context context;

    public ClassifyRightGridAdapter(List<ClassifyRightBean.DataBean.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.classify_right_item_griditem_layout, null);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, int position) {
        ImageView icon = holder.getIcon();
        TextView name = holder.getName();

        ClassifyRightBean.DataBean.ListBean listBean = list.get(position);
        String iconUrl = listBean.getIcon();
        final String nameStr = listBean.getName();

        Glide.with(context).load(iconUrl).into(icon);
        name.setText(nameStr);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SeekActivity.class);
                intent.putExtra("productName",nameStr);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class GridViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView name;

        public GridViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.classify_grid_icon);
            name = itemView.findViewById(R.id.classify_grid_name);
        }

        public ImageView getIcon() {
            return icon;
        }

        public TextView getName() {
            return name;
        }
    }
}
