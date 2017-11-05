package cn.zjnu.matcha.factory.mvp.reserve;

import java.util.List;

import cn.zjnu.matcha.core.factory.BaseContract;
import cn.zjnu.matcha.factory.model.reserve.ReserveModel;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public interface ReserveListContract {

    interface View extends BaseContract.View<Presenter> {
        void getDataSuccess(List<ReserveModel> models);
    }

    interface Presenter extends BaseContract.Presenter {
        void getData();
    }
}
