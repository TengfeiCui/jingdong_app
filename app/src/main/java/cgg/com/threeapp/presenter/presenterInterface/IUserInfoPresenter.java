package cgg.com.threeapp.presenter.presenterInterface;

import java.io.File;
import java.util.Map;

/**
 * Created by Administrator on 2018-02-28.
 */

public interface IUserInfoPresenter {
    void uploadImage(String url, File file, String fileName, Map<String, String> par);
    void uploadSucceed();
    void uploadFailure();
}
