package com.starfish.data.repositories;

import androidx.annotation.NonNull;

import com.starfish.base.BaseRepository;
import com.starfish.base.IBaseCallBack;
import com.starfish.utils.ObjectUtils;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
//implements HomeContract.IHomeMode
public class HomeRepository2 extends BaseRepository  {

//    private HomeContract.IHomeDataSource mRemote;
//    private HomeContract.IHomeDataSource mLocal;
//
//
//    public HomeRepository2(@NonNull HomeContract.IHomeDataSource remote, HomeContract.IHomeDataSource local) {
//        ObjectUtils.requireNonNull(remote, "remote source is null");
//        mRemote = remote;
//        mLocal = local;
//    }
//
//
//    @Override
//    public void getTopArticles(LifecycleProvider provider, int type, IBaseCallBack<List<Article>> callBack) {
//
//        Observable<List<Article>> localObservable = null;
//        Observable<List<Article>> remoteObservable = mRemote.getTopArticles();
//
//        if (type == HomeRepository.HomeLoadType.LOAD_TYPE_LOAD && mLocal != null) {
//            localObservable = mLocal.getTopArticles();
//
//
//        }
//
//
//
//        process(provider, localObservable, remoteObservable, new Consumer<List<Article>>() {
//            @Override
//            public void accept(List<Article> articles) throws Exception {
//                if(mLocal != null){
//                    mLocal.saveTopArticles(articles);
//                }
//            }
//        }, callBack);
//
//    }
//
//    @Override
//    public void getArticles(LifecycleProvider provider, int type, IBaseCallBack<ArticleData> callBack) {
//
//        Observable<ArticleData> localObservable = null;
//
//        Observable<ArticleData> remoteObservable = mRemote.getArticles();
//        if (type == HomeRepository.HomeLoadType.LOAD_TYPE_LOAD && mLocal != null) {
//            localObservable = mLocal.getArticles();
//        }
//
//
//        process(provider, localObservable, remoteObservable, new Consumer<ArticleData>() {
//            @Override
//            public void accept(ArticleData articleData) throws Exception {
//                if(mLocal != null){
//                    mLocal.saveNormalArticles(articleData);
//                }
//            }
//        }, callBack);
//    }
//
//    @Override
//    public void loadMoreArticles(LifecycleProvider provider, int type, int page, IBaseCallBack<ArticleData> callBack) {
//
//        process(provider, null, mRemote.loadMoreArticles(page), null, callBack);
//    }
//
//
//    private <R> void process(LifecycleProvider provider, Observable<R> localObservable,Observable<R> remoteObservable, Consumer<R> doOnNext, IBaseCallBack<R> callBack){
//
//        if (mLocal != null && doOnNext != null) {
//            // 服务器数据如果加载成功，不管是第一次还是刷新，再回调各 presenter 时先保存到本地，
//
//            remoteObservable = remoteObservable.doOnNext(doOnNext);
//
//        }
//
//        //通过 concat 连接操作符，实现 本地有数据先读本地，等本地读取完成后，再读取服务器，包装本地一定发发再服务器之前。
//       //Observable<R> resultObservable = localObservable == null ? remoteObservable : Observable.concat(localObservable, remoteObservable);
//
//        Observable<R> resultObservable = null;
//
//        if(localObservable == null){
//            resultObservable = remoteObservable;
//        }else{
//            resultObservable = Observable.concat(localObservable,remoteObservable).firstOrError().toObservable();
//        }
//        observerNoMap(provider, resultObservable, callBack);
//
//    }
//
//
//    @Override
//    public void getBanner(LifecycleProvider provider, int type, IBaseCallBack<List<Banner>> callBack) {
//        Observable<List<Banner>> localObservable = null;
//        Observable<List<Banner>> remoteObservable = mRemote.getBanner();
//
//
//        // 如果是第一次加载 则需要线去加载缓存
//        if (type == HomeRepository.HomeLoadType.LOAD_TYPE_LOAD && mLocal != null) {
//            localObservable = mLocal.getBanner();
//
//        }
//
//
//        process(provider, localObservable, remoteObservable, new Consumer<List<Banner>>() {
//            @Override
//            public void accept(List<Banner> banners) throws Exception {
//                if(mLocal != null && banners != null && banners.size() > 0){
//                    mLocal.saveBanner(banners);
//                }
//            }
//        }, callBack);
//    }
}
