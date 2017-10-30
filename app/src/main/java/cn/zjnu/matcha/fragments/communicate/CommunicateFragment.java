package cn.zjnu.matcha.fragments.communicate;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.content.EventNotificationContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.activities.MessageActivity;
import cn.zjnu.matcha.core.app.PresenterFragment;
import cn.zjnu.matcha.factory.mvp.communicate.CommunicateContract;
import cn.zjnu.matcha.factory.mvp.communicate.CommunicatePresenter;
import cn.zjnu.matcha.fragments.communicate.adapter.JoinedGroupsAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommunicateFragment extends PresenterFragment<CommunicateContract.Presenter> implements CommunicateContract.View {

    private JoinedGroupsAdapter mAdapter = null;
    private List<GroupInfo> mGroupInfos = new ArrayList<>();

    public static CommunicateFragment newInstance(Bundle bundle) {
        CommunicateFragment fragment = new CommunicateFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @OnClick(R.id.txt_join_group)
    void onJoinGroupClick() {
        startScanWithCheck(getContext());
    }

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_communicate;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        initAdapter();
        mPresenter.getGroupList();
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.initCallback();
    }

    @Override
    public void showGroupList(final List<GroupInfo> groupInfos) {
        mGroupInfos.clear();
        for (GroupInfo groupInfo : groupInfos) {
            if (!mGroupInfos.contains(groupInfo)) {
                mGroupInfos.add(groupInfo);
            }
        }
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void joinGroupSuccess(GroupInfo groupInfo) {
        mGroupInfos.add(groupInfo);
        mAdapter.notifyDataSetChanged();
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

    private void initAdapter() {
        if (mAdapter == null) {
            mAdapter = new JoinedGroupsAdapter(R.layout.item_grouplist, mGroupInfos);
            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(manager);
            mRecyclerView.setAdapter(mAdapter);
            initItemClickListener();
        }
    }

    public void onEvent(MessageEvent event) {
        final Message message = event.getMessage();
        switch (message.getContentType()) {
            case eventNotification:
                EventNotificationContent.EventNotificationType type =
                        ((EventNotificationContent) message.getContent()).getEventNotificationType();
                GroupInfo groupInfo = (GroupInfo) message.getTargetInfo();
                switch (type) {
                    case group_member_removed:
                    case group_member_exit:
                        final boolean isContainsMyself = JSONObject
                                .parseObject(message.getContent().toJson())
                                .getBoolean("isContainsMyself");
                        if (isContainsMyself)
                            for (int i = 0; i < mGroupInfos.size(); i++) {
                                if (mGroupInfos.get(i).getGroupID() == groupInfo.getGroupID()) {
                                    mGroupInfos.remove(i);
                                    mAdapter.notifyItemRemoved(i);
                                    break;
                                }
                            }
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }
}
