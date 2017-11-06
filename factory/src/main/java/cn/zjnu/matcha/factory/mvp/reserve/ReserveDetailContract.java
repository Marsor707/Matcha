package cn.zjnu.matcha.factory.mvp.reserve;

import cn.zjnu.matcha.core.factory.BaseContract;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public interface ReserveDetailContract {

    interface View extends BaseContract.View<Presenter> {
        void reserveSuccess();

        void getReserveStateSuccess(boolean isReserved);
    }

    interface Presenter extends BaseContract.Presenter {
        void reserve(int reserveId);

        void getReserveState(int reserveId);
    }
}
