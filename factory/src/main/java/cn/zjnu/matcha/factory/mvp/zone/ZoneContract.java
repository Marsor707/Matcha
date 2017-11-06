package cn.zjnu.matcha.factory.mvp.zone;

import java.util.List;

import cn.zjnu.matcha.core.factory.BaseContract;
import cn.zjnu.matcha.factory.model.notification.NotificationModel;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public interface ZoneContract {

    interface View extends BaseContract.View<Presenter> {
        void getDataSuccess(List<NotificationModel> notificationModels);

        void getDataFail();
    }

    interface Presenter extends BaseContract.Presenter {
        void getData();
    }
}
