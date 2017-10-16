package cn.zjnu.matcha.core.app;

import android.os.Handler;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class Configurator {

    private static final HashMap<Object, Object> MATCHA_CONFIGS = new HashMap<>();
    private static final Handler HANDLER = new Handler();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    private Configurator() {
        MATCHA_CONFIGS.put(ConfigKeys.CONFIG_READY, false);
        MATCHA_CONFIGS.put(ConfigKeys.HANDLER, HANDLER);
    }

    final HashMap<Object, Object> getMatchaConfigs() {
        return MATCHA_CONFIGS;
    }

    private static final class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    public final void configure() {
        initIcons();
        MATCHA_CONFIGS.put(ConfigKeys.CONFIG_READY, true);
    }

    public final Configurator withApiHost(String host) {
        MATCHA_CONFIGS.put(ConfigKeys.API_HOST, host);
        return this;
    }

    public final Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        MATCHA_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        MATCHA_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final Configurator withLoaderDelayed(long delayed) {
        MATCHA_CONFIGS.put(ConfigKeys.LOADER_DELAYED, delayed);
        return this;
    }

    private void initIcons() {
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    public final Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }

    private void checkConfiguration() {
        final boolean isReady = (boolean) MATCHA_CONFIGS.get(ConfigKeys.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        final Object value = MATCHA_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " IS NULL ");
        }
        return (T) value;
    }
}
