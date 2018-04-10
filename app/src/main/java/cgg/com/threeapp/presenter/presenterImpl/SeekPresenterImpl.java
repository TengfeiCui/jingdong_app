package cgg.com.threeapp.presenter.presenterImpl;

import java.util.Map;

import cgg.com.threeapp.model.bean.SeekBean;
import cgg.com.threeapp.model.modelImpl.SeekModelImpl;
import cgg.com.threeapp.model.modelInterface.ISeekModel;
import cgg.com.threeapp.presenter.presenterInterface.ISeekPresenter;
import cgg.com.threeapp.view.viewInterface.ISeekView;

/**
 * author: Wanderer
 * date:   2018/2/24 20:25
 * email:  none
 */

public class SeekPresenterImpl implements ISeekPresenter {
    private ISeekModel iSeekModel;
    private ISeekView iSeekView;

    public SeekPresenterImpl(ISeekView iSeekView) {
        this.iSeekView = iSeekView;
        iSeekModel = new SeekModelImpl(this);
    }

    @Override
    public void getSeekBean(String url, Map<String, String> map) {
        iSeekModel.getSeekBean(url,map);
    }

    @Override
    public void setSeekBean(SeekBean seekBean) {
        iSeekView.setSeekBean(seekBean);
    }
}
