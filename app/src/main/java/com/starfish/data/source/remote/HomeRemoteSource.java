package com.starfish.data.source.remote;

import com.starfish.base.ServerException;
import com.starfish.data.okhttp.WADataService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;


//extends HomeContract.IHomeDataSource
public class HomeRemoteSource  {

/*
    @Override
    public Observable<List<Banner>> getBanner() {

        return WADataService.getService().getBanners().flatMap(new Function<HttpResult<List<Banner>>, ObservableSource<List<Banner>>>() {
            @Override
            public ObservableSource<List<Banner>> apply(HttpResult<List<Banner>> listHttpResult) throws Exception {
                if (listHttpResult.errorCode == 0 && listHttpResult.data != null && listHttpResult.data.size() > 0) {


                    return Observable.just(listHttpResult.data);
                }
                return Observable.error(new ServerException(listHttpResult.errorMsg));
            }
        });
    }

    @Override
    public Observable<List<Article>> getTopArticles() {
        return WADataService.getService().getTopArticles().flatMap(new Function<HttpResult<List<Article>>, ObservableSource<List<Article>>>() {
            @Override
            public ObservableSource<List<Article>> apply(HttpResult<List<Article>> listHttpResult) throws Exception {
                if (listHttpResult.errorCode == 0 && listHttpResult.data != null && listHttpResult.data.size() > 0) {
                    return Observable.just(listHttpResult.data);
                }
                return Observable.error(new ServerException(listHttpResult.errorMsg));
            }
        });
    }

    @Override
    public Observable<ArticleData> getArticles() {
        return loadMoreArticles(0);
    }

    @Override
    public Observable<ArticleData> loadMoreArticles(int page) {
        return WADataService.getService().getArticleData(page).flatMap(new Function<HttpResult<ArticleData>, ObservableSource<ArticleData>>() {
            @Override
            public ObservableSource<ArticleData> apply(HttpResult<ArticleData> listHttpResult) throws Exception {
                if (listHttpResult.errorCode == 0 && listHttpResult.data != null && listHttpResult.data.getDatas() != null && listHttpResult.data.getDatas().size() > 0) {
                    return Observable.just(listHttpResult.data);
                }
                return Observable.error(new ServerException(listHttpResult.errorMsg));
            }
        });
    }*/


}
