package cn.zjnu.matcha.fragments.group.member;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.PresenterFragment;
import cn.zjnu.matcha.factory.model.group.member.MemberInfo;
import cn.zjnu.matcha.factory.mvp.group.MemberDefaultContract;
import cn.zjnu.matcha.factory.mvp.group.MemberDefaultPresenter;
import cn.zjnu.matcha.fragments.group.member.adapter.GroupMemberDefaultAdapter;

/**
 * Created by fsh on 2017/10/22.
 */

public class GroupMemberDefaultFragment extends PresenterFragment<MemberDefaultContract.Presenter> implements MemberDefaultContract.View {

    private List<MemberInfo> mMemberInfos;
    private GroupMemberDefaultAdapter mAdapter;
    private long mGroupId;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public static GroupMemberDefaultFragment newInstance(Bundle bundle) {
        GroupMemberDefaultFragment fragment = new GroupMemberDefaultFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_group_member_default;
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
    public void getMemberInfosSuccess(String response) {
        mMemberInfos = JSONObject.parseArray(response, MemberInfo.class);
        mAdapter = new GroupMemberDefaultAdapter(getContext(), mMemberInfos);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected MemberDefaultContract.Presenter initPresenter() {
        return new MemberDefaultPresenter(this);
    }
}
