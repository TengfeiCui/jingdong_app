package cgg.com.threeapp.model.modelImpl;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

import cgg.com.threeapp.model.bean.UserBean;
import cgg.com.threeapp.model.modelInterface.IGetUserInfoModel;
import cgg.com.threeapp.presenter.presenterInterface.IGetUserInfoPresenter;
import cgg.com.threeapp.utils.CommedUtil;
import cgg.com.threeapp.utils.OkHttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2018-03-01.
 */

public class GetUserInfoImpl implements IGetUserInfoModel {
    private IGetUserInfoPresenter iGetUserInfoPresenter;

    public GetUserInfoImpl(IGetUserInfoPresenter iGetUserInfoPresenter) {
        this.iGetUserInfoPresenter = iGetUserInfoPresenter;
    }

    @Override
    public void getUserInfo(String url, Map<String, String> map) {
        OkHttpUtil.doPost(url, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String json = response.body().string();
                    final UserBean userBean = new Gson().fromJson(json, UserBean.class);
                    CommedUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iGetUserInfoPresenter.setUserInfo(userBean);
                        }
                    });
                }
            }
        });
    }
}
