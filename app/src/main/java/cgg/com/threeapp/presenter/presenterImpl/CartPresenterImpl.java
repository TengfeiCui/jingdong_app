package cgg.com.threeapp.presenter.presenterImpl;

import java.util.Map;

import cgg.com.threeapp.model.bean.CartBean;
import cgg.com.threeapp.model.modelImpl.CartModelImpl;
import cgg.com.threeapp.model.modelInterface.ICartModel;
import cgg.com.threeapp.presenter.presenterInterface.ICartPresenter;
import cgg.com.threeapp.view.viewInterface.ICartView;

/**
 * author: Wanderer
 * date:   2018/2/25 14:57
 * email:  none
 */

public class CartPresenterImpl implements ICartPresenter{
    private ICartModel iCartModel;
    private ICartView iCartView;
    public CartPresenterImpl(ICartView iCartView) {
        this.iCartModel = new CartModelImpl(this);
        this.iCartView = iCartView;
    }


    @Override
    public void getCartBean(String url, Map<String, String> map) {
        iCartModel.getCartBean(url,map);
    }

    @Override
    public void setCartBean(CartBean cartBean) {
        iCartView.setCartBean(cartBean);
    }
}
