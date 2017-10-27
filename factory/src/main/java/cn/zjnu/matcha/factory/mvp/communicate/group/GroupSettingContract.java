package cn.zjnu.matcha.factory.mvp.communicate.group;

import cn.jpush.im.android.api.model.GroupInfo;
import cn.zjnu.matcha.core.factory.BaseContract;

/**
 * Created by Hu on 2017/10/27.
 */

public interface GroupSettingContract {
    interface View extends BaseContract.View<Presenter> {
        void quit();

        void getGroupInfoSuccess(GroupInfo groupInfo);
    }

    interface Presenter extends BaseContract.Presenter {
        void quitGroup(long groupId);

        void getGroupInfo(long groupId);
    }
}
