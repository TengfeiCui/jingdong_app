package cgg.com.threeapp.presenter.presenterInterface;

import java.util.Map;

import cgg.com.threeapp.model.bean.UserBean;

/**
 * author: Wanderer
 * date:   2018/2/27 23:22
 * email:  none
 */

public interface ISignInPresenter {
    void checkUserInfo(String url, Map<String,String>par);
    void signInFail(String msg);
    void signInSucceed(UserBean userBean);
}
