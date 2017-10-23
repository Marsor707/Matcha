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
import cn.zjnu.matcha.activities.MainActivity;
import cn.zjnu.matcha.core.app.Matcha;
import cn.zjnu.matcha.core.app.PresenterFragment;
import cn.zjnu.matcha.factory.mvp.account.RegisterContract;
import cn.zjnu.matcha.factory.mvp.account.RegisterPresenter;

/**
 * 注册界面的Fragment
 */
public class RegisterFragment extends PresenterFragment<RegisterContract.Presenter> implements RegisterContract.View {

    @BindView(R.id.edit_username)
    EditText mName = null;
    @BindView(R.id.edit_password)
    EditText mPassword = null;
    @BindView(R.id.edit_password2)
    EditText mRePassword = null;
    @BindView(R.id.btn_submit)
    Button mSubmit = null;
    @BindView(R.id.txt_go_login)
    TextView mTxtGoLogin = null;

    private IAccountTrigger mAccountTrigger;

    @OnClick(R.id.txt_go_login)
    void onGoLoginClick() {
        mAccountTrigger.triggerView();
    }

    @OnClick(R.id.btn_submit)
    void onClickRegister() {
        if (checkForm()) {
            mName.setEnabled(false);
            mPassword.setEnabled(false);
            mRePassword.setEnabled(false);
            mSubmit.setEnabled(false);
            mTxtGoLogin.setEnabled(false);
            mPresenter.getContext(getContext());
            mPresenter.register(mName.getText().toString(), mPassword.getText().toString());
        } else {
            Matcha.showToast("请检查所填信息是否有误");
        }
    }

    private boolean checkForm() {
        final String name = mName.getText().toString();
        final String password = mPassword.getText().toString();
        final String rePassword = mRePassword.getText().toString();
        boolean isPass = true;

        if (name.isEmpty()) {
            mName.setError("用户名不能为空");
            isPass = false;
        } else {
            mName.setError(null);
        }

        if (password.isEmpty()) {
            mPassword.setError("密码不能为空");
            isPass = false;
        } else if (password.length() < 4) {
            mPassword.setError("请至少输入4个字符");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        if (rePassword.isEmpty() || !rePassword.equals(password)) {
            mRePassword.setError("所填信息有误");
            isPass = false;
        } else {
            mRePassword.setError(null);
        }
        return isPass;
    }

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAccountTrigger = (IAccountTrigger) context;
    }

    @Override
    protected RegisterContract.Presenter initPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    public void showSuccess() {
        mName.setText("");
        mPassword.setText("");
        mRePassword.setText("");
        mName.setEnabled(true);
        mPassword.setEnabled(true);
        mRePassword.setEnabled(true);
        mSubmit.setEnabled(true);
        mTxtGoLogin.setEnabled(true);
        mAccountTrigger.triggerView();
    }

    @Override
    public void fetchUserNameAndPassword(String username, String password) {
        mPresenter.registerToLocal(username, password);
    }

    @Override
    public void showError(@StringRes int str) {
        super.showError(str);
        mName.setEnabled(true);
        mPassword.setEnabled(true);
        mRePassword.setEnabled(true);
        mSubmit.setEnabled(true);
        mTxtGoLogin.setEnabled(true);
    }

    @Override
    public void showError(String message) {
        super.showError(message);
        mName.setEnabled(true);
        mPassword.setEnabled(true);
        mRePassword.setEnabled(true);
        mSubmit.setEnabled(true);
        mTxtGoLogin.setEnabled(true);
    }
}
