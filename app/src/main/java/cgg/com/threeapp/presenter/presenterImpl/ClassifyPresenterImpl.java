package cgg.com.threeapp.presenter.presenterImpl;

import java.util.Map;

import cgg.com.threeapp.model.bean.ClassifyBean;
import cgg.com.threeapp.model.modelImpl.ClassifyModelIpml;
import cgg.com.threeapp.model.modelInterface.IClassifyModel;
import cgg.com.threeapp.presenter.presenterInterface.IClassifyPresenter;
import cgg.com.threeapp.view.viewInterface.IClassifyView;

/**
 * author: Wanderer
 * date:   2018/2/23 23:32
 * email:  none
 */

public class ClassifyPresenterImpl implements IClassifyPresenter {
    private IClassifyModel iClassifyModel;
    private IClassifyView iClassifyView;

    public ClassifyPresenterImpl(IClassifyView iClassifyView) {
        iClassifyModel  = new ClassifyModelIpml(this);
        this.iClassifyView = iClassifyView;
    }

    @Override
    public void setClassifyBean(ClassifyBean classifyBean) {
        iClassifyView.setClassifyBean(classifyBean);
    }

    @Override
    public void getClassifyBean(String url, Map<String, String> map) {
        iClassifyModel.getClassifyBean(url,map);
    }
}
