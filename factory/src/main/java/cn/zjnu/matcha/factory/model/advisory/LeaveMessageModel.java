package cn.zjnu.matcha.factory.model.advisory;

/**
 * Created by HuQiang on 2017/11/7.
 */

public class LeaveMessageModel {
    private String mExpertId;
    private String mUserName;
    private String mContent;
    private String mTime;

    public LeaveMessageModel(String mExpertId, String mUserName, String mContent, String mTime) {
        this.mExpertId = mExpertId;
        this.mUserName = mUserName;
        this.mContent = mContent;
        this.mTime = mTime;
    }

    public String getExpertId() {
        return mExpertId;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getContent() {
        return mContent;
    }

    public String getTime() {
        return mTime;
    }

    public static final class Builder {
        private String expertId;
        private String userName;
        private String content;
        private String time;

        public Builder setExpertId(String expertId) {
            this.expertId = expertId;
            return this;
        }

        public Builder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setTime(String time) {
            this.time = time;
            return this;
        }

        public LeaveMessageModel build() {
            return new LeaveMessageModel(expertId, userName, content, time);
        }
    }
}
