package cn.zjnu.matcha.activities;

import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.BaseActivity;
import cn.zjnu.matcha.core.app.BaseFragment;
import cn.zjnu.matcha.fragments.account.IAccountTrigger;
import cn.zjnu.matcha.fragments.account.LoginFragment;
import cn.zjnu.matcha.fragments.account.RegisterFragment;

/**
 * LoginFragment & RegisterFragment的容器Activity
 */
public class AccountActivity extends BaseActivity implements IAccountTrigger {

    private BaseFragment mCurrentFragment;
    private BaseFragment mLoginFragment;
    private BaseFragment mRegisterFragment;


    @Override
    protected Object getContentLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mCurrentFragment = mLoginFragment = new LoginFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container, mCurrentFragment)
                .commit();
    }

    @Override
    public void triggerView() {
        BaseFragment fragment;
        if (mCurrentFragment == mLoginFragment) {
            if (mRegisterFragment == null) {
                //默认情况下为null
                mRegisterFragment = new RegisterFragment();
            }
            fragment = mRegisterFragment;
        } else {
            //默认情况下不为null
            fragment = mLoginFragment;
        }
        mCurrentFragment = fragment;
        //切换显示
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.lay_container, fragment)
                .commit();
    }

}
