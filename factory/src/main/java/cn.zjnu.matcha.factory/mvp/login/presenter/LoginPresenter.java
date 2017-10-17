package cn.zjnu.matcha.factory.mvp.login.presenter;

import cn.zjnu.matcha.core.factory.BasePresenter;
import cn.zjnu.matcha.factory.mvp.login.contract.LoginContract;

/**
 * Created by Hu on 2017/10/17.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter{
    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    public void login(String username, String password) {

    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {

    }
}
