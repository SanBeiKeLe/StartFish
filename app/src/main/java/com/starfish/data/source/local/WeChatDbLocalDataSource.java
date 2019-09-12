package com.starfish.data.source.local;

import android.nfc.Tag;

import com.starfish.utils.Logger;
import com.starfish.utils.SystemFacade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


//extends WechatContract.IWeChatDataSouce
public class WeChatDbLocalDataSource  {

    private volatile static WeChatDbLocalDataSource mInstance;
    private WeChatDbLocalDataSource(){}

    public static WeChatDbLocalDataSource getInstance(){
        if(mInstance == null){
            synchronized (WeChatDbLocalDataSource.class){
                if(mInstance == null){
                    mInstance = new WeChatDbLocalDataSource();
                }
            }
        }

        return mInstance;
    }
/*
    @Override
    public Observable<ArrayList<WeChatTab>> getTab() {

        return Observable.create(new ObservableOnSubscribe<ArrayList<WeChatTab>>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList<WeChatTab>> emitter) throws Exception {
                if (SystemFacade.isMainThread()) {
                    throw new IllegalThreadStateException("查询数据库不能在UI 线程执行");
                }
                ArrayList<WeChatTab> weChatTabs = (ArrayList<WeChatTab>) WAApplication.getDaoSession().getWeChatTabDao().loadAll();

                emitter.onNext(weChatTabs);

                emitter.onComplete();

            }
        });



    }

    @Override
    public Observable<ArticleData> getArticlesByTab(final Long tabId, final int page) {

        return Observable.create(new ObservableOnSubscribe<ArticleData>() {
            @Override
            public void subscribe(ObservableEmitter<ArticleData> emitter) throws Exception {
                if (SystemFacade.isMainThread()) {
                    throw new IllegalThreadStateException("查询数据库不能在UI 线程执行");
                }
                // select * from ARTICLE where ? = tabId
                ArrayList<Article> articles = (ArrayList<Article>) WAApplication.getDaoSession().getArticleDao().queryRaw(" where " + ArticleDao.Properties.ChapterId.columnName + " =  ? ", String.valueOf(tabId));
                ArticleData articleData = new ArticleData();

                if (articles != null && articles.size() > 0) {
                    articleData.setDatas(articles);

                }
                emitter.onNext(articleData);

                emitter.onComplete();

            }
        });
    }


    @Override
    public void saveTab(final ArrayList<WeChatTab> weChatTabs) {


        if(SystemFacade.isListEmpty(weChatTabs))
            return;

        if (SystemFacade.isMainThread()) {
            Observable.just(weChatTabs).doOnNext(new Consumer<List<WeChatTab>>() {
                @Override
                public void accept(List<WeChatTab> articles) throws Exception {
                    deleteAndSaveTabs(weChatTabs);
                }
            }).subscribeOn(Schedulers.io()).subscribe();
        }else{
            deleteAndSaveTabs(weChatTabs);
        }


    }

    @Override
    public void saveArticles(final List<Article> articles) {

        if(articles == null && articles.size() == 0)
            return;

        if (SystemFacade.isMainThread()) {
            Observable.just(articles).doOnNext(new Consumer<List<Article>>() {
                @Override
                public void accept(List<Article> articles) throws Exception {
                    deleteAndSaveArticles(articles);
                }
            }).subscribeOn(Schedulers.io()).subscribe();
        }else{
            deleteAndSaveArticles(articles);
        }
    }


    private void deleteAndSaveArticles(List<Article> articles){

        ArticleDao articleDao = WAApplication.getDaoSession().getArticleDao();
        String tableName = articleDao.getTablename();
        int chapterId = articles.get(0).getChapterId();
        // 删除 数据库中 chapterId 等于 当前 文章列表里面某个文章的chapterId
        articleDao.getDatabase().execSQL("DELETE FROM " + tableName + " WHERE " + (ArticleDao.Properties.ChapterId.columnName) + " = " + chapterId);

        // 把文章列表插入到数据库中
        articleDao.insertInTx(articles);

        // 保存tags

        for(Article article : articles){
            for(Tag tag : article.getTags()){

               List<Tag>  tags = WAApplication.getDaoSession().getTagDao().queryRaw("where NAME = ? and url = ? ", tag.getName(),tag.getUrl());

                if(tags != null && tags.size() > 0){
                    tag.setId(tags.get(0).getId());
                }else{
                    WAApplication.getDaoSession().getTagDao().insert(tag);
                }



               // List<ArticleRelationTags> articleRelationTagsList = WAApplication.getDaoSession().getArticleRelationTagsDao().queryRaw("where ARTICLE_ID = ? and TAGS_ID = ? ",String.valueOf(article.getId()),String.valueOf(tag.getId()));

                //if(articleRelationTagsList == null || articleRelationTagsList.size() == 0){
                    ArticleRelationTags articleRelationTags = new ArticleRelationTags();
                    articleRelationTags.setArticleId(article.getId());
                    articleRelationTags.setTagsId(tag.getId());
                    WAApplication.getDaoSession().getArticleRelationTagsDao().insertOrReplace(articleRelationTags);
              //  }


            }

        }

        // 保存关系

    }
    private void deleteAndSaveTabs(List<WeChatTab> tabs){
        WeChatTabDao wechatTabDao = WAApplication.getDaoSession().getWeChatTabDao();
        if(Logger.DEBUG_D){
            List<WeChatTab> older =  wechatTabDao.loadAll();
            Logger.d("%s older = %s", "Test", Arrays.toString(older.toArray()));
        }

        wechatTabDao.deleteAll();
        wechatTabDao.insertInTx(tabs);


        if(Logger.DEBUG_D){
            List<WeChatTab> newD =  wechatTabDao.loadAll();
            Logger.d("%s new = %s", "Test", Arrays.toString(newD.toArray()));
        }

    }*/

    public static void destroy(){
        mInstance = null;
    }
}
