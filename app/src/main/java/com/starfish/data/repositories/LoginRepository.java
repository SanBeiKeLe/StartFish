package com.starfish.data.repositories;

import android.text.TextUtils;

import com.starfish.base.BaseRepository;
import com.starfish.data.okhttp.ApiService;
import com.starfish.data.okhttp.WADataService;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;


//implements LoginContract.ILoginSource
public class LoginRepository extends BaseRepository  {

    private ApiService mApiService;


    public LoginRepository(){
        mApiService = WADataService.getService();
    }



   /* private void request(LifecycleProvider provider, Observable<HttpResult<User>> observable, IBaseCallBack<User> callBack){


        observer(provider, observable,new Function<HttpResult<User>, ObservableSource<User>>() {
            @Override
            public ObservableSource<User> apply(HttpResult<User> userHttpResult) throws Exception {
                if(userHttpResult.errorCode == 0 && userHttpResult.data != null){
                    return Observable.just(userHttpResult.data);
                }

                String msg = "服务器返回数据为空";
                if(!TextUtils.isEmpty(userHttpResult.errorMsg)){
                    msg = userHttpResult.errorMsg;
                }

                return Observable.error(new NullPointerException(msg));
            }
        },callBack);
    }*/
}
