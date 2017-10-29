package cn.zjnu.matcha.factory.mvp.personal;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import cn.zjnu.matcha.core.factory.BasePresenter;
import cn.zjnu.matcha.core.net.RestClient;
import cn.zjnu.matcha.core.net.callbacks.ISuccess;
import cn.zjnu.matcha.core.utils.callback.CallbackManager;
import cn.zjnu.matcha.core.utils.callback.CallbackTypes;
import cn.zjnu.matcha.core.utils.callback.IGlobalCallback;
import cn.zjnu.matcha.factory.model.jiguang.ResponseCodes;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class PersonalPresenter extends BasePresenter<PersonalContract.View> implements PersonalContract.Presenter {

    public PersonalPresenter(PersonalContract.View view) {
        super(view);
    }

    @Override
    public void onFirstInit() {
        final UserInfo userInfo = JMessageClient.getMyInfo();
        String userName = userInfo.getUserName();
        getView().initUserName(userName);
        RestClient.builder()
                .url("https://api.im.jpush.cn/v1/users/" + userName)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject user = JSONObject.parseObject(response);
                        JSONObject extras = user.getJSONObject("extras");
                        String avatarMediaId = user.getString("avatar");
                        String nickName = user.getString("nickname");
                        if (extras != null) {
                            String phone = extras.getString("phone");
                            getView().initPhone(phone);
                        }
                        getView().initNickName(nickName);
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

    @Override
    public void initCallback() {
        CallbackManager.getInstance()
                .addCallback(CallbackTypes.ON_CROP, new IGlobalCallback<Uri>() {
                    @Override
                    public void executeCallback(@Nullable Uri args) {
                        if (args != null) {
                            updateUserPortrait(args);
                        }
                    }
                });
    }

    @Override
    public void setNickName(final String nickName) {
        final UserInfo userInfo = JMessageClient.getMyInfo();
        userInfo.setNickname(nickName);
        JMessageClient.updateMyInfo(UserInfo.Field.nickname, userInfo, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == ResponseCodes.SUCCESSFUL) {
                    getView().updateNickName(nickName);
                } else {
                    getView().showError(s);
                }
            }
        });
    }

    @Override
    public void setPhone(final String phone) {
        final UserInfo userInfo = JMessageClient.getMyInfo();
        userInfo.setUserExtras("phone", phone);
        JMessageClient.updateMyInfo(UserInfo.Field.extras, userInfo, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == ResponseCodes.SUCCESSFUL) {
                    getView().setPhoneSuccess(phone);
                } else {
                    getView().showError(s);
                }
            }
        });
    }

    private void updateUserPortrait(final Uri uri) {
        File file = null;
        try {
            file = new File(new URI(uri.toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (file != null) {
            JMessageClient.updateUserAvatar(file, new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    if (i == ResponseCodes.SUCCESSFUL) {
                        getView().setUserPortrait(uri);
                    } else {
                        getView().showError(s);
                    }
                }
            });
        }
    }
}
