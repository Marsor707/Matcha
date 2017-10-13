package cn.zjnu.matcha.core.app;

import android.support.annotation.StringRes;

import cn.zjnu.matcha.core.factory.BaseContract;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public abstract class PresenterActivity<Presenter extends BaseContract.Presenter> extends BaseActivity implements BaseContract.View<Presenter> {
    protected Presenter mPresenter;

    @Override
    protected void initBefore() {
        super.initBefore();
        //初始化Presenter
        initPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //界面关闭时进行销毁的操作
        if (mPresenter != null) {
            mPresenter.destroy();
        }
    }

    @Override
    public void showError(@StringRes int str) {
        Matcha.showToast(str);
    }

    /**
     * 初始化Presenter
     *
     * @return Presenter
     */
    protected abstract Presenter initPresenter();

    @Override
    public void setPresenter(Presenter presenter) {
        //View中赋值Presenter
        mPresenter = presenter;
    }
}
