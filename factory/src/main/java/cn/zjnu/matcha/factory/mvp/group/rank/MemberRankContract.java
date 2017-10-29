package cn.zjnu.matcha.factory.mvp.group.rank;

import cn.jpush.im.android.api.model.Conversation;
import cn.zjnu.matcha.core.factory.BaseContract;

/**
 * Created by Hu on 2017/10/29.
 */

public interface MemberRankContract {
    interface Presenter extends BaseContract.Presenter {
        void fetchConversation(long groupId);

        void getMemberInfos(long groupId);
    }

    interface View extends BaseContract.View<Presenter> {
        void getConversation(Conversation conversation);

        void getMemberInfosSuccess(String response);
    }
}
