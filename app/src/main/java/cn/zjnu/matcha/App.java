package cn.zjnu.matcha;


import cn.jpush.im.android.api.JMessageClient;
import cn.zjnu.matcha.core.app.Application;
import cn.zjnu.matcha.core.app.Matcha;
import cn.zjnu.matcha.core.net.interceptors.MainInterceptor;

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
                .withApiHost("http://115.159.62.44/RestServer/api/")
                .withInterceptor(new MainInterceptor())
                .withLoaderDelayed(300)
                .configure();

        initJMessage();
    }

    private void initJMessage() {
        JMessageClient.setDebugMode(true);
        JMessageClient.init(this, true);
    }
}
