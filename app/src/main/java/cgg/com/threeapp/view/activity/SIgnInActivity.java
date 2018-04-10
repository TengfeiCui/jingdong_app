package cgg.com.threeapp.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;

import cgg.com.threeapp.R;
import cgg.com.threeapp.model.bean.UserBean;
import cgg.com.threeapp.presenter.presenterImpl.SignInPresenterImpl;
import cgg.com.threeapp.utils.API;
import cgg.com.threeapp.view.viewInterface.ISignInView;

public class SIgnInActivity extends AppCompatActivity implements View.OnClickListener ,ISignInView{
    private Button login,sign;
    private EditText mobile,pwd;
    private ImageView mback;
    private SignInPresenterImpl signInPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mobile = findViewById(R.id.sign_user_mobile);
        pwd = findViewById(R.id.sign_user_pwd);
        mback = findViewById(R.id.sign_finish);
        login  =findViewById(R.id.sign_login_button);
        sign = findViewById(R.id.sign_sign_button);

        // 设置监听
        sign.setOnClickListener(this);
        login.setOnClickListener(this);
        mback.setOnClickListener(this);

        signInPresenter = new SignInPresenterImpl(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_finish:
                finish();
                break;
            case R.id.sign_login_button:
                finish();
                break;
            case R.id.sign_sign_button:
                HashMap<String, String> map = new HashMap<>();
                map.put("mobile",mobile.getText().toString());
                map.put("password",pwd.getText().toString());
                signInPresenter.checkUserInfo(API.SignIn_URL,map);
                break;
        }
    }

    @Override
    public void signInSucceed(UserBean userBean) {
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void signInFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
