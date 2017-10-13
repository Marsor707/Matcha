package cn.zjnu.matcha.core.app;

import android.content.Context;
import android.support.annotation.StringRes;

import cn.zjnu.matcha.core.factory.BaseContract;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public abstract class PresenterFragment<Presenter extends BaseContract.Presenter> extends PermissionFragment implements BaseContract.View<Presenter> {

    protected Presenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //在界面onAttach后就触发初始化Presenter
        initPresenter();
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

    @Override
    public void showError(@StringRes int str) {
        Matcha.showToast(str);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.destroy();
    }
}
