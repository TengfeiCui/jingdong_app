package cgg.com.threeapp.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cgg.com.threeapp.R;
import cgg.com.threeapp.model.bean.SettingBean;
import cgg.com.threeapp.utils.CommedUtil;
import cgg.com.threeapp.utils.GlideImgManager;
import cgg.com.threeapp.view.adapter.SettingAdatepr;

public class UserSettingActivity extends AppCompatActivity {

    private TextView name,setting_back;
    private ImageView icon,rightJT;
    private RecyclerView recyclerView;
    private List<SettingBean > list;
    private String userName;
    private String icon1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(R.layout.activity_user_setting);
        icon = findViewById(R.id.setting_icon);
        name = findViewById(R.id.setting_name);
        recyclerView = findViewById(R.id.setting_recycler);
        rightJT = findViewById(R.id.setting_rightJT);
        setting_back = findViewById(R.id.setting_back);

        name.setText(userName);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SettingAdatepr(list,this));

        // 加载图像
        loadIcon();

        setting_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(UserSettingActivity.this)
                        .setMessage("确定退出登录吗？")
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CommedUtil.backLogin();
                                finish();
                            }
                        }).show();

            }
        });
    }

    private void loadIcon() {
        String iconUrl = CommedUtil.getStringValue("icon");
        if("isNull".equals(iconUrl)){
            icon.setImageResource(R.drawable.user);
        }else{
            //获取用户信息成功....拿到icon在服务器上的路径,然后加载显示头像
            GlideImgManager.glideLoader(UserSettingActivity.this, iconUrl, R.drawable.user, R.drawable.user, icon, 0);
        }

    }

    private void initData() {
        list = new ArrayList<SettingBean>();
        list.add(new SettingBean("会员俱乐部",""));
        list.add(new SettingBean("PLUS会员","每月领取100元优惠券"));
        list.add(new SettingBean("小白信用","79.4分"));
        list.add(new SettingBean("地址管理",""));
        list.add(new SettingBean("赠票资质","添加赠票资质"));
        list.add(new SettingBean("实名认证","领188元支付礼包"));
        list.add(new SettingBean("账号安全 ","免费领取百万账户险"));
        list.add(new SettingBean("支付设置","支付验证\\扣款顺序"));
        list.add(new SettingBean("关联账号",""));
        list.add(new SettingBean("设置","清除缓存\\关于等"));
        list.add(new SettingBean("我的定制","定制个性化卡片"));

        userName = CommedUtil.getStringValue("userName");
        icon1 = CommedUtil.getStringValue("icon");
        Log.e("icon",icon1+"囧囧");
    }

    public void onMyFinish(View view) {
        finish();
    }

    public void goUserInfo(View view) {
        Intent intent = new Intent(this, UserInfoActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 加载图像
        loadIcon();
    }


}
