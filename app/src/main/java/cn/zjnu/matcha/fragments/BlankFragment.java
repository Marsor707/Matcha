package cn.zjnu.matcha.fragments;


import android.support.v4.app.Fragment;

import butterknife.OnClick;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.Matcha;
import cn.zjnu.matcha.core.app.PresenterFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends PresenterFragment<ContractForFragment.Presenter> implements ContractForFragment.View {


    @Override
    public void restSuccess(String str) {
        Matcha.showToast(str);
    }

    @OnClick(R.id.btn)
    void onBtnClick() {
        mPresenter.startRest();
    }

    @Override
    protected ContractForFragment.Presenter initPresenter() {
        return new PresenterForFragment(this);
    }

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_blank;
    }
}
