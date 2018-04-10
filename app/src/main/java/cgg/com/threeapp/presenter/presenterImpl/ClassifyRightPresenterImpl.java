package cgg.com.threeapp.presenter.presenterImpl;

import java.util.Map;

import cgg.com.threeapp.model.bean.ClassifyBean;
import cgg.com.threeapp.model.bean.ClassifyRightBean;
import cgg.com.threeapp.model.modelImpl.ClassifyRightModelImpl;
import cgg.com.threeapp.model.modelInterface.IClassifyRightModel;
import cgg.com.threeapp.presenter.presenterInterface.IClassifyPresenter;
import cgg.com.threeapp.presenter.presenterInterface.IClassifyRightPresenter;
import cgg.com.threeapp.view.viewInterface.IClassifyRightView;

/**
 * author: Wanderer
 * date:   2018/2/24 19:04
 * email:  none
 */

public class ClassifyRightPresenterImpl implements IClassifyRightPresenter{
    private IClassifyRightModel iClassifyRightModel;
    private IClassifyRightView iClassifyRightView;

    public ClassifyRightPresenterImpl( IClassifyRightView iClassifyRightView) {
        iClassifyRightModel = new ClassifyRightModelImpl(this);
        this.iClassifyRightView = iClassifyRightView;
    }

    @Override
    public void getClassRifhtBean(String url, Map<String, String> map) {
        iClassifyRightModel.getClassifyBean(url,map);
    }

    @Override
    public void setClassRightBean(ClassifyRightBean classRightBean) {
        iClassifyRightView.setClassRightBean(classRightBean);
    }
}
