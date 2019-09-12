package com.starfish.login;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.starfish.R;
import com.starfish.base.BaseFragment;

public class LoginFragment extends BaseFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.loginandrregister;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {

    }
}
