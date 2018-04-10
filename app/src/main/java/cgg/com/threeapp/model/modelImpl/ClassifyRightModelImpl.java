package cgg.com.threeapp.model.modelImpl;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

import cgg.com.threeapp.model.bean.ClassifyRightBean;
import cgg.com.threeapp.model.modelInterface.IClassifyRightModel;
import cgg.com.threeapp.presenter.presenterInterface.IClassifyRightPresenter;
import cgg.com.threeapp.utils.CommedUtil;
import cgg.com.threeapp.utils.OkHttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * author: Wanderer
 * date:   2018/2/24 19:06
 * email:  none
 */

public class ClassifyRightModelImpl implements IClassifyRightModel {
    private IClassifyRightPresenter iClassifyRightPresenter;

    public ClassifyRightModelImpl(IClassifyRightPresenter iClassifyRightPresenter) {
        this.iClassifyRightPresenter = iClassifyRightPresenter;
    }

    @Override
    public void getClassifyBean(String url, Map<String, String> map) {
        OkHttpUtil.doPost(url, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                final ClassifyRightBean classifyRightBean = new Gson().fromJson(string, ClassifyRightBean.class);
                CommedUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        iClassifyRightPresenter.setClassRightBean(classifyRightBean);
                    }
                });
            }
        });
    }
}
