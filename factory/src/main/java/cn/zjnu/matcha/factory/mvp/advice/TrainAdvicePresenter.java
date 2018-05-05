package cn.zjnu.matcha.factory.mvp.advice;

import android.text.TextUtils;

import cn.zjnu.matcha.core.factory.BasePresenter;
import cn.zjnu.matcha.core.net.RestClient;
import cn.zjnu.matcha.core.net.callbacks.IError;
import cn.zjnu.matcha.core.net.callbacks.ISuccess;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class TrainAdvicePresenter extends BasePresenter<TrainAdviceContract.View> implements TrainAdviceContract.Presenter {

    public TrainAdvicePresenter(TrainAdviceContract.View view) {
        super(view);
    }

    @Override
    public void getTrainAdviceData(long id) {
        RestClient.builder()
                .url("getTrain")
                .params("userId", id)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        if (!TextUtils.isEmpty(response)) {
                            getView().getTrainAdviceSuccess(response);
                        }
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .build()
                .post();
    }
}
