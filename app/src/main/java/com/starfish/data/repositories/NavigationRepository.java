package com.starfish.data.repositories;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.starfish.base.BaseRepository;
import com.starfish.base.IBaseCallBackWithCache;
import com.starfish.base.ServerException;
import com.starfish.data.okhttp.WADataService;
import com.starfish.utils.DataCacheUtils;
import com.starfish.utils.SystemFacade;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

//implements NavigationContract.INavigationModel
public class NavigationRepository extends BaseRepository  {


    public static final String CACHE_FILE_NAME = "NAVIGATION.JSON";

   /* @SuppressLint("StaticFieldLeak")
    @Override
    public void getNavigation(final LifecycleProvider provider, final IBaseCallBackWithCache<ArrayList<Navigation>> backWithCache) {

        new AsyncTask<Void,Void, ArrayList<Navigation>>(){

            @Override
            protected ArrayList<Navigation> doInBackground(Void... voids) {
               File cacheFile =  SystemFacade.getExternalCacheDir(WAApplication.mApplicationContext, CACHE_FILE_NAME);
               if(cacheFile == null || !cacheFile.exists()){
                   return null;
                }

               ArrayList<Navigation> navigationList = (ArrayList<Navigation>) DataCacheUtils.getDataListFromFile(Navigation.class, cacheFile);

               if(!SystemFacade.isListEmpty(navigationList)){
                   return navigationList;
               }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<Navigation> navigations) {
                if(!SystemFacade.isListEmpty(navigations)){
                    backWithCache.onCacheBack(navigations, IBaseCallBackWithCache.TYPE_CACHE_PERSISTENT);
                }
                requestServer(provider, backWithCache);
            }
        }.execute();
    }

// create ,just error,flatmap , doOnNext , concat ,merge,firstOrError


    private void requestServer(LifecycleProvider provider, final IBaseCallBackWithCache<ArrayList<Navigation>> backWithCache){
        backWithCache.onStartRequestServer();
        observer(provider, WADataService.getService().getNavigation(), new Function<HttpResult<ArrayList<Navigation>>, ObservableSource<ArrayList<Navigation>>>() {
            @Override
            public ObservableSource<ArrayList<Navigation>> apply(HttpResult<ArrayList<Navigation>> arrayListHttpResult) throws Exception {
                if(arrayListHttpResult.errorCode == 0 && !SystemFacade.isListEmpty(arrayListHttpResult.data)){

                    // 保存到到文件
                    File cacheFile =  SystemFacade.getExternalCacheDir(WAApplication.mApplicationContext, CACHE_FILE_NAME);

                    ArrayList<Navigation> cacheData = (ArrayList<Navigation>) DataCacheUtils.getDataListFromFile(Navigation.class, cacheFile);

                    if(SystemFacade.isListEmpty(cacheData)){ // 如果之前没有缓存，所有直接缓存这次请求回来的数据
                        DataCacheUtils.saveDataToFile(arrayListHttpResult.data, cacheFile);
                    }else{// 之前有缓存，那么比较下，服务器和缓存数据是否还保持一致，如果没变化就不需要保持，有变化则需要
                        // 第一步先判断size 是否一样
                        if(cacheData.size() != arrayListHttpResult.data.size()){
                            DataCacheUtils.saveDataToFile(arrayListHttpResult.data, cacheFile);
                        }else{
                            int index = -1;
                            for(Navigation navigation : arrayListHttpResult.data){
                                index = cacheData.indexOf(navigation);

                                if(index == -1){ // 在本地找不到一个和服务器一样的 navigation，说明服务器和本地数据不一致
                                    DataCacheUtils.saveDataToFile(arrayListHttpResult.data, cacheFile);
                                    break;
                                }
                            }

                            if(index != -1){ // 说明本地和服务器一样。一次不需要要保持到本地，也不要让界面刷新
                                arrayListHttpResult.data.clear();
                            }

                        }
                    }

                    return Observable.just(arrayListHttpResult.data);
                }
                return Observable.error(new ServerException(arrayListHttpResult.errorMsg));
            }
        }, backWithCache);
    }*/

}
