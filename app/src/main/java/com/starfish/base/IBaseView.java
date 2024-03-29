package com.starfish.base;

import android.app.Activity;


public interface IBaseView<T extends IBasePresenter> {


    void setPresenter(T presenter);

    Activity getActivityObject();


}
