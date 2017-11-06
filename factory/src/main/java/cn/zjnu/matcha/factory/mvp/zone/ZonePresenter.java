package cn.zjnu.matcha.factory.mvp.zone;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.zjnu.matcha.core.factory.BasePresenter;
import cn.zjnu.matcha.core.net.RestClient;
import cn.zjnu.matcha.core.net.callbacks.IError;
import cn.zjnu.matcha.core.net.callbacks.IFailure;
import cn.zjnu.matcha.core.net.callbacks.ISuccess;
import cn.zjnu.matcha.factory.model.notification.NotificationModel;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class ZonePresenter extends BasePresenter<ZoneContract.View> implements ZoneContract.Presenter {

    public ZonePresenter(ZoneContract.View view) {
        super(view);
    }

    @Override
    public void getData() {
        RestClient.builder()
                .url("queryAllNotice")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final List<NotificationModel> notificationList = new ArrayList<>();
                        JSONArray list = JSONObject.parseArray(response);
                        final int size = list.size();
                        for (int i = 0; i < size; i++) {
                            final NotificationModel model = new NotificationModel();
                            JSONObject notificationInfo = (JSONObject) list.get(i);
                            String title = notificationInfo.getString("title");
                            String date = notificationInfo.getString("date");
                            String content = notificationInfo.getString("content");
                            model.setContent(content);
                            model.setTitle(title);
                            final String year = date.substring(0, 4);
                            final String month = date.substring(5, 7);
                            final String day = date.substring(8, 10);
                            model.setDate_year_month(year + "." + month);
                            model.setDate_day(day);
                            notificationList.add(model);
                        }
                        getView().getDataSuccess(notificationList);
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        getView().showError("获取数据失败");
                        getView().getDataFail();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        getView().showError("获取数据失败");
                        getView().getDataFail();
                    }
                })
                .build()
                .get();
    }
}
