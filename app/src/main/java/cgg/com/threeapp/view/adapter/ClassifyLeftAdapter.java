package cgg.com.threeapp.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

public class ClassifyLeftAdapter extends BaseAdapter {
    private ClassifyBean classifyBean;
    private Context context;
    private int i;

    public ClassifyLeftAdapter(ClassifyBean classifyBean, Context context) {
        this.classifyBean = classifyBean;
        this.context = context;
    }

    @Override
    public int getCount() {
        return classifyBean.getData().size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MSViewHodler hodler;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.classify_left_layout, null);
            TextView name = convertView.findViewById(R.id.classify_left_name);
            hodler = new MSViewHodler(name);
            convertView.setTag(hodler);
        } else {
            hodler = (MSViewHodler) convertView.getTag();
        }

        TextView name = hodler.getName();
        ClassifyBean.DataBean dataBean = classifyBean.getData().get(position);
        String nameStr = dataBean.getName();
        name.setText(nameStr);
        if (i == position) {
            convertView.setBackgroundResource(R.drawable.classify_left_background);
            name.setTextColor(Color.RED);
        } else {
            convertView.setBackgroundColor(Color.WHITE);
            name.setTextColor(Color.GRAY);
        }

        return convertView;
    }

    class MSViewHodler {
        private TextView name;

        public MSViewHodler(TextView textView) {
            this.name = textView;
        }

        public TextView getName() {
            return name;
        }

    }

    public void setI(int i) {
        this.i = i;
    }
}
