package cn.zjnu.matcha.factory.mvp.group;

import cn.zjnu.matcha.core.factory.BaseContract;

/**
 * Created by Hu on 2017/10/27.
 */

public interface MemberDefaultContract {
    interface Presenter extends BaseContract.Presenter {
        void getMemberInfos(long groupId);

        void getMemberAvatar(String mediaId);
    }

    interface View extends BaseContract.View<Presenter> {
        void getMemberInfosSuccess(String response);

        void getMemberAvatarSuccess(String response);
    }
}
