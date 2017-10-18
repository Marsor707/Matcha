package cn.zjnu.matcha.fragments.account;


import android.content.Context;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.activities.MainActivity;
import cn.zjnu.matcha.core.app.PresenterFragment;
import cn.zjnu.matcha.factory.mvp.account.LoginContract;
import cn.zjnu.matcha.factory.mvp.account.LoginPresenter;

/**
 * 登录界面的Fragment
 */
public class LoginFragment extends PresenterFragment<LoginContract.Presenter> implements LoginContract.View {

    private IAccountTrigger mAccountTrigger;

    @BindView(R.id.edit_username)
    EditText mEditUsername;
    @BindView(R.id.edit_password)
    EditText mEditPassword;

    @OnClick(R.id.txt_go_register)
    void onGoRegisterClick() {
        mAccountTrigger.triggerView();
    }

    @OnClick(R.id.btn_submit)
    void onLoginClick() {
        String username = mEditUsername.getText().toString();
        String password = mEditPassword.getText().toString();
        mPresenter.login(username, password);
    }

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAccountTrigger = (IAccountTrigger) context;
    }

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void loginSuccess() {
        MainActivity.show(getContext());
    }
}
