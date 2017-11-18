package cn.zjnu.matcha.factory.mvp.account;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import cn.zjnu.matcha.core.app.Matcha;
import cn.zjnu.matcha.core.factory.BasePresenter;
import cn.zjnu.matcha.core.net.RestClient;
import cn.zjnu.matcha.core.net.callbacks.IError;
import cn.zjnu.matcha.core.net.callbacks.IFailure;
import cn.zjnu.matcha.core.net.callbacks.ISuccess;
import cn.zjnu.matcha.factory.model.jiguang.ResponseCodes;

/**
 * Created by Hu on 2017/10/17.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {
    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    public void login(String username, String password) {
        getView().showLoading();

        JMessageClient.login(username, password, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == ResponseCodes.SUCCESSFUL) {
                    getView().loginSuccess();
                    syncUser();
                } else {
                    getView().showError(s);
                }
            }
        });
    }

    @Override
    public void autoLogin() {
        if (JMessageClient.getMyInfo() != null) {
            getView().loginSuccess();
        }
    }

    private void syncUser() {
        final UserInfo userInfo = JMessageClient.getMyInfo();
        final long userId = userInfo.getUserID();
        final String userName = userInfo.getUserName();
        RestClient.builder()
                .url("GetUserID")
                .params("userId", userId)
                .params("userName", userName)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        getView().showError(msg);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Matcha.showToast("网络连接失败");
                    }
                })
                .build()
                .get();
    }
}
