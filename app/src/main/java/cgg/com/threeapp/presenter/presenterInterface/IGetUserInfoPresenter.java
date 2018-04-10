package cgg.com.threeapp.presenter.presenterInterface;

import java.util.Map;

import cgg.com.threeapp.model.bean.UserBean;

/**
 * Created by Administrator on 2018-03-01.
 */

public interface IGetUserInfoPresenter {
    void setUserInfo(UserBean userBean);
    void getUserInfo(String url, Map<String,String>map);
}
