package cn.zjnu.matcha.factory.mvp.function;

import cn.zjnu.matcha.core.factory.BaseContract;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public interface FunctionContract {

    interface Presenter extends BaseContract.Presenter {
        void getUserPortrait();
    }

    interface View extends BaseContract.View<Presenter> {
        void initPortrait(String url);
    }
}
