package cn.zjnu.matcha.activities;

import butterknife.OnClick;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.PresenterActivity;
import cn.zjnu.matcha.fragments.BlankFragment;

public class MainActivity extends PresenterActivity<ContractForActivity.Presenter> implements ContractForActivity.View {

    @Override
    protected Object getContentLayoutId() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.btn_scan)
    void onBtnClick() {
        mPresenter.startRest();
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.lay_container,new BlankFragment())
                .commit();
    }

    @Override
    public void restSuccess(String str) {
        showToast(str);
    }

    @Override
    protected ContractForActivity.Presenter initPresenter() {
        return new PresenterForActivity(this);
    }
}
