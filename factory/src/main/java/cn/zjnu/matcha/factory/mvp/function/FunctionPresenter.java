package cn.zjnu.matcha.factory.mvp.function;

import com.alibaba.fastjson.JSONObject;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.zjnu.matcha.core.factory.BasePresenter;
import cn.zjnu.matcha.core.net.RestClient;
import cn.zjnu.matcha.core.net.callbacks.ISuccess;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class FunctionPresenter extends BasePresenter<FunctionContract.View> implements FunctionContract.Presenter {

    public FunctionPresenter(FunctionContract.View view) {
        super(view);
    }

    @Override
    public void getUserPortrait() {
        UserInfo userInfo = JMessageClient.getMyInfo();
        String userName = userInfo.getUserName();
        RestClient.builder()
                .url("https://api.im.jpush.cn/v1/users/" + userName)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject user = JSONObject.parseObject(response);
                        String avatarMediaId = user.getString("avatar");
                        initUserPortrait(avatarMediaId);
                    }
                })
                .build()
                .get();
    }

    private void initUserPortrait(String mediaId) {
        RestClient.builder()
                .url("https://api.im.jpush.cn/v1/resource")
                .params("mediaId", mediaId)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        JSONObject avatarInfo = JSONObject.parseObject(response);
                        String url = avatarInfo.getString("url");
                        getView().initPortrait(url);
                    }
                })
                .build()
                .get();
    }
}

