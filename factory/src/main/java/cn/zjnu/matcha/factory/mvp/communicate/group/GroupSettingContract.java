package cn.zjnu.matcha.factory.mvp.communicate.group;

import cn.zjnu.matcha.core.factory.BaseContract;

/**
 * Created by Hu on 2017/10/27.
 */

public interface GroupSettingContract {
    interface View extends BaseContract.View<Presenter> {
        void quit();
    }

    interface Presenter extends BaseContract.Presenter {
        void quitGroup(long groupId);
    }
}
