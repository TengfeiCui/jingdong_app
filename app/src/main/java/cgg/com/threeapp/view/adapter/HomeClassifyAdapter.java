package cgg.com.threeapp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import cgg.com.threeapp.R;
import cgg.com.threeapp.model.bean.ClassifyBean;

/**
 * author: Wanderer
 * date:   2018/2/23 22:05
 * email:  none
 */

public class HomeClassifyAdapter extends RecyclerView.Adapter<HomeClassifyAdapter.MSViewHodler> {
    private ClassifyBean classifyBean;
    private Context context;

    public HomeClassifyAdapter(ClassifyBean classifyBean, Context context) {
        this.classifyBean = classifyBean;
        this.context = context;
    }

    @Override
    public MSViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.home_classify_layout, null);
        return new MSViewHodler(view);
    }

    @Override
    public void onBindViewHolder(MSViewHodler holder, int position) {
        ImageView icon = holder.getIcon();
        TextView name = holder.getName();

        ClassifyBean.DataBean dataBean = classifyBean.getData().get(position);
        String urlIcon = dataBean.getIcon();
        final String nameStr = dataBean.getName();

        Glide.with(context).load(urlIcon).into(icon);
        name.setText(nameStr);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, nameStr, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return classifyBean.getData().size();
    }

    class MSViewHodler extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView name;

        public MSViewHodler(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.home_classify_icon);
            name = itemView.findViewById(R.id.home_classify_name);
        }


        public ImageView getIcon() {
            return icon;
        }

        public TextView getName() {
            return name;
        }
    }
}
