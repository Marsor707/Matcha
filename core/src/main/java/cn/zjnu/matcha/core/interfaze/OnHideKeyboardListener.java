package cn.zjnu.matcha.core.interfaze;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Hu on 2017/10/24.
 */

public interface OnHideKeyboardListener {
    boolean hideKeyboard(View v, MotionEvent ev);
}
