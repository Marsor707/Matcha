package cn.zjnu.matcha.factory.mvp.advisory.specialist;

import cn.zjnu.matcha.core.factory.BaseContract;

/**
 * Created by Hu on 2017/11/7.
 */

public interface SpecialistContract {
    interface Presenter extends BaseContract.Presenter {
        void getSpecialist();
    }

    interface View extends BaseContract.View<Presenter> {
        void getSpecialistSuccess(String response);
    }
}
