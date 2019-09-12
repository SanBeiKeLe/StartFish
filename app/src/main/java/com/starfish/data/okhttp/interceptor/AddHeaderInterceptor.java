package com.starfish.data.okhttp.interceptor;

import android.text.TextUtils;

import com.starfish.utils.SPUtils;

import java.io.IOException;


import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class AddHeaderInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        String requestUrl = request.url().toString();


        Request.Builder builder = request.newBuilder();

        if(requestUrl.contains(AppConstant.WEB_SITE_COLLECTIONS) || requestUrl.contains(AppConstant.WEB_SITE_UNCOLLECTIONS)
               || requestUrl.contains(AppConstant.WEB_SITE_ARTICLE)){

            String cookies = SPUtils.getValue(AppConstant.LoginParamsKey.SET_COOKIE_KEY);

            if(!TextUtils.isEmpty(cookies)){
                builder.addHeader(AppConstant.LoginParamsKey.SET_COOKIE_NAME, cookies);
            }
        }

        return chain.proceed(builder.build());

    }



}
