package cn.zjnu.matcha.fragments;

import cn.zjnu.matcha.core.factory.BaseContract;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public interface ContractForFragment {

    interface Presenter extends BaseContract.Presenter{
        void startRest();
    }

    interface View extends BaseContract.View<Presenter>{
        void restSuccess(String str);
    }
}
