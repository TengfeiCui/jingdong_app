package cgg.com.threeapp.presenter.presenterInterface;

import java.util.Map;

import cgg.com.threeapp.model.bean.SeekBean;

/**
 * author: Wanderer
 * date:   2018/2/24 20:24
 * email:  none
 */

public interface ISeekPresenter {
    void getSeekBean(String url, Map<String,String>map);
    void setSeekBean(SeekBean seekBean);
}
