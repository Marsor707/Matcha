package cn.zjnu.matcha.factory.mvp.account;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import cn.zjnu.matcha.core.app.Matcha;
import cn.zjnu.matcha.core.factory.BasePresenter;

/**
 * Created by Hu on 2017/10/17.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {
    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    public void login(String username, String password) {
        JMessageClient.login(username, password, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0) {
                    getView().loginSuccess();
                } else {
                    Matcha.showToast("错误");
                }
            }
        });
    }
}
