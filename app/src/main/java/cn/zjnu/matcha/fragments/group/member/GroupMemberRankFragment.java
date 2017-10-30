package cn.zjnu.matcha.fragments.group.member;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.jpush.im.android.api.model.Conversation;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.PresenterFragment;
import cn.zjnu.matcha.factory.model.group.member.MemberInfo;
import cn.zjnu.matcha.factory.mvp.group.rank.MemberRankContract;
import cn.zjnu.matcha.factory.mvp.group.rank.MemberRankPresenter;
import cn.zjnu.matcha.fragments.group.member.adapter.GroupMemberRankAdapter;

/**
 * Created by fsh on 2017/10/22.
 */

public class GroupMemberRankFragment extends PresenterFragment<MemberRankContract.Presenter> implements MemberRankContract.View {
    private List<MemberInfo> mMemberInfos;
    private GroupMemberRankAdapter mAdapter;
    private long mGroupId;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public static GroupMemberRankFragment newInstance(Bundle bundle) {
        GroupMemberRankFragment fragment = new GroupMemberRankFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getMemberInfos(mGroupId);
    }

    @Override
    protected void initArgs(Bundle bundle) {
        super.initArgs(bundle);
        mGroupId = bundle.getLong("group_id");
    }

    @Override
    public void getConversation(Conversation conversation) {

    }

    @Override
    public void getMemberInfosSuccess(String response) {
        mMemberInfos = JSONObject.parseArray(response, MemberInfo.class);
        mAdapter = new GroupMemberRankAdapter(getContext(), mMemberInfos);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected MemberRankContract.Presenter initPresenter() {
        return new MemberRankPresenter(this);
    }

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_group_member_rank;
    }
}
