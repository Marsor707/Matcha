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

        void initUserName(String userName);

        void initNickName(String nickName);

        void initPhone(String phone);

        void setUserPortrait(Uri uri);

        void updateNickName(String nickName);

        void setPhoneSuccess(String phone);
    }

    interface Presenter extends BaseContract.Presenter {
        void getUserPortrait();

        void initCallback();

        void getUserName();

        void getNickName();

        void setNickName(String nickName);

        void getUserPhone();

        void setPhone(String phone);
    }
}
