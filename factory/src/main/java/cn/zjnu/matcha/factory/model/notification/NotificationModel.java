package cn.zjnu.matcha.factory.model.notification;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Admin on 2017/10/31.
 */

public class NotificationModel implements Parcelable {

    private String date_year_month;
    private String date_day;
    private String title;
    private String content;

    public NotificationModel() {
    }

    protected NotificationModel(Parcel in) {
        date_year_month = in.readString();
        date_day = in.readString();
        title = in.readString();
        content = in.readString();
    }

    public static final Creator<NotificationModel> CREATOR = new Creator<NotificationModel>() {
        @Override
        public NotificationModel createFromParcel(Parcel in) {
            return new NotificationModel(in);
        }

        @Override
        public NotificationModel[] newArray(int size) {
            return new NotificationModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date_year_month);
        dest.writeString(date_day);
        dest.writeString(title);
        dest.writeString(content);
    }
}
