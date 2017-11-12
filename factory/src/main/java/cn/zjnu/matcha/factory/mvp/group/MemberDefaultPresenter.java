package cn.zjnu.matcha.factory.mvp.group;

import cn.zjnu.matcha.core.factory.BasePresenter;
import cn.zjnu.matcha.core.net.RestClient;
import cn.zjnu.matcha.core.net.callbacks.IError;
import cn.zjnu.matcha.core.net.callbacks.IFailure;
import cn.zjnu.matcha.core.net.callbacks.ISuccess;

/**
 * Created by Hu on 2017/10/27.
 */

public class MemberDefaultPresenter extends BasePresenter<MemberDefaultContract.View> implements MemberDefaultContract.Presenter {
    public MemberDefaultPresenter(MemberDefaultContract.View view) {
        super(view);
    }

    @Override
    public void getMemberInfos(long groupId) {
        RestClient.builder()
                .url("https://api.im.jpush.cn/v1/groups/" + groupId + "/members/")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        getView().getMemberInfosSuccess(response);
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        getView().showError(msg);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        getView().showError("网络不见了");
                    }
                })
                .build()
                .get();

    }

}
