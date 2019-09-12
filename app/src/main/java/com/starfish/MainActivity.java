package com.starfish;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.PersistableBundle;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.starfish.base.BaseActivity;
import com.starfish.base.BaseFragment;
import com.starfish.data.okhttp.interceptor.AppConstant;
import com.starfish.data.repositories.WeChatRepository;
import com.starfish.home.HomeFragment;
import com.starfish.proguard.ProguardFragment;
import com.starfish.question.QuestionFragment;
import com.starfish.utils.Logger;
import com.starfish.utils.PermissionUtils;
import com.starfish.widget.view.ButtonNavigationView;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private TextView mTvTitle;
    private ButtonNavigationView mButtonNavigationView;
    private static final String TAG = "MainActivity";

    private static final String [] TAKE_PHOTO_PERMISSIONS_MUST = new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.REQUEST_INSTALL_PACKAGES,Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final String [] TAKE_PHOTO_PERMISSIONS_OPTIONAL = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
    private static final String TAKE_PHOTO_TIPS_ = "拍照必须获得相机,文件等权限，否则不能拍照，位置权限可选，如果打开位置权限，拍照可以记录拍照地点信息";

    private ArrayList<String> mUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.therqpistscenter_private);


        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.REQUEST_INSTALL_PACKAGES};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
        PermissionUtils permissionUtils = new PermissionUtils(this);

        permissionUtils.checkPermission(MainActivity.this, new PermissionUtils.OnPermissionCallBack() {
            @Override
            public void onAllMustAccept() {

            }

            @Override
            public void shouldShowRationale(final PermissionUtils.PermissionCall call) {
                showToast("显示解释对话框");

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("权限说明").setMessage(TAKE_PHOTO_TIPS_).setNegativeButton("放弃", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                }).setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        call.requestPermission();
                        dialog.dismiss();
                    }
                }).show();
            }

            @Override
            public void shouldShowPermissionSetting() {
                showToast("引导用户手动去开启");
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("权限说明").setMessage(TAKE_PHOTO_TIPS_).setNegativeButton("放弃", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showToast("用户放弃开启权限");
                        dialog.dismiss();
                    }
                }).setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Uri packageURI = Uri.parse("package:" + getPackageName());
                        startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI));
                        dialog.dismiss();
                    }
                }).show();
            }

            @Override
            public void onDenied() {
                showToast("权限不足，不能拍照");
            }
        },TAKE_PHOTO_PERMISSIONS_MUST , null);


        Toolbar toolbar = findViewById(R.id.load_toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
       

//          addFragment(getSupportFragmentManager(), HomeFragment.class, R.id.main_fragment_container, null);

        mTvTitle = findViewById(R.id.main_tv_title);

        mButtonNavigationView = findViewById(R.id.main_bottom_navigation);


        mButtonNavigationView.setOnTabChangedListener(new ButtonNavigationView.OnTabCheckedChangedListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                int id = buttonView.getId();
                Class<? extends BaseFragment> aClass = null;
                switch (id) {
                    case R.id.main_button_tab_home: {
                        aClass = HomeFragment.class;
                        mTvTitle.setText(R.string.main_text_tab_home);
                        break;
                    }
                    case R.id.main_button_tab_proguard: {
                        aClass = ProguardFragment.class;
                        mTvTitle.setText(R.string.main_text_tab_proguard);
                        break;
                    }
                    case R.id.main_button_tab_question: {
                        aClass = QuestionFragment.class;
                        mTvTitle.setText(R.string.main_text_tab_question);
                        break;
                    }
                    case R.id.main_button_tab_myself: {
                        aClass = MySelfFragment.class;
                        mTvTitle.setText(R.string.main_text_tab_myself);
                        break;
                    }
                    default: {
                        return;
                    }

                }
                addFragment(getSupportFragmentManager(), aClass, R.id.main_fragment_container, null);
            }
        });

        mButtonNavigationView.setTabChecked(R.id.main_button_tab_home);


        mUrls.add(AppConstant.DOWNLOAD_URL1);
        mUrls.add(AppConstant.DOWNLOAD_URL2);
        mUrls.add(AppConstant.DOWNLOAD_URL3);
        mUrls.add(AppConstant.DOWNLOAD_URL4);
        mUrls.add(AppConstant.DOWNLOAD_URL5);

        Uri packageURI = Uri.parse("package:" + getPackageName());
        RxPermissions rxPermissions = new RxPermissions(this);

        boolean granted = rxPermissions.isGranted(Manifest.permission.REQUEST_INSTALL_PACKAGES);

        if (granted) {
            //startService(intent);
        } else {
            rxPermissions.ensure(Manifest.permission.CAMERA);
        }


    }


    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
