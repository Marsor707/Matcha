package cn.zjnu.matcha.factory.mvp.advisory.specialist;

import cn.zjnu.matcha.core.app.Matcha;
import cn.zjnu.matcha.core.factory.BasePresenter;
import cn.zjnu.matcha.core.net.RestClient;
import cn.zjnu.matcha.core.net.callbacks.IFailure;
import cn.zjnu.matcha.core.net.callbacks.ISuccess;

/**
 * Created by Hu on 2017/11/7.
 */

public class SpecialistPresenter extends BasePresenter<SpecialistContract.View> implements SpecialistContract.Presenter {
    public SpecialistPresenter(SpecialistContract.View view) {
        super(view);
    }

    @Override
    public void getSpecialist() {
        RestClient.builder()
                .url("listExpert")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        getView().getSpecialistSuccess(response);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Matcha.showToast("网络连接失败");
                    }
                })
                .build()
                .get();
    }

    @Override
    public void getMsg(long userId, String specialId) {
        RestClient.builder()
                .url("wordsList")
                .params("userId", userId)
                .params("expertId", specialId)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        getView().getMsgSuccess(response);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Matcha.showToast("网络连接失败");
                    }
                })
                .build()
                .post();
    }

    @Override
    public void sendMsg(String content, long userId, String expertId) {
        RestClient.builder()
                .url("insertWords")
                .params("content", content)
                .params("user.userId", userId)
                .params("expert.expertId", expertId)
                .params("item", 1)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        getView().sendMsgSuccess(response);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Matcha.showToast("网络连接失败");
                    }
                })
                .build()
                .post();
    }
}
