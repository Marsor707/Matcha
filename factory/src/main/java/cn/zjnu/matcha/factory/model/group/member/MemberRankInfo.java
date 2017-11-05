package cn.zjnu.matcha.factory.model.group.member;

import android.support.annotation.NonNull;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class MemberRankInfo implements Comparable<MemberRankInfo> {

    private String userName;
    private int messageSize;

    public MemberRankInfo(String userName, int messageSize) {
        this.userName = userName;
        this.messageSize = messageSize;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getMessageSize() {
        return messageSize;
    }

    public void setMessageSize(int messageSize) {
        this.messageSize = messageSize;
    }

    @Override
    public int compareTo(@NonNull MemberRankInfo o) {
        return o.getMessageSize() - this.getMessageSize();
    }
}
