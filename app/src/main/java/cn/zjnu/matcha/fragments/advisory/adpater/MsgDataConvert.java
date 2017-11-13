package cn.zjnu.matcha.fragments.advisory.adpater;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

import cn.zjnu.matcha.core.ui.recycler.DataConvert;
import cn.zjnu.matcha.factory.model.advisory.LeaveMessageModel;

/**
 * Created by Hu on 2017/11/9.
 */

public class MsgDataConvert extends DataConvert<LeaveMessageModel> {
    @Override
    public ArrayList<LeaveMessageModel> convert() {
        final JSONArray dataList = JSON.parseArray(getJsonData());
        final int dataListSize = dataList.size();
        for (int i = 0; i < dataListSize; i++) {
            final JSONObject object = dataList.getJSONObject(i);
            final JSONObject userObj = object.getJSONObject("user");
            final String name = userObj.getString("userName");
            final JSONObject expertObj = object.getJSONObject("expert");
            final String expertId = expertObj.getString("expertId");
            final String content = object.getString("content");
            final String time = object.getString("formatWordtime");
            final LeaveMessageModel messageModel = new LeaveMessageModel.Builder()
                    .setContent(content)
                    .setExpertId(expertId)
                    .setTime(time)
                    .setUserName(name)
                    .build();
            ENTITIES.add(messageModel);
        }
        return ENTITIES;
    }
}
