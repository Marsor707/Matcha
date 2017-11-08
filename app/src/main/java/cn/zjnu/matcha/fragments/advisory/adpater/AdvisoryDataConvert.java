package cn.zjnu.matcha.fragments.advisory.adpater;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

import cn.zjnu.matcha.core.ui.recycler.DataConvert;
import cn.zjnu.matcha.factory.model.advisory.SpecialistModel;

/**
 * Created by HuQiang on 2017/11/7.
 */

public class AdvisoryDataConvert extends DataConvert<SpecialistModel> {
    @Override
    public ArrayList<SpecialistModel> convert() {
        final JSONArray dataList = JSON.parseArray(getJsonData());
        final int dataListSize = dataList.size();
        for (int i = 0; i < dataListSize; i++) {
            final JSONObject jsonObject = dataList.getJSONObject(i);
            final String name = jsonObject.getString("expertName");
            final String area = jsonObject.getString("area");
            final String picture = jsonObject.getString("picture");
            final SpecialistModel model = new SpecialistModel();
            model.setArea(area)
                    .setExpertName(name)
                    .setPicture(picture);
            ENTITIES.add(model);
        }
        return ENTITIES;
    }
}
