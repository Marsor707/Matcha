package cn.zjnu.matcha.core.utils.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import cn.zjnu.matcha.core.app.Matcha;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class DimenUtil {

    public static int getScreenWidth() {
        final Resources resources = Matcha.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight() {
        final Resources resources = Matcha.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
