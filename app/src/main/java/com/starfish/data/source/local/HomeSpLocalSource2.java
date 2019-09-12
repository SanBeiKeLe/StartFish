package com.starfish.data.source.local;

import android.os.Looper;
import android.text.TextUtils;

import com.starfish.utils.DataCacheUtils;
import com.starfish.utils.SPUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


// extends   HomeContract.IHomeDataSource
public class HomeSpLocalSource2 {

    private static final String BANNER_CACHE_KEY  = "BANNER";
    private static final String TOP_ARTICLE_CACHE_KEY  = "TOP_ARTICLE";
    private static final String NORMAL_ARTICLE_CACHE_KEY  = "NORMAL_ARTICLE";

/*
    @Override
    public Observable<List<Banner>> getBanner() {
       return  getDataListObservable(Banner.class,BANNER_CACHE_KEY);


    }

    @Override
    public Observable<List<Article>> getTopArticles() {
        return  getDataListObservable(Article.class, TOP_ARTICLE_CACHE_KEY);
    }

    @Override
    public Observable<ArticleData> getArticles() {
        return  getDataObservable(ArticleData.class, NORMAL_ARTICLE_CACHE_KEY).doOnNext(new Consumer<ArticleData>() {
            @Override
            public void accept(ArticleData articleData) throws Exception {

                articleData.setCurPage(-1);
            }
        });
    }*/

    private <T> Observable<T> getDataObservable(final Class<T> tClass,final String key){
            return Observable.create(new ObservableOnSubscribe<T>() {
                @Override
                public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                    String json = SPUtils.getValue(key);
                    if(!TextUtils.isEmpty(json)){
                        T data  = DataCacheUtils.convertDataFromJson(tClass,json);
                        if(data != null){
                            emitter.onNext(data);
                        }

                    }

                    emitter.onComplete();

                }
            });
    }

    private <T> Observable<List<T>> getDataListObservable(final Class<T> tClass,final String key){
        return Observable.create(new ObservableOnSubscribe<List<T>>() {
            @Override
            public void subscribe(ObservableEmitter<List<T>> emitter) throws Exception {
                String json = SPUtils.getValue(key);
                if(!TextUtils.isEmpty(json)){
                    List<T> data  = DataCacheUtils.convertDataListFromJson(tClass,json);
                    if(data != null){
                        emitter.onNext(data);
                    }

                }
                emitter.onComplete();

            }
        });
    }

   /* @Override
    public Observable<ArticleData> loadMoreArticles(int page) {

        return null;
    }

    @Override
    public void saveBanner(final List<Banner> banners) {
        saveTo(banners,BANNER_CACHE_KEY);

    }

    @Override
    public void saveTopArticles(final List<Article> articles) {
       saveTo(articles, TOP_ARTICLE_CACHE_KEY);
    }

    @Override
    public void saveNormalArticles(ArticleData articleData) {
        saveTo(articleData, NORMAL_ARTICLE_CACHE_KEY);
    }

*/


    private void saveTo(final Object o, final String key){
        if(Looper.getMainLooper().getThread() == Thread.currentThread()){ // 运行在主线程

            Observable.create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                    String json = DataCacheUtils.convertToJsonFromObject(o);
                    SPUtils.saveValueToDefaultSpByCommit(key, json);
                    emitter.onComplete();
                }
            }).subscribeOn(Schedulers.io()).subscribe();
        }else{
            String json = DataCacheUtils.convertToJsonFromObject(o);
            SPUtils.saveValueToDefaultSpByCommit(key, json);
        }
    }
}
