package cn.zjnu.matcha.factory.model.notification;

/**
 * Created by Admin on 2017/10/31.
 */

public class NotificationModel {

    private String date_year_month;
    private String date_day;
    private String title;
    private String content;

    public String getDate_year_month() {
        return date_year_month;
    }

    public void setDate_year_month(String date_year_month) {
        this.date_year_month = date_year_month;
    }

    public String getDate_day() {
        return date_day;
    }

    public void setDate_day(String date_day) {
        this.date_day = date_day;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
