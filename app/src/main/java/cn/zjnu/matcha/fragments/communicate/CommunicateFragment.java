package cn.zjnu.matcha.fragments.communicate;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupIDListCallback;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.BaseFragment;
import cn.zjnu.matcha.core.app.Matcha;
import cn.zjnu.matcha.factory.model.jiguang.ResponseCodes;
import cn.zjnu.matcha.widget.adapter.communicate.GroupsAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommunicateFragment extends BaseFragment {

    private final List<GroupInfo> groupList = new ArrayList<>();

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_communicate;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        JMessageClient.getGroupIDList(new GetGroupIDListCallback() {
            @Override
            public void gotResult(int i, String s, List<Long> list) {
                if (i == ResponseCodes.SUCCESSFUL) {
                    for (Long groupId : list) {
                        JMessageClient.getGroupInfo(groupId, new GetGroupInfoCallback() {
                            @Override
                            public void gotResult(int i, String s, GroupInfo groupInfo) {
                                if (i == ResponseCodes.SUCCESSFUL) {
                                    groupList.add(groupInfo);
                                } else {
                                    Matcha.showToast("error");
                                }
                            }
                        });
                    }
                } else {
                    Matcha.showToast("error");
                }
            }
        });
        GroupsAdapter adapter = new GroupsAdapter(R.layout.item_grouplist, groupList);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
    }

    public static CommunicateFragment newInstance(Bundle bundle) {
        CommunicateFragment fragment = new CommunicateFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
