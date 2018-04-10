package cgg.com.threeapp.presenter.presenterInterface;

import java.util.Map;

import cgg.com.threeapp.model.bean.DetailBean;

/**
 * author: Wanderer
 * date:   2018/2/23 23:04
 * email:  none
 */

public interface IDetailPresenter {
    void setDetailBean(DetailBean detailBean);
    void getDetailBean(String detailUrl, Map<String,String>map);
}
