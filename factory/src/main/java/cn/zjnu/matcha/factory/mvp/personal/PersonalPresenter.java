package cn.zjnu.matcha.factory.mvp.personal;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import cn.zjnu.matcha.core.factory.BasePresenter;
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
    public void getUserPortrait() {
        UserInfo userInfo = JMessageClient.getMyInfo();
        userInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
            @Override
            public void gotResult(int i, String s, Bitmap bitmap) {
                if (i == ResponseCodes.SUCCESSFUL) {
                    ByteArrayOutputStream bo = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bo);
                    byte[] bytes = bo.toByteArray();
                    getView().initPortrait(bytes);
                } else if (i == 871311) {
                    // 用户没有头像时逻辑
                } else {
                    getView().showError(s);
                }
            }
        });
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
    public void getUserName() {
        UserInfo userInfo = JMessageClient.getMyInfo();
        String userName = userInfo.getUserName();
        if (userName != null && !TextUtils.isEmpty(userName)) {
            getView().initUserName(userName);
        }
    }

    @Override
    public void getNickName() {
        UserInfo userInfo = JMessageClient.getMyInfo();
        String nickName = userInfo.getNickname();
        if (nickName != null && !TextUtils.isEmpty(nickName)) {
            getView().initNickName(nickName);
        }
    }

    @Override
    public void setNickName(final String nickName) {
        UserInfo userInfo = JMessageClient.getMyInfo();
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
