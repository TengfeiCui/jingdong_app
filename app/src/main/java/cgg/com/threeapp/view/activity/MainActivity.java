package cgg.com.threeapp.view.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import cgg.com.threeapp.R;
import cgg.com.threeapp.utils.ChenjinUtil;
import cgg.com.threeapp.view.fragment.CartFragment;
import cgg.com.threeapp.view.fragment.ClassifyFragment;
import cgg.com.threeapp.view.fragment.DiscoverFragment;
import cgg.com.threeapp.view.fragment.HomeFragment;
import cgg.com.threeapp.view.fragment.MineFragment;
import cn.jpush.android.api.JPushInterface;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {


    private RadioGroup main_rg;
    private RadioButton main_discover_rb, main_mine_rb, main_cart_rb, main_index_rb, main_classify_rb;
    private FragmentManager fragmentManager;
    private HomeFragment fragmentIndex;
    private CartFragment fragmentCart;
    private ClassifyFragment fragmentClassify;
    private DiscoverFragment fragmentDiscover;
    private MineFragment fragmentMine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //沉浸
        ChenjinUtil.startChenJin(this);
        init();
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);
        // 默认显示的碎片
        fragmentIndex = new HomeFragment();
        FragmentTransaction f = fragmentManager.beginTransaction();
        f.add(R.id.main_frame,fragmentIndex).commit();
    }

    // 初始化配置
    public void init() {
        // 初始化控件
        main_rg = findViewById(R.id.main_rg);
        main_cart_rb = findViewById(R.id.main_cart_rb);
        main_index_rb = findViewById(R.id.main_home_rb);
        main_discover_rb = findViewById(R.id.main_discover_rb);
        main_mine_rb = findViewById(R.id.main_mine_rb);
        main_classify_rb = findViewById(R.id.main_classify_rb);

        // 获取事务管理者
        fragmentManager = getSupportFragmentManager();

        // 设置RadioGroup的监听
        main_rg.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        hintFragment(); // 隐藏所有的碎片

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // 通过隐藏显示切换碎片
        switch (checkedId) {
            case R.id.main_home_rb:
                fragmentTransaction.show(fragmentIndex);
                break;
            case R.id.main_classify_rb:
                if(fragmentClassify==null){
                    fragmentClassify = new ClassifyFragment();
                    fragmentTransaction.add(R.id.main_frame,fragmentClassify);
                }else{
                    fragmentTransaction.show(fragmentClassify);
                }
                break;
            case R.id.main_discover_rb:
                if(fragmentDiscover==null){
                    fragmentDiscover = new DiscoverFragment();
                    fragmentTransaction.add(R.id.main_frame,fragmentDiscover);
                }else{
                    fragmentTransaction.show(fragmentDiscover);
                }
                break;
            case R.id.main_cart_rb:
                if(fragmentCart==null){
                    fragmentCart = new CartFragment();
                    fragmentTransaction.add(R.id.main_frame,fragmentCart);
                }else{
                    fragmentTransaction.show(fragmentCart);
                }
                break;
            case R.id.main_mine_rb:
                if(fragmentMine==null){
                    fragmentMine = new MineFragment();
                    fragmentTransaction.add(R.id.main_frame,fragmentMine);
                }else{
                    fragmentTransaction.show(fragmentMine);
                }
                break;
        }
        fragmentTransaction.commit();
    }

    public void hintFragment() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (fragmentMine != null) ft.hide(fragmentMine);
        if (fragmentCart != null) ft.hide(fragmentCart);
        if (fragmentClassify != null) ft.hide(fragmentClassify);
        if (fragmentDiscover != null) ft.hide(fragmentDiscover);
        ft.hide(fragmentIndex).commit();
    }
}
