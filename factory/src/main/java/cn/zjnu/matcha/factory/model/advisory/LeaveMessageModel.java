package cn.zjnu.matcha.factory.model.advisory;

/**
 * Created by HuQiang on 2017/11/7.
 */

public class LeaveMessageModel {
    private String expertId;
    private String userName;
    private String content;
    private String time;

    public String getUserName() {
        return userName;
    }

    public LeaveMessageModel setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getContent() {
        return content;
    }

    public LeaveMessageModel setContent(String content) {
        this.content = content;
        return this;
    }

    public String getTime() {
        return time;
    }

    public LeaveMessageModel setTime(String time) {
        this.time = time;
        return this;
    }

    public String getExpertId() {
        return expertId;
    }

    public LeaveMessageModel setExpertId(String expertId) {
        this.expertId = expertId;
        return this;
    }
}
