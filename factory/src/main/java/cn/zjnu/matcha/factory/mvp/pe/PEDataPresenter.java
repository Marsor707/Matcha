package cn.zjnu.matcha.factory.mvp.pe;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.zjnu.matcha.core.app.BaseActivity;
import cn.zjnu.matcha.core.factory.BasePresenter;
import cn.zjnu.matcha.core.net.RestClient;
import cn.zjnu.matcha.core.net.callbacks.IError;
import cn.zjnu.matcha.core.net.callbacks.IFailure;
import cn.zjnu.matcha.core.net.callbacks.ISuccess;
import cn.zjnu.matcha.factory.model.pe.PEDataModel;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class PEDataPresenter extends BasePresenter<PEDataContract.View> implements PEDataContract.Presenter {

    public PEDataPresenter(PEDataContract.View view) {
        super(view);
    }

    @Override
    public void getPEData(long id) {
        // TODO: 2017/11/16 请求后端根据userId拿体测数据
        final List<PEDataModel> shape = new ArrayList<>();
        final List<PEDataModel> function = new ArrayList<>();
        final List<PEDataModel> quality = new ArrayList<>();
        RestClient.builder()
                .url("http://10.7.90.230:8888/getPe")
                .params("userId", id)
                .loader((BaseActivity) getView())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        JSONObject obj = JSONObject.parseObject(response);
                        JSONArray shapeArray = obj.getJSONArray("shape");
                        JSONArray functionArray = obj.getJSONArray("function");
                        JSONArray qualityArray = obj.getJSONArray("quality");
                        final long total = obj.getLong("totalScore");
                        shape.addAll(parseArray(shapeArray));
                        function.addAll(parseArray(functionArray));
                        quality.addAll(parseArray(qualityArray));
                        getView().getPEDataSuccess(shape, function, quality, total);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        getView().showError("查询失败");
                        getView().getPEDataFail();
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        getView().showError("查询失败");
                        getView().getPEDataFail();
                    }
                })
                .build()
                .get();

    }

    private List<PEDataModel> parseArray(JSONArray array) {
        final int size = array.size();
        final List<PEDataModel> dataList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            JSONObject obj = array.getJSONObject(i);
            final String name = obj.getString("name");
            final String mark = obj.getString("mark");
            final long score = obj.getLong("score");
            PEDataModel model = new PEDataModel(name, mark, score);
            dataList.add(model);
        }
        return dataList;
    }
}
