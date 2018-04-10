package cgg.com.threeapp.view.viewInterface;

import cgg.com.threeapp.model.bean.UserBean;

/**
 * author: Wanderer
 * date:   2018/2/27 23:17
 * email:  none
 */

public interface ILoginView {
    void loginSucceed(UserBean userBean);
    void loginFail(String msg);
}
