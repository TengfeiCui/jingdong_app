package cgg.com.threeapp.model.modelImpl;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

import cgg.com.threeapp.model.bean.HomeBean;
import cgg.com.threeapp.model.modelInterface.IHomeModel;
import cgg.com.threeapp.presenter.presenterInterface.IHomePresenter;
import cgg.com.threeapp.utils.CommedUtil;
import cgg.com.threeapp.utils.OkHttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * author: Wanderer
 * date:   2018/2/23 21:20
 * email:  none
 */

public class HomeModelImpl implements IHomeModel {
    private IHomePresenter iHomePresenter;

    public HomeModelImpl(IHomePresenter iHomePresenter) {
        this.iHomePresenter = iHomePresenter;
    }

    @Override
    public void getHomeBean(String homeUrl, Map<String, String> map) {
        OkHttpUtil.doPost(homeUrl, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    String homeBeanJson = response.body().string();
                    final HomeBean homeBean = new Gson().fromJson(homeBeanJson, HomeBean.class);
                    CommedUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iHomePresenter.setHomeBean(homeBean);
                        }
                    });
                }
            }
        });
    }

}
