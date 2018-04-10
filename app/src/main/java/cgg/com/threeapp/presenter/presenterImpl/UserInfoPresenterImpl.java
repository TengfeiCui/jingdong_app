package cgg.com.threeapp.presenter.presenterImpl;

import java.io.File;
import java.util.Map;

import cgg.com.threeapp.model.modelImpl.UserInfoModelImpl;
import cgg.com.threeapp.model.modelInterface.IUserInfoModel;
import cgg.com.threeapp.presenter.presenterInterface.IUserInfoPresenter;
import cgg.com.threeapp.view.viewInterface.IUserInfoView;

/**
 * Created by Administrator on 2018-02-28.
 */

public class UserInfoPresenterImpl implements IUserInfoPresenter {
    private IUserInfoModel iUserInfoModel;
    private IUserInfoView iUserInfoView;

    public UserInfoPresenterImpl( IUserInfoView iUserInfoView) {
        this.iUserInfoModel = new UserInfoModelImpl(this);
        this.iUserInfoView = iUserInfoView;
    }

    @Override
    public void uploadImage(String url, File file, String fileName, Map<String, String> par) {
        iUserInfoModel.uploadImage(url,file,fileName,par);
    }

    @Override
    public void uploadSucceed() {
        iUserInfoView.uploadSucceed();
    }

    @Override
    public void uploadFailure() {
        iUserInfoView.uploadFailure();
    }
}
