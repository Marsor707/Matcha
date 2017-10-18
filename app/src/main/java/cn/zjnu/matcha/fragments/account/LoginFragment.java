package cn.zjnu.matcha.fragments.account;


import android.content.Context;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.activities.AccountActivity;
import cn.zjnu.matcha.activities.MainActivity;
import cn.zjnu.matcha.core.app.Matcha;
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
    @BindView(R.id.txt_go_register)
    TextView mTxtGoRegister;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
    @BindView(R.id.loading)
    AVLoadingIndicatorView mLoadingView;

    @OnClick(R.id.txt_go_register)
    void onGoRegisterClick() {
        mAccountTrigger.triggerView();
    }

    @OnClick(R.id.btn_submit)
    void onLoginClick() {
        if (checkForm()) {
            final String username = mEditUsername.getText().toString();
            final String password = mEditPassword.getText().toString();
            mEditUsername.setEnabled(false);
            mEditPassword.setEnabled(false);
            mTxtGoRegister.setEnabled(false);
            mPresenter.login(username, password);
        } else {
            Matcha.showToast("请检查所填信息是否有误");
        }
    }

    private boolean checkForm() {
        final String name = mEditUsername.getText().toString();
        final String password = mEditPassword.getText().toString();
        boolean isPass = true;

        if (name.isEmpty()) {
            mEditUsername.setError("用户名不能为空");
            isPass = false;
        } else {
            mEditUsername.setError(null);
        }

        if (password.isEmpty()) {
            mEditPassword.setError("密码不能为空");
            isPass = false;
        } else if (password.length() < 4) {
            mEditPassword.setError("请至少输入4个字符");
            isPass = false;
        } else {
            mEditPassword.setError(null);
        }

        return isPass;
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
        stopLoading();
        MainActivity.show(getContext());
        AccountActivity activity = (AccountActivity) getContext();
        activity.finish();
    }

    @Override
    public void showLoading() {
        if (mLoadingView != null) {
            mLoadingView.show();
            mBtnSubmit.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(@StringRes int str) {
        super.showError(str);
        mEditUsername.setEnabled(true);
        mEditPassword.setEnabled(true);
        mTxtGoRegister.setEnabled(true);
        mBtnSubmit.setVisibility(View.VISIBLE);
        stopLoading();
    }

    private void stopLoading() {
        if (mLoadingView != null) {
            mLoadingView.hide();
        }
    }
}
