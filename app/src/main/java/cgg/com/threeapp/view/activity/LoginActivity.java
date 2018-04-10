package cgg.com.threeapp.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import cgg.com.threeapp.R;
import cgg.com.threeapp.model.bean.UserBean;
import cgg.com.threeapp.presenter.presenterImpl.LoginPresenterImpl;
import cgg.com.threeapp.utils.API;
import cgg.com.threeapp.utils.CommedUtil;
import cgg.com.threeapp.view.viewInterface.ILoginView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ILoginView {
    private ImageView mine_cha;
    private EditText login_user_mobile, login_user_pwd;
    private TextView login_mobile_signIn;
    private Button login_button;
    private LoginPresenterImpl loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mine_cha = findViewById(R.id.mine_cha);
        login_user_mobile = findViewById(R.id.login_user_mobile);
        login_user_pwd = findViewById(R.id.login_user_pwd);
        login_button = findViewById(R.id.login_button);
        login_mobile_signIn = findViewById(R.id.login_mobile_signIn);
        mine_cha = findViewById(R.id.mine_cha);

        mine_cha.setOnClickListener(this);
        login_mobile_signIn.setOnClickListener(this);
        login_button.setOnClickListener(this);

        loginPresenter = new LoginPresenterImpl(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_cha:
                finish();
                break;
            case R.id.login_mobile_signIn:
                Intent intent = new Intent(this, SIgnInActivity.class);
                startActivity(intent);
                break;
            case R.id.login_button:
                Toast.makeText(this, "login_button", Toast.LENGTH_SHORT).show();
                HashMap<String, String> map = new HashMap<>();
                map.put("mobile", login_user_mobile.getText().toString());
                map.put("password", login_user_pwd.getText().toString());
                loginPresenter.checkUserInfo(API.LOGIN_URL, map);
                break;
        }
    }

    @Override
    public void loginSucceed(UserBean userBean) {
        Toast.makeText(this, userBean.getMsg(), Toast.LENGTH_SHORT).show();
        UserBean.DataBean data = userBean.getData();
        CommedUtil.setSharedBoolean("isLogin", true); // 存储状态
        CommedUtil.setSharedInt("uid", data.getUid()); // 存储uid
        String iconUrl = data.getIcon();
        String userName = data.getUsername();
        if(null == iconUrl && "".equals(iconUrl)){
            iconUrl = "isNull";
        }
        if(null == userName && "".equals(userName)){
            iconUrl = "isNull";
        }
        CommedUtil.setSharedString("icon",iconUrl);
        CommedUtil.setSharedString("userName",userName);
        finish();
    }

    @Override
    public void loginFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
