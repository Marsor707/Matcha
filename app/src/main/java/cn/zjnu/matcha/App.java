package cn.zjnu.matcha;


import android.content.Intent;
import android.support.annotation.Nullable;

import cn.jpush.im.android.api.JMessageClient;
import cn.zjnu.matcha.activities.AccountActivity;
import cn.zjnu.matcha.core.app.Application;
import cn.zjnu.matcha.core.app.Matcha;
import cn.zjnu.matcha.core.net.interceptors.MainInterceptor;
import cn.zjnu.matcha.core.utils.callback.CallbackManager;
import cn.zjnu.matcha.core.utils.callback.CallbackTypes;
import cn.zjnu.matcha.core.utils.callback.IGlobalCallback;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Matcha.init(this)
                .withApiHost("http://115.159.62.44:8080/sport/")
                .withInterceptor(new MainInterceptor())
                .withLoaderDelayed(300)
                .withAppKey("2a26068439feefd043504ca8")
                .withMasterSecret("f63146136efe1ec314f1a45a")
                .configure();

        initJMessage();

        initShowLoginView();
    }

    private void initJMessage() {
        JMessageClient.setDebugMode(true);
        JMessageClient.init(this, true);
        JMessageClient.setNotificationFlag(JMessageClient.FLAG_NOTIFY_DISABLE);
    }

    private void initShowLoginView() {
        CallbackManager.getInstance().addCallback(CallbackTypes.SHOW_LOGIN_VIEW, new IGlobalCallback() {
            @Override
            public void executeCallback(@Nullable Object args) {
                Intent intent = new Intent(App.this, AccountActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finishAllActivities();
                startActivity(intent);
            }
        });
    }
}
