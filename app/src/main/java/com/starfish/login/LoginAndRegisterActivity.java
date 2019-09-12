package com.starfish.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.starfish.R;
import com.starfish.base.BaseActivity;

public class LoginAndRegisterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_register);
        addFragment(getSupportFragmentManager(), LoginFragment.class, R.id.login_fragment_container, null);
    }
}
