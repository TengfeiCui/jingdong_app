package cgg.com.threeapp.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.HashMap;
import java.util.Map;

import cgg.com.threeapp.R;
import cgg.com.threeapp.model.bean.SeekBean;
import cgg.com.threeapp.presenter.presenterImpl.SeekPresenterImpl;
import cgg.com.threeapp.utils.API;
import cgg.com.threeapp.view.adapter.SeekGridAdapter;
import cgg.com.threeapp.view.adapter.SeekLineAdapter;
import cgg.com.threeapp.view.viewInterface.ISeekView;

public class SeekActivity extends AppCompatActivity implements ISeekView, View.OnClickListener {
    private ImageView seek_back, seek_android, seek_layout;
    private RecyclerView seekRecycler_line,seekRecycler_grid;
    private SmartRefreshLayout smartRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek);

        seek_back = findViewById(R.id.seek_back);
        seek_android = findViewById(R.id.seek_android);
        seek_layout = findViewById(R.id.seek_layout);
        seekRecycler_line = findViewById(R.id.seekRecycler_line);
        seekRecycler_grid = findViewById(R.id.seekRecycler_grid);
        smartRefreshLayout = findViewById(R.id.seek_smart);

        seekRecycler_line.setLayoutManager(new LinearLayoutManager(this));
        seekRecycler_grid.setLayoutManager(new GridLayoutManager(this,2));

        seek_back.setOnClickListener(this);
        seek_android.setOnClickListener(this);
        seek_layout.setOnClickListener(this);

        smartRefreshLayout.setOnLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                smartRefreshLayout.finishLoadmore(1000);
                Toast.makeText(SeekActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                smartRefreshLayout.finishLoadmore(1000);
                Toast.makeText(SeekActivity.this, "加载成功", Toast.LENGTH_SHORT).show();
            }
        });

        String productName = getIntent().getStringExtra("productName");
        HashMap<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("keywords", productName);
        new SeekPresenterImpl(this).getSeekBean(API.SEEK_URL, map);
    }


    @Override
    public void setSeekBean(SeekBean seekBean) {
        Toast.makeText(this, "" + seekBean.getMsg(), Toast.LENGTH_SHORT).show();
        seekRecycler_line.setAdapter(new SeekLineAdapter(seekBean,this));
        seekRecycler_grid.setAdapter(new SeekGridAdapter(seekBean,this));
    }

    private boolean flag = true;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.seek_back:
                finish();
                break;
            case R.id.seek_android:
                Toast.makeText(this, "机器人", Toast.LENGTH_SHORT).show();
                break;
            case R.id.seek_layout:
                flag = !flag;
                if (flag) {
                    seek_layout.setImageResource(R.drawable.kind_liner);
                    seekRecycler_line.setVisibility(View.VISIBLE);
                    seekRecycler_grid.setVisibility(View.GONE);
                } else {
                    seek_layout.setImageResource(R.drawable.kind_grid);
                    seekRecycler_line.setVisibility(View.GONE);
                    seekRecycler_grid.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}
