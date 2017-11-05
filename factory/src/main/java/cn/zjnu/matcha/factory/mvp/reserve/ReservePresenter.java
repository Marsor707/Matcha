package cn.zjnu.matcha.factory.mvp.reserve;

import cn.zjnu.matcha.core.factory.BasePresenter;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class ReservePresenter extends BasePresenter<ReserveListContract.View> implements ReserveListContract.Presenter {

    public ReservePresenter(ReserveListContract.View view) {
        super(view);
    }

    @Override
    public void getData() {
        // TODO: 2017/11/5 先后端获取数据
    }
}
