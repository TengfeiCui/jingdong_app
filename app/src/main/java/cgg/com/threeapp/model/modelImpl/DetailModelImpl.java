package cgg.com.threeapp.model.modelImpl;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

import cgg.com.threeapp.model.bean.DetailBean;
import cgg.com.threeapp.model.modelInterface.IDateilModel;
import cgg.com.threeapp.presenter.presenterInterface.IDetailPresenter;
import cgg.com.threeapp.utils.CommedUtil;
import cgg.com.threeapp.utils.OkHttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * author: Wanderer
 * date:   2018/2/23 23:06
 * email:  none
 */

public class DetailModelImpl implements IDateilModel {
    private IDetailPresenter iDetailPresenter;

    public DetailModelImpl(IDetailPresenter iDetailPresenter) {
        this.iDetailPresenter = iDetailPresenter;
    }

    @Override
    public void getDetailBean(String detailUrl, Map<String, String> map) {
        OkHttpUtil.doPost(detailUrl, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               if(response.isSuccessful()) {
                   String detailUrl = response.body().string();
                   Log.e("AA",detailUrl);
                   final DetailBean detailBean = new Gson().fromJson(detailUrl, DetailBean.class);
                   CommedUtil.runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           iDetailPresenter.setDetailBean(detailBean);
                       }
                   });
               }
            }
        });
    }
}
