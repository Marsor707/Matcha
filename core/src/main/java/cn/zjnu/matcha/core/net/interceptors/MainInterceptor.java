package cn.zjnu.matcha.core.net.interceptors;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
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
        return chain.proceed(chain.request());
    }
}
