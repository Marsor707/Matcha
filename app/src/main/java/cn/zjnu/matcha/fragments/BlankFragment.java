package cn.zjnu.matcha.fragments;


import android.support.v4.app.Fragment;
import android.widget.Toast;

import butterknife.OnClick;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.PresenterFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends PresenterFragment<ContractForFragment.Presenter> implements ContractForFragment.View {


    @Override
    public void restSuccess(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
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
