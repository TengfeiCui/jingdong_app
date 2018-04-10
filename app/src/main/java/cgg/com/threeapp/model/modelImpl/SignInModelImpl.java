package cgg.com.threeapp.model.modelImpl;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import cgg.com.threeapp.model.bean.UserBean;
import cgg.com.threeapp.model.modelInterface.ISignInModel;
import cgg.com.threeapp.presenter.presenterInterface.ISignInPresenter;
import cgg.com.threeapp.utils.CommedUtil;
import cgg.com.threeapp.utils.OkHttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * author: Wanderer
 * date:   2018/2/27 23:25
 * email:  none
 */

public class SignInModelImpl implements ISignInModel {
    private ISignInPresenter iSignInPresenter;

    public SignInModelImpl(ISignInPresenter iSignInPresenter) {
        this.iSignInPresenter = iSignInPresenter;
    }

    @Override
    public void checkUserInfo(String url, Map<String, String> par) {
        OkHttpUtil.doPost(url, par, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                CommedUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        iSignInPresenter.signInFail("请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String string = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(string);
                        final String code = jsonObject.getString("code");
                        final String msg = jsonObject.getString("msg");
                        CommedUtil.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if ("0".equals(code)) {
                                    UserBean userBean = new Gson().fromJson(string, UserBean.class);
                                    iSignInPresenter.signInSucceed(userBean);
                                } else {
                                    iSignInPresenter.signInFail(msg);
                                }

                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}
