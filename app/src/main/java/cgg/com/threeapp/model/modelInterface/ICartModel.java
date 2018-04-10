package cgg.com.threeapp.model.modelInterface;

import java.util.Map;

import cgg.com.threeapp.model.bean.CartBean;

/**
 * author: Wanderer
 * date:   2018/2/25 14:57
 * email:  none
 */

public interface ICartModel
{
    void getCartBean(String url, Map<String,String>map);
}
