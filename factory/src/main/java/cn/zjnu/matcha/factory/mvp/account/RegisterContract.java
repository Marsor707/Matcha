package cn.zjnu.matcha.factory.mvp.account;

import android.content.Context;

import cn.zjnu.matcha.core.factory.BaseContract;

/**
 * Created by Hu on 2017/10/18.
 */

public interface RegisterContract {
    interface Presenter extends BaseContract.Presenter {
        void register(String username, String password);

        void registerToLocal(String username, String password);

        void getContext(Context context);
    }

    interface View extends BaseContract.View<Presenter> {
        void showSuccess();

        void fetchUserNameAndPassword(String username, String password);
    }
}
