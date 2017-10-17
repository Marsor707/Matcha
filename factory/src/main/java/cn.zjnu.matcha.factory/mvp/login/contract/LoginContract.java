package cn.zjnu.matcha.factory.mvp.login.contract;

import cn.zjnu.matcha.core.factory.BaseContract;

/**
 * Created by Hu on 2017/10/17.
 */

public interface LoginContract {

    interface View extends BaseContract.View<Presenter> {

    }

    interface Presenter extends BaseContract.Presenter {
        void login(String username, String password);
    }
}
