package cn.zjnu.matcha.factory.mvp.account;

import android.content.Context;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import cn.zjnu.matcha.core.factory.BasePresenter;
import cn.zjnu.matcha.core.net.RestClient;
import cn.zjnu.matcha.core.net.callbacks.IError;
import cn.zjnu.matcha.core.net.callbacks.IFailure;
import cn.zjnu.matcha.core.net.callbacks.ISuccess;
import cn.zjnu.matcha.factory.model.jiguang.ResponseCodes;

/**
 * Created by Hu on 2017/10/18.
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {
    private Context mContext;


    public RegisterPresenter(RegisterContract.View view) {
        super(view);
    }

    @Override
    public void register(final String username, final String password) {
        JMessageClient.register(username, password, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == ResponseCodes.SUCCESSFUL) {
                    getView().fetchUserNameAndPassword(username, password);
                } else {
                    getView().showError(s + "");
                }
            }
        });
    }

    @Override
    public void registerToLocal(String username, String password, int sex, String birthday) {
        // TODO: 2017/11/11 新增性别出生日期
        RestClient.builder()
                .url("register")
                .params("userName", username)
                .params("password", password)
                .loader(mContext)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        getView().showSuccess();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        getView().showError("错误");
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        getView().showError(msg);
                    }
                })
                .build()
                .post();
    }

    @Override
    public void getContext(Context context) {
        this.mContext = context;
    }

}
