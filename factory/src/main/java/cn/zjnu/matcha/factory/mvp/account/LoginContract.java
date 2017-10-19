package cn.zjnu.matcha.factory.mvp.account;

import cn.zjnu.matcha.core.factory.BaseContract;

/**
 * Created by Hu on 2017/10/17.
 */

public interface LoginContract {

    interface View extends BaseContract.View<Presenter> {
        void loginSuccess();

        void showLoading();
    }

    interface Presenter extends BaseContract.Presenter {
        void login(String username, String password);

        void autoLogin();
    }
}
