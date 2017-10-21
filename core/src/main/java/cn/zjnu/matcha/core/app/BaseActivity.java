package cn.zjnu.matcha.core.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.LoginStateChangeEvent;
import cn.zjnu.matcha.core.utils.callback.CallbackManager;
import cn.zjnu.matcha.core.utils.callback.CallbackTypes;
import qiu.niorgai.StatusBarCompat;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public abstract class BaseActivity extends AppCompatActivity {

    private static final long WAIT_TIME = 2000;
    private long TOUCH_TIME = 0;
    protected Unbinder mRootUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在界面未初始化之前调用的初始化窗口
        initWindows();
        if (initArgs(getIntent().getExtras())) {
            //得到界面id并设置到Activity界面中
            if (getContentLayoutId() instanceof Integer) {
                int layId = (int) getContentLayoutId();
                setContentView(layId);
            } else if (getContentLayoutId() instanceof View) {
                View baseView = (View) getContentLayoutId();
                setContentView(baseView);
            }
            initBefore();
            initWidget();
            initData();
        } else {
            finish();
        }
    }

    /**
     * 初始化控件调用之前
     */
    protected void initBefore() {

    }

    /**
     * 初始化窗口
     */
    protected void initWindows() {
        JMessageClient.registerEventReceiver(this);
    }

    /**
     * 初始化相关参数
     *
     * @param bundle 参数Bundle
     * @return 如果参数正确返回true，否则返回false
     */
    protected boolean initArgs(Bundle bundle) {
        return true;
    }

    /**
     * 得到当前界面的资源文件ID
     * 子类必须复写
     *
     * @return 资源文件ID
     */
    protected abstract Object getContentLayoutId();

    /**
     * 初始化控件
     */
    protected void initWidget() {
        mRootUnBinder = ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        StatusBarCompat.translucentStatusBar(this, true);
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    @Override
    public boolean onSupportNavigateUp() {
        //当点击界面导航返回时finish当前界面
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        //得到当前Activity下所有Fragment
        @SuppressWarnings("RestrictedApi")
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment fragment : fragments) {
                //判断是否是我们能够处理的Fragment
                if (fragment instanceof BaseFragment) {
                    //判断是否拦截了返回按钮
                    if (((BaseFragment) fragment).onBackPressed()) {
                        //如果有直接return
                        return;
                    }
                }
            }
        }
        if (Matcha.getActivitiesSize() == 1) {
            if (System.currentTimeMillis() - TOUCH_TIME >= WAIT_TIME) {
                TOUCH_TIME = System.currentTimeMillis();
                Matcha.showToast("再按一次退出");
            } else {
                super.onBackPressed();
                finish();
            }
        } else {
            super.onBackPressed();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRootUnBinder.unbind();
        JMessageClient.unRegisterEventReceiver(this);
    }

    public void onEvent(LoginStateChangeEvent event) {
        final LoginStateChangeEvent.Reason reason = event.getReason();
        switch (reason) {
            case user_logout:
                showLoginView();
                Matcha.showToast("您的账号在其他设备上登陆");
                break;
            default:
                break;
        }
    }

    @SuppressWarnings("unchecked")
    private void showLoginView() {
        CallbackManager.getInstance()
                .getCallback(CallbackTypes.SHOW_LOGIN_VIEW)
                .executeCallback(null);
    }
}
