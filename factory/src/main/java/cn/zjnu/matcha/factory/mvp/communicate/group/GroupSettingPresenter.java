package cn.zjnu.matcha.factory.mvp.communicate.group;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.api.BasicCallback;
import cn.zjnu.matcha.core.factory.BasePresenter;
import cn.zjnu.matcha.factory.model.jiguang.ResponseCodes;

/**
 * Created by Hu on 2017/10/27.
 */

public class GroupSettingPresenter extends BasePresenter<GroupSettingContract.View> implements GroupSettingContract.Presenter {
    public GroupSettingPresenter(GroupSettingContract.View view) {
        super(view);
    }

    @Override
    public void quitGroup(long groupId) {
        JMessageClient.exitGroup(groupId, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == ResponseCodes.SUCCESSFUL) {
                    getView().quit();
                } else {
                    getView().showError("发生未知错误");
                }
            }
        });
    }

    @Override
    public void getGroupInfo(long groupId) {
        JMessageClient.getGroupInfo(groupId, new GetGroupInfoCallback() {
            @Override
            public void gotResult(int i, String s, GroupInfo groupInfo) {
                if(i==ResponseCodes.SUCCESSFUL){
                    getView().getGroupInfoSuccess(groupInfo);
                }else {
                    getView().showError(s);
                }
            }
        });
    }
}
