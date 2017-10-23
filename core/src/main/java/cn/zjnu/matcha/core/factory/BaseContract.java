package cn.zjnu.matcha.core.factory;

import android.support.annotation.StringRes;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public interface BaseContract {

    //基本的界面职责
    interface View<T extends Presenter> {
        //公共的显示一个字符串错误
        void showError(@StringRes int str);

        void showError(String message);

        //支持设置Presenter
        void setPresenter(T presenter);
    }

    //基本的Presenter职责
    interface Presenter {
        //公用的开始触发
        void start();

        //公用的销毁触发
        void destroy();
    }
}
