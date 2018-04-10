package cgg.com.threeapp.model.modelImpl;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

import cgg.com.threeapp.model.bean.ClassifyBean;
import cgg.com.threeapp.model.bean.ClassifyRightBean;
import cgg.com.threeapp.model.modelInterface.IClassifyModel;
import cgg.com.threeapp.presenter.presenterInterface.IClassifyPresenter;
import cgg.com.threeapp.utils.CommedUtil;
import cgg.com.threeapp.utils.OkHttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * author: Wanderer
 * date:   2018/2/23 23:34
 * email:  none
 */

public class ClassifyModelIpml implements IClassifyModel {
    private IClassifyPresenter iClassifyPresenter;

    public ClassifyModelIpml(IClassifyPresenter iClassifyPresenter) {
        this.iClassifyPresenter = iClassifyPresenter;
    }

    @Override

    public void getClassifyBean(String classifyUrl, Map<String, String> map) {
        OkHttpUtil.doPost(classifyUrl, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String classifyJson = response.body().string();
                final ClassifyBean classifyBean = new Gson().fromJson(classifyJson, ClassifyBean.class);
                CommedUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (classifyBean != null)
                            iClassifyPresenter.setClassifyBean(classifyBean);
                    }
                });
            }
        });
    }
}
