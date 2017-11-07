package cn.zjnu.matcha.core.ui.recycler;

import java.util.ArrayList;

/**
 * Created by HuQiang on 2017/11/7.
 */

public abstract class DataConvert<T> {
    protected final ArrayList<T> ENTITIES = new ArrayList<>();

    public abstract ArrayList<T> convert();

    private String mJsonData;

    public String getJsonData() {
        if (mJsonData == null || mJsonData.isEmpty()) {
            throw new NullPointerException("JSONData IS NULL");
        }
        return mJsonData;
    }

    public DataConvert setJsonData(String jsonData) {
        this.mJsonData = jsonData;
        return this;
    }
}
