package cn.zjnu.matcha.factory.mvp.reserve;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.zjnu.matcha.core.factory.BasePresenter;
import cn.zjnu.matcha.core.net.RestClient;
import cn.zjnu.matcha.core.net.callbacks.IError;
import cn.zjnu.matcha.core.net.callbacks.IFailure;
import cn.zjnu.matcha.core.net.callbacks.ISuccess;
import cn.zjnu.matcha.factory.model.reserve.ReserveModel;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class ReserveListPresenter extends BasePresenter<ReserveListContract.View> implements ReserveListContract.Presenter {

    public ReserveListPresenter(ReserveListContract.View view) {
        super(view);
    }

    @Override
    public void getData() {
        RestClient.builder()
                .url("queryAllOrder")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final List<ReserveModel> models = new ArrayList<>();
                        JSONArray array = JSONObject.parseArray(response);
                        final int size = array.size();
                        for (int i = 0; i < size; i++) {
                            final ReserveModel model = new ReserveModel();
                            final JSONObject object = (JSONObject) array.get(i);
                            final int id = object.getInteger("id");
                            final String reserveName = object.getString("orderName");
                            final int numberTotal = object.getInteger("numberLimit");
                            final int numberNow = object.getInteger("number");
                            final String content = object.getString("content");
                            model.setId(id);
                            model.setName(reserveName);
                            model.setNumberNow(numberNow);
                            model.setNumberTotal(numberTotal);
                            model.setContent(content);
                            models.add(model);

                        }
                        getView().getDataSuccess(models);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        getView().showError("获取数据失败");
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        getView().showError("获取数据失败");
                    }
                })
                .build()
                .get();
    }
}
