package cn.zjnu.matcha.factory.mvp.personal;

import android.net.Uri;

import cn.zjnu.matcha.core.factory.BaseContract;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public interface PersonalContract {

    interface View extends BaseContract.View<Presenter> {
        void initPortrait(byte[] bytes);

        void setUserPortrait(Uri uri);
    }

    interface Presenter extends BaseContract.Presenter {
        void setUserPortrait();

        void initCallback();
    }
}
