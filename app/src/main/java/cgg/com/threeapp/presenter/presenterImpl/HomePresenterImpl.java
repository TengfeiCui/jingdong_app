package cgg.com.threeapp.presenter.presenterImpl;

import java.util.Map;

import cgg.com.threeapp.model.bean.HomeBean;
import cgg.com.threeapp.model.modelImpl.HomeModelImpl;
import cgg.com.threeapp.model.modelInterface.IHomeModel;
import cgg.com.threeapp.presenter.presenterInterface.IHomePresenter;
import cgg.com.threeapp.view.viewInterface.IHomeView;

/**
 * author: Wanderer
 * date:   2018/2/23 21:19
 * email:  none
 */

public class HomePresenterImpl implements IHomePresenter {
    private IHomeModel iHomeModel;
    private IHomeView iHomeView;

    public HomePresenterImpl(IHomeView iHomeView) {
        iHomeModel = new HomeModelImpl(this);
        this.iHomeView = iHomeView;
    }

    @Override
    public void getHomeBean(String homeUrl, Map<String, String> map) {
        iHomeModel.getHomeBean(homeUrl, map);
    }

    @Override
    public void setHomeBean(HomeBean homeBean) {
        if (iHomeView != null)
            iHomeView.setHomeBean(homeBean);
    }

    @Override
    public void destroy() {
        iHomeView = null;
    }
}
