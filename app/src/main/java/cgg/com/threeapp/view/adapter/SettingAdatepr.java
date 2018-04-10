package cgg.com.threeapp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cgg.com.threeapp.R;
import cgg.com.threeapp.model.bean.SettingBean;

/**
 * author: Wanderer
 * date:   2018/2/28 13:57
 * email:  none
 */

public class SettingAdatepr extends RecyclerView.Adapter<SettingAdatepr.SettingViewholder> {
    private List<SettingBean> list;
    private Context context;

    public SettingAdatepr(List<SettingBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public SettingViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.setting_function_layout, null);
        return new SettingViewholder(view);
    }

    @Override
    public void onBindViewHolder(SettingViewholder holder, int position) {
        TextView intro = holder.getIntro();
        TextView name = holder.getName();
        ImageView rjt = holder.getRjt();
        View haokan = holder.getHaokan();
        SettingBean settingBean = list.get(position);
        name.setText(settingBean.getFunctionName());
        intro.setHint(settingBean.getFunctionIntro());

        if(position%4==0){
            haokan.setVisibility(View.VISIBLE);
        }else{
            haokan.setVisibility(View.GONE);
        }
        rjt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "right", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SettingViewholder extends RecyclerView.ViewHolder {
        private ImageView rjt;
        private TextView intro, name;
        private View haokan;

        public SettingViewholder(View itemView) {
            super(itemView);
            intro = itemView.findViewById(R.id.setting_item_intro);
            name = itemView.findViewById(R.id.setting_item_name);
            rjt = itemView.findViewById(R.id.setting_item_rightJt);
            haokan = itemView.findViewById(R.id.haokan);
        }

        public ImageView getRjt() {
            return rjt;
        }

        public TextView getIntro() {
            return intro;
        }

        public TextView getName() {
            return name;
        }

        public View getHaokan() {
            return haokan;
        }
    }
}
