package cn.zjnu.matcha.factory.mvp.communicate.chat;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.zjnu.matcha.core.app.Matcha;
import cn.zjnu.matcha.core.factory.BasePresenter;
import cn.zjnu.matcha.factory.model.jiguang.ResponseCodes;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class ChatGroupPresenter extends BasePresenter<ChatGroupContract.View> implements ChatGroupContract.Presenter {

    public ChatGroupPresenter(ChatGroupContract.View view) {
        super(view);
    }

    @Override
    public void getGroupInfo(long groupId) {
        JMessageClient.getGroupInfo(groupId, new GetGroupInfoCallback() {
            @Override
            public void gotResult(int i, String s, GroupInfo groupInfo) {
                if (i == ResponseCodes.SUCCESSFUL) {
                    getView().initHeader(groupInfo);
                } else {
                    Matcha.showToast("error");
                }
            }
        });
    }
}
