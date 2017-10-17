package cn.zjnu.matcha.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.PresenterFragment;
import cn.zjnu.matcha.factory.mvp.login.contract.LoginContract;
import cn.zjnu.matcha.factory.mvp.login.presenter.LoginPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends PresenterFragment<LoginContract.Presenter> implements LoginContract.View {


    @BindView(R.id.edit_username)
    EditText mEditUsername;
    @BindView(R.id.edit_password)
    EditText mEditPassword;
    @BindView(R.id.txt_go_register)
    TextView mTxtGoRegister;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter(this);
    }

}
