package cn.zjnu.matcha.fragments.zone;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.BaseFragment;
import cn.zjnu.matcha.factory.model.notification.NotificationModel;
import cn.zjnu.matcha.fragments.zone.adapter.NotificationAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZoneFragment extends BaseFragment {

    private List<NotificationModel> models = new ArrayList<>();

    @BindView(R.id.recycler_view)
    RecyclerView mRecycler;

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_zone;
    }

    @Override
    protected void initData() {
        super.initData();
        NotificationModel model = new NotificationModel();
        model.setDate_day("30");
        model.setDate_year_month("2017.10");
        model.setTitle("title");
        model.setContent("content");
        for (int i = 0; i < 10; i++) {
            models.add(model);
        }
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        NotificationAdapter adapter = new NotificationAdapter(R.layout.item_notification, models);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecycler.setAdapter(adapter);
        mRecycler.setLayoutManager(manager);
    }

    public static ZoneFragment newInstance(Bundle bundle) {
        ZoneFragment fragment = new ZoneFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

}
