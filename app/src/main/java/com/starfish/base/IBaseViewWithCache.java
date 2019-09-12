package com.starfish.base;

public interface IBaseViewWithCache<T extends IBasePresenter,D > extends IBaseView<T> {


    // 缓存数数据回调接口。
    void onCacheReceived(D data, @IBaseCallBackWithCache.CallBackCacheType int type);

}
