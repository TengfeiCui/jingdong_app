package cgg.com.threeapp.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.HashMap;

import cgg.com.threeapp.R;
import cgg.com.threeapp.model.bean.DetailBean;
import cgg.com.threeapp.presenter.presenterImpl.DetailPresenterImpl;
import cgg.com.threeapp.utils.API;
import cgg.com.threeapp.utils.CommedUtil;
import cgg.com.threeapp.utils.OkHttpUtil;
import cgg.com.threeapp.view.viewInterface.IDetailView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DetailActivity extends AppCompatActivity implements IDetailView, View.OnClickListener {
    TextView detail_bargainPrice, detail_price, detail_title, lookCart, addCart;
    ImageView detail_icon;
    private int pid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String pid = getIntent().getStringExtra("pid");

        DetailPresenterImpl detailPresenter = new DetailPresenterImpl(this);
        HashMap<String, String> map = new HashMap<>();
        map.put("pid",pid);
        detailPresenter.getDetailBean(API.INDEX_DETAIL_URL,map);

        detail_bargainPrice = findViewById(R.id.detail_bargainPrice);
        detail_icon = findViewById(R.id.detail_icon);
        detail_price = findViewById(R.id.detail_price);
        detail_title = findViewById(R.id.detail_title);
        addCart = findViewById(R.id.addCart);
        lookCart = findViewById(R.id.lookCart);

        addCart.setOnClickListener(this);
        lookCart.setOnClickListener(this);
    }

    @Override
    public void setDetailBean(DetailBean detailBean) {
        DetailBean.DataBean data = detailBean.getData();
        detail_bargainPrice.setText("折扣价: " + data.getBargainPrice() + "元");
        detail_price.setText("原价: " + data.getPrice() + "元");
        detail_title.setText(data.getTitle());
        pid = data.getPid();
        Glide.with(this).load(data.getImages().split("\\|")[0]).into(detail_icon);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lookCart:
                Intent intent = new Intent(this, CartActivity.class);
                startActivity(intent);
                break;
            case R.id.addCart:
                if(CommedUtil.getBooleanValue("isLogin")){
                    Toast.makeText(this, "添加购物车成功", Toast.LENGTH_SHORT).show();
                    HashMap<String, String> map = new HashMap<>();
                    map.put("uid", CommedUtil.getIntValue("uid")+"");
                    map.put("pid",pid+"");
                    OkHttpUtil.doPost(API.ADD_CART_URL, map, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                        }
                    });
                }else{
                    new AlertDialog.Builder(DetailActivity.this)
                            .setTitle("消息提示")
                            .setMessage("您还没登录,现在去登录？")
                            .setNegativeButton("稍后再去",null)
                            .setPositiveButton("现在就去", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(DetailActivity.this, LoginActivity.class);
                                    DetailActivity.this.startActivity(intent);
                                }
                            }).show();
                }

                break;
        }
    }

    public void detail_back(View view) {
        finish();
    }
}
