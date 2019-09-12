package com.starfish.data.source.local;

import android.os.Looper;
import android.text.TextUtils;

import com.starfish.utils.DataCacheUtils;
import com.starfish.utils.Logger;
import com.starfish.utils.SPUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


// extends HomeContract.IHomeDataSource
public class HomeSpLocalSource {

    public static final String BANNER_CACHE_KEY  = "BANNER";
    private static final String TOP_ARTICLE_CACHE_KEY  = "TOP_ARTICLE";
    private static final String NORMAL_ARTICLE_CACHE_KEY  = "NORMAL_ARTICLE";

   /* @Override
    public Observable<List<Banner>> getBanner() {


       return Observable.create(new ObservableOnSubscribe<List<Banner>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Banner>> emitter) throws Exception {
                String cacheJson = SPUtils.getValue(BANNER_CACHE_KEY);
                List<Banner> list = DataCacheUtils.convertDataListFromJson(Banner.class, cacheJson);
                if(list != null && list.size() > 0){
                    emitter.onNext(list);
                    Logger.d(" %s = %s main thread = %s", BANNER_CACHE_KEY,list.size(), Looper.getMainLooper().getThread() == Thread.currentThread());
                }


                 emitter.onComplete();
            }
        });
    }

    @Override
    public Observable<List<Article>> getTopArticles() {
        return  getDataListFromCache(Article.class, TOP_ARTICLE_CACHE_KEY);
    }

    @Override
    public Observable<ArticleData> getArticles() {
        return getDataFromCache(ArticleData.class, NORMAL_ARTICLE_CACHE_KEY).doOnNext(new Consumer<ArticleData>() {
            @Override
            public void accept(ArticleData articleData) throws Exception {
                if(articleData != null){
                    articleData.setCurPage(-1);
                }
            }
        });
    }

    @Override
    public Observable<ArticleData> loadMoreArticles(int page) {
        return null;
    }


    @Override
    public void saveBanner(List<Banner> banners) {
        if(banners == null || banners.size() == 0)
            return;
        saveTo(banners, BANNER_CACHE_KEY);
    }

    @Override
    public void saveTopArticles(List<Article> articles) {

        if(articles == null || articles.size() == 0)
            return;
        saveTo(articles, TOP_ARTICLE_CACHE_KEY);

    }

    @Override
    public void saveNormalArticles(ArticleData articleData) {

        if(articleData == null || articleData.getDatas() == null || articleData.getDatas().size() == 0)
            return;
        saveTo(articleData, NORMAL_ARTICLE_CACHE_KEY);
    }
*/


    private void saveTo(Object o,final String key){
        // 如果是在主线程调用该方法，那么得开启新的现场去保存
        if(Looper.getMainLooper().getThread() == Thread.currentThread()){

            Observable.just(o).doOnNext(new Consumer<Object>() {
                @Override
                public void accept(Object o) throws Exception {
                    String cacheString = DataCacheUtils.convertToJsonFromObject(o);
                    if(!TextUtils.isEmpty(cacheString)){
                        SPUtils.saveValueToDefaultSpByCommit(key,cacheString);
                    }

                }
            }).subscribeOn(Schedulers.io()).subscribe();
        }else{
            // 如果是在工作线程中，直接保存
            String cacheString = DataCacheUtils.convertToJsonFromObject(o);
            if(!TextUtils.isEmpty(cacheString)){
                SPUtils.saveValueToDefaultSpByCommit(key,cacheString);
            }
        }
    }

    private <T> Observable<List<T>> getDataListFromCache(final Class<T> aClass, final String key){

     return  Observable.create(new ObservableOnSubscribe<List<T>>() {
            @Override
            public void subscribe(ObservableEmitter<List<T>> emitter) throws Exception {
                String cacheJson = SPUtils.getValue(key);
                List<T> list = DataCacheUtils.convertDataListFromJson(aClass, cacheJson);
                if(list != null && list.size() > 0){
                    emitter.onNext(list);
                    Logger.d(" %s = %s", key,list.size());
                }
                emitter.onComplete();
            }
        });
    }

    private <T> Observable<T> getDataFromCache(final Class<T> aClass, final String key){

        return  Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                String cacheJson = SPUtils.getValue(key);
                T data = DataCacheUtils.convertDataFromJson(aClass, cacheJson);

                if(data != null){
                    emitter.onNext(data);
                    Logger.d(" %s = %s", key,data.toString());
                }
                emitter.onComplete();
            }
        });
    }
}
