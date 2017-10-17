package cn.zjnu.matcha;


import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
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

    private List<Activity> activities = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        Matcha.init(this)
                .withApiHost("http://115.159.62.44/RestServer/api/")
                .withInterceptor(new MainInterceptor())
                .withLoaderDelayed(300)
                .configure();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activities.add(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                activities.remove(activity);
            }
        });

        CallbackManager.getInstance().addCallback(CallbackTypes.FINISH_ACTIVITIES, new IGlobalCallback() {
            @Override
            public void executeCallback(@Nullable Object args) {
                finishAll();
            }
        });

        initJMessage();
    }

    private void finishAll() {
        for (Activity activity : activities) {
            activity.finish();
        }
    }

    private void initJMessage() {
        JMessageClient.setDebugMode(true);
        JMessageClient.init(this, true);
    }
}
