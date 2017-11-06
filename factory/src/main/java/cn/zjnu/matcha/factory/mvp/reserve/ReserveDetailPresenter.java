package cn.zjnu.matcha.factory.mvp.reserve;

import cn.zjnu.matcha.core.factory.BasePresenter;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class ReserveDetailPresenter extends BasePresenter<ReserveDetailContract.View> implements ReserveDetailContract.Presenter {

    public ReserveDetailPresenter(ReserveDetailContract.View view) {
        super(view);
    }

    @Override
    public void reserve(int reserveId) {
        // TODO: 2017/11/6 请求后端进行预约
    }

    @Override
    public void getReserveState(int reserveId) {
        // TODO: 2017/11/6 请求后端查询此项目预约状态
    }
}
