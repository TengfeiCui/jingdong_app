package cgg.com.threeapp.model.modelImpl;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.Map;

import cgg.com.threeapp.model.bean.CartBean;
import cgg.com.threeapp.model.modelInterface.ICartModel;
import cgg.com.threeapp.presenter.presenterInterface.ICartPresenter;
import cgg.com.threeapp.utils.CommedUtil;
import cgg.com.threeapp.utils.OkHttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * author: Wanderer
 * date:   2018/2/25 14:58
 * email:  none
 */

public class CartModelImpl implements ICartModel {
    private ICartPresenter iCartPresenter;

    public CartModelImpl(ICartPresenter iCartPresenter) {
        this.iCartPresenter = iCartPresenter;
    }

    @Override
    public void getCartBean(String url, Map<String, String> map) {
        OkHttpUtil.doPost(url, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String cartJson = response.body().string();
                    try {
                        final CartBean cartBean = new Gson().fromJson(cartJson, CartBean.class);
                        CommedUtil.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iCartPresenter.setCartBean(cartBean);
                            }
                        });
                    }catch (JsonSyntaxException e){
                        Log.e("JsonSyntaxException","Json语法错误");
                        Log.e("JsonSyntaxException",""+e.toString());
                        Log.e("cartJson=",cartJson+"");
                    }



                }
            }
        });
    }
}
