package cgg.com.threeapp.presenter.presenterImpl;

import android.widget.ImageView;

import java.util.Map;

import cgg.com.threeapp.model.bean.DetailBean;
import cgg.com.threeapp.model.modelImpl.DetailModelImpl;
import cgg.com.threeapp.model.modelInterface.IDateilModel;
import cgg.com.threeapp.presenter.presenterInterface.IDetailPresenter;
import cgg.com.threeapp.view.viewInterface.IDetailView;

/**
 * author: Wanderer
 * date:   2018/2/23 23:05
 * email:  none
 */

public class DetailPresenterImpl implements IDetailPresenter{
    private IDetailView iDetailView;
    private IDateilModel iDateilModel;

    public DetailPresenterImpl(IDetailView iDetailView) {
        iDateilModel = new DetailModelImpl(this);
        this.iDetailView = iDetailView;
    }

    @Override
    public void setDetailBean(DetailBean detailBean) {
        iDetailView.setDetailBean(detailBean);
    }

    @Override
    public void getDetailBean(String detailUrl, Map<String, String> map) {
        iDateilModel.getDetailBean(detailUrl,map);
    }
}
