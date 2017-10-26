package cn.zjnu.matcha.factory.mvp.communicate;

import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupIDListCallback;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.UserInfo;
import cn.zjnu.matcha.core.app.Matcha;
import cn.zjnu.matcha.core.factory.BasePresenter;
import cn.zjnu.matcha.core.net.RestClient;
import cn.zjnu.matcha.core.net.callbacks.IError;
import cn.zjnu.matcha.core.net.callbacks.ISuccess;
import cn.zjnu.matcha.core.utils.callback.CallbackManager;
import cn.zjnu.matcha.core.utils.callback.CallbackTypes;
import cn.zjnu.matcha.core.utils.callback.IGlobalCallback;
import cn.zjnu.matcha.factory.model.group.AddGroupRequestModel;
import cn.zjnu.matcha.factory.model.jiguang.ResponseCodes;

/**
 * Created by HuQiang on 2017/10/21.
 */

public class CommunicatePresenter extends BasePresenter<CommunicateContract.View> implements CommunicateContract.Presenter {
    public CommunicatePresenter(CommunicateContract.View view) {
        super(view);
    }

    @Override
    public void getGroupList() {
        final List<Long> groudIds = new ArrayList<>();
        JMessageClient.getGroupIDList(new GetGroupIDListCallback() {
            @Override
            public void gotResult(int i, String s, List<Long> list) {
                if (i == ResponseCodes.SUCCESSFUL) {
                    groudIds.addAll(list);
                    getGroupInfos(groudIds);
                } else {
                    getView().showError(s);
                }
            }
        });
    }

    private void getGroupInfos(List<Long> ids) {
        final List<GroupInfo> infoList = new ArrayList<>();
        for (Long id : ids) {
            JMessageClient.getGroupInfo(id, new GetGroupInfoCallback() {
                @Override
                public void gotResult(int i, String s, GroupInfo groupInfo) {
                    if (i == ResponseCodes.SUCCESSFUL) {
                        infoList.add(groupInfo);
                        getView().showGroupList(infoList);
                    } else {
                        getView().showError(s);
                    }
                }
            });
        }
    }

    @Override
    public void initCallback() {
        final UserInfo userInfo = JMessageClient.getMyInfo();
        final String userName = userInfo.getUserName();
        CallbackManager.getInstance()
                .addCallback(CallbackTypes.ON_SCAN, new IGlobalCallback<String>() {
                    @Override
                    public void executeCallback(@Nullable String args) {
                        joinGroup(args, userName);
                    }
                });
    }

    private void joinGroup(final String groupId, String userName) {
        final AddGroupRequestModel model = new AddGroupRequestModel();
        final ArrayList<String> userNames = new ArrayList<>();
        userNames.add(userName);
        model.setAdd(userNames);
        final String requestStr = JSONObject.toJSONString(model);
        RestClient.builder()
                .url("https://api.im.jpush.cn" + "/v1/groups/" + groupId + "/members")
                .raw(requestStr)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        getGroupInfo(Long.parseLong(groupId));
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        getView().showError("加群失败");
                    }
                })
                .build()
                .post();
    }

    private void getGroupInfo(long groupId) {
        JMessageClient.getGroupInfo(groupId, new GetGroupInfoCallback() {
            @Override
            public void gotResult(int i, String s, GroupInfo groupInfo) {
                if (i == ResponseCodes.SUCCESSFUL) {
                    Matcha.showToast("加群成功");
                    getView().joinGroupSuccess(groupInfo);
                } else {
                    getView().showError(s);
                }
            }
        });
    }
}
