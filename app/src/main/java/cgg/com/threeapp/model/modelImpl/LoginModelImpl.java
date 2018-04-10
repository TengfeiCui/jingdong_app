package cgg.com.threeapp.model.modelImpl;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

import cgg.com.threeapp.model.bean.UserBean;
import cgg.com.threeapp.model.modelInterface.ILoginModel;
import cgg.com.threeapp.presenter.presenterInterface.ILoginPresenter;
import cgg.com.threeapp.utils.CommedUtil;
import cgg.com.threeapp.utils.OkHttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * author: Wanderer
 * date:   2018/2/28 11:13
 * email:  none
 */

public class LoginModelImpl implements ILoginModel {
    private ILoginPresenter iLoginPresenter;

    public LoginModelImpl(ILoginPresenter iLoginPresenter) {
        this.iLoginPresenter = iLoginPresenter;
    }

    @Override
    public void checkUserInfo(String url, Map<String, String> par) {
        OkHttpUtil.doPost(url, par, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                CommedUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        iLoginPresenter.loginFail("请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    final UserBean userBean = new Gson().fromJson(response.body().string(), UserBean.class);
                    CommedUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if("0".equals(userBean.getCode())){
                                iLoginPresenter.loginSucceed(userBean);
                            }else{
                                iLoginPresenter.loginFail(userBean.getMsg());
                            }

                        }
                    });
                }
            }
        });
    }
}
