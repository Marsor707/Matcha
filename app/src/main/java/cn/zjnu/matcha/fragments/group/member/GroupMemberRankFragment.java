package cn.zjnu.matcha.fragments.group.member;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

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

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private GroupMemberRankAdapter mAdapter;
    List<MemberInfo> mMemberInfos;
    private long mGroupId;
    private Map<String, Integer> mMsgCount = new HashMap<>();

    public static GroupMemberRankFragment newInstance(Bundle bundle) {
        GroupMemberRankFragment fragment = new GroupMemberRankFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_group_member_rank;
    }

    @Override
    protected void initArgs(Bundle bundle) {
        super.initArgs(bundle);
        mGroupId = bundle.getLong("group_id");
    }

    @Override
    public void getConversation(Conversation conversation) {
//        List<Message> mMsgList = conversation.getAllMessage();
//        for (MemberInfo mInfo : mMemberInfos) {
//            mMsgCount.put(mInfo.getUsername(), 0);
//        }
//        for (Message msg : mMsgList) {
//            UserInfo userInfo = msg.getFromUser();
//            int count = mMsgCount.get(userInfo.getUserName());
//            if (count != null) {
//                mMsgCount.put(userInfo.getUserName(), ++count);
//            }
//        }
    }

    @Override
    public void getMemberInfosSuccess(String response) {
//        mMemberInfos = JSONArray.parseArray(response, MemberInfo.class);
//        mPresenter.fetchConversation(mGroupId);
    }

    @Override
    protected MemberRankContract.Presenter initPresenter() {
        return new MemberRankPresenter(this);
    }
}
