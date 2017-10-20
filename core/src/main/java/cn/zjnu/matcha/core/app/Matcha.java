package cn.zjnu.matcha.core.app;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

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

    public static void finishAllActivities() {
        Application.getInstance().finishAllActivities();
    }

    public static int getActivitiesSize() {
        return Application.getInstance().getCurActivitiesSize();
    }

    public static void showToast(final Object res) {
        if (res instanceof String) {
            //切换到主线程
            getHandler().post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), (CharSequence) res, Toast.LENGTH_SHORT).show();
                }
            });
        } else if (res instanceof Integer) {
            getHandler().post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), getApplicationContext().getString((Integer) res), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
