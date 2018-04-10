package cgg.com.threeapp.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * author: Wanderer
 * date:   2018/2/23 18:59
 * email:  none
 */

public class OkHttpUtil {
    /**
     * 利用懒汉式 加同步获取对象
     * 第一步:声明私有的静态变量
     * 第二步:私有的构造方法
     * 第三步:提供共有的方法返回一个实例
     */
    private static OkHttpClient okHttpClient;

    private OkHttpUtil() {
    }

    public static OkHttpClient getInstance() {
        // 第一个为空才进入，如果不加只要调用一次就会进入锁一次，加锁的目的是线程安全，防止多线程时同时操作
        if (okHttpClient == null) {
            synchronized (OkHttpUtil.class) {
                // 第二个即使进入了锁只有在对象没创建的时候才创建，如果没有这个，进来每个线程之后就会创建一个新的对象
                if (okHttpClient == null) {
                    // 指定缓存的路径
                    File cache = new File(Environment.getExternalStorageDirectory(), "cache");
                    // 指定缓存的大小
                    int cacheSize = 10 * 1024 * 1024;
                    // 构建OkHttpClient
                    okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(15, TimeUnit.SECONDS) // 连接超时
                            .writeTimeout(15, TimeUnit.SECONDS)    //
                            .readTimeout(15, TimeUnit.SECONDS)
                            .cache(new okhttp3.Cache(cache,cacheSize))
                            .addInterceptor(new MyInterceptor())//添加应用拦截器
                            .build();
                }
            }
        }
        return okHttpClient;
    }

    public static class MyInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            // 创建Map集合 添加公共参数
            HashMap<String, String> map = new HashMap<>();
            map.put("source", "android");
            Log.e("VVV", "MyInterceptor");

            // 获取拦截器的请求
            Request request = chain.request();
            // 获取请求的方式
            String method = request.method();
            // 获取请求的地址
            String oldUrl = request.url().toString();

            // 判断请求的方式
            if ("GET".equals(method)) {
                Log.e("AAA", "doget");
                // 创建一个字符串缓冲区
                StringBuilder sb = new StringBuilder();
                sb.append(oldUrl);

                if (oldUrl.contains("?")) {// 判断是否包含?

                    if (oldUrl.indexOf("?") == oldUrl.length() - 1) {
                        // nothing
                    } else {
                        // 拼接上&
                        sb.append("&");
                    }
                } else {
                    // 不包含? 拼接上?
                    sb.append("?");
                }

                // 添加公共参数
                for (String key : map.keySet()) {
                    sb.append(key + "=");
                    // Log.e("KEY",map.get(key));
                    sb.append(map.get(key) + "&");
                }

                // 删除最后一个&
                if (sb.indexOf("&") != -1) {
                    sb.deleteCharAt(sb.lastIndexOf("&"));
                }

                // 得到一个含有公共参数的新路径
                String newPath = sb.toString();
                Log.e("PATH", newPath);
                // 重新创建request请求
                request = new Request.Builder().url(newPath).build();

            } else if ("POST".equals(method)) {
                Log.e("AAA", "doPost");

                // 获取请求的实体 全部添加到新的FormBody
                RequestBody requestBody = request.body();

                if (requestBody instanceof FormBody) { // 判断左边的参数是不是右边参数的子类/实例对象
                    FormBody body = (FormBody) requestBody;
                    // 创建新的body
                    FormBody.Builder newBody = new FormBody.Builder();

                    // 将以前的body加入到新的body里面
                    for (int i = 0; i < body.size(); i++) {
                        String name = body.name(i);
                        String value = body.value(i);
                        newBody.add(name, value);
                    }

                    // 添加公共参数
                    for (String key : map.keySet()) {
                        newBody.add(key, map.get(key));
                    }
                    FormBody formBody = newBody.build();
                    // 构建一个新的请求
                    request = request.newBuilder().post(formBody).build();
                }
            }

            // 执行请求
            Response proceed = chain.proceed(request);
            return proceed;
        }
    }

    // doGet
    public static void doGet(String url, Callback callback) {
        // 获取OkHttpClient
        OkHttpClient client = OkHttpUtil.getInstance();
        // 创建Request
        Request request = new Request.Builder().url(url).build();
        // 获取ll
        Call call = client.newCall(request);
        // 执行异步请求
        call.enqueue(callback);
    }

    // doPost
    public static void doPost(String url, Map<String, String> parameter, Callback callback) {
        OkHttpClient client = OkHttpUtil.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        // 遍历Map
        for (String key : parameter.keySet()) {
            builder.add(key, parameter.get(key));
        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder().post(formBody).url(url).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static void uploadFile(String url, File file, String fileName,Map<String,String> params,Callback callback) {
        //创建OkHttpClient请求对象
        OkHttpClient okHttpClient = getInstance();

        //MultipartBody多功能的请求实体对象,,,formBody只能传表单形式的数据
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        //参数
        if (params != null){
            for (String key : params.keySet()){
                builder.addFormDataPart(key,params.get(key));
            }
        }
        //文件...参数name指的是请求路径中所接受的参数...如果路径接收参数键值是fileeeee,此处应该改变
        builder.addFormDataPart("file",fileName,RequestBody.create(MediaType.parse("application/octet-stream"),file));

        //构建
        MultipartBody multipartBody = builder.build();

        //创建Request
        Request request = new Request.Builder().url(url).post(multipartBody).build();

        //得到Call
        Call call = okHttpClient.newCall(request);
        //执行请求
        call.enqueue(callback);

    }

}
