package cn.zjnu.matcha.fragments.reserve;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.PresenterFragment;
import cn.zjnu.matcha.factory.model.reserve.ReserveModel;
import cn.zjnu.matcha.factory.mvp.reserve.ReserveListContract;
import cn.zjnu.matcha.factory.mvp.reserve.ReservePresenter;
import cn.zjnu.matcha.fragments.reserve.adapter.ReserveListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReserveListFragment extends PresenterFragment<ReserveListContract.Presenter>
        implements ReserveListContract.View, BaseQuickAdapter.OnItemClickListener {

    private List<ReserveModel> mReserveData = new ArrayList<>();
    private ReserveListAdapter mAdapter;
    private IReserveTrigger mTrigger;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_reserve_list;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mTrigger = (IReserveTrigger) context;
    }

    @Override
    protected ReserveListContract.Presenter initPresenter() {
        return new ReservePresenter(this);
    }

    @Override
    protected void initData() {
        super.initData();
        if (mReserveData.size() == 0) {
            mPresenter.getData();
        }
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mAdapter = new ReserveListAdapter(R.layout.item_reserve, mReserveData);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(manager);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final ReserveModel model = (ReserveModel) adapter.getData().get(position);
        mTrigger.triggerView(model);
    }

    @Override
    public void getDataSuccess(List<ReserveModel> models) {
        mReserveData.addAll(models);
        mAdapter.notifyDataSetChanged();
    }
}
