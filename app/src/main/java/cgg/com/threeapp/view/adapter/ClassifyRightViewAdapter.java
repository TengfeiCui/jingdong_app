package cgg.com.threeapp.view.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cgg.com.threeapp.R;
import cgg.com.threeapp.model.bean.ClassifyRightBean;

/**
 * author: Wanderer
 * date:   2018/2/24 19:10
 * email:  none
 */

public class ClassifyRightViewAdapter extends RecyclerView.Adapter<ClassifyRightViewAdapter.CRViewHolder> {
    private ClassifyRightBean classifyRightBean;
    private Context context;

    public ClassifyRightViewAdapter(ClassifyRightBean classifyRightBean, Context context) {
        this.classifyRightBean = classifyRightBean;
        this.context = context;
    }

    @Override
    public CRViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.classify_right_view_layout,null);
        return new CRViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CRViewHolder holder, int position) {
        RecyclerView recyclerView = holder.getRecyclerView();
        recyclerView.setLayoutManager(new GridLayoutManager(context,3));
        TextView textView = holder.getTextView();

        ClassifyRightBean.DataBean dataBean = classifyRightBean.getData().get(position);
        String name = dataBean.getName();
        List<ClassifyRightBean.DataBean.ListBean> list = dataBean.getList();
        recyclerView.setAdapter(new ClassifyRightGridAdapter(list,context));
        textView.setText(name);
    }

    @Override
    public int getItemCount() {
        return classifyRightBean.getData().size();
    }

    class CRViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private RecyclerView recyclerView;
        public CRViewHolder(View itemView) {
            super(itemView);
             textView = itemView.findViewById(R.id.classify_right_item_title);
             recyclerView = itemView.findViewById(R.id.classify_right_item_recycler);
        }

        public TextView getTextView() {
            return textView;
        }

        public RecyclerView getRecyclerView() {
            return recyclerView;
        }
    }
}
