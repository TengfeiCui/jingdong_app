package cgg.com.threeapp.presenter.presenterImpl;

import java.util.Map;

import cgg.com.threeapp.model.bean.UserBean;
import cgg.com.threeapp.model.modelImpl.GetUserInfoImpl;
import cgg.com.threeapp.model.modelInterface.IGetUserInfoModel;
import cgg.com.threeapp.presenter.presenterInterface.IGetUserInfoPresenter;
import cgg.com.threeapp.view.viewInterface.IGetUserInfoView;

/**
 * Created by Administrator on 2018-03-01.
 */

public class GetUserInfoPresenterImpl implements IGetUserInfoPresenter {
    private IGetUserInfoModel iGetUserInfoModel;
    private IGetUserInfoView iGetUserInfoView;

    public GetUserInfoPresenterImpl(IGetUserInfoView iGetUserInfoView) {
        iGetUserInfoModel = new cgg.com.threeapp.model.modelImpl.GetUserInfoImpl(this);
        this.iGetUserInfoView = iGetUserInfoView;
    }

    @Override
    public void setUserInfo(UserBean userBean) {
        iGetUserInfoView.setUserInfo(userBean);
    }

    @Override
    public void getUserInfo(String url, Map<String, String> map) {
        iGetUserInfoModel.getUserInfo(url,map);
    }
}
