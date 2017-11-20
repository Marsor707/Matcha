package cn.zjnu.matcha.factory.mvp.pe;

import cn.zjnu.matcha.core.factory.BaseContract;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public interface EvaluateContract extends BaseContract {

    interface View extends BaseContract.View<Presenter> {
        void getEvaluateContentSuccess();

        void getEvaluateContentFail();
    }

    interface Presenter extends BaseContract.Presenter {
        void getEvaluateContent(long userId);
    }
}
