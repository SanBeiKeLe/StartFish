package com.starfish.data.source.remote;

import com.starfish.base.ServerException;
import com.starfish.data.okhttp.WADataService;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
//extends WechatContract.IWeChatDataSouce
public class WeChatRemoteSource  {

    private volatile static WeChatRemoteSource mInstance;
    private WeChatRemoteSource(){}

    public static WeChatRemoteSource getInstance(){
        if(mInstance == null){
            synchronized (WeChatRemoteSource.class){
                if(mInstance == null){
                    mInstance = new WeChatRemoteSource();
                }
            }
        }

        return mInstance;
    }
/*

    @Override
    public Observable<ArrayList<WeChatTab>> getTab() {
       return WADataService.getService().getWechatTag().flatMap(new Function<HttpResult<ArrayList<WeChatTab>>, ObservableSource<ArrayList<WeChatTab>>>() {
            @Override
            public ObservableSource<ArrayList<WeChatTab>> apply(HttpResult<ArrayList<WeChatTab>> wechatTabHttpResult) throws Exception {
                if(wechatTabHttpResult.errorCode == 0 && wechatTabHttpResult.data != null && wechatTabHttpResult.data.size() > 0){

                    return Observable.just(wechatTabHttpResult.data);

                }
                return Observable.error(new ServerException(wechatTabHttpResult.errorMsg));
            }
        });
    }

    @Override
    public Observable<ArticleData> getArticlesByTab(Long tabId,int page) {
        return WADataService.getService().getArticleDataByTab(tabId,page).flatMap(new Function<HttpResult<ArticleData>, ObservableSource< ArticleData>>() {
            @Override
            public ObservableSource<ArticleData> apply(HttpResult<ArticleData> articleDataHttpResult) throws Exception {

                if(articleDataHttpResult.errorCode == 0 && articleDataHttpResult.data != null && articleDataHttpResult.data.getDatas() != null && articleDataHttpResult.data.getDatas().size() > 0){

                    return Observable.just(articleDataHttpResult.data);
                }
                return Observable.error(new ServerException(articleDataHttpResult.errorMsg));
            }
        });
    }*/
    public static void destroy(){
        mInstance = null;
    }


}
