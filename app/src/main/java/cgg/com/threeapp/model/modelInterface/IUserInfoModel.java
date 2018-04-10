package cgg.com.threeapp.model.modelInterface;

import java.io.File;
import java.util.Map;

/**
 * Created by Administrator on 2018-02-28.
 */

public interface IUserInfoModel {
    void uploadImage(String url,File file,String fileName, Map<String,String> par );
}
