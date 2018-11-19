package com.blessingsoftware.myweather.android.uitls;
//本工具类使用OkHttp进行交互，调用sendOkHttpRequest()传入请求地址
//在注册一个回调以处理服务器的响应
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
