package cn.zjnu.matcha.factory.mvp.pe;

import java.util.List;

import cn.zjnu.matcha.core.factory.BaseContract;
import cn.zjnu.matcha.factory.model.pe.PEDataModel;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public interface PEDataContract extends BaseContract {

    interface View extends BaseContract.View<Presenter> {
        void getPEDataSuccess(List<PEDataModel> shape, List<PEDataModel> function, List<PEDataModel> quality, float total);

        void getPEDataFail();
    }

    interface Presenter extends BaseContract.Presenter {
        void getPEData(long id);
    }
}
