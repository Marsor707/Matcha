package cn.zjnu.matcha.factory.mvp.communicate.chat;

import cn.jpush.im.android.api.model.GroupInfo;
import cn.zjnu.matcha.core.factory.BaseContract;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public interface ChatGroupContract {

    interface Presenter extends BaseContract.Presenter {
        void getGroupInfo(long groupId);
    }

    interface View extends BaseContract.View<Presenter> {
        void initHeader(GroupInfo group);
    }
}
