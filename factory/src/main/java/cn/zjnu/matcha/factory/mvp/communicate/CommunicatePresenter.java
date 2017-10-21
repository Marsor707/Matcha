package cn.zjnu.matcha.factory.mvp.communicate;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupIDListCallback;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.zjnu.matcha.core.app.Matcha;
import cn.zjnu.matcha.core.factory.BasePresenter;
import cn.zjnu.matcha.factory.model.jiguang.ResponseCodes;

/**
 * Created by HuQiang on 2017/10/21.
 */

public class CommunicatePresenter extends BasePresenter<CommunicateContract.View> implements CommunicateContract.Presenter {
    public CommunicatePresenter(CommunicateContract.View view) {
        super(view);
    }

    @Override
    public void getGroupId() {
        JMessageClient.getGroupIDList(new GetGroupIDListCallback() {
            @Override
            public void gotResult(int i, String s, List<Long> list) {
                if (i == ResponseCodes.SUCCESSFUL) {
                    getView().fetchGroupId(list);
                } else {
                    Matcha.showToast("error");
                }
            }
        });
    }

    @Override
    public void getGroupList(List<Long> id) {
        final List<GroupInfo> groupInfos = new ArrayList<>();
        for (long groupIds : id) {
            JMessageClient.getGroupInfo(groupIds, new GetGroupInfoCallback() {
                @Override
                public void gotResult(int i, String s, GroupInfo groupInfo) {
                    if (i == ResponseCodes.SUCCESSFUL) {
                        groupInfos.add(groupInfo);
                        getView().showGroupList(groupInfos);
                    } else {
                        Matcha.showToast("error");
                    }
                }
            });
        }
    }
}
