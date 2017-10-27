package cn.zjnu.matcha.fragments.group.member;


import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.PresenterFragment;
import cn.zjnu.matcha.factory.model.group.member.MemberInfo;
import cn.zjnu.matcha.factory.mvp.group.MemberDefaultContract;

/**
 * Created by fsh on 2017/10/22.
 */

public class GroupMemberDefaultFragment extends PresenterFragment<MemberDefaultContract.Presenter> implements MemberDefaultContract.View {

    private List<MemberInfo> mMemberInfos;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_group_member_default;
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    public void getMemberInfosSuccess(String response) {

    }

    @Override
    public void getMemberAvatarSuccess(String response) {

    }

    @Override
    protected MemberDefaultContract.Presenter initPresenter() {
        return null;
    }
}
