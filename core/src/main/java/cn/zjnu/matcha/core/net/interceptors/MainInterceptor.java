package cn.zjnu.matcha.core.net.interceptors;

import android.support.annotation.NonNull;

import com.blankj.utilcode.util.EncodeUtils;

import java.io.IOException;

import cn.zjnu.matcha.core.app.ConfigKeys;
import cn.zjnu.matcha.core.app.Matcha;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class MainInterceptor extends BaseInterceptor {

    public MainInterceptor() {
    }

    @Override
    public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder();
        builder.addHeader("Content-Type", "application/json");
        builder.addHeader("Authorization", "Basic " + getBaseAuthStr());
        Request request = builder.build();
        return chain.proceed(request);
    }

    private String getBaseAuthStr() {
        String appKey = Matcha.getConfiguration(ConfigKeys.APP_KEY);
        String masterSecret = Matcha.getConfiguration(ConfigKeys.MASTER_SECRET);
        return new String(EncodeUtils.base64Encode(appKey + ":" + masterSecret));
    }
}
