package cgg.com.threeapp.presenter.presenterInterface;

import java.util.Map;

import cgg.com.threeapp.model.bean.CartBean;

/**
 * author: Wanderer
 * date:   2018/2/25 14:56
 * email:  none
 */

public interface ICartPresenter {
    void getCartBean(String url, Map<String,String>map);
    void setCartBean(CartBean cartBean);
}
