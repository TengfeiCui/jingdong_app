package cgg.com.threeapp.presenter.presenterInterface;

import java.util.Map;

import cgg.com.threeapp.model.bean.HomeBean;

/**
 * author: Wanderer
 * date:   2018/2/23 21:17
 * email:  none
 */

public interface IHomePresenter {
    void getHomeBean(String homeUrl, Map<String,String> map);
    void setHomeBean(HomeBean homeBean);
    void destroy();
}
