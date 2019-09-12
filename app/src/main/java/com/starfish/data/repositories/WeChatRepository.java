package com.starfish.data.repositories;

import android.annotation.SuppressLint;

import androidx.annotation.IntDef;

import com.starfish.base.BaseRepository;
import com.starfish.base.IBaseCallBackWithCache;
import com.starfish.data.source.local.WeChatDbLocalDataSource;
import com.starfish.data.source.remote.WeChatRemoteSource;
import com.starfish.utils.Logger;
import com.starfish.utils.SystemFacade;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import io.reactivex.functions.Consumer;


//implements WechatContract.IWeChatModel
@SuppressLint("StaticFieldLeak")
public class WeChatRepository extends BaseRepository  {


    private static final String TAG = "WeChatRepository";

    public static final int LOAD_TYPE_LOAD = 1;
    public static final int LOAD_TYPE_REFRESH = 2;
    public static final int LOAD_TYPE_MORE = 3;


    private volatile static WeChatRepository mInstance;

    @IntDef({LOAD_TYPE_LOAD, LOAD_TYPE_REFRESH, LOAD_TYPE_MORE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LoadType {

    }

//    private WechatContract.IWeChatDataSouce mLocal;
//    private WechatContract.IWeChatDataSouce mRemote;

  /*  private HashMap<Long, CacheEntry> mMemoryCache = new HashMap<>();

    public static WeChatRepository getInstance(WechatContract.IWeChatDataSouce local, WechatContract.IWeChatDataSouce remote) {
        if (mInstance == null) {
            synchronized (WeChatRepository.class) {
                if (mInstance == null) {
                    mInstance = new WeChatRepository(local, remote);
                }
            }
        }
        return mInstance;
    }




    private WeChatRepository(WechatContract.IWeChatDataSouce local, WechatContract.IWeChatDataSouce remote) {
        this.mLocal = local;
        this.mRemote = remote;
    }


    @Override
    public void getTabCacheFirst(final LifecycleProvider provider, final IBaseCallBackWithCache<ArrayList<WeChatTab>> callBack) {

        if (mLocal != null) {
           observerNoMap(provider, mLocal.getTab(), new IBaseCallBack<ArrayList<WeChatTab>>() {
                @Override
                public void onSuccess(ArrayList<WeChatTab> data) {
                    if (data.size() > 0) {
                        Collections.sort(data);
                        callBack.onCacheBack(data, IBaseCallBackWithCache.TYPE_CACHE_PERSISTENT);
                    }

                    requestTabs(provider, callBack);
                }

                @Override
                public void onFail(String msg) {
                    requestTabs(provider, callBack);
                }

            });
        } else {
            requestTabs(provider, callBack);
        }
    }

    @Override
    public void getArticlesByTabCacheFirst(final LifecycleProvider provider, final int type, final Long tabId, final int page, final IBaseCallBackWithCache<ArticleData> callBack) {
        if (type == LOAD_TYPE_LOAD) {
            CacheEntry cacheData = mMemoryCache.get(tabId);

            if ( cacheData != null && cacheData.articles.size() > 0) {
                ArticleData articleData = new ArticleData();
                articleData.setDatas(cacheData.articles);
                articleData.setCurPage(cacheData.page); //10
                callBack.onCacheBack(articleData, IBaseCallBackWithCache.TYPE_CACHE_MEMORY);
                return;
            }

            if(mLocal != null){
                observerNoMap(provider, mLocal.getArticlesByTab(tabId, page), new IBaseCallBack<ArticleData>() {
                    @Override
                    public void onSuccess(ArticleData data) {
                        if (data != null && !SystemFacade.isListEmpty(data.getDatas())) {
                            callBack.onCacheBack(data, IBaseCallBackWithCache.TYPE_CACHE_PERSISTENT);
                        } else {
                            requestArticle(provider, tabId, page, callBack);
                        }
                    }

                    @Override
                    public void onFail(String msg) {
                        requestArticle(provider, tabId, page, callBack);
                    }
                });
            }else{
                requestArticle(provider, tabId, page, callBack);
            }

        } else if (type == LOAD_TYPE_REFRESH) {
            requestArticle(provider, tabId, page, callBack);
        }

    }

    @Override
    public void loadMoreArticlesByTab(LifecycleProvider provider, Long tabId, int page, IBaseCallBack<ArticleData> callBack) {
        requestArticle(provider, tabId, page, callBack);
    }

    private void requestArticle(final LifecycleProvider provider, final Long tabId, final int page, final IBaseCallBack<ArticleData> callBack) {
        if (callBack instanceof IBaseCallBackWithCache) {
            ((IBaseCallBackWithCache<ArticleData>) callBack).onStartRequestServer();
        }

        observerNoMap(provider, mRemote.getArticlesByTab(tabId, page).doOnNext(new Consumer<ArticleData>() {
            @Override
            public void accept(ArticleData articleData) throws Exception {

                if ( articleData.getCurPage() < 2) { // 0 或者1 都表示第一页。
                    if(mLocal != null){
                        mLocal.saveArticles(articleData.getDatas());

                    }

                }

                saveToMemory(tabId, articleData);
            }
        }), callBack);
    }


    @Override
    public void getCacheTab(final LifecycleProvider provider, final IBaseCallBack<ArrayList<WeChatTab>> callBack) {

        observerNoMap(provider, mLocal.getTab(), new IBaseCallBack<ArrayList<WeChatTab>>() {
            @Override
            public void onSuccess(ArrayList<WeChatTab> data) {
                callBack.onSuccess(data);

            }

            @Override
            public void onFail(String msg) {
                callBack.onFail(msg);
            }

        });
    }

    @Override
    public void saveTabToCache(List<WeChatTab> weChatTabs) {
        if (mLocal != null) {
            mLocal.saveTab((ArrayList<WeChatTab>) weChatTabs);
        }
    }


    private void requestTabs(final LifecycleProvider provider, final IBaseCallBackWithCache<ArrayList<WeChatTab>> callBack) {
        callBack.onStartRequestServer();
        observerNoMap(provider, mRemote.getTab().doOnNext(new Consumer<ArrayList<WeChatTab>>() {
            @Override
            public void accept(ArrayList<WeChatTab> serverTabs) throws Exception {
                //TODO 需要合并 tabs 后才能插入数据库
                Logger.d("%s ---------------   收到服务器返回的tab数据   ------------------", TAG);
                if (mLocal != null) {
                    ArrayList<WeChatTab> localTabs = (ArrayList<WeChatTab>) WAApplication.getDaoSession().getWeChatTabDao().loadAll();

                    boolean isChange = false;

                    if (localTabs == null || localTabs.size() == 0) {

                        Logger.d("%s 本地数据库没有tab,因此按照服务器的 order 为 customOrder 字段排好序回调给 P 层，并存入数据库", TAG);

                        //按照服务器返回的order 字段排序，为什么要排序呢？因为服务器返回的数据不一定是排好序的，以防万一，重新排序一下
                        Collections.sort(serverTabs, new Comparator<WeChatTab>() {
                            @Override
                            public int compare(WeChatTab o1, WeChatTab o2) {
                                return o1.getOrder() - o2.getOrder();
                            }
                        });

                        // 按照排好序的属性，给我们本地用来排序的 customOrder 字段赋值。不然全是 0，以后我们加载本地tab时都是按照这个来排序
                        int order = 1;
                        for (WeChatTab tab : serverTabs) {
                            tab.setCustomOrder(order++);
                        }
                        // 保存到本地数据库
                        mLocal.saveTab(serverTabs);
                        return;
                    }

                    //serverTabs.remove(3);


                    if (serverTabs.size() != localTabs.size()) {
                        Logger.d("%s 本地数据保存的tab 数量和服务器返回的数量不一致，说明服务器的tab有改变，因此需要合并数据", TAG);
                        isChange = true;
                    } else {
                        Logger.d("%s 本地数据保存的tab 数量和服务器返回的数量一致，但是还需要一个一个比较，除了 id 和 customOrder 字段以外的其他字段是否一致", TAG);
                        WeChatTab server;
                        for (WeChatTab tab : localTabs) {

                            int index = serverTabs.indexOf(tab);
                            // 不等于-1 说明服务器有一个tab 和当前tab 的id 是一样的。那么在看看其他字段是否一样。如果一样就没有改变，
                            if (index != -1) {
                                // 如果其他字段也一样，那就说明本地的这个tab 目前在服务器上没有发生改变
                                server = serverTabs.get(index);

                                if (!WeChatTab.isChangeCompareTo(tab, server)) {
                                    if(Logger.DEBUG_D){
                                        Logger.d("%s 本地的 id = %s name =  %s 的 tab 和服务器的  id = %s name = %s 的tab 一样 ", TAG, tab.getId(), tab.getName(), server.getId(), server.getName());
                                    }
                                    // 继续比较下一个
                                    continue;
                                } else {
                                    Logger.d("%s 本地的 id = %s name =  %s 的 tab 和服务器的  id = %s name = %s 的tab 有属性发生了改变了 ", TAG, tab.getId(), tab.getName(), server.getId(), server.getName());
                                    Logger.d("%s local = %s", TAG, tab);
                                    Logger.d("%s server = %s", TAG, server);
                                }
                            } else {
                                Logger.d("%s id = %s name = %s 的 本地tab 在服务器返回的列表里面找不到了，因为 id =  %s 的tab 可能已经被服务干掉了", TAG, tab.getId(), tab.getName(), tab.getId());
                            }

                            isChange = true;
                            Logger.d("%s   ----- 经过比较发现 本地和tab 和 服务器的tab 存在不一致，因此需要做数据合并 ----- ", TAG);
                            break;
                        }

                    }


                    if (isChange) {
                        mergeTabs(localTabs, serverTabs);
                    } else {
                        Logger.d("%s   ----- 经过比较发现 本地的tab 和 服务器的tab 一致，因此就以界面上显示的本都数据为准，此次服务器请求tab 到此为止  ----- ", TAG);
                        // 由于是在doOnNext 方法里面，并且rxjava 要么发送数据要么发送失败， 不能发送 null ,也不能发送失败，也不能再次拦截（不回调给P层）因此直接把 服务器返回的列表情况，让view 层去判断，只要
                        serverTabs.clear();
                    }


                }
            }
        }), callBack);
    }


    private void mergeTabs(ArrayList<WeChatTab> localTabs, ArrayList<WeChatTab> serverWeChatTabs) {

        Logger.d(" +++   开始合并数据 +++   ", TAG);

        if (Logger.DEBUG_D) {
            Logger.d("%s 合并前服务器数据为:", TAG);
            for (WeChatTab tab : serverWeChatTabs) {
                Logger.d("%s    %s = %s ", TAG, tab.getId(), tab.getName());
            }
        }


        // 用来装新增的tab
        ArrayList<WeChatTab> serverNewTabs = new ArrayList<>();


        Iterator<WeChatTab> iterator = serverWeChatTabs.iterator();
        WeChatTab remoteTab;
        WeChatTab localTab;
        int localIndex = -1;
        while (iterator.hasNext()) {
            remoteTab = iterator.next();
            localIndex = localTabs.indexOf(remoteTab);
            if (localIndex != -1) { // 本地有这个tab。
                localTab = localTabs.get(localIndex);
                Logger.d("%s id = %s name = %s 的本地tab 在服务器上也以一个tab 和它的id 一样因此，把 本地的 selected 和 customOrder 字段赋值给服务器的tab,毕竟最终其他属性都要以服务器为准", TAG, localTab.getId(), localTab.getName());
                WeChatTab.mergeLocalToServer(localTab, remoteTab);
            } else {
                Logger.d("%s id = %s name = %s 的 tab 存在于服务器，但是本地没有，此tab 为新增的，", TAG, remoteTab.getId(), remoteTab.getName());
                serverNewTabs.add(remoteTab); // 加入到新增的列表里面
                remoteTab.setSelected(true); //新增的tab，默认都加入到用户选择的列表里面
                iterator.remove(); // 从原来服务器列表里面移除
            }

        }
        // 按照 customOrder 对服务器列表（除新增以外）进行排序
        Collections.sort(serverWeChatTabs);

        if (Logger.DEBUG_D) {
            Logger.d("%s 除去新增tab,和本地数据库合并后排序后为 :", TAG);
            for (WeChatTab tab : serverWeChatTabs) {
                Logger.d("%s    %s = %s ", TAG, tab.getId(), tab.getName());
            }
        }

        if (serverNewTabs.size() > 0) {
            Logger.d("%s 存在新增的tab,默认我们把新增的从插入到原有的第四个位置", TAG);
            serverWeChatTabs.addAll(3, serverNewTabs);
            int order = 1;
            for (WeChatTab tab : serverWeChatTabs) {
                tab.setCustomOrder(order++);
            }
        }
        if (Logger.DEBUG_D) {
            Logger.d("%s 把新增加的tab 加入后，排序后为", TAG);
            for (WeChatTab tab : serverWeChatTabs) {
                Logger.d("%s    %s = %s ", TAG, tab.getId(), tab.getName());
            }
        }

        Logger.d("%s +++++  清空本地数据库的所有数据把合并后服务器数保存到数据库,同时把新的tab 回调给 p 层。++++  ", TAG);
        mLocal.saveTab(serverWeChatTabs);

    }


    private void saveToMemory(Long tabId, ArticleData articleData) {

        CacheEntry cacheEntry = mMemoryCache.get(tabId);

        if (cacheEntry == null) {
            cacheEntry = new CacheEntry(articleData);
            mMemoryCache.put(tabId, cacheEntry);
        }else{

            if(articleData.getCurPage() < 2){// 第一次加载或者刷新
                cacheEntry.articles.clear();
            }
            cacheEntry.add(articleData);
        }


    }


    public class CacheEntry {
        public ArrayList<Article> articles;
        public int page;

        public CacheEntry(ArticleData articleData) {
            this.articles = new ArrayList<>();
            this.articles.addAll(articleData.getDatas());
            this.page = articleData.getCurPage();
            Logger.d("%s 缓存 %s %s 到内存 page = %s", TAG,articleData.getDatas().get(0).getAuthor(),articleData.getDatas().size(),page);
        }

        public void add(ArticleData articleData) {
            this.articles.addAll(articleData.getDatas());
            this.page = articleData.getCurPage();
            Logger.d("%s 缓存 %s %s 到内存 page = %s", TAG,articleData.getDatas().get(0).getAuthor(),articleData.getDatas().size(),page);
        }
    }*/

    public static void destroy() {
        mInstance = null;
        WeChatDbLocalDataSource.destroy();
        WeChatRemoteSource.destroy();
    }

}
