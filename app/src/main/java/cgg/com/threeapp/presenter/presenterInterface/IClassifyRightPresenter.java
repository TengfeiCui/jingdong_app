package cgg.com.threeapp.presenter.presenterInterface;

import java.util.Map;

import cgg.com.threeapp.model.bean.ClassifyRightBean;

/**
 * author: Wanderer
 * date:   2018/2/24 19:04
 * email:  none
 */

public interface IClassifyRightPresenter {
    void getClassRifhtBean(String url,Map<String,String> map);
    void setClassRightBean(ClassifyRightBean classRightBean);
}
