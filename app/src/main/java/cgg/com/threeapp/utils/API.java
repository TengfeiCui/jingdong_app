package cgg.com.threeapp.utils;

/**
 * author: Wanderer
 * date:   2018/2/23 21:14
 * email:  none
 */

public class API {
    private static final String BASE_URL = "https://www.zhaoapi.cn/";
    // 首页
    public static final String INDEX_URL = "https://www.zhaoapi.cn/ad/getAd";
    // 分类
    public static final String INDEX_SORT_URL = "https://www.zhaoapi.cn/product/getCatagory";
    // 详情
    public static final String INDEX_DETAIL_URL = "https://www.zhaoapi.cn/product/getProductDetail";
    // 子分类
    public static final String CLASSIFY_RIGHT_URL = "https://www.zhaoapi.cn/product/getProductCatagory";
    // 搜索
    public static final String SEEK_URL = "https://www.zhaoapi.cn/product/searchProducts";
    // 登录
    public static final String LOGIN_URL = "https://www.zhaoapi.cn/user/login";
    // 查询购物车
    public static final String SELECT_CART = "https://www.zhaoapi.cn/product/getCarts";
    // 更新购物车 https://www.zhaoapi.cn/product/updateCarts?uid=71&sellerid=1&pid=1&selected=0&num=10
    public static final String UPDATE_CART_URL = "https://www.zhaoapi.cn/product/updateCarts";
    // 添加购物车
    public static final String ADD_CART_URL = "https://www.zhaoapi.cn/product/addCart";
    //删除购物车...product/deleteCart?uid=72&pid=1
    public static final String DELETE_CART_URL = BASE_URL+"product/deleteCart";
    // 注册
    public static final String SignIn_URL = "https://www.zhaoapi.cn/user/reg";
    //https://www.zhaoapi.cn/file/upload上传的服务器路径
    public static final String UPLOAD_ICON_URL = BASE_URL+"file/upload";
    //获取用户信息...https://www.zhaoapi.cn/user/getUserInfo
    public static final String USER_INFO_URL = BASE_URL+"user/getUserInfo";
    //创建订单...https://www.zhaoapi.cn/product/createOrder
    public static final String CREATE_ORDER_URL = BASE_URL+"product/createOrder";
    //订单信息....https://www.zhaoapi.cn/product/getOrders?uid=71
    public static final String ORDER_LIST_URL = BASE_URL+"product/getOrders";
    //修改订单状态...https://www.zhaoapi.cn/product/updateOrder?uid=71&status=1&orderId=1
    public static final String UPDATE_ORDER_URL = BASE_URL+"product/updateOrder";
    //查询默认地址...https://www.zhaoapi.cn/user/getDefaultAddr?uid=71
    public static final String GET_DEFAULT_ADDR_URL = BASE_URL+"user/getDefaultAddr";
    //添加新地址...https://www.zhaoapi.cn/user/addAddr?uid=71&addr=北京市昌平区金域国际1-1-1&mobile=18612991023&name=kson
    public static final String ADD_NEW_ADDR_URL = BASE_URL+"user/addAddr";
    //获取地址列表...https://www.zhaoapi.cn/user/getAddrs?uid=71
    public static final String GET_ALL_ADDR_URL = BASE_URL+"user/getAddrs";
    //设置默认地址...https://www.zhaoapi.cn/user/setAddr?uid=71&addrid=3&status=1
    public static final String SET_DEFAULT_ADDR_URL = BASE_URL+"user/setAddr";
}
