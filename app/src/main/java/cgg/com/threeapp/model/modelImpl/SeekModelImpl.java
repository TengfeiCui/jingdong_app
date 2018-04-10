package cgg.com.threeapp.model.modelImpl;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

import cgg.com.threeapp.model.bean.SeekBean;
import cgg.com.threeapp.model.modelInterface.ISeekModel;
import cgg.com.threeapp.presenter.presenterInterface.ISeekPresenter;
import cgg.com.threeapp.utils.CommedUtil;
import cgg.com.threeapp.utils.OkHttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * author: Wanderer
 * date:   2018/2/24 20:26
 * email:  none
 */

public class SeekModelImpl implements ISeekModel{
    private ISeekPresenter iSeekPresenter;

    public SeekModelImpl(ISeekPresenter iSeekPresenter) {
        this.iSeekPresenter = iSeekPresenter;
    }

    @Override
    public void getSeekBean(String url, Map<String, String> map) {
        OkHttpUtil.doPost(url, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String string = response.body().string();
                    final SeekBean seekBean = new Gson().fromJson(string, SeekBean.class);
                    CommedUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iSeekPresenter.setSeekBean(seekBean);
                        }
                    });
                }
            }
        });
    }
}
