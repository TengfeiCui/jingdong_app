package cgg.com.threeapp.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import cgg.com.threeapp.R;
import cgg.com.threeapp.model.bean.UserBean;
import cgg.com.threeapp.presenter.presenterImpl.GetUserInfoPresenterImpl;
import cgg.com.threeapp.presenter.presenterImpl.UserInfoPresenterImpl;
import cgg.com.threeapp.utils.API;
import cgg.com.threeapp.utils.CommedUtil;
import cgg.com.threeapp.utils.GlideImgManager;
import cgg.com.threeapp.view.viewInterface.IGetUserInfoView;
import cgg.com.threeapp.view.viewInterface.IUserInfoView;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener, IUserInfoView, IGetUserInfoView {
    private View pView;
    private TextView shangchuan, bendi, quxiao;
    private PopupWindow popupWindow;
    private View parent,pOther;
    private ImageView userInfoIcon, userInfo_back;
    //拍照保存的路径任意
    private String pic_path = Environment.getExternalStorageDirectory() + "/head.jpg";
    //裁剪完成之后图片保存的路径
    private String crop_icon_path = Environment.getExternalStorageDirectory() + "/head_icon.jpg";
    private UserInfoPresenterImpl userInfoPresenter;
    private GetUserInfoPresenterImpl getUserInfoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        pView = View.inflate(this, R.layout.push_icon_layout, null);
        parent = View.inflate(UserInfoActivity.this, R.layout.activity_user_info, null);

        shangchuan = pView.findViewById(R.id.shangchuan);
        quxiao = pView.findViewById(R.id.quxiao);
        bendi = pView.findViewById(R.id.bendi);
        pOther = pView.findViewById(R.id.pOther);
        userInfoIcon = findViewById(R.id.userInfoIcon);
        userInfo_back = findViewById(R.id.userInfo_back);

        popupWindow = new PopupWindow(pView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ColorDrawable dw = new ColorDrawable(0x30000000);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);

        shangchuan.setOnClickListener(this);
        bendi.setOnClickListener(this);
        quxiao.setOnClickListener(this);
        userInfo_back.setOnClickListener(this);
        pOther.setOnClickListener(this);

        loadIcon();

        userInfoPresenter = new UserInfoPresenterImpl(this);
        getUserInfoPresenter = new GetUserInfoPresenterImpl(this);
    }

    public void iconClick(View view) {
        View parent = findViewById(R.id.userParent);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shangchuan:
                paiZhao();
                break;
            case R.id.quxiao:
                break;
            case R.id.bendi:
                getImags();
                break;
            case R.id.userInfo_back:
                finish();
                break;
        }

        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    public void getImags() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 3000);

    }

    /**
     * 调用相机拍照的操作...隐式意图去调用相机
     */
    private void paiZhao() {

        Intent intent = new Intent();
        //指定动作...拍照的动作 CAPTURE...捕获
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        //给相机传递一个指令,,,告诉他拍照之后保存..MediaStore.EXTRA_OUTPUT向外输出的指令,,,指定存放的位置
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(pic_path)));

        //拍照的目的是拿到拍的图片
        startActivityForResult(intent, 1000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == RESULT_OK) {

            //拍照保存之后进行裁剪....根据图片的uri路径
            crop(Uri.fromFile(new File(pic_path)));
        }

        //获取相册图片
        if (requestCode == 3000 && resultCode == RESULT_OK) {
            //获取的是相册里面某一张图片的uri地址
            Uri uri = data.getData();

            //根据这个uri地址进行裁剪
            crop(uri);
        }

        if (requestCode == 2000 && resultCode == RESULT_OK) {
            //获取到裁剪完的图片
            Bitmap bitmap = data.getParcelableExtra("data");

            //拿到了bitmap图片 ..需要把bitmap图片压缩保存到文件中去..应该去做上传到服务器的操作了
            File saveIconFile = new File(crop_icon_path);

            if (saveIconFile.exists()) {
                saveIconFile.delete();
            }

            try {
                //创建出新的文件
                saveIconFile.createNewFile();

                FileOutputStream fos = new FileOutputStream(saveIconFile);
                //把bitmap通过流的形式压缩到文件中
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();

                //保存到sd卡中之后再去把文件上传到服务器
                HashMap<String, String> map = new HashMap<>();
                map.put("uid", CommedUtil.getIntValue("uid") + "");
                userInfoPresenter.uploadImage(API.UPLOAD_ICON_URL, saveIconFile, "touxiang.jpg", map);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    /**
     * 根据图片的uri路径进行裁剪
     *
     * @param uri
     */
    private void crop(Uri uri) {

        Intent intent = new Intent();

        //指定裁剪的动作
        intent.setAction("com.android.camera.action.CROP");

        //设置裁剪的数据(uri路径)....裁剪的类型(image/*)
        intent.setDataAndType(uri, "image/*");

        //执行裁剪的指令
        intent.putExtra("crop", "true");
        //指定裁剪框的宽高比
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        //指定输出的时候宽度和高度
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);

        //设置取消人脸识别
        intent.putExtra("noFaceDetection", false);
        //设置返回数据
        intent.putExtra("return-data", true);

        //
        startActivityForResult(intent, 2000);

    }


    @Override
    public void uploadSucceed() {
        Toast.makeText(this, "上传成功", Toast.LENGTH_SHORT).show();
        // 上传成功后获取用户信息
        HashMap<String, String> mmap = new HashMap<>();
        mmap.put("uid", CommedUtil.getIntValue("uid") + "");
        getUserInfoPresenter.getUserInfo(API.USER_INFO_URL, mmap);
    }

    @Override
    public void uploadFailure() {

    }

    @Override
    public void setUserInfo(UserBean userBean) {
        String iconUrl = userBean.getData().getIcon();
        //获取用户信息成功....拿到icon在服务器上的路径,然后加载显示头像
        GlideImgManager.glideLoader(UserInfoActivity.this, userBean.getData().getIcon(), R.drawable.user, R.drawable.user, userInfoIcon, 0);
        // 保存地址
        CommedUtil.setSharedString("icon", iconUrl);
        //获取用户信息成功....拿到icon在服务器上的路径,然后加载显示头像
    }

    private void loadIcon() {
        String iconUrl = CommedUtil.getStringValue("icon");
        if("isNull".equals(iconUrl)){
            userInfoIcon.setImageResource(R.drawable.user);
        }else{
            //获取用户信息成功....拿到icon在服务器上的路径,然后加载显示头像
            GlideImgManager.glideLoader(UserInfoActivity.this, iconUrl, R.drawable.user, R.drawable.user, userInfoIcon, 0);
        }

    }
}
