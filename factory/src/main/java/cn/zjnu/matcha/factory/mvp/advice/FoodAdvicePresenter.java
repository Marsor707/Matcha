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

public class FoodAdvicePresenter extends BasePresenter<FoodAdviceContract.View> implements FoodAdviceContract.Presenter {

    public FoodAdvicePresenter(FoodAdviceContract.View view) {
        super(view);
    }

    @Override
    public void getFoodAdviceData(long id) {
        RestClient.builder()
                .url("getDiet")
                .params("userId", id)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        if (!TextUtils.isEmpty(response)) {
                            getView().getFoodAdviceSuccess(response);
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
