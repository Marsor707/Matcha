package cn.zjnu.matcha.factory.model.group.member;

/**
 * Created by Hu on 2017/10/27.
 */

public class MemberInfo {

    /**
     * username : admin
     * flag : 1
     * group_time : 2017-10-21 14:36:54
     * nickname : admin
     * address : 18888888888
     * avatar : qiniu/image/a/48119F7F652193BC15B62A7AB3A748CB
     * ctime : 2017-10-13 18:59:22
     * extras : {"phone":"18888888888"}
     */

    private String username;
    private int flag;
    private String group_time;
    private String nickname;
    private String address;
    private String avatar;
    private String ctime;
    private ExtrasBean extras;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getGroup_time() {
        return group_time;
    }

    public void setGroup_time(String group_time) {
        this.group_time = group_time;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public ExtrasBean getExtras() {
        return extras;
    }

    public void setExtras(ExtrasBean extras) {
        this.extras = extras;
    }

    public static class ExtrasBean {
        /**
         * phone : 18888888888
         */

        private String phone;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
