package cn.zjnu.matcha;


import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.ArrayList;
import java.util.List;

import cn.zjnu.matcha.core.app.Matcha;
import cn.zjnu.matcha.core.net.interceptors.MainInterceptor;
import cn.zjnu.matcha.core.utils.callback.CallbackManager;
import cn.zjnu.matcha.core.utils.callback.CallbackTypes;
import cn.zjnu.matcha.core.utils.callback.IGlobalCallback;
import cn.zjnu.matcha.icon.FontEcModule;

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
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
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
    }

    private void finishAll() {
        for (Activity activity : activities) {
            activity.finish();
        }
    }
}
