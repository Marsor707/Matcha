package cn.zjnu.matcha.fragments.account;


import android.content.Context;

import butterknife.OnClick;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.BaseFragment;

/**
 * 注册界面的Fragment
 */
public class RegisterFragment extends BaseFragment {

    private IAccountTrigger mAccountTrigger;

    @OnClick(R.id.txt_go_login)
    void onGoLoginClick() {
        mAccountTrigger.triggerView();
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

}
