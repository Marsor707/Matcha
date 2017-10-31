package cn.zjnu.matcha.fragments.zone.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.zjnu.matcha.R;
import cn.zjnu.matcha.factory.model.notification.NotificationModel;

/**
 * Created by Admin on 2017/10/31.
 */

public class NotificationAdapter extends BaseQuickAdapter<NotificationModel, BaseViewHolder> {

    public NotificationAdapter(@LayoutRes int layoutResId, @Nullable List<NotificationModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NotificationModel item) {
        helper.setText(R.id.txt_notification_date_day, item.getDate_day());
        helper.setText(R.id.txt_notification_date_year_month, item.getDate_year_month());
        helper.setText(R.id.txt_notification_title, item.getTitle());
        helper.setText(R.id.txt_notification_content, item.getContent());
    }
}
