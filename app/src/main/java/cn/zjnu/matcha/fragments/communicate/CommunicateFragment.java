package cn.zjnu.matcha.fragments.communicate;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.activities.MessageActivity;
import cn.zjnu.matcha.core.app.PresenterFragment;
import cn.zjnu.matcha.factory.mvp.communicate.CommunicateContract;
import cn.zjnu.matcha.factory.mvp.communicate.CommunicatePresenter;
import cn.zjnu.matcha.widget.adapter.communicate.GroupsAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommunicateFragment extends PresenterFragment<CommunicateContract.Presenter> implements CommunicateContract.View {

    private GroupsAdapter mAdapter;

    public static CommunicateFragment newInstance(Bundle bundle) {
        CommunicateFragment fragment = new CommunicateFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_communicate;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mPresenter.getGroupId();
    }

    @Override
    public void showGroupList(final List<GroupInfo> groupInfos) {
        mAdapter = new GroupsAdapter(R.layout.item_grouplist, groupInfos);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        initItemClickListener();
    }

    @Override
    public void fetchGroupId(List<Long> id) {
        mPresenter.getGroupList(id);
    }

    @Override
    protected CommunicateContract.Presenter initPresenter() {
        return new CommunicatePresenter(this);
    }

    private void initItemClickListener() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MessageActivity.show(getContext(), (GroupInfo) adapter.getData().get(position));
            }
        });
    }

}
