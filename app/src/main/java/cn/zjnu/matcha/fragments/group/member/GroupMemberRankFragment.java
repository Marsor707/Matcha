package cn.zjnu.matcha.fragments.group.member;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import cn.jpush.im.android.api.content.MessageContent;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.PresenterFragment;
import cn.zjnu.matcha.factory.model.group.member.MemberInfo;
import cn.zjnu.matcha.factory.model.group.member.MemberRankInfo;
import cn.zjnu.matcha.factory.mvp.group.rank.MemberRankContract;
import cn.zjnu.matcha.factory.mvp.group.rank.MemberRankPresenter;
import cn.zjnu.matcha.fragments.group.member.adapter.MemberRankAdapter;

/**
 * Created by fsh on 2017/10/22.
 */

public class GroupMemberRankFragment extends PresenterFragment<MemberRankContract.Presenter> implements MemberRankContract.View {

    private List<MemberInfo> mMemberInfos;
    private MemberRankAdapter mAdapter;
    private HashMap<String, List<MessageContent>> messageMap = new HashMap<>();
    private List<MemberRankInfo> memberRankInfoList = new ArrayList<>();
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
        if (conversation != null) {
            final List<Message> messageList = conversation.getAllMessage();
            for (Message message : messageList) {
                if (message.getContentType() == ContentType.text) {
                    final String userName = message.getFromUser().getUserName();
                    if (isInGroup(userName)) {
                        final MessageContent messageContent = message.getContent();
                        messageMap.get(userName).add(messageContent);
                        messageMap.put(userName, messageMap.get(userName));
                    }
                }
            }
        }
        for (MemberInfo memberInfo : mMemberInfos) {
            final String userName = memberInfo.getUsername();
            final int messageSize = messageMap.get(userName).size();
            MemberRankInfo info = new MemberRankInfo(userName, messageSize);
            memberRankInfoList.add(info);
        }
        sort(memberRankInfoList);
        mAdapter = new MemberRankAdapter(R.layout.item_group_member_list, memberRankInfoList);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(manager);
    }

    @Override
    public void getMemberInfosSuccess(String response) {
        mMemberInfos = JSONObject.parseArray(response, MemberInfo.class);
        for (MemberInfo memberInfo : mMemberInfos) {
            final List<MessageContent> messageContents = new ArrayList<>();
            messageMap.put(memberInfo.getUsername(), messageContents);
        }
        mPresenter.fetchConversation(mGroupId);
    }

    @Override
    protected MemberRankContract.Presenter initPresenter() {
        return new MemberRankPresenter(this);
    }

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_group_member_rank;
    }

    private boolean isInGroup(String userName) {
        for (MemberInfo memberInfo : mMemberInfos) {
            if (memberInfo.getUsername().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    private void sort(List<MemberRankInfo> list) {
        Collections.sort(list, new Comparator<MemberRankInfo>() {
            @Override
            public int compare(MemberRankInfo o1, MemberRankInfo o2) {
                return o1.compareTo(o2);
            }
        });
    }
}
