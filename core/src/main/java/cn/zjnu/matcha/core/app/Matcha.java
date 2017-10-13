package cn.zjnu.matcha.core.app;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import cn.zjnu.matcha.core.utils.callback.CallbackManager;
import cn.zjnu.matcha.core.utils.callback.CallbackTypes;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class Matcha {

    public static Configurator init(Context context) {
        getConfigurator()
                .getMatchaConfigs()
                .put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }

    public static Context getApplicationContext() {
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }

    public static Handler getHandler() {
        return getConfiguration(ConfigKeys.HANDLER);
    }

    @SuppressWarnings("unchecked")
    public static void finishAllActivities() {
        CallbackManager.getInstance()
                .getCallback(CallbackTypes.FINISH_ACTIVITIES)
                .executeCallback(null);
    }

    public static void showToast(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }
}
