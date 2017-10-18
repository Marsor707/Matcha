package cn.zjnu.matcha.factory.mvp.account;

import cn.zjnu.matcha.core.factory.BaseContract;

/**
 * Created by Hu on 2017/10/18.
 */

public interface RegisterContract {
    interface Presenter extends BaseContract.Presenter {
        void register(String username, String password);
    }

    interface View extends BaseContract.View<Presenter> {
        void showSuccess();

        void showLoading();
    }
}
