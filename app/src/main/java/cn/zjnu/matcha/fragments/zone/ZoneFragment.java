package cn.zjnu.matcha.fragments.zone;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.activities.ZoneDetailActivity;
import cn.zjnu.matcha.core.app.PresenterFragment;
import cn.zjnu.matcha.factory.model.notification.NotificationModel;
import cn.zjnu.matcha.factory.mvp.zone.ZoneContract;
import cn.zjnu.matcha.factory.mvp.zone.ZonePresenter;
import cn.zjnu.matcha.fragments.zone.adapter.NotificationAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZoneFragment extends PresenterFragment<ZoneContract.Presenter> implements ZoneContract.View,
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener {

    private List<NotificationModel> models = new ArrayList<>();

    @BindView(R.id.recycler_view)
    RecyclerView mRecycler;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_zone;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getData();
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        NotificationAdapter adapter = new NotificationAdapter(R.layout.item_notification, models);
        adapter.setOnItemClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecycler.setAdapter(adapter);
        mRecycler.setLayoutManager(manager);
        mRefresh.setOnRefreshListener(this);
    }

    public static ZoneFragment newInstance(Bundle bundle) {
        ZoneFragment fragment = new ZoneFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected ZoneContract.Presenter initPresenter() {
        return new ZonePresenter(this);
    }

    @Override
    public void getDataSuccess(List<NotificationModel> notificationModels) {
        if (models.size() > 0) {
            models.clear();
        }
        models.addAll(notificationModels);
        mRecycler.getAdapter().notifyDataSetChanged();
        if (mRefresh.isRefreshing()) {
            mRefresh.setRefreshing(false);
        }
    }

    @Override
    public void getDataFail() {
        if (mRefresh.isRefreshing()) {
            mRefresh.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.getData();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        NotificationModel model = (NotificationModel) adapter.getItem(position);
        ZoneDetailActivity.show(getActivity(), model);
    }
}
