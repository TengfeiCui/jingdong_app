package cgg.com.threeapp.view.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cgg.com.threeapp.R;
import cgg.com.threeapp.model.bean.CartBean;
import cgg.com.threeapp.utils.API;
import cgg.com.threeapp.utils.CommedUtil;
import cgg.com.threeapp.utils.OkHttpUtil;
import cgg.com.threeapp.view.activity.CartActivity;
import cgg.com.threeapp.view.fragment.CartFragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * author: Wanderer
 * date:   2018/2/25 14:30
 * email:  none
 */

public class CartActivityExpandableAdapter extends BaseExpandableListAdapter {
    private CartBean cartBean;
    private Context context;
    private int allIndex;
    private int childIndex;
    private PopupWindow popupWindow;
    private TextView textView;

    public CartActivityExpandableAdapter(CartBean cartBean, Context context) {
        this.cartBean = cartBean;
        this.context = context;
        initPupWid();
    }

    @Override
    public int getGroupCount() {
        return cartBean.getData().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return cartBean.getData().get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.cart_parent_layout, null);
        }

        final CheckBox parent_cb = convertView.findViewById(R.id.ex_parent_che);
        TextView parent_sellerName = convertView.findViewById(R.id.ex_parent_sellerName);

        // 获取数据
        final CartBean.DataBean dataBean = cartBean.getData().get(groupPosition);
        String sellerName = dataBean.getSellerName();
        final boolean check = dataBean.getCheck();

        // 填充控件
        parent_sellerName.setText(sellerName);
        parent_cb.setChecked(check);

        // 设置监听事件
        parent_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击时 更改子布局里面checkbox是否选中
                CheckBox parentChe = (CheckBox) v;
                childIndex =0;
                //updateChildCheckedState(parentChe.isChecked(), dataBean);
                updateChildCheckedStateDG(parentChe.isChecked(), dataBean);
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.cart_child_layout, null);
        }

        CheckBox child_che = convertView.findViewById(R.id.ex_child_che);
        ImageView child_icon = convertView.findViewById(R.id.ex_child_icon);
        TextView child_addCount = convertView.findViewById(R.id.ex_child_addCount);
        TextView child_count = convertView.findViewById(R.id.ex_child_count);
        TextView child_jianCount = convertView.findViewById(R.id.ex_child_jianCount);
        TextView child_price = convertView.findViewById(R.id.ex_child_price);
        TextView child_title = convertView.findViewById(R.id.ex_child_title);

        // 获取数据
        final CartBean.DataBean.ListBean listBean = cartBean.getData().get(groupPosition).getList().get(childPosition);
        double bargainPrice = listBean.getBargainPrice();
        String iconUrl = listBean.getImages().split("\\|")[0];
        String title = listBean.getTitle();
        final int num = listBean.getNum();
        int selected = listBean.getSelected();


        // 填充控件
        Glide.with(context).load(iconUrl).into(child_icon);
        child_price.setText("￥ " + bargainPrice);
        child_count.setText("" + num);
        child_title.setText(title);
        if (selected == 1) {
            child_che.setChecked(true);
        } else {
            child_che.setChecked(false);
        }


        // 设置点击事件 加
        child_addCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> params = new HashMap<>();
                //?uid=71&sellerid=1&pid=1&selected=0&num=10
                params.put("uid", CommedUtil.getIntValue("uid") + "");
                params.put("sellerid", String.valueOf(listBean.getSellerid()));
                params.put("pid", String.valueOf(listBean.getPid()));
                params.put("selected", String.valueOf(listBean.getSelected()));
                //num+1
                params.put("num", String.valueOf(listBean.getNum() + 1));

                OkHttpUtil.doPost(API.UPDATE_CART_URL, params, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                       CartActivity.getData();
                    }
                });
            }
        });

        // 删除
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                Map<String, String> params = new HashMap<>();
                //?uid=71&sellerid=1&pid=1&selected=0&num=10
                params.put("uid", CommedUtil.getIntValue("uid") + "");
                params.put("pid", String.valueOf(listBean.getPid()));
                OkHttpUtil.doPost(API.DELETE_CART_URL, params, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        CartActivity.getData();
                    }
                });
            }
        });

        child_che.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox v1 = (CheckBox) v;
                int chess = 0;
                if (v1.isChecked()) {
                    chess = 1;
                } else {
                    chess = 0;
                }
                Map<String, String> params = new HashMap<>();
                //?uid=71&sellerid=1&pid=1&selected=0&num=10
                params.put("uid", CommedUtil.getIntValue("uid") + "");
                params.put("sellerid", String.valueOf(listBean.getSellerid()));
                params.put("pid", String.valueOf(listBean.getPid()));
                Log.e("isChecked", v1.isChecked() + "");
                params.put("selected", String.valueOf(chess));
                params.put("num", String.valueOf(listBean.getNum()));

                OkHttpUtil.doPost(API.UPDATE_CART_URL, params, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        CartActivity.getData();
                    }
                });
            }
        });

        // 设置点击事件 减
        child_jianCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> params = new HashMap<>();
                //?uid=71&sellerid=1&pid=1&selected=0&num=10
                params.put("uid", CommedUtil.getIntValue("uid") + "");
                params.put("sellerid", String.valueOf(listBean.getSellerid()));
                params.put("pid", String.valueOf(listBean.getPid()));
                params.put("selected", String.valueOf(listBean.getSelected()));
                //num+1
                params.put("num", String.valueOf(listBean.getNum() - 1));

                OkHttpUtil.doPost(API.UPDATE_CART_URL, params, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        CartActivity.getData();
                    }
                });
            }
        });

        // 设置长按的监听
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                popupWindow.showAsDropDown(v,0,-80, Gravity.CENTER);
                return false;
            }


        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    // 点击一级列表的checkbox时 更改其二级列表的checkbox
    private void updateChildCheckedState(boolean checked, CartBean.DataBean dataBean) {//
        // 遍历更改所有的列表的checkbox
        for (int i = 0; i < dataBean.getList().size(); i++) {
            CartBean.DataBean.ListBean listBean = dataBean.getList().get(i);
            HashMap<String, String> params = new HashMap<>();
            // params.put("uid", CommonUtils.getString("uid"));
            params.put("uid", CommedUtil.getIntValue("uid") + "");
            params.put("sellerid", String.valueOf(listBean.getSellerid()));
            params.put("pid", String.valueOf(listBean.getPid()));
            params.put("selected", String.valueOf(checked ? 1 : 0));
            params.put("num", String.valueOf(listBean.getNum()));

            OkHttpUtil.doPost(API.UPDATE_CART_URL, params, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    CartActivity.getData();
                }
            });
        }
    }

    // 遍历商家里面商品 如果商品全被选中 就让商家的选中
    public boolean setParentChecked(CartBean.DataBean dataBean) {
        for (int i = 0; i < dataBean.getList().size(); i++) {
            CartBean.DataBean.ListBean listBean = dataBean.getList().get(i);
            int selected = listBean.getSelected();
            if (selected == 0) {
                return false;
            }
        }
        return true;
    }

    private double price1 = 0;
    private int count1 = 0;

    public void jiesuanPrice() {
        price1 = 0;
        count1 = 0;
        for (int i = 0; i < cartBean.getData().size(); i++) {
            //遍历每一组里面的子孩子
            List<CartBean.DataBean.ListBean> list = cartBean.getData().get(i).getList();
            for (int j = 0; j < list.size(); j++) {
                //判断每一个子孩子是否选中,,,如果选中计算价格和数量
                if (list.get(j).getSelected() == 1) {
                    price1 = price1 + list.get(j).getBargainPrice() * list.get(j).getNum();
                    count1 += list.get(j).getNum();
                }
            }
        }

        CommedUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CartActivity.setCount(count1);
                CartActivity.setTotalPrice(price1);
            }
        });

    }


    public void updateChildCheckedStateDG(final boolean isAllChecked, final CartBean.DataBean dataBean){
        CartBean.DataBean.ListBean listBean = dataBean.getList().get(childIndex);
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", CommedUtil.getIntValue("uid") + "");
        params.put("sellerid", String.valueOf(listBean.getSellerid()));
        params.put("pid", String.valueOf(listBean.getPid()));
        params.put("selected", String.valueOf(isAllChecked ? 1 : 0));
        params.put("num", String.valueOf(listBean.getNum()));

        OkHttpUtil.doPost(API.UPDATE_CART_URL, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    childIndex++;
                    if(childIndex == dataBean.getList().size()){
                        CartActivity.getData();
                    }else{

                        updateChildCheckedStateDG(isAllChecked,dataBean);
                    }
                }
            }
        });
    }

    public void setAllChildChecked(boolean checked) {

        //改变购物车所有商品的选中状态...要有这些所有的商品...创建一个结合装所有的商品
        List<CartBean.DataBean.ListBean> allList = new ArrayList<>();
        for (int i = 0;i<cartBean.getData().size();i++) {
            List<CartBean.DataBean.ListBean> list = cartBean.getData().get(i).getList();
            for (int j = 0; j< list.size(); j++) {

                allList.add(list.get(j));
            }
        }
        allIndex = 0;//从第一条开始  索引是0
        //操作这个allList集合
        updateAllChild(allList,checked);
    }


    private void updateAllChild(final List<CartBean.DataBean.ListBean> allList, final boolean checked) {
        //第一条开始
        CartBean.DataBean.ListBean listBean = allList.get(allIndex);

        //更新购物车....跟新当前商品是否能选中的状态...selected值
        Map<String, String> params = new HashMap<>();
        //?uid=71&sellerid=1&pid=1&selected=0&num=10
        params.put("uid", CommedUtil.getIntValue("uid") + "");
        params.put("sellerid", String.valueOf(listBean.getSellerid()));
        params.put("pid", String.valueOf(listBean.getPid()));

        params.put("selected", String.valueOf(checked ? 1:0));

        params.put("num", String.valueOf(listBean.getNum()));

        OkHttpUtil.doPost(API.UPDATE_CART_URL, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    //成功之后索引+1
                    allIndex ++;
                    if (allIndex <allList.size()) {
                        //继续更新下一条
                        updateAllChild(allList,checked);
                    }else {
                        //代表全部更新完毕....请求查询购物车的数据
                        CartActivity.getData();
                    }

                }
            }
        });

    }

    public void initPupWid(){
        View pView = View.inflate(context,R.layout.cart_popup_layout,null);
        textView = pView.findViewById(R.id.cart_adapter_del);
        popupWindow = new PopupWindow(pView, LinearLayout.LayoutParams.MATCH_PARENT,80);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x30000000));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
    }
}
