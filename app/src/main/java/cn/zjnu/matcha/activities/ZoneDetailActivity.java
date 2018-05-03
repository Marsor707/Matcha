package cn.zjnu.matcha.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.BaseActivity;
import cn.zjnu.matcha.factory.model.notification.NotificationModel;

public class ZoneDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.tv_content)
    TextView mTvContent;

    private static final String ZONE_DETAIL_KEY = "ZONE_DETAIL_KEY";
    private NotificationModel model = null;

    public static void show(Context context, NotificationModel model) {
        Intent intent = new Intent(context, ZoneDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(ZONE_DETAIL_KEY, model);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected Object getContentLayoutId() {
        return R.layout.activity_zone_detail;
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        this.model = bundle.getParcelable(ZONE_DETAIL_KEY);
        return model != null;
    }

    @Override
    protected void initData() {
        mTvTitle.setText(new StringBuilder(model.getTitle()));
        mTvDate.setText(new StringBuilder(model.getDate_year_month()).append(".")
                .append(model.getDate_day()));
        mTvContent.setText(new StringBuilder(model.getContent()));
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        initToolbar();
    }

    private void initToolbar() {
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
