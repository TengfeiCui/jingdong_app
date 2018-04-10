package cgg.com.threeapp.presenter.presenterImpl;

import java.util.Map;

import cgg.com.threeapp.model.bean.UserBean;
import cgg.com.threeapp.model.modelImpl.SignInModelImpl;
import cgg.com.threeapp.model.modelInterface.ISignInModel;
import cgg.com.threeapp.presenter.presenterInterface.ISignInPresenter;
import cgg.com.threeapp.view.viewInterface.ISignInView;

/**
 * author: Wanderer
 * date:   2018/2/27 23:24
 * email:  none
 */

public class SignInPresenterImpl implements ISignInPresenter {
    private ISignInView iSignInView;
    private ISignInModel iSignInModel;

    public SignInPresenterImpl(ISignInView iSignInView) {
        iSignInModel = new SignInModelImpl(this);
        this.iSignInView = iSignInView;
    }

    @Override
    public void checkUserInfo(String url, Map<String, String> par) {
        iSignInModel.checkUserInfo(url,par);
    }

    @Override
    public void signInFail(String msg) {
        iSignInView.signInFail(msg);
    }


    @Override
    public void signInSucceed(UserBean userBean) {
        iSignInView.signInSucceed(userBean);
    }


}
