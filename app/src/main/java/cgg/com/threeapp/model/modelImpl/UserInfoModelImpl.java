package cgg.com.threeapp.model.modelImpl;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import cgg.com.threeapp.model.modelInterface.IUserInfoModel;
import cgg.com.threeapp.presenter.presenterInterface.IUserInfoPresenter;
import cgg.com.threeapp.utils.CommedUtil;
import cgg.com.threeapp.utils.OkHttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2018-02-28.
 */

public class UserInfoModelImpl implements IUserInfoModel {
    private IUserInfoPresenter iUserInfoPresenter;

    public UserInfoModelImpl(IUserInfoPresenter iUserInfoPresenter) {
        this.iUserInfoPresenter = iUserInfoPresenter;
    }


    @Override
    public void uploadImage(String url, File file, String fileName, Map<String, String> par) {
        OkHttpUtil.uploadFile(url, file, fileName, par, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                iUserInfoPresenter.uploadFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                CommedUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        iUserInfoPresenter.uploadSucceed();
                    }
                });
            }
        });
    }
}
