package cn.zjnu.matcha.factory.mvp.reserve;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.jpush.im.android.api.JMessageClient;
import cn.zjnu.matcha.core.app.BaseFragment;
import cn.zjnu.matcha.core.factory.BasePresenter;
import cn.zjnu.matcha.core.net.RestClient;
import cn.zjnu.matcha.core.net.callbacks.IError;
import cn.zjnu.matcha.core.net.callbacks.IFailure;
import cn.zjnu.matcha.core.net.callbacks.ISuccess;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class ReserveDetailPresenter extends BasePresenter<ReserveDetailContract.View> implements ReserveDetailContract.Presenter {

    public ReserveDetailPresenter(ReserveDetailContract.View view) {
        super(view);
    }

    @Override
    public void reserve(int reserveId) {
        final long userId = JMessageClient.getMyInfo().getUserID();
        //请求后端进行预约
        RestClient.builder()
                .url("beginOrder")
                .params("userId", userId)
                .params("orderId", reserveId)
                .loader(((BaseFragment) getView()).getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        getView().reserveSuccess();
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
                        getView().showError("网络连接失败");
                    }
                })
                .build()
                .get();
    }

    @Override
    public void getReserveState(int reserveId) {
        final long userId = JMessageClient.getMyInfo().getUserID();
        // 请求后端查询此项目预约状态
        RestClient.builder()
                .url("queryOrderState")
                .params("userId", userId)
                .params("orderId", reserveId)
                .loader(((BaseFragment) getView()).getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        JSONArray array = JSON.parseArray(response);
                        if (array.size() > 0) {
                            JSONObject object = array.getJSONObject(0);
                            final int state = object.getInteger("state");
                            final boolean isReserved = state == 1;
                            getView().getReserveStateSuccess(isReserved);
                        }
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
                        getView().showError("网络连接失败");
                    }
                })
                .build()
                .get();
    }
}
