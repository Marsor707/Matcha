package cn.zjnu.matcha.factory.mvp.account;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import cn.zjnu.matcha.core.app.Matcha;
import cn.zjnu.matcha.core.factory.BasePresenter;
import cn.zjnu.matcha.factory.model.jiguang.ResponseCodes;

/**
 * Created by Hu on 2017/10/18.
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {
    public RegisterPresenter(RegisterContract.View view) {
        super(view);
    }

    @Override
    public void register(final String username, final String password) {
        getView().showLoading();
        JMessageClient.register(username, password, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == ResponseCodes.SUCCESSFUL) {
                    getView().showSuccess();
                } else {
                    Matcha.showToast("错误" + s);
                }
            }
        });
    }
}
