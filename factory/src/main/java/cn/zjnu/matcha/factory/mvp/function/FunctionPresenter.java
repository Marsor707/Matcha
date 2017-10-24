package cn.zjnu.matcha.factory.mvp.function;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.zjnu.matcha.core.factory.BasePresenter;
import cn.zjnu.matcha.factory.model.jiguang.ResponseCodes;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class FunctionPresenter extends BasePresenter<FunctionContract.View> implements FunctionContract.Presenter {

    public FunctionPresenter(FunctionContract.View view) {
        super(view);
    }

    @Override
    public void setUserPortrait() {
        UserInfo userInfo = JMessageClient.getMyInfo();
        userInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
            @Override
            public void gotResult(int i, String s, Bitmap bitmap) {
                if (i == ResponseCodes.SUCCESSFUL) {
                    ByteArrayOutputStream bo = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bo);
                    byte[] bytes = bo.toByteArray();
                    getView().initPortrait(bytes);
                } else if (i == 871311) {
                    // 用户没有头像时逻辑
                } else {
                    getView().showError(s);
                }
            }
        });
    }
}
