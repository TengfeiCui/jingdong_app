package cgg.com.threeapp.presenter.presenterInterface;

import java.util.Map;

import cgg.com.threeapp.model.bean.ClassifyBean;

/**
 * author: Wanderer
 * date:   2018/2/23 23:32
 * email:  none
 */

public interface IClassifyPresenter {
    void setClassifyBean(ClassifyBean classifyBean);
    void getClassifyBean(String url, Map<String,String>map);
}
