package cgg.com.threeapp.view.viewInterface;

import java.util.Map;

import cgg.com.threeapp.model.bean.UserBean;

/**
 * author: Wanderer
 * date:   2018/2/27 23:17
 * email:  none
 */

public interface ISignInView {
    void signInSucceed(UserBean userBean);
    void signInFail(String msg);
}
