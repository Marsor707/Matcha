package cn.zjnu.matcha.factory.mvp.communicate;

import java.util.List;

import cn.jpush.im.android.api.model.GroupInfo;
import cn.zjnu.matcha.core.factory.BaseContract;

/**
 * Created by HuQiang on 2017/10/21.
 */

public interface CommunicateContract {
    interface Presenter extends BaseContract.Presenter {
        void getGroupList();

        void initCallback();
    }

    interface View extends BaseContract.View<Presenter> {
        void showGroupList(List<GroupInfo> groupInfos);

        void joinGroupSuccess(GroupInfo groupInfo);
    }
}
