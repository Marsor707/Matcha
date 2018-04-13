package cn.zjnu.matcha.factory.mvp.advice;

import cn.zjnu.matcha.core.factory.BaseContract;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public interface FoodAdviceContract extends BaseContract {

    interface View extends BaseContract.View<Presenter> {
        void getFoodAdviceSuccess(String advice);
    }

    interface Presenter extends BaseContract.Presenter {
        void getFoodAdviceData(long id);
    }
}
